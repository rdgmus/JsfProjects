/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import jpa.entities.AnniScolastici;
import jpa.entities.Classi;
import jpa.entities.Insegnanti;
import jpa.entities.Lezioni;
import jpa.entities.Materie;
import jpa.entities.OreAssenze;
import jpa.entities.ParametriOrarioAs;
import jpa.entities.PeriodiAnnoScolastico;
import jpa.entities.PeriodiAnnoScolasticoPK;
import jpa.entities.Scuole;
import jpa.entities.Studenti;
import jpa.session.AnniScolasticiFacade;
import jpa.session.ClassiFacade;
import jpa.session.InsegnantiFacade;
import jpa.session.LezioniFacade;
import jpa.session.MaterieFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.ParametriOrarioAsFacade;
import jpa.session.PeriodiAnnoScolasticoFacade;
import jpa.session.ScuoleFacade;
import jpa.session.StudentiFacade;
import jpa.session.UtentiLoggerFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.UICalendar;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "gestioneScuolaWizardBean")
@SessionScoped
public class GestioneScuolaWizardBean implements Serializable, ValueChangeListener {

    private int page = 1;
    private boolean rewind, forward;
    private String tabella;
    private Object locale;
    private boolean popup = true;
    private String pattern = "dd MMM yyyy";
    private boolean showApply = true;
    private boolean disableCommitRollback = true;//Abilita o disabilita bottoni COMMIT , ROLLBACK
    private boolean disableCreateModifyDelete = false;//Abilita o disabilita bottoni CREA, MODIFICA, CANCELLA
    private String azioneInCorso = "In attesa...";//Messaggio relativo al'azione in corso nel WIZARD
    private boolean modifyAction = false;//Attivo quando siamo in operazione MODIFICA
    private boolean createAction = false;//Attivo quando siamo in operazione CREA
    private boolean deleteAction = false;//Attivo quando siamo in operazione CANCELLA
    private boolean crudAction = false;
    private double completamentoAS = 0.0;

    public double getCompletamentoAS() {
        calcCoperturaAS();
        return completamentoAS;
    }

    public void setCompletamentoAS(double completamentoAS) {

        this.completamentoAS = completamentoAS;
    }

    /**
     * Calcola la percentuale di copertura dell'anno scolastico ottenuta con i
     * periodi anno scolastico creati
     *
     */
    public void calcCoperturaAS() {

        Calendar asStart = Calendar.getInstance();
        Calendar asEnd = Calendar.getInstance();
        Calendar pStart = Calendar.getInstance();
        Calendar pEnd = Calendar.getInstance();

        //ANNO SCOLASTICO
        asStart.setTime(selectedAS.getStartDate());
        asEnd.setTime(selectedAS.getEndDate());
        //PERIODI
        int rows = getListaPeriodiAS().size();
        double percent = 0.0;
        switch (rows) {
            case 0:
                percent = 0.0;
                break;
            default:
                pStart.setTime(getListaPeriodiAS().get(0).getStartDate());
                pEnd.setTime(getListaPeriodiAS().get(rows - 1).getEndDate());
                long durataAnno = asEnd.getTimeInMillis() - asStart.getTimeInMillis();
                long durataPeriodi = pEnd.getTimeInMillis() - pStart.getTimeInMillis();
                percent = ((double) durataPeriodi / (double) durataAnno) * 100.0;
                break;
        }

        setCompletamentoAS(percent);
    }

    public boolean isCrudAction() {
        crudAction = modifyAction || createAction || deleteAction;
        return crudAction;
    }

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
    @EJB
    ScuoleFacade scuoleFacade;
    private List<Scuole> listaScuole;
    private Scuole selectedScuola;
    @EJB
    AnniScolasticiFacade anniScolasticiFacade;
    private List<AnniScolastici> listaAS;
    private AnniScolastici selectedAS;
    @EJB
    PeriodiAnnoScolasticoFacade periodiAnnoScolasticoFacade;
    private List<PeriodiAnnoScolastico> listaPeriodiAS;
    private PeriodiAnnoScolastico selectedPeriod;
    private String periodType;//stringa relativa al campo periodo della tabella scuola.periodi_anno_scolastico
    @EJB
    ClassiFacade classiFacade;
    private List<Classi> listaClassi;
    private Classi selectedClasse;
    @EJB
    StudentiFacade studentiFacade;
    private List<Studenti> listaStudenti;
    private Studenti selectedStudente;
    private List<Studenti> listaAllStudenti;
    private Studenti selectedExistingStudente;
    @EJB
    ParametriOrarioAsFacade parametriOrarioAsFacade;
    private ParametriOrarioAs parametriOrarioAs;
    private Time inizioLezioni;
    private Integer durataOra;
    private Integer durataIntervallo;
    private Long idParamOrario;
    @EJB
    InsegnantiFacade insegnantiFacade;
    private List<Insegnanti> listaInsegnanti;
    private Insegnanti selectedInsegnante;
    private List<Insegnanti> listaAllInsegnanti;
    private Insegnanti selectedExistingInsegnante;
    @EJB
    MaterieFacade materieFacade;
    private List<Materie> listaMaterie;
    private Materie selectedMateria;
    private List<Materie> listaAllMaterie;
    private Materie selectedExistingMateria;

    public String getPeriodType() {
        if (periodType == null) {
            periodType = "BIMESTRE";
        }
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public GestioneScuolaWizardBean() {
    }

    public Object getLocale() {
        return locale;
    }

    public void setLocale(Object locale) {
        this.locale = locale;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean isShowApply() {
        return showApply;
    }

    public void setShowApply(boolean showApply) {
        this.showApply = showApply;
    }

    /*
     * SCUOLE
     */
    public ScuoleFacade getScuoleFacade() {
        return scuoleFacade;
    }

    public Scuole getSelectedScuola() {
        if (selectedScuola == null) {
            if (listaScuole != null && listaScuole.size() > 0) {
                selectedScuola = listaScuole.get(0);
            }
        }
        return selectedScuola;
    }

    public void setSelectedScuola(Scuole selectedScuola) {
        this.selectedScuola = selectedScuola;
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

    /*
     * ANNI SCOLASTICI
     */
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
                selectedAS = listaAS.get(0);
            }
        }
        return selectedAS;
    }

    public void setSelectedAS(AnniScolastici selectedAS) {
        this.selectedAS = selectedAS;
    }

    /*
     * PERIODI ANNO SCOLASTICO
     */
    public PeriodiAnnoScolasticoFacade getPeriodiAnnoScolasticoFacade() {
        return periodiAnnoScolasticoFacade;
    }

    public List<PeriodiAnnoScolastico> getListaPeriodiAS() {
        try {
            listaPeriodiAS = getPeriodiAnnoScolasticoFacade().findByAnnoScolastico(getSelectedAS());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista PERIODI ANNO SCOLASTICO:"
                    + ejbex.getMessage());
        }
        return listaPeriodiAS;
    }

    public void setListaPeriodiAS(List<PeriodiAnnoScolastico> listaPeriodiAS) {
        this.listaPeriodiAS = listaPeriodiAS;
    }

    public PeriodiAnnoScolastico getSelectedPeriod() {
        if (selectedPeriod == null) {
            if (listaPeriodiAS != null && listaPeriodiAS.size() > 0) {
                selectedPeriod = listaPeriodiAS.get(0);
            }
        }
        return selectedPeriod;
    }

    public void setSelectedPeriod(PeriodiAnnoScolastico selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }

    /**
     * PARAMETRI ORARIO
     * @return 
     */
    public ParametriOrarioAsFacade getParametriOrarioAsFacade() {
        return parametriOrarioAsFacade;
    }

    public Time findInizioLezioni() {
        try {
            this.inizioLezioni = getParametriOrarioAsFacade().findInizioLezioni(selectedAS);
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre PARAMETRO INIZIO LEZIONI:"
                    + ejbex.getMessage());
            this.inizioLezioni = null;
        }
        return this.inizioLezioni;
    }

    public Integer findDurataOra() {
        try {
            this.durataOra = getParametriOrarioAsFacade().findDurataOra(selectedAS);
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre PARAMETRO DURATA ORA LEZIONE:"
                    + ejbex.getMessage());
            this.durataOra = null;
        }
        return this.durataOra;
    }

    public Integer findDurataIntervallo() {
        try {
            this.durataIntervallo = getParametriOrarioAsFacade().findDurataIntervallo(selectedAS);
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre PARAMETRO DURATA INTERVALLO:"
                    + ejbex.getMessage());
            this.durataIntervallo = null;
        }
        return this.durataIntervallo;
    }

    public Long findIdParamOrario() {
        try {
            this.idParamOrario = getParametriOrarioAsFacade().findIdParamOrario(selectedAS);
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre PARAMETRO IDENTIFICATIVO ORARIO:"
                    + ejbex.getMessage());
            this.idParamOrario = null;
        }
        return idParamOrario;
    }

    public ParametriOrarioAs getParametriOrarioAs() {
        if (crudAction) {
            return parametriOrarioAs;
        }
        try {
            parametriOrarioAs = getParametriOrarioAsFacade().findParametriOrarioAS(getSelectedAS());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre i PARAMETRI ORARIO:"
                    + ejbex.getMessage());
            setParametriOrarioAs(null);
        }
        return parametriOrarioAs;
    }

    public void setParametriOrarioAs(ParametriOrarioAs parametriOrarioAs) {
        this.parametriOrarioAs = parametriOrarioAs;
    }

    /*
     * CLASSI
     */
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
                selectedClasse = listaClassi.get(0);
            }
        }
        return selectedClasse;
    }

    public void setSelectedClasse(Classi selectedClasse) {
        this.selectedClasse = selectedClasse;
    }
    private int countStudenti;

    public int getCountStudenti() {
        setCountStudenti(listaStudenti.size());

        return countStudenti;
    }

    public void setCountStudenti(int countStudenti) {
        this.countStudenti = countStudenti;
    }

    /*
     * STUDENTI
     */
    public StudentiFacade getStudentiFacade() {
        return studentiFacade;
    }

    public List<Studenti> getListaStudenti() {
        try {
            listaStudenti = getStudentiFacade().retrieveStudentiClasseOrderedList(getSelectedClasse());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista STUDENTI:"
                    + ejbex.getMessage());
        }
        return listaStudenti;
    }

    public void setListaStudenti(List<Studenti> listaStudenti) {
        this.listaStudenti = listaStudenti;
    }

    public Studenti getSelectedStudente() {
        if (selectedStudente == null) {
            if (listaStudenti != null && listaStudenti.size() > 0) {
                selectedStudente = listaStudenti.get(0);
            }
        }
        return selectedStudente;
    }

    public void setSelectedStudente(Studenti selectedStudente) {
        this.selectedStudente = selectedStudente;
    }

    public List<Studenti> getListaAllStudenti() {
        try {
            listaAllStudenti = getStudentiFacade().retrieveAllStudentiDistinctOrderedList(getListaStudenti());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista dei nomi degli STUDENTI nel DATABASE:"
                    + ejbex.getMessage());
        }
        for (Studenti i : listaAllStudenti) {
            if (i.getCognome().equals(selectedStudente.getCognome())
                    && i.getNome().equals(selectedStudente.getNome())) {
                setSelectedExistingStudente(i);
                break;
            }
        }
        return listaAllStudenti;
    }

    public void setListaAllStudenti(List<Studenti> listaAllStudenti) {
        this.listaAllStudenti = listaAllStudenti;
    }

    public Studenti getSelectedExistingStudente() {
        if (selectedExistingStudente == null) {
            if (listaAllStudenti != null && listaAllStudenti.size() > 0) {
                selectedExistingStudente = listaAllStudenti.get(0);
            }
        }
        return selectedExistingStudente;
    }

    public void setSelectedExistingStudente(Studenti selectedExistingStudente) {
        this.selectedExistingStudente = selectedExistingStudente;
    }

    /*
     * INSEGNANTI
     */
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
                selectedInsegnante = listaInsegnanti.get(0);
            }
        }
        return selectedInsegnante;
    }

    public void setSelectedInsegnante(Insegnanti selectedInsegnante) {
        this.selectedInsegnante = selectedInsegnante;
    }
    //TUTTI GLI INSEGNANTI NEL DATABASE

    public List<Insegnanti> getListaAllInsegnanti() {
        try {
            listaAllInsegnanti = getInsegnantiFacade().retrieveAllInsegnantiDistinctOrderedList(getListaInsegnanti());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista dei nomi degli INSEGNANTI nel DATABASE:"
                    + ejbex.getMessage());
        }
        for (Insegnanti i : listaAllInsegnanti) {
            if (i.getCognome().equals(selectedInsegnante.getCognome())
                    && i.getNome().equals(selectedInsegnante.getNome())) {
                setSelectedExistingInsegnante(i);
                break;
            }
        }
        return listaAllInsegnanti;
    }

    public void setListaAllInsegnanti(List<Insegnanti> listaAllInsegnanti) {
        this.listaAllInsegnanti = listaAllInsegnanti;
    }

    public Insegnanti getSelectedExistingInsegnante() {
        if (selectedExistingInsegnante == null) {
            if (listaAllInsegnanti != null && listaAllInsegnanti.size() > 0) {
                selectedExistingInsegnante = listaAllInsegnanti.get(0);
            }
        }
        return selectedExistingInsegnante;
    }

    public void setSelectedExistingInsegnante(Insegnanti selectedExistingInsegnante) {
        this.selectedExistingInsegnante = selectedExistingInsegnante;
    }

    /*
     * MATERIE
     */
    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public List<Materie> getListaMaterie() {
        try {
            listaMaterie = getMaterieFacade().retrieveMaterieInsegnanteOrderedList(selectedAS, selectedClasse, selectedInsegnante);
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
                selectedMateria = listaMaterie.get(0);
            }
        }
        return selectedMateria;
    }

    public void setSelectedMateria(Materie selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    public List<Materie> getListaAllMaterie() {
        try {
            listaAllMaterie = getMaterieFacade().retrieveAllMaterieDistinctOrderedList(getListaMaterie());
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista delle MATERIE nel DATABASE:"
                    + ejbex.getMessage());
        }
        for (Materie i : listaAllMaterie) {
            if (i.getMateria().equals(selectedMateria.getMateria())) {
                setSelectedExistingMateria(i);
                break;
            }
        }
        return listaAllMaterie;
    }

    public void setListaAllMaterie(List<Materie> listaAllMaterie) {
        this.listaAllMaterie = listaAllMaterie;
    }

    public Materie getSelectedExistingMateria() {
        if (selectedExistingMateria == null) {
            if (listaAllMaterie != null && listaAllMaterie.size() > 0) {
                selectedExistingMateria = listaAllMaterie.get(0);
            }
        }
        return selectedExistingMateria;
    }

    public void setSelectedExistingMateria(Materie selectedExistingMateria) {
        this.selectedExistingMateria = selectedExistingMateria;
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

    public String getTabella() {
        switch (page) {
            case 1:
                tabella = "Scuole";
                break;
            case 2:
                tabella = "Anni Scolastici";
                break;
            case 3:
                tabella = "Periodi Anno Scolastico";
                break;
            case 4:
                tabella = "Parametri Orario A.S.";
                break;
            case 5:
                tabella = "Classi";
                break;
            case 6:
                tabella = "Studenti";
                break;
            case 7:
                tabella = "Insegnanti";
                break;
            case 8:
                tabella = "Materie o Registri";
                break;
            case 9:
                tabella = "RIEPILOGO";
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

    public void decrementaPagina() {
        page--;
    }

    public void incrementaPagina() {
        page++;
        switch (page) {
            case 1://SCUOLE
                break;
            case 2://ANNI SCOLASTICI
                if (getListaScuole().size() > 0) {
                    if (getListaAS().size() > 0) {
                        setSelectedAS(getListaAS().get(0));
                    } else {
                        setSelectedAS(null);
                    }
                } else {
                    page--;
                    JsfUtil.addErrorMessage("Bisogna aggiungere almeno un record SCUOLE");
                }
                break;
            case 3://PERIODI AS
                if (getListaAS().size() > 0) {
                    if (getListaPeriodiAS().size() > 0) {
                        setSelectedPeriod(getListaPeriodiAS().get(0));
                    } else {
                        setSelectedPeriod(null);
                    }
                } else {
                    page--;
                    JsfUtil.addErrorMessage("Bisogna aggiungere almeno un record ANNI SCOLASTICI");
                }
                break;
            case 4://PARAMETRI ORARIO
                if (getCompletamentoAS() == 100.0) {
                } else {
                    page--;
                    JsfUtil.addErrorMessage("Bisogna aggiungere i periodi in cui è suddiviso l'anno scolastico con una copertura totale dello stesso per procedere oltre");
                }
                break;
            case 5://CLASSI
                if (getParametriOrarioAs() == null) {
                    page--;
                    JsfUtil.addErrorMessage("Bisogna valorizzare i PARAMETRI ORARIO ANNO SCOLASTICO prima di creare le CLASSI");
                } else {
                    if (getListaClassi().size() > 0) {
                        setSelectedClasse(getListaClassi().get(0));
                    } else {
                        setSelectedClasse(null);
                    }
                }
                break;
            case 6://STUDENTI
                if (getSelectedClasse() != null) {
                    if (getListaStudenti().size() > 0) {
                        setSelectedStudente(getListaStudenti().get(0));
                    } else {
                        setSelectedStudente(null);
                    }
                } else {
                    page--;
                    JsfUtil.addErrorMessage("Non sono state ancora inserite le denominazioni delle CLASSI, per cui non possono"
                            + " essere inseriti gli STUDENTI!");
                }
                break;
            case 7://INSEGNANTI
                if (getSelectedClasse() != null && getListaAS() != null) {
                    if (getListaInsegnanti().size() > 0) {
                        setSelectedInsegnante(getListaInsegnanti().get(0));
                    } else {
                        setSelectedInsegnante(null);
                    }
                } else {
                    page--;
                    JsfUtil.addErrorMessage("Non sono state ancora inserite le denominazioni delle CLASSI, per cui non possono"
                            + " essere inseriti gli INSEGNANTI!");
                }
                break;
            case 8://MATERIE
                if (getSelectedInsegnante() != null && getListaInsegnanti() != null) {
                    if (getListaMaterie().size() > 0) {
                        setSelectedMateria(getListaMaterie().get(0));
                    } else {
                        setSelectedMateria(null);
                    }
                } else {
                    page--;
                    JsfUtil.addErrorMessage("Non sono state ancora inseriti gli INSEGNANTI, per cui non possono"
                            + " essere inserite le MATERIE!");
                }
                break;
            case 9://FINE
                JsfUtil.addSuccessMessage("Fine del Wizard e Riepilogo delle scelte effettuate");
                break;
            case 10://FINE
                page = 1;
                JsfUtil.addSuccessMessage("Restart del Wizard");
                break;
            default:
                break;
        }
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
            case 3://PERIODI ANNO SCOLASTICO
                JsfUtil.addSuccessMessage("ATTENZIONE verrà sempre cancellato l'ultimo periodo disponibile!");
                int size = getListaPeriodiAS().size();

                setSelectedPeriod(getListaPeriodiAS().get(size - 1));
                break;
            default:
                break;
        }
    }

    /**
     * Modifica record selezionato
     */
    public void modificaAction() {
        switch (page) {
            case 1://SCUOLE
                if (getSelectedScuola() == null) {
                    JsfUtil.addErrorMessage("Non ci sono SCUOLE da modificare");
                    return;
                }
                break;
            case 2://ANNO SCOLASTICO
                if (getSelectedAS() == null) {
                    JsfUtil.addErrorMessage("Non ci sono ANNO SCOLASTICI da modificare");
                    return;
                }
                break;
            case 3://PERIODI ANNO SCOLASTICO
                if (getSelectedPeriod() == null) {
                    JsfUtil.addErrorMessage("Non ci sono PERIODI ANNO SCOLASTICO da modificare");
                    return;
                }
                JsfUtil.addSuccessMessage("ATTENZIONE verrà sempre modificata la sola data finale dell'ultimo periodo disponibile"
                        + " oppure la sua denominazione. Altrimenti procedere con cancella e ricrea!");
                int size = getListaPeriodiAS().size();

                setSelectedPeriod(getListaPeriodiAS().get(size - 1));
                break;
            case 4://PARAMETRI ORARIO ANNO SCOLASTICO
                if (getParametriOrarioAs() == null) {
                    JsfUtil.addErrorMessage("Non ci sono PARAMETRI ORARIO ANNO SCOLASTICO da modificare");
                    return;
                }
                break;
            case 5://CLASSI
                if (getSelectedClasse() == null) {
                    JsfUtil.addErrorMessage("Non ci sono CLASSI da modificare");
                    return;
                }
                break;
            case 6://STUDENTI
                if (getSelectedStudente() == null) {
                    JsfUtil.addErrorMessage("Non ci sono STUDENTI da modificare");
                    return;
                }
                break;
            case 7://INSEGNANTI
                if (getSelectedInsegnante() == null) {
                    JsfUtil.addErrorMessage("Non ci sono INSEGNANTI da modificare");
                    return;
                }
                break;
            case 8://MATERIE
                if (getSelectedMateria() == null) {
                    JsfUtil.addErrorMessage("Non ci sono MATERIE da modificare");
                    return;
                }
                break;
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
            case 1://SCUOLE
                if (isCreateAction()) {
                    if (getScuoleFacade() != null) {
                        Scuole nuovaScuola = new Scuole();
//                        Long nextId = getScuoleFacade().getNextId();
//                        nuovaScuola.setIdScuola(nextId);
                        nuovaScuola.setTipoScuolaAcronimo("[Tipo]");
                        nuovaScuola.setNomeScuola("[NomeScuola]");
                        try {
                            getScuoleFacade().create(nuovaScuola);
//                            this.listaScuole = getScuoleFacade().findAll();
                            getListaScuole();
                            this.selectedScuola = getScuoleFacade().find(nuovaScuola.getIdScuola());
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare la Scuola:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
                break;
            case 2://ANNO SCOLASTICO
                if (isCreateAction()) {
                    if (getAnniScolasticiFacade() != null) {
                        AnniScolastici nuovoAnno = new AnniScolastici();
                        Long nextId = getAnniScolasticiFacade().getNextId();
                        nuovoAnno.setIdAnnoScolastico(nextId);
                        nuovoAnno.setIdScuola(this.selectedScuola);

                        Calendar startCal = Calendar.getInstance();

                        nuovoAnno.setStartDate(startCal.getTime());
                        nuovoAnno.setEndDate(startCal.getTime());

                        nuovoAnno.setAnnoScolastico(getAnniScolasticiFacade().getAnnoAscolasticoAsString(nuovoAnno));
                        try {
                            getAnniScolasticiFacade().create(nuovoAnno);
//                            this.listaAS = getAnniScolasticiFacade().findAll();
                            getListaAS();
                            this.selectedAS = getAnniScolasticiFacade().find(nuovoAnno.getIdAnnoScolastico());
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare l'Anno Scolastico:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
                break;
            case 3://PERIODI ANNO SCOLASTICO
                if (isCreateAction()) {
                    if (getPeriodiAnnoScolasticoFacade() != null) {
                        PeriodiAnnoScolastico nuovoPeriodo = new PeriodiAnnoScolastico();
                        Long nextId = getPeriodiAnnoScolasticoFacade().getNextId();
                        nuovoPeriodo.setScuole(selectedScuola);
                        nuovoPeriodo.setAnniScolastici(selectedAS);
                        PeriodiAnnoScolasticoPK pkPeriod = new PeriodiAnnoScolasticoPK(
                                nextId, getSelectedScuola().getIdScuola(), getSelectedAS().getIdAnnoScolastico());
                        nuovoPeriodo.setPeriodiAnnoScolasticoPK(pkPeriod);
                        nuovoPeriodo.setPeriodo(getPeriodType());
                        int size = getListaPeriodiAS().size();
                        switch (size) {
                            case 0:
                                nuovoPeriodo.setStartDate(getSelectedAS().getStartDate());
                                break;
                            default:
                                Calendar startCal = Calendar.getInstance();
                                startCal.setTime(getListaPeriodiAS().get(size - 1).getEndDate());
                                startCal.add(Calendar.DAY_OF_YEAR, 1);
                                nuovoPeriodo.setStartDate(startCal.getTime());
                                break;
                        }
                        if (nuovoPeriodo.getStartDate().after(getSelectedAS().getEndDate())) {
                            JsfUtil.addSuccessMessage("COPERTURA PERIODI COMPLETATA!");
                            setDisableCommitRollback(true);
                            setDisableCreateModifyDelete(false);
                            setAzioneInCorso("In attesa...");

                            setModifyAction(false);
                            setCreateAction(false);
                            setDeleteAction(false);
                            return;
                        }

                        Date endDate = getEndPeriodDate(nuovoPeriodo.getStartDate());
                        if (endDate.after(getSelectedAS().getEndDate())) {
                            endDate = getSelectedAS().getEndDate();
                        }

                        nuovoPeriodo.setEndDate(endDate);
                        try {
                            getPeriodiAnnoScolasticoFacade().create(nuovoPeriodo);
                            getListaPeriodiAS();
                            this.selectedPeriod = getPeriodiAnnoScolasticoFacade().findByIdPeriod(nextId);
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare il Periodo Anno Scolastico:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
                break;
            case 4://PARAMETRI ORARIO ANNO SCOLASTICO
                if (isCreateAction()) {
                    if (getParametriOrarioAsFacade() != null) {
                        ParametriOrarioAs paramsOrario = new ParametriOrarioAs();
                        Long nextId = getParametriOrarioAsFacade().getNextId();
                        paramsOrario.setIdParamOrario(nextId);

                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 8);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);

                        paramsOrario.setIdAnnoScolastico(getSelectedAS());
                        paramsOrario.setInizioLezioni(cal.getTime());
                        paramsOrario.setDurataOraMinuti(60);
                        paramsOrario.setDurataIntervalloMinuti(15);
                        try {
                            getParametriOrarioAsFacade().create(paramsOrario);
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare i PARAMETRI ORARIO ANNO SCOLASTICO:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
            case 5://CLASSI
                if (isCreateAction()) {
                    if (getClassiFacade() != null) {
                        Classi nuovaClasse = new Classi();
                        Long nextId = getClassiFacade().getNextId();

                        nuovaClasse.setIdClasse(nextId);
                        nuovaClasse.setAnnoScolastico(getSelectedAS().getAnnoScolastico());
                        nuovaClasse.setIdAnnoScolastico(getSelectedAS());
                        nuovaClasse.setIdScuola(getSelectedScuola());
                        nuovaClasse.setNomeClasse("[CLASSE]");
                        nuovaClasse.setSpecializzazione("[SPECIALIZZAZIONE]");
                        try {
                            getClassiFacade().create(nuovaClasse);
                            int size = getListaClassi().size();
                            setSelectedClasse(getListaClassi().get(size - 1));
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare la CLASSE:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
            case 6://STUDENTI
                if (isCreateAction()) {
                    if (getStudentiFacade() != null) {
                        Studenti nuovoStudente = new Studenti();
                        Long nextId = getStudentiFacade().getNextId();

                        nuovoStudente.setIdStudente(nextId);
                        nuovoStudente.setAnnoScolastico(selectedAS.getAnnoScolastico());
                        nuovoStudente.setIdAnnoScolastico(selectedAS.getIdAnnoScolastico());
                        nuovoStudente.setCognome("[COGNOME]");
                        nuovoStudente.setNome("[NOME]");
                        nuovoStudente.setIdClasse(selectedClasse);
                        nuovoStudente.setAttivo((short)1);
                        try {
                            getStudentiFacade().create(nuovoStudente);
                            int size = getListaStudenti().size();
                            setSelectedStudente(getListaStudenti().get(size - 1));
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare lo STUDENTE:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
                break;
            case 7://INSEGNANTI
                if (isCreateAction()) {
                    if (getInsegnantiFacade() != null) {
                        Insegnanti nuovoInsegnante = new Insegnanti();
                        Long nextId = getInsegnantiFacade().getNextId();

                        nuovoInsegnante.setIdInsegnante(nextId);
                        nuovoInsegnante.setCognome("[cognome]");
                        nuovoInsegnante.setNome("[nome]");
                        nuovoInsegnante.setIdClasse(getSelectedClasse());
                        nuovoInsegnante.setIdAnnoScolastico(getSelectedAS());
                        nuovoInsegnante.setAnnoScolastico(getSelectedAS().getAnnoScolastico());
                        try {
                            getInsegnantiFacade().create(nuovoInsegnante);
                            int size = getListaInsegnanti().size();
                            setSelectedInsegnante(getListaInsegnanti().get(size - 1));
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare l' INSEGNANTE:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
                break;
            case 8://MATERIE
                if (isCreateAction()) {
                    if (getMaterieFacade() != null) {
                        Materie nuovaMateria = new Materie();
                        Long nextId = getMaterieFacade().getNextId();

                        nuovaMateria.setIdMateria(nextId);
                        nuovaMateria.setIdClasse(getSelectedClasse());
                        nuovaMateria.setIdAnnoScolastico(getSelectedAS());
                        nuovaMateria.setAnnoScolastico(getSelectedAS().getAnnoScolastico());
                        nuovaMateria.setIdInsegnante(getSelectedInsegnante());
                        nuovaMateria.setMateria("[MATERIA]");
//                        nuovaMateria.set
                        try {
                            getMaterieFacade().create(nuovaMateria);
                            int size = getListaMaterie().size();
                            setSelectedMateria(getListaMaterie().get(size - 1));
                        } catch (Exception ejbex) {
                            JsfUtil.addErrorMessage("Non è stato possibile creare la MATERIA:"
                                    + ejbex.getMessage());
                            setCreateAction(false);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private Date getEndPeriodDate(Date startDate) {
        Date endDate = null;
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(startDate);
        if (periodType.equals("BIMESTRE")) {
            endCal.add(Calendar.MONTH, 2);
        } else if (periodType.equals("TRIMESTRE")) {
            endCal.add(Calendar.MONTH, 3);
        } else if (periodType.equals("QUADRIMESTRE")) {
            endCal.add(Calendar.MONTH, 4);
        } else if (periodType.equals("PENTAMESTRE")) {
            endCal.add(Calendar.MONTH, 5);
        } else if (periodType.equals("SEMESTRE")) {
            endCal.add(Calendar.MONTH, 6);
        } else if (periodType.equals("ANNUALITA'")) {
            endCal.add(Calendar.MONTH, 12);
        }

        endDate = endCal.getTime();
        return endDate;
    }

    /**
     * Finalizza le operazioni CANCELLA, MODIFICA, CREA nel database
     */
    public void commitAction() {
        try {
            switch (page) {
                case 1://SCUOLE
                    if (isModifyAction()) {
                        getScuoleFacade().updateScuola(this.selectedScuola);
                        this.selectedScuola = getScuoleFacade().find(selectedScuola.getIdScuola());
                    }
                    if (isCreateAction()) {
                        this.selectedScuola = getScuoleFacade().find(selectedScuola.getIdScuola());
                    }
                    if (isDeleteAction()) {
                        getScuoleFacade().remove(selectedScuola);
                        if (getListaScuole().size() > 0) {
                            selectedScuola = listaScuole.get(0);
                        }
                    }
                    break;
                case 2://ANNI SCOLASTICI
                    if (isModifyAction()) {
                        getAnniScolasticiFacade().updateAnnoScolastico(this.selectedAS);
                        this.selectedAS = getAnniScolasticiFacade().find(selectedAS.getIdAnnoScolastico());
                    }
                    if (isCreateAction()) {
                        this.selectedAS = getAnniScolasticiFacade().find(selectedAS.getIdAnnoScolastico());
                    }
                    if (isDeleteAction()) {
                        getAnniScolasticiFacade().remove(selectedAS);
                        if (getListaAS().size() > 0) {
                            selectedAS = listaAS.get(0);
                        }
                    }
                    break;
                case 3://PERIODI ANNO SCOLASTICO
                    if (isModifyAction()) {
                        if (getSelectedPeriod().getEndDate().after(getSelectedAS().getEndDate())) {
                            JsfUtil.addErrorMessage("Impossibile procedere nella modifica perchè la data è oltre la data di fine Anno Scolastico");
                            this.selectedPeriod = getPeriodiAnnoScolasticoFacade().find(selectedPeriod.getPeriodiAnnoScolasticoPK());
                            break;
                        }
                        if (getSelectedPeriod().getEndDate().before(getSelectedPeriod().getStartDate())) {
                            JsfUtil.addErrorMessage("Impossibile procedere nella modifica perchè la data precede la data di inizio Periodo Anno Scolastico");
                            this.selectedPeriod = getPeriodiAnnoScolasticoFacade().find(selectedPeriod.getPeriodiAnnoScolasticoPK());
                            break;
                        }
                        getPeriodiAnnoScolasticoFacade().updatePeriodoAnnoScolastico(getSelectedPeriod());
                        this.selectedPeriod = getPeriodiAnnoScolasticoFacade().find(selectedPeriod.getPeriodiAnnoScolasticoPK());
                    }
                    if (isCreateAction()) {
                        int size = getListaPeriodiAS().size();
                        selectedPeriod = listaPeriodiAS.get(size - 1);
                    }
                    if (isDeleteAction()) {
                        int size = getListaPeriodiAS().size();
                        if (size > 0) {//rimuove sempre l'ultimo periodo
                            getPeriodiAnnoScolasticoFacade().remove(getListaPeriodiAS().get(size - 1));
                        }
                        if (getListaPeriodiAS().size() > 0) {
                            selectedPeriod = listaPeriodiAS.get(0);
                        }
                    }
                    break;
                case 4://PARAMETRI ORARIO ANNO SCOLASTICO
                    if (isModifyAction()) {
                        getParametriOrarioAsFacade().updateParamsOrario(this.parametriOrarioAs);
                        this.parametriOrarioAs = getParametriOrarioAsFacade().findParametriOrarioAS(getSelectedAS());
                    }
                    if (isCreateAction()) {
                    }
                    if (isDeleteAction()) {
                        getParametriOrarioAsFacade().remove(getParametriOrarioAs());
                    }
                    break;
                case 5://CLASSI
                    if (isModifyAction()) {
                        getClassiFacade().updateDenominazioneClasse(this.selectedClasse);
                        this.selectedClasse = getClassiFacade().find(getSelectedClasse().getIdClasse());
                    }
                    if (isCreateAction()) {
                        int size = getListaClassi().size();
                        selectedClasse = listaClassi.get(size - 1);
                    }
                    if (isDeleteAction()) {
                        getClassiFacade().remove(selectedClasse);
                        if (getListaClassi().size() > 0) {
                            selectedClasse = listaClassi.get(0);
                        } else {
                            setSelectedClasse(null);
                        }
                    }
                    break;
                case 6://STUDENTE
                    if (isModifyAction()) {
                        getStudentiFacade().updateCognomeNomeStudente(this.selectedStudente);
                        getStudentiFacade().updateDataEntrata(selectedStudente, selectedStudente.getDataEntrata());
                        getStudentiFacade().updateRitiratoData(selectedStudente, selectedStudente.getRitiratoData());
                        this.selectedStudente = getStudentiFacade().find(getSelectedStudente().getIdStudente());
                    }
                    if (isCreateAction()) {
                        this.selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
                    }
                    if (isDeleteAction()) {
                        getStudentiFacade().remove(selectedStudente);
                        if (getListaStudenti().size() > 0) {
                            selectedStudente = listaStudenti.get(0);
                        } else {
                            setSelectedStudente(null);
                        }
                    }
                    break;
                case 7://INSEGNANTE
                    if (isModifyAction()) {
                        getInsegnantiFacade().updateCognomeNomeInsegnante(selectedInsegnante);
                        this.selectedInsegnante = getInsegnantiFacade().find(getSelectedInsegnante().getIdInsegnante());
                    }
                    if (isCreateAction()) {
                        this.selectedInsegnante = getInsegnantiFacade().find(getSelectedInsegnante().getIdInsegnante());
                    }
                    if (isDeleteAction()) {
                        getInsegnantiFacade().remove(selectedInsegnante);
                        if (getListaInsegnanti().size() > 0) {
                            selectedInsegnante = listaInsegnanti.get(0);
                        } else {
                            setSelectedInsegnante(null);
                        }
                    }
                    break;
                case 8://MATERIE
                    if (isModifyAction()) {
                        getMaterieFacade().updateMateria(selectedMateria);
                        this.selectedMateria = getMaterieFacade().find(getSelectedMateria().getIdMateria());
                    }
                    if (isCreateAction()) {
                        this.selectedMateria = getMaterieFacade().find(getSelectedMateria().getIdMateria());
                    }
                    if (isDeleteAction()) {
                        getMaterieFacade().remove(selectedMateria);
                        if (getListaMaterie().size() > 0) {
                            selectedMateria = listaMaterie.get(0);
                        } else {
                            setSelectedMateria(null);
                        }
                    }
                    break;
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
                case 1://SCUOLE
                    if (isModifyAction()) {
                        this.selectedScuola = getScuoleFacade().find(selectedScuola.getIdScuola());
                    }
                    if (isCreateAction()) {
                        getScuoleFacade().remove(selectedScuola);
//                        this.listaScuole = getScuoleFacade().findAll();
                        if (getListaScuole().size() > 0) {
                            selectedScuola = getListaScuole().get(0);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 2://ANNI SCOLASTICI
                    if (isModifyAction()) {
                        this.selectedAS = getAnniScolasticiFacade().find(selectedAS.getIdAnnoScolastico());
                    }
                    if (isCreateAction()) {
                        getAnniScolasticiFacade().remove(selectedAS);
                        if (getListaAS().size() > 0) {
                            selectedAS = getListaAS().get(0);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 3://PERIODI ANNO SCOLASTICO
                    if (isModifyAction()) {
                        this.selectedPeriod = getPeriodiAnnoScolasticoFacade().find(selectedPeriod.getPeriodiAnnoScolasticoPK());
                    }
                    if (isCreateAction()) {
                        getPeriodiAnnoScolasticoFacade().remove(selectedPeriod);
                        if (getListaPeriodiAS().size() > 0) {
                            selectedPeriod = getListaPeriodiAS().get(0);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 4://PARAMETRI ORARIO ANNO SCOLASTICO
                    if (isModifyAction()) {
                        this.parametriOrarioAs = getParametriOrarioAsFacade().findParametriOrarioAS(getSelectedAS());
                    }
                    if (isCreateAction()) {
                        getParametriOrarioAsFacade().remove(getParametriOrarioAs());
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 5://CLASSI
                    if (isModifyAction()) {
                        this.selectedClasse = getClassiFacade().find(selectedClasse.getIdClasse());
                    }
                    if (isCreateAction()) {
                        getClassiFacade().remove(getSelectedClasse());
                        if (getListaClassi().size() > 0) {
                            selectedClasse = getListaClassi().get(0);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 6://STUDENTE
                    if (isModifyAction()) {
                        this.selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
                    }
                    if (isCreateAction()) {
                        getStudentiFacade().remove(getSelectedStudente());
                        if (getListaStudenti().size() > 0) {
                            selectedStudente = getListaStudenti().get(0);
                        } else {
                            setSelectedStudente(null);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 7://INSEGNANTI
                    if (isModifyAction()) {
                        this.selectedInsegnante = getInsegnantiFacade().find(selectedInsegnante.getIdInsegnante());
                    }
                    if (isCreateAction()) {
                        getInsegnantiFacade().remove(getSelectedInsegnante());
                        if (getListaInsegnanti().size() > 0) {
                            selectedInsegnante = getListaInsegnanti().get(0);
                        } else {
                            setSelectedInsegnante(null);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
                case 8://MATERIE & REGISTRI
                    if (isModifyAction()) {
                        this.selectedMateria = getMaterieFacade().find(selectedMateria.getIdMateria());
                    }
                    if (isCreateAction()) {
                        getMaterieFacade().remove(getSelectedMateria());
                        if (getListaMaterie().size() > 0) {
                            selectedMateria = getListaMaterie().get(0);
                        } else {
                            setSelectedMateria(null);
                        }
                    }
                    if (isDeleteAction()) {
                    }
                    break;
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

    public String getAzioneInCorso() {
        return azioneInCorso;
    }

    public void setAzioneInCorso(String azioneInCorso) {
        this.azioneInCorso = azioneInCorso;
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
            if (hmenu.getId().equals("selectedPeriod")) {
                setSelectedPeriod((PeriodiAnnoScolastico) event.getNewValue());
            }
            if (hmenu.getId().equals("selectPeriodType")) {
                setPeriodType((String) event.getNewValue());
                JsfUtil.addSuccessMessage("Prossimo periodo impostato a: " + periodType);
            }
            if (hmenu.getId().equals("selectedClasse")) {
                setSelectedClasse((Classi) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedStudente")) {
                setSelectedStudente((Studenti) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedExistingStudente")) {
                setSelectedExistingStudente((Studenti) event.getNewValue());
                if (selectedStudente != null) {
                    this.selectedStudente.setCognome(selectedExistingStudente.getCognome());
                    this.selectedStudente.setNome(selectedExistingStudente.getNome());
                    if (isCreateAction()) {
                        getStudentiFacade().updateCognomeNomeStudente(selectedStudente);
                    }
                }
            }
            if (hmenu.getId().equals("selectedInsegnante")) {
                setSelectedInsegnante((Insegnanti) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedExistingInsegnante")) {
                setSelectedExistingInsegnante((Insegnanti) event.getNewValue());
                if (selectedInsegnante != null) {
                    this.selectedInsegnante.setCognome(selectedExistingInsegnante.getCognome());
                    this.selectedInsegnante.setNome(selectedExistingInsegnante.getNome());
                    if (isCreateAction()) {
                        getInsegnantiFacade().updateCognomeNomeInsegnante(selectedInsegnante);
                    }
                }
            }
            if (hmenu.getId().equals("selectedMateria")) {
                setSelectedMateria((Materie) event.getNewValue());
            }
            if (hmenu.getId().equals("selectedExistingMateria")) {
                setSelectedExistingMateria((Materie) event.getNewValue());
                if (selectedMateria != null) {
                    this.selectedMateria.setMateria(selectedExistingMateria.getMateria());
                    if (isCreateAction()) {
                        getMaterieFacade().updateMateria(selectedMateria);
                    }
                }
            }
            JsfUtil.addSuccessMessage(" modifica in corso... di " + hmenu.getId() + " a '" + event.getNewValue() + "'");

        }
        if (source instanceof HtmlInputText) {
            HtmlInputText intext = (HtmlInputText) source;
            switch (page) {
                case 1://SCUOLE
                    if (intext.getId().equals("page1_tipoScuolaAcronimo")) {
                        this.selectedScuola.setTipoScuolaAcronimo((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_nomeScuola")) {
                        this.selectedScuola.setNomeScuola((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_indirizzo")) {
                        this.selectedScuola.setIndirizzo((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_cap")) {
                        this.selectedScuola.setCap((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_città")) {
                        this.selectedScuola.setCittà((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_provincia")) {
                        this.selectedScuola.setProvincia((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_telefono")) {
                        this.selectedScuola.setTelefono((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_fax")) {
                        this.selectedScuola.setFax((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_email")) {
                        this.selectedScuola.setEmail((String) event.getNewValue());
                    }
                    if (intext.getId().equals("page1_web")) {
                        this.selectedScuola.setWeb((String) event.getNewValue());
                    }
                    break;
                case 4://PARAMETRI ORARIO ANNO SCOLASTICO
                    if (intext.getId().equals("page4_oraInizioLezioni")) {
                        this.parametriOrarioAs.setInizioLezioni((Date) event.getNewValue());
                    }
                    if (intext.getId().equals("page4_durataOraMinuti")) {
                        this.parametriOrarioAs.setDurataOraMinuti(Integer.parseInt(String.valueOf(event.getNewValue())));
                    }
                    if (intext.getId().equals("page4_durataIntervalloMinuti")) {
                        this.parametriOrarioAs.setDurataIntervalloMinuti(Integer.valueOf(String.valueOf(event.getNewValue())));
                    }
                    break;
                case 5://CLASSI
                    if (intext.getId().equals("page5_nomeClasse")) {
                        this.selectedClasse.setNomeClasse((String) event.getNewValue());
                    }
                    break;
                case 6://STUDENTI
                    if (intext.getId().equals("page6_cognome")) {
                        this.selectedStudente.setCognome((String) event.getNewValue());
                        getStudentiFacade().updateCognomeNomeStudente(selectedStudente);
                    }
                    if (intext.getId().equals("page6_nome")) {
                        this.selectedStudente.setNome((String) event.getNewValue());
                        getStudentiFacade().updateCognomeNomeStudente(selectedStudente);
                    }
                    break;
                case 7://INSEGNANTI
                    if (intext.getId().equals("page7_cognome")) {
                        this.selectedInsegnante.setCognome((String) event.getNewValue());
                        getInsegnantiFacade().updateCognomeNomeInsegnante(selectedInsegnante);
                    }
                    if (intext.getId().equals("page7_nome")) {
                        this.selectedInsegnante.setNome((String) event.getNewValue());
                        getInsegnantiFacade().updateCognomeNomeInsegnante(selectedInsegnante);
                    }
                    break;
                default:
                    break;
            }
            JsfUtil.addSuccessMessage(" modifica in corso... di " + intext.getId() + " a '" + event.getNewValue() + "'");
        }
        if (source instanceof HtmlInputTextarea) {
            HtmlInputTextarea intextarea = (HtmlInputTextarea) source;
            switch (page) {
                case 5://CLASSI
                    if (intextarea.getId().equals("page5_specializzazione")) {
                        this.selectedClasse.setSpecializzazione((String) event.getNewValue());
                    }
                    break;
                case 8://MATERIE
                    if (intextarea.getId().equals("page8_materia")) {
                        this.selectedMateria.setMateria((String) event.getNewValue());
                    }
                    if (isCreateAction()) {
                        getMaterieFacade().updateMateria(selectedMateria);
                    }
                    break;
                default:
                    break;
            }
            JsfUtil.addSuccessMessage(" modifica in corso... di " + intextarea.getId() + " a '" + event.getNewValue() + "'");
        }
        if (source instanceof UICalendar) {
            UICalendar uicalendar = (UICalendar) source;
            switch (page) {
                case 2://ANNI SCOLASTICI
                    if (uicalendar.getId().equals("page2_startDate")) {
                        this.selectedAS.setStartDate((Date) event.getNewValue());
                    }
                    if (uicalendar.getId().equals("page2_endDate")) {
                        this.selectedAS.setEndDate((Date) event.getNewValue());
                    }
                    break;
                case 3://PERIODI ANNO SCOLASTICO
                    if (uicalendar.getId().equals("page3_endPeriodDate")) {
                        this.selectedPeriod.setEndDate((Date) event.getNewValue());
                    }
                    break;
                case 6://STUDENTI
                    if (uicalendar.getId().equals("page6_dataEntrata")) {
                        this.selectedStudente.setDataEntrata((Date) event.getNewValue());
                    }
                    if (uicalendar.getId().equals("page6_ritiratoData")) {
                        this.selectedStudente.setRitiratoData((Date) event.getNewValue());
                    }
                    break;
                default:
                    break;
            }
            JsfUtil.addSuccessMessage(" modifica in corso... di " + uicalendar.getId() + " a '" + event.getNewValue() + "'");
        }
        if (source instanceof HtmlSelectBooleanCheckbox) {
            HtmlSelectBooleanCheckbox uicheckbox = (HtmlSelectBooleanCheckbox) source;
            switch (page) {
                case 8://MATERIE
                    if (uicheckbox.getId().equals("page8_giudizio")) {
                        this.selectedMateria.setGiudizio((short)(Boolean.parseBoolean(String.valueOf(event.getNewValue()))?1:0));
                    }
                    if (uicheckbox.getId().equals("page8_scritto")) {
                        this.selectedMateria.setScritto((short)(Boolean.parseBoolean(String.valueOf(event.getNewValue()))?1:0));
                    }
                    if (uicheckbox.getId().equals("page8_orale")) {
                        this.selectedMateria.setOrale((short)(Boolean.parseBoolean(String.valueOf(event.getNewValue()))?1:0));
                    }
                    if (uicheckbox.getId().equals("page8_pratico")) {
                        this.selectedMateria.setPratico((short)(Boolean.parseBoolean(String.valueOf(event.getNewValue()))?1:0));
                    }
                    break;
                default:
                    break;
            }
            JsfUtil.addSuccessMessage(" modifica in corso... di " + uicheckbox.getId() + " a '" + event.getNewValue() + "'");
        }
    }
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;
    @EJB
    LezioniFacade lezioniFacade;
    @EJB
    OreAssenzeFacade oreAssenzeFacade;

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }

    public LezioniFacade getLezioniFacade() {
        return lezioniFacade;
    }

    public OreAssenzeFacade getOreAssenzeFacade() {
        return oreAssenzeFacade;
    }
    private int contaAssenzePrecEntrata;

    public int getContaAssenzePrecEntrata() {
        contaAssenzeAllievoLezioniPrecEntrata();
        return contaAssenzePrecEntrata;
    }

    public void setContaAssenzePrecEntrata(int contaAssenzePrecEntrata) {
        this.contaAssenzePrecEntrata = contaAssenzePrecEntrata;
    }

    /**
     * Conta le assenze da inserire precedenti l'iscrizione
     */
    private void contaAssenzeAllievoLezioniPrecEntrata() {
        //        throw new UnsupportedOperationException("Not yet implemented");
        setContaAssenzePrecEntrata(0);
        Classi classe = selectedStudente.getIdClasse();
        List<Materie> listaMaterie = getMaterieFacade().findListaMaterieClasse(classe);
        for (Materie m : listaMaterie) {
            //Per ogni materia ottiene la lista delle lezioni precedenti alla data di entrata
            List<Lezioni> listaLezioniPrecEntrata = getLezioniFacade().findLezioniMateriaPrecData(m, selectedStudente.getDataEntrata());
            for (Lezioni l : listaLezioniPrecEntrata) {
                Long idLezione = l.getIdLezione();
                int ore = l.getOreLezione();
                if (selectedStudente.getAttivo() == (short)1
                        && l.getDataLezione().before(selectedStudente.getDataEntrata())) {
                    Long idStudente = selectedStudente.getIdStudente();

                    for (int i = 1; i <= ore; i++) {
                        OreAssenze entity;
                        entity = new OreAssenze(idLezione, i, idStudente);
                        entity.setAssenza((short)1);
                        if (!getOreAssenzeFacade().existsEntity(entity)) {
                            contaAssenzePrecEntrata++;
                        }
                    }

                }

            }
        }
    }

    /**
     * Imposta le assenze precedenti l'iscrizione
     */
    public void impostaAssenzeAllievoLezioniPrecEntrata() {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Classi classe = selectedStudente.getIdClasse();
        List<Materie> listaMaterie = getMaterieFacade().findListaMaterieClasse(classe);
        for (Materie m : listaMaterie) {
            //Per ogni materia ottiene la lista delle lezioni precedenti alla data di entrata
            List<Lezioni> listaLezioniPrecEntrata = getLezioniFacade().findLezioniMateriaPrecData(m, selectedStudente.getDataEntrata());
            for (Lezioni l : listaLezioniPrecEntrata) {
                Long idLezione = l.getIdLezione();
                int ore = l.getOreLezione();
                if (selectedStudente.getAttivo() == (short)1
                        && l.getDataLezione().before(selectedStudente.getDataEntrata())) {
                    Long idStudente = selectedStudente.getIdStudente();

                    for (int i = 1; i <= ore; i++) {
                        OreAssenze entity;
                        entity = new OreAssenze(idLezione, i, idStudente);
                        entity.setAssenza((short)1);
                        if (!getOreAssenzeFacade().existsEntity(entity)) {
                            getOreAssenzeFacade().create(entity);
                            messageCreataAssenza(i, selectedStudente, l, classe, m);
                        }
                    }

                }

            }
        }
    }
    private int contaAssenzeSuccRitiro;

    public int getContaAssenzeSuccRitiro() {
        contaAssenzeAllievoLezioniSuccRitiro();
        return contaAssenzeSuccRitiro;
    }

    public void setContaAssenzeSuccRitiro(int contaAssenzeSuccRitiro) {
        this.contaAssenzeSuccRitiro = contaAssenzeSuccRitiro;
    }

    /**
     * Conta le assenze da inserire successive al ritiro
     */
    private void contaAssenzeAllievoLezioniSuccRitiro() {
//        throw new UnsupportedOperationException("Not yet implemented");
        setContaAssenzeSuccRitiro(0);
        Classi classe = selectedStudente.getIdClasse();
        List<Materie> listaMaterie = getMaterieFacade().findListaMaterieClasse(classe);
        for (Materie m : listaMaterie) {
            //Per ogni materia ottiene la lista delle lezioni precedenti alla data di entrata
            List<Lezioni> listaLezioniSuccRitirato = getLezioniFacade().findLezioniMateriaSuccData(m, selectedStudente.getRitiratoData());
            for (Lezioni l : listaLezioniSuccRitirato) {
                Long idLezione = l.getIdLezione();
                int ore = l.getOreLezione();
                if (selectedStudente.getAttivo() == (short)0
                        && l.getDataLezione().after(selectedStudente.getRitiratoData())) {
                    Long idStudente = selectedStudente.getIdStudente();

                    for (int i = 1; i <= ore; i++) {
                        OreAssenze entity;
                        entity = new OreAssenze(idLezione, i, idStudente);
                        entity.setAssenza((short)1);
                        if (!getOreAssenzeFacade().existsEntity(entity)) {
                            contaAssenzeSuccRitiro++;
                        }
                    }

                }

            }
        }
    }

    /**
     * Imposta le assenze successive al ritiro
     */
    public void impostaAssenzeAllievoLezioniSuccRitiro() {
//        throw new UnsupportedOperationException("Not yet implemented");
        Classi classe = selectedStudente.getIdClasse();
        List<Materie> listaMaterie = getMaterieFacade().findListaMaterieClasse(classe);
        for (Materie m : listaMaterie) {
            //Per ogni materia ottiene la lista delle lezioni precedenti alla data di entrata
            List<Lezioni> listaLezioniSuccRitirato = getLezioniFacade().findLezioniMateriaSuccData(m, selectedStudente.getRitiratoData());
            for (Lezioni l : listaLezioniSuccRitirato) {
                Long idLezione = l.getIdLezione();
                int ore = l.getOreLezione();
                if (selectedStudente.getAttivo() == (short)0
                        && l.getDataLezione().after(selectedStudente.getRitiratoData())) {
                    Long idStudente = selectedStudente.getIdStudente();

                    for (int i = 1; i <= ore; i++) {
                        OreAssenze entity;
                        entity = new OreAssenze(idLezione, i, idStudente);
                        entity.setAssenza((short)1);
                        if (!getOreAssenzeFacade().existsEntity(entity)) {
                            getOreAssenzeFacade().create(entity);
                            messageCreataAssenza(i, selectedStudente, l, classe, m);
                        }
                    }

                }

            }
        }
    }

    private void messageCreataAssenza(int ora, Studenti selectedStudente, Lezioni l, Classi classe, Materie m) {
//        throw new UnsupportedOperationException("Not yet implemented");
        String msg = ResourceBundle.getBundle("/resources/Registro").getString("CreataAssenzaMsgString")
                + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                + " Classe:" + classe.getNomeClasse()
                + " Materia:" + m.getMateria()
                + " in Data: " + l.getDataLezione().toString()
                + " alla " + ora + "a Ora";
        JsfUtil.addSuccessMessage(msg);
        JsfUtil.registraEventoNelDatabase(msg,
                ResourceBundle.getBundle("/resources/Registro").getString("CreataAssenzaTypeString"),
                getUtentiLoggerFacade());

    }
}
