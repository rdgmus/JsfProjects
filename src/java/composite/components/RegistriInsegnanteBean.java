/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import jpa.entities.AnniScolastici;
import jpa.entities.Classi;
import jpa.entities.Insegnanti;
import jpa.entities.Materie;
import jpa.entities.PeriodiAnnoScolastico;
import jpa.entities.RegistriInsegnanti;
import jpa.session.LezioniMateriaFacade;
import jpa.session.MaterieFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.PeriodiAnnoScolasticoFacade;
import jpa.session.RegistriInsegnantiFacade;
import jpa.session.StudentiFacade;
import jpa.session.UtentiLoggerFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "registriInsegnanteBean")
@SessionScoped
public class RegistriInsegnanteBean implements ValueChangeListener, Serializable {

    private Insegnanti insegnante;
    private AnniScolastici annoScolastico;
    private List<PeriodiAnnoScolastico> periodiAnnoScolastico;
    @EJB
    RegistriInsegnantiFacade registriInsegnantiFacade;
    @EJB
    MaterieFacade materieFacade;
    @EJB
    StudentiFacade studentiFacade;
    private DataModel registriInsegnanteItems = null;
    private int itemsCount;
    private Materie materiaSelezionata;
    private Classi classeSelezionata;
    private DataModel studentiItems;
    @EJB
    LezioniMateriaFacade lezioniMateriaFacade;
    @EJB
    PeriodiAnnoScolasticoFacade periodiAnnoScolasticoFacade;
    @EJB
    OreAssenzeFacade oreAssenzeFacade;
    private boolean renderRegistro = true;
    private boolean renderRiepilogo = false;
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;

    public RegistriInsegnanteBean() {
        getParametersForRegistri();
    }

    public OreAssenzeFacade getOreAssenzeFacade() {
        return oreAssenzeFacade;
    }

    public void setOreAssenzeFacade(OreAssenzeFacade oreAssenzeFacade) {
        this.oreAssenzeFacade = oreAssenzeFacade;
    }

    public LezioniMateriaFacade getLezioniMateriaFacade() {
        return lezioniMateriaFacade;
    }

    public PeriodiAnnoScolasticoFacade getPeriodiAnnoScolasticoFacade() {
        return periodiAnnoScolasticoFacade;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public DataModel getStudentiItems() {
        return studentiItems;
    }

    public void setStudentiItems(DataModel studentiItems) {
        this.studentiItems = studentiItems;
        JsfUtil.setSessionMapValue("studentiItems", studentiItems);
    }

    public Materie getMateriaSelezionata() {
        return materiaSelezionata;
    }

    public void setMateriaSelezionata(Materie materiaSelezionata) {
        this.materiaSelezionata = materiaSelezionata;
        JsfUtil.setSessionMapValue("materiaSelected", materiaSelezionata);
    }

    public Classi getClasseSelezionata() {
        return classeSelezionata;
    }

    public void setClasseSelezionata(Classi classeSelezionata) {
        this.classeSelezionata = classeSelezionata;
    }

    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public StudentiFacade getStudentiFacade() {
        return studentiFacade;
    }

    public RegistriInsegnantiFacade getRegistriInsegnantiFacade() {
        return registriInsegnantiFacade;
    }

    public DataModel getRegistriInsegnanteItems() {
        return registriInsegnanteItems;
    }

    public void setRegistriInsegnanteItems(DataModel registriInsegnanteItems) {
        this.registriInsegnanteItems = registriInsegnanteItems;
    }

    public AnniScolastici getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(AnniScolastici annoScolastico) {
        this.annoScolastico = annoScolastico;
    }

    public List<PeriodiAnnoScolastico> getPeriodiAnnoScolastico() {
        return periodiAnnoScolastico;
    }

    public void setPeriodiAnnoScolastico(List<PeriodiAnnoScolastico> periodiAnnoScolastico) {
        this.periodiAnnoScolastico = periodiAnnoScolastico;
        setPeriodiAnnoScolastico();
    }

    public Insegnanti getInsegnante() {
        //TAKE INSEGNANTE DALLA SESSION , INSEGNANTE SELEZIONATO IN ScuolaStructureBean
        return insegnante;
    }

    public void setInsegnante(Insegnanti insegnante) {
        this.insegnante = insegnante;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void getParametersForRegistri() {
        ScuolaStructureBean scuolaStructureBean = (ScuolaStructureBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("scuolaStructureBean");

        // This only works if myBean2 is request scoped and already created.
        if (scuolaStructureBean != null) {
            insegnante = scuolaStructureBean.getSelectedInsegnante();
            annoScolastico = scuolaStructureBean.getSelectedAS();
            periodiAnnoScolastico = scuolaStructureBean.getPeriodiAnnoScolastico();
        }
    }

    public String prepareView() {
//        List<RegistriInsegnanti> list = getRegistriInsegnantiFacade().findAll();
        registriInsegnanteItems = new ListDataModel(getRegistriInsegnantiFacade().findByInsegnanteAnnoScolastico(annoScolastico, insegnante));
        setItemsCount(registriInsegnanteItems.getRowCount());
        return "/registro/ScegliRegistroScolastico";
    }

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }

    public String openRegistro(RegistriInsegnanti item) {
        setMateriaSelezionata(getMaterieFacade().findByIdMateria(item.getIdMateria()));
        setClasseSelezionata(getMateriaSelezionata().getIdClasse());
        setStudentiItems(new ListDataModel(getStudentiFacade().retrieveStudentiClasseOrderedList(classeSelezionata)));
        setAnnoScolastico(materiaSelezionata.getIdAnnoScolastico());
        setPeriodiAnnoScolastico();

        AllievoLezioneBean controller = (AllievoLezioneBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("allievoLezioneBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setRow(0);
            controller.setRenderGestioneLezioni(true);
            controller.setRenderGestioneVoti(false);
        }
        RisultatiPeriodoBean risultatiController = (RisultatiPeriodoBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("risultatiPeriodoBean");

        // This only works if myBean2 is request scoped and already created.
        if (risultatiController != null) {
            risultatiController.setRow(0);
        }

        String msg = ResourceBundle.getBundle("/resources/Registro").getString("RegistroApertoMsgString")
                + " Materia:" + getMateriaSelezionata().getMateria()
                + " Classe:" + getClasseSelezionata().getNomeClasse()
                + " A.S.:" + getAnnoScolastico().getAnnoScolastico()
                + " del prof. " + getInsegnante().getCognome() + " " + getInsegnante().getNome();
        JsfUtil.addSuccessMessage(msg);
        JsfUtil.registraEventoNelDatabase(msg,
                ResourceBundle.getBundle("/resources/Registro").getString("RegistroApertoTypeString"),
                getUtentiLoggerFacade());

        return "/registro/RegistroScolastico";
    }

    public void closeRegistro() {
        String msg = ResourceBundle.getBundle("/resources/Registro").getString("RegistroChiusoMsgString")
                + " Materia:" + getMateriaSelezionata().getMateria()
                + " Classe:" + getClasseSelezionata().getNomeClasse()
                + " A.S.:" + getAnnoScolastico().getAnnoScolastico()
                + " del prof. " + getInsegnante().getCognome() + " " + getInsegnante().getNome();
        JsfUtil.addSuccessMessage(msg);
        JsfUtil.registraEventoNelDatabase(msg,
                ResourceBundle.getBundle("/resources/Registro").getString("RegistroChiusoTypeString"),
                getUtentiLoggerFacade());

        setMateriaSelezionata(null);
    }

    private void setPeriodiAnnoScolastico() {
        LezioniMateriaBean controller = (LezioniMateriaBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("lezioniMateriaBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setPeriodiAnnoScolastico(periodiAnnoScolastico);
            controller.setAnnoScolasticoSelected(annoScolastico);
        }
    }

    public boolean isRenderRegistro() {
        return renderRegistro;
    }

    public void setRenderRegistro(boolean renderRegistro) {
        setRenderRiepilogo(!renderRegistro);
        this.renderRegistro = renderRegistro;
    }

    public boolean isRenderRiepilogo() {
        return renderRiepilogo;
    }

    public void setRenderRiepilogo(boolean renderRiepilogo) {
        renderRegistro = (!renderRiepilogo);
        this.renderRiepilogo = renderRiepilogo;
//
//        RisultatiPeriodoBean controller = (RisultatiPeriodoBean) FacesContext.getCurrentInstance().getExternalContext()
//                .getSessionMap().get("risultatiPeriodoBean");
//
//        // This only works if myBean2 is request scoped and already created.
//        if (controller != null) {
//            controller.initRecords();
//        }

    }
}
