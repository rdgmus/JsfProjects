/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import jpa.entities.AnniScolastici;
import jpa.entities.Classi;
import jpa.entities.Insegnanti;
import jpa.entities.Materie;
import jpa.entities.RuoliGrantedToUtenti;
import jpa.entities.RuoliUtenti;
import jpa.entities.Scuole;
import jpa.entities.UtentiScuola;
import jpa.session.AnniScolasticiFacade;
import jpa.session.ClassiFacade;
import jpa.session.InsegnantiFacade;
import jpa.session.MaterieFacade;
import jpa.session.RuoliGrantedToUtentiFacade;
import jpa.session.RuoliUtentiFacade;
import jpa.session.ScuoleFacade;
import jpa.session.UtentiScuolaFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "permessiRegistriWizardBean")
@SessionScoped
public class PermessiRegistriWizardBean implements Serializable, ValueChangeListener {

    private String tabella;
    private int page = 1;
    private boolean disableCreateModifyDelete = false;//Abilita o disabilita bottoni CREA, MODIFICA, CANCELLA
    private boolean rewind, forward;
    private boolean disableCommitRollback = true;//Abilita o disabilita bottoni COMMIT , ROLLBACK
    private String azioneInCorso = "In attesa...";//Messaggio relativo al'azione in corso nel WIZARD
    private boolean modifyAction = false;//Attivo quando siamo in operazione MODIFICA
    private boolean createAction = false;//Attivo quando siamo in operazione CREA
    private boolean deleteAction = false;//Attivo quando siamo in operazione CANCELLA
    private boolean crudAction = false;

    public PermessiRegistriWizardBean() {
    }
    @EJB
    ScuoleFacade scuoleFacade;
    private List<Scuole> listaScuole;
    private Scuole selectedScuola;

    public ScuoleFacade getScuoleFacade() {
        return scuoleFacade;
    }

    public List<Scuole> getListaScuole() {
        try {
            listaScuole = getScuoleFacade().findAll();
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista delle SCUOLE:"
                    + ejbex.getMessage());
        }
        return listaScuole;
    }

    public void setListaScuole(List<Scuole> listaScuole) {
        this.listaScuole = listaScuole;
    }

    public Scuole getSelectedScuola() {
        if (selectedScuola == null) {
            if (listaScuole != null && listaScuole.size() > 0) {
                setSelectedScuola(listaScuole.get(0));
            }
        }
        return selectedScuola;
    }

    public void setSelectedScuola(Scuole selectedScuola) {
        this.selectedScuola = selectedScuola;
        retrieveAnniScolastici();
    }
    @EJB
    AnniScolasticiFacade anniScolasticiFacade;
    private List<AnniScolastici> listaAS;
    private AnniScolastici selectedAS;

    public AnniScolasticiFacade getAnniScolasticiFacade() {
        return anniScolasticiFacade;
    }

    public List<AnniScolastici> getListaAS() {
        try {
            listaAS = getAnniScolasticiFacade().retrieveAnniScolasticiScuolaOrderedList(getSelectedScuola());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista ANNI SCOLASTICI:"
                    + ejbex.getMessage());
        }
        return listaAS;
    }

    public void setListaAS(List<AnniScolastici> listaAS) {
        this.listaAS = listaAS;
    }

    public AnniScolastici getSelectedAS() {
        if (selectedAS == null) {
            if (listaAS != null && listaAS.size() > 0) {
                setSelectedAS(listaAS.get(0));
            }
        }
        return selectedAS;
    }

    public void setSelectedAS(AnniScolastici selectedAS) {
        this.selectedAS = selectedAS;
        retrieveClassi();
    }
    @EJB
    ClassiFacade classiFacade;
    private List<Classi> listaClassi;
    private Classi selectedClasse;

    public ClassiFacade getClassiFacade() {
        return classiFacade;
    }

    public List<Classi> getListaClassi() {
        try {
            listaClassi = getClassiFacade().retrieveClassiAnnoScolasticoOrderedList(getSelectedAS());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista CLASSI:"
                    + ejbex.getMessage());
        }
        return listaClassi;
    }

    public void setListaClassi(List<Classi> listaClassi) {
        this.listaClassi = listaClassi;
    }

    public Classi getSelectedClasse() {
        if (selectedClasse == null) {
            if (listaClassi != null && listaClassi.size() > 0) {
                setSelectedClasse(listaClassi.get(0));
            }
        }
        return selectedClasse;
    }

    public void setSelectedClasse(Classi selectedClasse) {
        this.selectedClasse = selectedClasse;
        retrieveInsegnanti();
    }
    @EJB
    InsegnantiFacade insegnantiFacade;
    private List<Insegnanti> listaInsegnanti;
    private Insegnanti selectedInsegnante;

    public InsegnantiFacade getInsegnantiFacade() {
        return insegnantiFacade;
    }

    public List<Insegnanti> getListaInsegnanti() {
        try {
            listaInsegnanti = getInsegnantiFacade().retrieveInsegnantiClasseOrderedList(
                    getSelectedAS(), getSelectedClasse());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista INSEGNANTI:"
                    + ejbex.getMessage());
        }
        return listaInsegnanti;
    }

    public void setListaInsegnanti(List<Insegnanti> listaInsegnanti) {
        this.listaInsegnanti = listaInsegnanti;
    }

    public Insegnanti getSelectedInsegnante() {
        if (selectedInsegnante == null) {
            if (listaInsegnanti != null && listaInsegnanti.size() > 0) {
                setSelectedInsegnante(listaInsegnanti.get(0));
            }
        }
        return selectedInsegnante;
    }

    public void setSelectedInsegnante(Insegnanti selectedInsegnante) {
        this.selectedInsegnante = selectedInsegnante;
        retrieveMaterie();
    }
    @EJB
    MaterieFacade materieFacade;
    private List<Materie> listaMaterie;
    private Materie selectedMateria;

    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public List<Materie> getListaMaterie() {
        try {
            listaMaterie = getMaterieFacade().retrieveMaterieInsegnanteOrderedList(
                    getSelectedAS(), getSelectedClasse(), getSelectedInsegnante());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista MATERIE:"
                    + ejbex.getMessage());
        }
        return listaMaterie;
    }

    public void setListaMaterie(List<Materie> listaMaterie) {
        this.listaMaterie = listaMaterie;
    }

    public Materie getSelectedMateria() {
        if (selectedMateria == null) {
            if (listaMaterie != null && listaMaterie.size() > 0) {
                setSelectedMateria(listaMaterie.get(0));
            }
        }
        return selectedMateria;
    }

    public void setSelectedMateria(Materie selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    /**
     *
     * @return
     */
    public boolean isModifyAction() {
        return modifyAction;
    }

    public void setModifyAction(boolean modifyAction) {
        this.modifyAction = modifyAction;
    }

    public boolean isCreateAction() {
        return createAction;
    }

    public void setCreateAction(boolean createAction) {
        this.createAction = createAction;
    }

    public boolean isDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(boolean deleteAction) {
        this.deleteAction = deleteAction;
    }

    public boolean isCrudAction() {
        crudAction = modifyAction || createAction || deleteAction;
        return crudAction;
    }

    public void setCrudAction(boolean crudAction) {
        this.crudAction = crudAction;
    }

    public String getTabella() {
        switch (page) {
            case 1:
                tabella = "Registri dei professori";
                break;
            default:
                tabella = "NON DEFINITA";
                break;
        }
        return tabella;
    }

    public void setTabella(String tabella) {
        this.tabella = tabella;
    }

    public String backToScegliRegistro() {
        page = 1;
        listaScuole = null;
        selectedScuola = null;
        return "/registro/ScegliRegistroScolastico";
    }

    public void grantReadPermission() {
        JsfUtil.addSuccessMessage("Abilitati i permessi di LETTURA del registro:"
                + " Materia:" + getSelectedMateria().getMateria()
                + " Insegnante:" + getSelectedMateria().getIdInsegnante().getCognome() + " " + getSelectedMateria().getIdInsegnante().getNome()
                + " Classe:" + getSelectedMateria().getIdClasse().getNomeClasse()
                + " A.S.:" + getSelectedMateria().getAnnoScolastico()
                + " Scuola:" + getSelectedMateria().getIdAnnoScolastico().getIdScuola().getNomeScuola()
                + ", all'utente: " + getSelectedUtente());

    }

    public void grantWritePermission() {
        JsfUtil.addSuccessMessage("Abilitati i permessi di SCRITTURA sul registro:"
                + " Materia:" + getSelectedMateria().getMateria()
                + " Insegnante:" + getSelectedMateria().getIdInsegnante().getCognome() + " " + getSelectedMateria().getIdInsegnante().getNome()
                + " Classe:" + getSelectedMateria().getIdClasse().getNomeClasse()
                + " A.S.:" + getSelectedMateria().getAnnoScolastico()
                + " Scuola:" + getSelectedMateria().getIdAnnoScolastico().getIdScuola().getNomeScuola()
                + ", all'utente: " + getSelectedUtente());
    }

    public void decrementaPagina() {
        page--;
    }

    public void incrementaPagina() {
        page++;
    }

    public String getAzioneInCorso() {
        return azioneInCorso;
    }

    public void setAzioneInCorso(String azioneInCorso) {
        this.azioneInCorso = azioneInCorso;
    }

    /**
     * pagination
     * @return 
     */
    public boolean isRewind() {
        rewind = page > 1;
        return rewind;
    }

    public void setRewind(boolean rewind) {
        this.rewind = rewind;
    }

    public boolean isForward() {
        forward = page < 10;
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isDisableCommitRollback() {
        return disableCommitRollback;
    }

    public void setDisableCommitRollback(boolean disableCommitRollback) {
        this.disableCommitRollback = disableCommitRollback;
    }

    public boolean isDisableCreateModifyDelete() {
        return disableCreateModifyDelete;
    }

    public void setDisableCreateModifyDelete(boolean disableCreateModifyDelete) {
        this.disableCreateModifyDelete = disableCreateModifyDelete;
    }

    /**
     * Cancella record selezionato
     */
    public void cancellaAction() {
        setDisableCommitRollback(false);
        setDisableCreateModifyDelete(true);
        setAzioneInCorso("Cancella record!");

        setDeleteAction(true);
        JsfUtil.addErrorMessage("ATTENZIONE siamo in procinto di cancellare il record!");
        switch (page) {
            default:
                break;
        }
    }

    /**
     * Modifica record selezionato
     */
    public void modificaAction() {
        switch (page) {

            default:
                break;
        }
        setDisableCommitRollback(false);
        setDisableCreateModifyDelete(true);
        setAzioneInCorso("Modifica record!");

        setModifyAction(true);
        JsfUtil.addErrorMessage("ATTENZIONE siamo in procinto di modificare il record!");

    }

    /**
     * Crea record del tipo selezionato
     */
    public void createAction() {
        setDisableCommitRollback(false);
        setDisableCreateModifyDelete(true);
        setAzioneInCorso("Crea record!");

        setCreateAction(true);
        JsfUtil.addErrorMessage("ATTENZIONE stiamo creando un nuovo record!");
        switch (page) {

            default:
                break;
        }
    }

    /**
     * Finalizza le operazioni CANCELLA, MODIFICA, CREA nel database
     */
    public void commitAction() {
        try {
            switch (page) {

                default:
                    break;
            }
            JsfUtil.addSuccessMessage("Eseguita operazione COMMIT!");
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Errore durante l'operazione di COMMIT:"
                    + ejbex.getMessage());
        }
        setDisableCommitRollback(true);
        setDisableCreateModifyDelete(false);
        setAzioneInCorso("In attesa...");

        setModifyAction(false);
        setCreateAction(false);
        setDeleteAction(false);
    }

    /**
     * Esce senza finalizzare le operazioni CANCELLA, MODIFICA, CREA nel
     * database
     */
    public void rollbackAction() {
        setDisableCommitRollback(true);
        setDisableCreateModifyDelete(false);
        setAzioneInCorso("In attesa...");
        try {
            switch (page) {

                default:
                    break;
            }
            JsfUtil.addSuccessMessage("Eseguita operazione di ROLLBACK!");
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Errore durante l'operazione di ROLLBACK:"
                    + ejbex.getMessage());
        }
        setModifyAction(false);
        setCreateAction(false);
        setDeleteAction(false);

    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
//        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        // PARAMETERS FOR UPDATING ASSENZE
        if (source instanceof HtmlSelectOneMenu) {
            HtmlSelectOneMenu hmenu = (HtmlSelectOneMenu) source;
            if (hmenu.getId().equals("selectedScuola")) {
                setSelectedScuola((Scuole) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedAS")) {
                setSelectedAS((AnniScolastici) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedClasse")) {
                setSelectedClasse((Classi) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedInsegnante")) {
                setSelectedInsegnante((Insegnanti) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedMateria")) {
                setSelectedMateria((Materie) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedUtenteScuola")) {
                setSelectedUtente((UtentiScuola) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedRuoloGrantedToUtente")) {
                setSelectedRuoloGrantedToUtente((RuoliGrantedToUtenti) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedRuoloUtente")) {
                setSelectedRuoloUtente((RuoliUtenti) event.getNewValue());
            }
            JsfUtil.addSuccessMessage(hmenu.getId() + " = '" + event.getNewValue() + "'");
        }
    }

    /**
     * Carica i records di anni scolastici legati alla scuola selezionata
     */
    private void retrieveAnniScolastici() {
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            listaAS = getAnniScolasticiFacade().retrieveAnniScolasticiScuolaOrderedList(getSelectedScuola());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista ANNI SCOLASTICI:"
                    + ejbex.getMessage());
        }
        setSelectedAS(null);
    }

    /**
     * Carica le classi dell'anno scolastico che è legato alla scuola
     * selezionata
     */
    private void retrieveClassi() {
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            listaClassi = getClassiFacade().retrieveClassiAnnoScolasticoOrderedList(getSelectedAS());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista CLASSI:"
                    + ejbex.getMessage());
        }
        setSelectedClasse(null);
    }

    /**
     * Carica gli insegnanti dell'anno scolastico selezionato nella classe
     * selezionata
     */
    private void retrieveInsegnanti() {
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            listaInsegnanti = getInsegnantiFacade().retrieveInsegnantiClasseOrderedList(
                    getSelectedAS(), getSelectedClasse());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista INSEGNANTI:"
                    + ejbex.getMessage());
        }
        setSelectedInsegnante(null);
    }

    /**
     * Carica le materie (ognuna delle quali corrisponderà ad un registro
     * scolastico) dell'anno scolastico selezionato nella classe selezionata
     */
    private void retrieveMaterie() {
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            listaMaterie = getMaterieFacade().retrieveMaterieInsegnanteOrderedList(
                    getSelectedAS(), getSelectedClasse(), getSelectedInsegnante());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista MATERIE:"
                    + ejbex.getMessage());
        }
        setSelectedMateria(null);
    }
    /**
     * Utenti della scuola
     */
    @EJB
    UtentiScuolaFacade utentiScuolaFacade;
    private UtentiScuola selectedUtente;
    private List<UtentiScuola> listUtenti;

    public UtentiScuolaFacade getUtentiScuolaFacade() {
        return utentiScuolaFacade;
    }

    public UtentiScuola getSelectedUtente() {
        if (selectedUtente == null) {
            if (listUtenti != null && listUtenti.size() > 0) {
                setSelectedUtente(listUtenti.get(0));
            }
        }
        return selectedUtente;
    }

    public void setSelectedUtente(UtentiScuola selectedUtente) {
        this.selectedUtente = selectedUtente;
        retrieveRuoliGrantedToUtente();
    }

    public List<UtentiScuola> getListUtenti() {
        if (listUtenti == null) {
            listUtenti = getUtentiScuolaFacade().findAll();
        }
        return listUtenti;
    }

    public void setListUtenti(List<UtentiScuola> listUtenti) {
        this.listUtenti = listUtenti;
    }
    @EJB
    RuoliGrantedToUtentiFacade ruoliGrantedToUtentiFacade;
    private RuoliGrantedToUtenti selectedRuoloGrantedToUtente;
    private List<RuoliGrantedToUtenti> listRuoliGrantedToUtente;

    public RuoliGrantedToUtentiFacade getRuoliGrantedToUtentiFacade() {
        return ruoliGrantedToUtentiFacade;
    }

    public RuoliGrantedToUtenti getSelectedRuoloGrantedToUtente() {
        if (selectedRuoloGrantedToUtente == null) {
            if (listRuoliGrantedToUtente != null && listRuoliGrantedToUtente.size() > 0) {
                setSelectedRuoloGrantedToUtente(listRuoliGrantedToUtente.get(0));
            }
        }
        return selectedRuoloGrantedToUtente;
    }

    public void setSelectedRuoloGrantedToUtente(RuoliGrantedToUtenti selectedRuoloGrantedToUtente) {
        this.selectedRuoloGrantedToUtente = selectedRuoloGrantedToUtente;
    }

    public List<RuoliGrantedToUtenti> getListRuoliGrantedToUtente() {
        try {
            listRuoliGrantedToUtente = getRuoliGrantedToUtentiFacade().findByUtente(selectedUtente);
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista dei ruoli dell'utente:" + selectedUtente + " "
                    + ejbex.getMessage());
        }
        return listRuoliGrantedToUtente;
    }

    public void setListRuoliGrantedToUtente(List<RuoliGrantedToUtenti> listRuoliGrantedToUtente) {
        this.listRuoliGrantedToUtente = listRuoliGrantedToUtente;
    }
    @EJB
    RuoliUtentiFacade ruoliUtentiFacade;
    private RuoliUtenti selectedRuoloUtente;
    private List<RuoliUtenti> listRuoliUtente;

    public RuoliUtentiFacade getRuoliUtentiFacade() {
        return ruoliUtentiFacade;
    }

    public RuoliUtenti getSelectedRuoloUtente() {
        if (selectedRuoloUtente == null) {
            if (listRuoliUtente != null && listRuoliUtente.size() > 0) {
                setSelectedRuoloUtente(listRuoliUtente.get(0));
            }
        }
        return selectedRuoloUtente;
    }

    public void setSelectedRuoloUtente(RuoliUtenti selectedRuoloUtente) {
        this.selectedRuoloUtente = selectedRuoloUtente;
    }

    public List<RuoliUtenti> getListRuoliUtente() {
        if (listRuoliUtente == null) {
            retrieveRuoliUtente();
        }
        return listRuoliUtente;
    }

    public void setListRuoliUtente(List<RuoliUtenti> listRuoliUtente) {
        this.listRuoliUtente = listRuoliUtente;
    }

    /**
     * Carica i ruoli garantiti all'utente selezionato
     */
    private void retrieveRuoliGrantedToUtente() {
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            listRuoliGrantedToUtente = getRuoliGrantedToUtentiFacade().findByUtente(selectedUtente);
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista dei ruoli dell'utente:" + selectedUtente + " "
                    + ejbex.getMessage());
            return;
        }
        setSelectedRuoloGrantedToUtente(null);
    }

    /**
     * Carica la lista dei ruoli previsti nel database
     */
    private void retrieveRuoliUtente() {
//        throw new UnsupportedOperationException("Not yet implemented");
        try {
            listRuoliUtente = getRuoliUtentiFacade().findAll();
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista dei ruoli ricopribili dall'utente:" + selectedUtente + " "
                    + ejbex.getMessage());
            return;
        }
        setSelectedRuoloUtente(null);
    }
}
