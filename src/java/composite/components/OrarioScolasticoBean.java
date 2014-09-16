/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.awt.Color;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import jpa.entities.AnniScolastici;
import jpa.entities.ScansioneOrarioAs;
import jpa.entities.Scuole;
import jpa.session.AnniScolasticiFacade;
import jpa.session.ParametriOrarioAsFacade;
import jpa.session.ScansioneOrarioAsFacade;
import jpa.session.ScuoleFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.UITabPanel;
import org.richfaces.event.ItemChangeEvent;
import org.richfaces.event.ItemChangeListener;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "orarioScolasticoBean")
@SessionScoped
public class OrarioScolasticoBean implements Serializable, ValueChangeListener, ActionListener, ItemChangeListener {

    private Scuole selectedScuola;
    private AnniScolastici selectedAS;
    private List<Scuole> scuole;
    private List<AnniScolastici> as;
    @EJB
    ScuoleFacade scuoleFacade;
    @EJB
    AnniScolasticiFacade asFacade;
    @EJB
    ScansioneOrarioAsFacade scansioneOrarioAsFacade;
    private List<ScansioneOrarioAs> righeOrarioLunedi;
    private List<ScansioneOrarioAs> righeOrarioMartedi;
    private List<ScansioneOrarioAs> righeOrarioMercoledi;
    private List<ScansioneOrarioAs> righeOrarioGiovedi;
    private List<ScansioneOrarioAs> righeOrarioVenerdi;
    private List<ScansioneOrarioAs> righeOrarioSabato;
    private boolean editLun, editMar, editMer, editGio, editVen, editSab;
    private boolean renderEditLun = true;
    private boolean renderEditMar = true;
    private boolean renderEditMer = true;
    private boolean renderEditGio = true;
    private boolean renderEditVen = true;
    private boolean renderEditSab = true;
    private ScansioneOrarioAs selectedOrario;
    private List<String> giorniOrario;
    private String selectedGiorno;
    private String selectedOrarioDelGiorno;
    private Integer durataOra, durataIntervallo;
    @EJB
    ParametriOrarioAsFacade parametriOrarioAsFacade;
    private Date inizioLezioni;

    public OrarioScolasticoBean() {
    }

    public String backToScegliRegistro() {
        setEditLun(false);
        setEditMar(false);
        setEditMer(false);
        setEditGio(false);
        setEditVen(false);
        setEditSab(false);
        renderOtherEdit();
        return "/registro/ScegliRegistroScolastico";
    }

    public String orarioScolastico() {
        return "/registro/ImpostaOrarioScolastico";
    }

    public String coloriMaterie() {
        return "/registro/ImpostaColoriMaterie";
    }

    public String scuolaWizard() {
        GestioneScuolaWizardBean controller = (GestioneScuolaWizardBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("gestioneScuolaWizardBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setPage(1);
        }

        return "/wizards/GestioneScuolaWizard";
    }

    public String grantWizard() {
        PermessiRegistriWizardBean controller = (PermessiRegistriWizardBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("permessiRegistriWizardBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setPage(1);
        }

        return "/wizards/PermessiRegistriWizard";
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        //        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        if (source instanceof HtmlInputText) {
            HtmlInputText text = (HtmlInputText) source;
            if (text.getId().equals("iniziaOre")) {
                String nuovoOrario = String.valueOf(event.getNewValue());
                List<UIComponent> childrens = text.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("idScansione")) {
                            Integer idScansione = Integer.valueOf(String.valueOf(value));
                            changeOraInizio(idScansione, event.getNewValue());
                            initRecordsOrario();
                        }
                    }
                }
            }
            if (text.getId().equals("finisceOre")) {
                String nuovoOrario = String.valueOf(event.getNewValue());
                List<UIComponent> childrens = text.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("idScansione")) {
                            Integer idScansione = Integer.valueOf(String.valueOf(value));
                            changeOraFine(idScansione, event.getNewValue());
                            initRecordsOrario();
                        }
                    }
                }
            }
        }
        if (source instanceof HtmlSelectOneMenu) {
            HtmlSelectOneMenu menu = (HtmlSelectOneMenu) event.getSource();
            if (menu.getId().equals("selectedScuola")) {
                setSelectedScuola((Scuole) event.getNewValue());
            }
            if (menu.getId().equals("selectedAS")) {
                setSelectedAS((AnniScolastici) event.getNewValue());
            }
            if (menu.getId().equals("selectedOrario")) {
                setSelectedOrario((ScansioneOrarioAs) event.getNewValue());
            }
            if (menu.getId().equals("giorniOrario")) {
                setSelectedOrarioDelGiorno(String.valueOf(event.getNewValue()));
            }
        }
        if (source instanceof HtmlSelectBooleanCheckbox) {
            HtmlSelectBooleanCheckbox cb = (HtmlSelectBooleanCheckbox) source;
            if (cb.getId().equals("Lunedì")) {
                setEditLun(Boolean.valueOf(String.valueOf(event.getNewValue())));
                if (!Boolean.parseBoolean(String.valueOf(event.getNewValue()))) {
                    initRecordsOrario();
                    setSelectedOrario(null);
                    renderOtherEdit();
                } else {
                    unrenderOtherEditThan(cb.getId());
                }
            }
            if (cb.getId().equals("Martedì")) {
                setEditMar(Boolean.valueOf(String.valueOf(event.getNewValue())));
                if (!Boolean.valueOf(String.valueOf(event.getNewValue()))) {
                    initRecordsOrario();
                    setSelectedOrario(null);
                    renderOtherEdit();
                } else {
                    unrenderOtherEditThan(cb.getId());
                }
            }
            if (cb.getId().equals("Mercoledì")) {
                setEditMer(Boolean.valueOf(String.valueOf(event.getNewValue())));
                if (!Boolean.valueOf(String.valueOf(event.getNewValue()))) {
                    initRecordsOrario();
                    setSelectedOrario(null);
                    renderOtherEdit();
                } else {
                    unrenderOtherEditThan(cb.getId());
                }
            }
            if (cb.getId().equals("Giovedì")) {
                setEditGio(Boolean.valueOf(String.valueOf(event.getNewValue())));
                if (!Boolean.valueOf(String.valueOf(event.getNewValue()))) {
                    initRecordsOrario();
                    setSelectedOrario(null);
                    renderOtherEdit();
                } else {
                    unrenderOtherEditThan(cb.getId());
                }
            }
            if (cb.getId().equals("Venerdì")) {
                setEditVen(Boolean.valueOf(String.valueOf(event.getNewValue())));
                if (!Boolean.valueOf(String.valueOf(event.getNewValue()))) {
                    initRecordsOrario();
                    setSelectedOrario(null);
                    renderOtherEdit();
                } else {
                    unrenderOtherEditThan(cb.getId());
                }
            }
            if (cb.getId().equals("Sabato")) {
                setEditSab(Boolean.valueOf(String.valueOf(event.getNewValue())));
                if (!Boolean.valueOf(String.valueOf(event.getNewValue()))) {
                    initRecordsOrario();
                    setSelectedOrario(null);
                    renderOtherEdit();
                } else {
                    unrenderOtherEditThan(cb.getId());
                }
            }
            if (cb.getId().equals("selectAttivita")) {
                List<UIComponent> childrens = cb.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("rigaOrario")) {
                            Integer idScansione = Integer.valueOf(String.valueOf(value));
                            setTypeAttivita(idScansione, event.getNewValue());
                            initRecordsOrario();
                        }
                    }
                }

            }
        }
    }

    public Scuole getSelectedScuola() {
        if (selectedScuola == null) {
            scuole = retrieveScuole();
            if (scuole.size() > 0) {
                setSelectedScuola(scuole.get(0));
            }
        } else if (selectedScuolaNotIn(scuole)) {
            setSelectedScuola(scuole.get(0));
        }
        return selectedScuola;
    }

    public void setSelectedScuola(Scuole selectedScuola) {
        this.selectedScuola = selectedScuola;
    }

    public AnniScolastici getSelectedAS() {
        if (selectedAS == null) {
            as = retrieveAS();
            if (as.size() > 0) {
                setSelectedAS(as.get(0));
            }
        } else if (selectedAnnoScolasticoNotIn(as)) {
            setSelectedAS(as.get(0));
        }
        return selectedAS;
    }

    public void setSelectedAS(AnniScolastici selectedAS) {
        this.selectedAS = selectedAS;
        selectedOrario = null;
        durataOra = null;
        durataIntervallo = null;
        inizioLezioni = null;
        initRecordsOrario();
    }

    public ScuoleFacade getScuoleFacade() {
        return scuoleFacade;
    }

    public AnniScolasticiFacade getAsFacade() {
        return asFacade;
    }

    /*
     * SCUOLE
     */
    public List<Scuole> retrieveScuole() {
        scuole = getScuoleFacade().findAll();
        return scuole;
    }

    public int scuoleSize() {
        return scuole.size();
    }

    public List<Scuole> getScuole() {
        if (scuole == null) {
            retrieveScuole();
        }
        return scuole;
    }

    public void setScuole(List<Scuole> scuole) {
        this.scuole = scuole;
    }

//    @EJB 
//    ScuolaStructureBean scuolaStructureBean;
//    @Resource(name = "scuolaStructureBean")
//    private ScuolaStructureBean scuolaStructureBean;

//    public ScuolaStructureBean getScuolaStructureBean() {
//
//        return (ScuolaStructureBean)JsfUtil.getSessionMapValue("scuolaStructureBean");
//    }

    public List<AnniScolastici> retrieveAS() {
        as = getAsFacade().retrieveAnniScolasticiScuolaOrderedList(selectedScuola);
//        as = getScuolaStructureBean().getAs();
        return as;
    }

    public List<AnniScolastici> getAs() {
        if (as == null) {
            retrieveAS();
        }
        return as;
    }

    public void setAs(List<AnniScolastici> as) {
        this.as = as;
    }

    private boolean selectedScuolaNotIn(List<Scuole> scuole) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (scuoleSize() == 0) {
            selectedScuola = null;
            return false;
        }
        for (Scuole c : scuole) {
            if (c.equals(selectedScuola)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    private boolean selectedAnnoScolasticoNotIn(List<AnniScolastici> as) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (anniScolasticiSize() == 0) {
            selectedAS = null;
            return false;
        }
        for (AnniScolastici c : as) {
            if (c.equals(selectedAS)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    public int anniScolasticiSize() {
        return as.size();
    }

    public ScansioneOrarioAsFacade getScansioneOrarioAsFacade() {
        return scansioneOrarioAsFacade;
    }

    private void initRecordsOrario() {
        //        throw new UnsupportedOperationException("Not yet implemented");
        righeOrarioLunedi = getScansioneOrarioAsFacade().findOrario(selectedAS, "Lunedì");
        righeOrarioMartedi = getScansioneOrarioAsFacade().findOrario(selectedAS, "Martedì");
        righeOrarioMercoledi = getScansioneOrarioAsFacade().findOrario(selectedAS, "Mercoledì");
        righeOrarioGiovedi = getScansioneOrarioAsFacade().findOrario(selectedAS, "Giovedì");
        righeOrarioVenerdi = getScansioneOrarioAsFacade().findOrario(selectedAS, "Venerdì");
        righeOrarioSabato = getScansioneOrarioAsFacade().findOrario(selectedAS, "Sabato");
    }

    public List<ScansioneOrarioAs> getRigheOrarioLunedi() {
        if (righeOrarioLunedi == null) {
            initRecordsOrario();
            if (righeOrarioLunedi.size() > 0) {
                setSelectedOrario(righeOrarioLunedi.get(0));
            }
        }
        return righeOrarioLunedi;
    }

    public void setRigheOrarioLunedi(List<ScansioneOrarioAs> righeOrarioLunedi) {
        this.righeOrarioLunedi = righeOrarioLunedi;
    }

    public List<ScansioneOrarioAs> getRigheOrarioMartedi() {
        if (righeOrarioMartedi == null) {
            initRecordsOrario();
        }
        return righeOrarioMartedi;
    }

    public void setRigheOrarioMartedi(List<ScansioneOrarioAs> righeOrarioMartedi) {
        this.righeOrarioMartedi = righeOrarioMartedi;
    }

    public List<ScansioneOrarioAs> getRigheOrarioMercoledi() {
        if (righeOrarioMercoledi == null) {
            initRecordsOrario();
        }
        return righeOrarioMercoledi;
    }

    public void setRigheOrarioMercoledi(List<ScansioneOrarioAs> righeOrarioMercoledi) {
        this.righeOrarioMercoledi = righeOrarioMercoledi;
    }

    public List<ScansioneOrarioAs> getRigheOrarioGiovedi() {
        if (righeOrarioGiovedi == null) {
            initRecordsOrario();
        }
        return righeOrarioGiovedi;
    }

    public void setRigheOrarioGiovedi(List<ScansioneOrarioAs> righeOrarioGiovedi) {
        this.righeOrarioGiovedi = righeOrarioGiovedi;
    }

    public List<ScansioneOrarioAs> getRigheOrarioVenerdi() {
        if (righeOrarioVenerdi == null) {
            initRecordsOrario();
        }
        return righeOrarioVenerdi;
    }

    public void setRigheOrarioVenerdi(List<ScansioneOrarioAs> righeOrarioVenerdi) {
        this.righeOrarioVenerdi = righeOrarioVenerdi;
    }

    public List<ScansioneOrarioAs> getRigheOrarioSabato() {
        if (righeOrarioSabato == null) {
            initRecordsOrario();
        }
        return righeOrarioSabato;
    }

    public void setRigheOrarioSabato(List<ScansioneOrarioAs> righeOrarioSabato) {
        this.righeOrarioSabato = righeOrarioSabato;
    }

    public boolean isEditLun() {
        return editLun;
    }

    public void setEditLun(boolean editLun) {
        this.editLun = editLun;
    }

    public boolean isEditMar() {
        return editMar;
    }

    public void setEditMar(boolean editMar) {
        this.editMar = editMar;
    }

    public boolean isEditMer() {
        return editMer;
    }

    public void setEditMer(boolean editMer) {
        this.editMer = editMer;
    }

    public boolean isEditGio() {
        return editGio;
    }

    public void setEditGio(boolean editGio) {
        this.editGio = editGio;
    }

    public boolean isEditVen() {
        return editVen;
    }

    public void setEditVen(boolean editVen) {
        this.editVen = editVen;
    }

    public boolean isEditSab() {
        return editSab;
    }

    public void setEditSab(boolean editSab) {
        this.editSab = editSab;
    }

    public ScansioneOrarioAs getSelectedOrario() {
        return selectedOrario;
    }

    public void setSelectedOrario(ScansioneOrarioAs selectedOrario) {
        this.selectedOrario = selectedOrario;
    }

    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        //        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        if (source instanceof HtmlCommandButton) {
            HtmlCommandButton cb = (HtmlCommandButton) source;
            if (cb.getId().equals("aggiungiOra")) {
                List<UIComponent> childrens = cb.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("giorno")) {
                            String giorno = String.valueOf(value);
                            aggiungiOra(giorno);
                        }
                    }
                }
            }
            if (cb.getId().equals("aggiungiIntervallo")) {
                List<UIComponent> childrens = cb.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("giorno")) {
                            String giorno = String.valueOf(value);
                            aggiungiIntervallo(giorno);
                        }
                    }
                }
            }
            if (cb.getId().equals("cancellaOra")) {
                List<UIComponent> childrens = cb.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("giorno")) {
                            String giorno = String.valueOf(value);
                            cancellaOra(giorno);
                        }
                    }
                }
            }
            if (cb.getId().equals("copiaOrario")) {
                List<UIComponent> childrens = cb.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("giorno")) {
                            String giorno = String.valueOf(value);
                            copiaOrarioGiornoSelezionatoIn(giorno);
                        }
                    }
                }
            }
            if (cb.getId().equals("cancellaOrario")) {
                List<UIComponent> childrens = cb.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("giorno")) {
                            String giorno = String.valueOf(value);
                            cancellaOrario(giorno);
                        }
                    }
                }
            }
        }
    }

    public List<String> getGiorniOrario() {
        giorniOrario = getScansioneOrarioAsFacade().giorniOrario(selectedAS);
        if (selectedGiorno == null && giorniOrario.size() > 0) {
            selectedOrarioDelGiorno = giorniOrario.get(0);
        }
        return giorniOrario;
    }

    public void setGiorniOrario(List<String> giorniOrario) {
        this.giorniOrario = giorniOrario;
    }

    public String getSelectedGiorno() {
        return selectedGiorno;
    }

    public void setSelectedGiorno(String selectedGiorno) {
        this.selectedGiorno = selectedGiorno;
    }

    private void aggiungiOra(String giorno) {
//        throw new UnsupportedOperationException("Not yet implemented");
        getScansioneOrarioAsFacade().aggiungiOra(selectedAS, giorno, getDurataOra(), getInizioLezioni());
        initRecordsOrario();
        JsfUtil.addSuccessMessage("Aggiunta ora al giorno: \"" + giorno
                + "\" dell'anno scolastico:" + selectedAS.getAnnoScolastico());
    }

    private void aggiungiIntervallo(String giorno) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = getScansioneOrarioAsFacade().aggiungiIntervallo(selectedAS, giorno, getDurataIntervallo());
        initRecordsOrario();
        if (esito) {
            JsfUtil.addSuccessMessage("Aggiunto intervallo al giorno: \"" + giorno
                    + "\" dell'anno scolastico:" + selectedAS.getAnnoScolastico());
        } else {
            JsfUtil.addErrorMessage("L'intervallo non è stato aggiunto al giorno: \"" + giorno
                    + "\" dell'anno scolastico:" + selectedAS.getAnnoScolastico());
        }
    }

    private void cancellaOra(String giorno) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (selectedOrario.getGiornoSettimana().equals(giorno)) {
            getScansioneOrarioAsFacade().remove(selectedOrario);
            getScansioneOrarioAsFacade().enumOreLezione(selectedAS, selectedGiorno);
            initRecordsOrario();
            JsfUtil.addSuccessMessage("Cancellata ora: \"" + getSelectedOrario().toString() + "\" nel giorno: \"" + giorno + "\"");
        } else {
            JsfUtil.addErrorMessage("Non è stato possibile cancellare l'ora:" + selectedOrario.toString());
        }
    }

    private void copiaOrarioGiornoSelezionatoIn(String giorno) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (getSelectedOrarioDelGiorno().equals(giorno)) {
            JsfUtil.addErrorMessage("Inutile copiare orario del giorno: \"" + getSelectedOrarioDelGiorno() + "\" nel giorno: \"" + giorno + "\"");
        } else {
            getScansioneOrarioAsFacade().copiaOrario(selectedAS, getSelectedOrarioDelGiorno(), getSelectedGiorno());
            initRecordsOrario();
            JsfUtil.addSuccessMessage("Copiato orario del giorno: \"" + getSelectedOrarioDelGiorno() + "\" nel giorno: \"" + giorno + "\"");
        }
    }

    public boolean isRenderEditLun() {
        return renderEditLun;
    }

    public void setRenderEditLun(boolean renderEditLun) {
        this.renderEditLun = renderEditLun;
    }

    public boolean isRenderEditMar() {
        return renderEditMar;
    }

    public void setRenderEditMar(boolean renderEditMar) {
        this.renderEditMar = renderEditMar;
    }

    public boolean isRenderEditMer() {
        return renderEditMer;
    }

    public void setRenderEditMer(boolean renderEditMer) {
        this.renderEditMer = renderEditMer;
    }

    public boolean isRenderEditGio() {
        return renderEditGio;
    }

    public void setRenderEditGio(boolean renderEditGio) {
        this.renderEditGio = renderEditGio;
    }

    public boolean isRenderEditVen() {
        return renderEditVen;
    }

    public void setRenderEditVen(boolean renderEditVen) {
        this.renderEditVen = renderEditVen;
    }

    public boolean isRenderEditSab() {
        return renderEditSab;
    }

    public void setRenderEditSab(boolean renderEditSab) {
        this.renderEditSab = renderEditSab;
    }

    private void unrenderOtherEditThan(String id) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (id.equals("Lunedì")) {
            setRenderEditMar(false);
            setRenderEditMer(false);
            setRenderEditGio(false);
            setRenderEditVen(false);
            setRenderEditSab(false);
        }
        if (id.equals("Martedì")) {
            setRenderEditLun(false);
            setRenderEditMer(false);
            setRenderEditGio(false);
            setRenderEditVen(false);
            setRenderEditSab(false);
        }
        if (id.equals("Mercoledì")) {
            setRenderEditMar(false);
            setRenderEditLun(false);
            setRenderEditGio(false);
            setRenderEditVen(false);
            setRenderEditSab(false);
        }
        if (id.equals("Giovedì")) {
            setRenderEditMar(false);
            setRenderEditMer(false);
            setRenderEditLun(false);
            setRenderEditVen(false);
            setRenderEditSab(false);
        }
        if (id.equals("Venerdì")) {
            setRenderEditMar(false);
            setRenderEditMer(false);
            setRenderEditGio(false);
            setRenderEditLun(false);
            setRenderEditSab(false);
        }
        if (id.equals("Sabato")) {
            setRenderEditMar(false);
            setRenderEditMer(false);
            setRenderEditGio(false);
            setRenderEditVen(false);
            setRenderEditLun(false);
        }
        setSelectedGiorno(id);
    }

    private void renderOtherEdit() {
//        throw new UnsupportedOperationException("Not yet implemented");
        setRenderEditLun(true);
        setRenderEditMar(true);
        setRenderEditMer(true);
        setRenderEditGio(true);
        setRenderEditVen(true);
        setRenderEditSab(true);

    }

    public String getSelectedOrarioDelGiorno() {
        return selectedOrarioDelGiorno;
    }

    public void setSelectedOrarioDelGiorno(String selectedOrarioDelGiorno) {
        this.selectedOrarioDelGiorno = selectedOrarioDelGiorno;
    }

    private void setTypeAttivita(Integer idScansione, Object value) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Boolean boolValue = Boolean.valueOf(String.valueOf(value));
        getScansioneOrarioAsFacade().updateTipoAttivita(idScansione.longValue(), boolValue);
        getScansioneOrarioAsFacade().enumOreLezione(selectedAS, selectedGiorno);
        ScansioneOrarioAs nuovaRigaOrario = getScansioneOrarioAsFacade().find(idScansione.longValue());
        JsfUtil.addSuccessMessage("Cambiata l'attività svolta nell'ora: " + nuovaRigaOrario.toString());
    }

    private void changeOraInizio(Integer idScansione, Object newValue) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        ScansioneOrarioAs rigaOrario = getScansioneOrarioAsFacade().find(idScansione.longValue());
        getScansioneOrarioAsFacade().updateInizioOrario(idScansione.longValue(), Time.valueOf(String.valueOf(newValue)));
        getScansioneOrarioAsFacade().compattaOraPrecedente(selectedAS, selectedGiorno, idScansione.longValue());
        initRecordsOrario();
        ScansioneOrarioAs nuovaRigaOrario = getScansioneOrarioAsFacade().find(idScansione.longValue());
        JsfUtil.addSuccessMessage("Cambiata ora di inizio dell'ora: " + rigaOrario.toString() + " in:" + nuovaRigaOrario.toString());
    }

    private void changeOraFine(Integer idScansione, Object newValue) {
//        throw new UnsupportedOperationException("Not yet implemented");
        ScansioneOrarioAs rigaOrario = getScansioneOrarioAsFacade().find(idScansione.longValue());
        getScansioneOrarioAsFacade().updateFineOrario(idScansione.longValue(), Time.valueOf(String.valueOf(newValue)));
        getScansioneOrarioAsFacade().compattaOraSuccessiva(selectedAS, selectedGiorno, idScansione.longValue());
        initRecordsOrario();
        ScansioneOrarioAs nuovaRigaOrario = getScansioneOrarioAsFacade().find(idScansione.longValue());
        JsfUtil.addSuccessMessage("Cambiata ora finale dell'ora: " + rigaOrario.toString() + " in:" + nuovaRigaOrario.toString());
    }

    public ParametriOrarioAsFacade getParametriOrarioAsFacade() {
        return parametriOrarioAsFacade;
    }

    public Integer getDurataOra() {
        if (durataOra == null) {
            durataOra = getParametriOrarioAsFacade().findDurataOra(selectedAS);
        }
        return durataOra;
    }

    public void setDurataOra(Integer durataOra) {
        this.durataOra = durataOra;
    }

    public Integer getDurataIntervallo() {
        if (durataIntervallo == null) {
            durataIntervallo = getParametriOrarioAsFacade().findDurataIntervallo(selectedAS);
        }
        return durataIntervallo;
    }

    public void setDurataIntervallo(Integer durataIntervallo) {
        this.durataIntervallo = durataIntervallo;
    }

    public Date getInizioLezioni() {
        if (inizioLezioni == null) {
            inizioLezioni = getParametriOrarioAsFacade().findInizioLezioni(selectedAS);
        }
        return inizioLezioni;
    }

    public void setInizioLezioni(Date inizioLezioni) {
        this.inizioLezioni = inizioLezioni;
    }

    private void cancellaOrario(String giorno) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        List<ScansioneOrarioAs> orario = getScansioneOrarioAsFacade().findOrario(selectedAS, giorno);
        for (ScansioneOrarioAs ora : orario) {
            getScansioneOrarioAsFacade().remove(ora);
        }
        initRecordsOrario();
        JsfUtil.addSuccessMessage("Cancellato completamente l'orario del giorno:" + giorno);
    }

    @Override
    public void processItemChange(ItemChangeEvent ice) throws AbortProcessingException {
//        throw new UnsupportedOperationException("Not supported yet.");
        Object source = ice.getSource();
        if (source instanceof UITabPanel) {
            UITabPanel sourcePanel = (UITabPanel) source;//UITabPanel

            String id = sourcePanel.getId();
            String changeOrigin = ice.getNewItemName();

            if (id.equals("orarioScolastico")) {
                JsfUtil.addSuccessMessage(changeOrigin);
            }
        }
    }

    public static List<Color> pick(int num) {
        List<Color> colors = new ArrayList<Color>();
        if (num < 2) {
            return colors;
        }
        float dx = 1.0f / (float) (num - 1);
        for (int i = 0; i < num; i++) {
            colors.add(get(i * dx));
        }
        return colors;
    }

    public static Color get(float x) {
        float r = 0.0f;
        float g = 0.0f;
        float b = 1.0f;
        if (x >= 0.0f && x < 0.2f) {
            x = x / 0.2f;
            r = 0.0f;
            g = x;
            b = 1.0f;
        } else if (x >= 0.2f && x < 0.4f) {
            x = (x - 0.2f) / 0.2f;
            r = 0.0f;
            g = 1.0f;
            b = 1.0f - x;
        } else if (x >= 0.4f && x < 0.6f) {
            x = (x - 0.4f) / 0.2f;
            r = x;
            g = 1.0f;
            b = 0.0f;
        } else if (x >= 0.6f && x < 0.8f) {
            x = (x - 0.6f) / 0.2f;
            r = 1.0f;
            g = 1.0f - x;
            b = 0.0f;
        } else if (x >= 0.8f && x <= 1.0f) {
            x = (x - 0.8f) / 0.2f;
            r = 1.0f;
            g = 0.0f;
            b = x;
        }
        return new Color(r, g, b);
    }
}
