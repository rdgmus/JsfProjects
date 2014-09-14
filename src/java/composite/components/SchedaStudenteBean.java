/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import compositecomponents.records.AssenzeStudenteRecord;
import compositecomponents.records.VotiStudenteRecord;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import jpa.entities.Classi;
import jpa.entities.Lezioni;
import jpa.entities.Materie;
import jpa.entities.OreAssenze;
import jpa.entities.PeriodiAnnoScolastico;
import jpa.entities.Studenti;
import jpa.entities.VotiLezioniStudente;
import jpa.session.LezioniFacade;
import jpa.session.MaterieFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.PeriodiAnnoScolasticoFacade;
import jpa.session.StudentiFacade;
import jpa.session.UtentiLoggerFacade;
import jpa.session.VotiLezioniStudenteFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.UICalendar;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "schedaStudenteBean")
@SessionScoped
public class SchedaStudenteBean implements Serializable, ValueChangeListener {

    private String cognome, nome;
    private Studenti selectedStudente;
    private Date dataEntrata, ritiratoData;
    private boolean attivo;
    private Object locale;
    private boolean popup = true;
    private String pattern = "dd MMM yyyy";
    private boolean showApply = true;
    private boolean editable = false;
    private boolean insertingNew = false;
    private boolean updatable = false;
    private long id;
    @EJB
    StudentiFacade studentiFacade;
    private boolean dataEntrataChanged = false;
    private boolean ritiratoDataChanged = false;
    private boolean cognomeChanged = false;
    private boolean nomeChanged = false;
    private Studenti oldSelectedStudente;
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;
    @EJB
    MaterieFacade materieFacade;
    @EJB
    LezioniFacade lezioniFacade;
    @EJB
    OreAssenzeFacade oreAssenzeFacade;
    private Date assenzeDal, assenzeAl;
    @EJB
    PeriodiAnnoScolasticoFacade periodiAnnoScolasticoFacade;
    private List<AssenzeStudenteRecord> recordsAssenze;
    private List<Materie> listaMaterieStudente;
    private Materie selectedMateria;
    private Long idLezione, idStudente;
    private List<VotiStudenteRecord> recordsVotiStudente;

    public SchedaStudenteBean() {
    }

    public StudentiFacade getStudentiFacade() {
        return studentiFacade;
    }

    public Studenti getSelectedStudente() {
        return selectedStudente;
    }

    public void setSelectedStudente(Studenti selectedStudente) {
        this.selectedStudente = selectedStudente;
        if (selectedStudente != null) {
            loadFields();
            setListaMaterieStudente(null);
        }
    }

    public boolean isInsertingNew() {
        return insertingNew;
    }

    public Studenti getOldSelectedStudente() {
        return oldSelectedStudente;
    }

    public void setOldSelectedStudente(Studenti oldSelectedStudente) {
        this.oldSelectedStudente = oldSelectedStudente;
    }

    public void setInsertingNew(boolean insertingNew) {
        if (insertingNew) {
            setEditable(true);
            Studenti newStudent = new Studenti();
            Long nextId = getStudentiFacade().getNextId();
//            Long nextId = new Long(String.valueOf(maxId.intValue() + 1));
            newStudent.setIdStudente(nextId);
            newStudent.setCognome("[cognome]");
            newStudent.setNome("[nome]");
            newStudent.setIdClasse(selectedStudente.getIdClasse());
            newStudent.setAnnoScolastico(selectedStudente.getAnnoScolastico());
            setOldSelectedStudente(selectedStudente);
            setSelectedStudente(newStudent);
        } else {
            setEditable(false);
            if (oldSelectedStudente != null) {
                setSelectedStudente(oldSelectedStudente);
                setOldSelectedStudente(null);
            }
        }
        this.insertingNew = insertingNew;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        if (updatable) {
            setEditable(true);
        } else {
            setEditable(false);
            selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
            loadFields();
        }
        this.updatable = updatable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataEntrata() {
        return dataEntrata;
    }

    public void setDataEntrata(Date dataEntrata) {
        this.dataEntrata = dataEntrata;
    }

    public Date getRitiratoData() {
        return ritiratoData;
    }

    public void setRitiratoData(Date ritiratoData) {
        this.ritiratoData = ritiratoData;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
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

    public boolean isDataEntrataChanged() {
        return dataEntrataChanged;
    }

    public void setDataEntrataChanged(boolean dataEntrataChanged) {
        this.dataEntrataChanged = dataEntrataChanged;
    }

    public boolean isRitiratoDataChanged() {
        return ritiratoDataChanged;
    }

    public void setRitiratoDataChanged(boolean ritiratoDataChanged) {
        this.ritiratoDataChanged = ritiratoDataChanged;
    }

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }

    public void updateStudent() {
        if (dataEntrataChanged) {
            //Deve eliminare le assenze eventualmente create fino alla precedente data di iscrizione
            //e riportare la situazione nella condizione precedente
            getStudentiFacade().updateDataEntrata(selectedStudente, dataEntrata);
            //Deve porre assente l'allievo per tutte le lezioni precedenti la data di entrata
            selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
            impostaAssenzeAllievoLezioniPrecEntrata(dataEntrata, selectedStudente);

            String msg = null;
            if (dataEntrata != null) {
                msg = ResourceBundle.getBundle("/resources/Registro").getString("DataEntrataChangedMsgString")
                        + " Studente:" + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                        + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                        + " A.S.:" + selectedStudente.getAnnoScolastico()
                        + " Data:" + dataEntrata.toString();
            } else {
                msg = ResourceBundle.getBundle("/resources/Registro").getString("DataEntrataChangedMsgString")
                        + " Studente:" + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                        + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                        + " A.S.:" + selectedStudente.getAnnoScolastico()
                        + " Data: annullata!";
            }

            JsfUtil.addSuccessMessage(msg);
            JsfUtil.registraEventoNelDatabase(msg,
                    ResourceBundle.getBundle("/resources/Registro").getString("DataEntrataChangedTypeString"),
                    getUtentiLoggerFacade());


            selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
            dataEntrataChanged = false;
        }
        if (ritiratoDataChanged) {
            //Deve eliminare le assenze eventualmente create a partire dalla precedente data di ritiro
            //e riportare la situazione nella condizione precedente
            getStudentiFacade().updateRitiratoData(selectedStudente, ritiratoData);
            //Devo porre assente l'allievo per tutte le lezioni a partire dalla data di ritiro
            //già presenti nel database
            selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
            impostaAssenzeAllievoLezioniSuccRitiro(ritiratoData, selectedStudente);

            String msg = null;
            if (ritiratoData != null) {
                msg = ResourceBundle.getBundle("/resources/Registro").getString("RitiratoDataChangedMsgString")
                        + " Studente:" + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                        + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                        + " A.S.:" + selectedStudente.getAnnoScolastico()
                        + " Data:" + ritiratoData.toString();
            } else {
                msg = ResourceBundle.getBundle("/resources/Registro").getString("RitiratoDataChangedMsgString")
                        + " Studente:" + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                        + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                        + " A.S.:" + selectedStudente.getAnnoScolastico()
                        + " Data: annullata!";
            }

            JsfUtil.addSuccessMessage(msg);
            JsfUtil.registraEventoNelDatabase(msg,
                    ResourceBundle.getBundle("/resources/Registro").getString("RitiratoDataChangedTypeString"),
                    getUtentiLoggerFacade());


            selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());
            ritiratoDataChanged = false;
        }
        if (cognomeChanged || nomeChanged) {
            getStudentiFacade().updateCognomeNomeStudente(selectedStudente);
            selectedStudente = getStudentiFacade().find(selectedStudente.getIdStudente());

            String msg = ResourceBundle.getBundle("/resources/Registro").getString("CognomeOrNomeStudenteChangedMsgString")
                    + " Studente:" + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                    + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                    + " A.S.:" + selectedStudente.getAnnoScolastico();

            JsfUtil.addSuccessMessage(msg);
            JsfUtil.registraEventoNelDatabase(msg,
                    ResourceBundle.getBundle("/resources/Registro").getString("CognomeOrNomeStudenteChangedTypeString"),
                    getUtentiLoggerFacade());

            cognomeChanged = false;
            nomeChanged = false;
        }
        setUpdatable(false);
        setEditable(false);
        loadFields();
    }

    public void insertNewStudent() {
        selectedStudente.setAnnoScolastico(oldSelectedStudente.getAnnoScolastico());
        selectedStudente.setIdAnnoScolastico(oldSelectedStudente.getIdAnnoScolastico());
        selectedStudente.setAttivo(true);
        Calendar cal = Calendar.getInstance();
        selectedStudente.setDataEntrata(cal.getTime());
        selectedStudente.setIdClasse(oldSelectedStudente.getIdClasse());
        selectedStudente.setCognome(cognome.toUpperCase());
        selectedStudente.setNome(nome.toUpperCase());
        getStudentiFacade().create(selectedStudente);
        setInsertingNew(false);
        setEditable(false);
        if (selectedStudente != null) {
            loadFields();
        }
    }

    private void loadFields() {
        if (selectedStudente != null) {
            setId(selectedStudente.getIdStudente());
            setCognome(selectedStudente.getCognome());
            setNome(selectedStudente.getNome());
            setAttivo(selectedStudente.getAttivo());
            setDataEntrata(selectedStudente.getDataEntrata());
            setRitiratoData(selectedStudente.getRitiratoData());
            setAssenzeDal(null);
            setAssenzeAl(null);
        }
    }

    public boolean isCognomeChanged() {
        return cognomeChanged;
    }

    public void setCognomeChanged(boolean cognomeChanged) {
        this.cognomeChanged = cognomeChanged;
    }

    public boolean isNomeChanged() {
        return nomeChanged;
    }

    public void setNomeChanged(boolean nomeChanged) {
        this.nomeChanged = nomeChanged;
    }

    public Long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(Long idLezione) {
        this.idLezione = idLezione;
    }

    public Long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(Long idStudente) {
        this.idStudente = idStudente;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        //        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        if (source instanceof UICalendar) {
            UICalendar cal = (UICalendar) source;
            if (cal.getId().equals("dataEntrata")) {
                if (event.getNewValue() == null
                        || (!event.getNewValue().equals(event.getOldValue()))) {
                    setDataEntrataChanged(true);
                }
            }
            if (cal.getId().equals("ritiratoData")) {
                if (event.getNewValue() == null
                        || (!event.getNewValue().equals(event.getOldValue()))) {
                    setRitiratoDataChanged(true);
                }
            }
            if (cal.getId().equals("assenzeDal")) {
                if (event.getNewValue() == null
                        || (!event.getNewValue().equals(event.getOldValue()))) {
                    Date newDate = (Date) event.getNewValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                    setRecordsAssenze(null);
                    setRecordsVotiStudente(null);
                    
                    JsfUtil.addSuccessMessage("Impostata data inizio periodo a : " + sdf.format(newDate));

                }
            }
            if (cal.getId().equals("assenzeAl")) {
                if (event.getNewValue() == null
                        || (!event.getNewValue().equals(event.getOldValue()))) {
                    Date newDate = (Date) event.getNewValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                    setRecordsAssenze(null);
                    setRecordsVotiStudente(null);

                    JsfUtil.addSuccessMessage("Impostata data fine periodo a : " + sdf.format(newDate));
                }
            }
        }
        if (source instanceof HtmlInputText) {
            HtmlInputText in = (HtmlInputText) source;
            if (in.getId().equals("cognome")) {
                setCognomeChanged(true);
                selectedStudente.setCognome(String.valueOf(event.getNewValue()));
            }
            if (in.getId().equals("nome")) {
                setNomeChanged(true);
                selectedStudente.setNome(String.valueOf(event.getNewValue()));
            }
        }
        if (source instanceof HtmlSelectOneMenu) {
            HtmlSelectOneMenu menu = (HtmlSelectOneMenu) source;
            if (menu.getId().equals("selectedMateria")) {
                setRecordsAssenze(null);
                setRecordsVotiStudente(null);
                Materie m = (Materie) event.getNewValue();
                JsfUtil.addSuccessMessage("Selezionata la materia: " + m.getMateria());
            }
            if (menu.getId().equals("giustificaRitardo")) {
                List<UIComponent> childrens = menu.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("idLezione")) {
                            Long bi = (Long) value;
                            setIdLezione(bi);
                        }
                        if (name.equals("idStudente")) {
                            Long bi = (Long) value;
                            setIdStudente(bi);
                        }
                    }
                }
                Boolean newValue = (Boolean) event.getNewValue();
                giustificaRitardo(newValue);
            }
            if (menu.getId().equals("giustificaAssenze")) {
                List<UIComponent> childrens = menu.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("idLezione")) {
                            Long bi = (Long) value;
                            setIdLezione(bi);
                        }
                        if (name.equals("idStudente")) {
                            Long bi = (Long) value;
                            setIdStudente(bi);
                        }
                    }
                }
                Boolean newValue = (Boolean) event.getNewValue();
                giustificaAssenza(newValue);
            }
        }
        if (source instanceof HtmlInputTextarea) {
            HtmlInputTextarea textArea = (HtmlInputTextarea) source;
            if (textArea.getId().equals("motivoGiustifica")) {
                List<UIComponent> childrens = textArea.getChildren();
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        String name = p.getName();
                        Object value = p.getValue();
                        if (name.equals("idLezione")) {
                            Long bi = (Long) value;
                            setIdLezione(bi);
                        }
                        if (name.equals("idStudente")) {
                            Long bi = (Long) value;
                            setIdStudente(bi);
                        }
                    }
                }
                String newValue = (String) event.getNewValue();
//                for()
//                motivoGiustifica(newValue);
            }
        }
    }

    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public LezioniFacade getLezioniFacade() {
        return lezioniFacade;
    }

    public OreAssenzeFacade getOreAssenzeFacade() {
        return oreAssenzeFacade;
    }

    private void impostaAssenzeAllievoLezioniPrecEntrata(Date dataEntrata, Studenti selectedStudente) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Classi classe = selectedStudente.getIdClasse();
        List<Materie> listaMaterie = getMaterieFacade().findListaMaterieClasse(classe);
        for (Materie m : listaMaterie) {
            //Per ogni materia ottiene la lista delle lezioni precedenti alla data di entrata
            List<Lezioni> listaLezioniPrecEntrata = getLezioniFacade().findLezioniMateriaPrecData(m, dataEntrata);
            for (Lezioni l : listaLezioniPrecEntrata) {
                Long idLezione = l.getIdLezione();
                int ore = l.getOreLezione().intValue();
                if (selectedStudente.getAttivo() == true
                        && l.getDataLezione().before(selectedStudente.getDataEntrata())) {
                    Long idStudente = selectedStudente.getIdStudente();

                    for (int i = 1; i <= ore; i++) {
                        OreAssenze entity;
                        entity = new OreAssenze(idLezione, i, idStudente);
                        entity.setAssenza(true);
                        if (!getOreAssenzeFacade().existsEntity(entity)) {
                            getOreAssenzeFacade().create(entity);
                            messageCreataAssenza(i, selectedStudente, l, classe, m);
                        }
                    }

                }

            }
        }
    }

    private void impostaAssenzeAllievoLezioniSuccRitiro(Date ritiratoData, Studenti selectedStudente) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Classi classe = selectedStudente.getIdClasse();
        List<Materie> listaMaterie = getMaterieFacade().findListaMaterieClasse(classe);
        for (Materie m : listaMaterie) {
            //Per ogni materia ottiene la lista delle lezioni precedenti alla data di entrata
            List<Lezioni> listaLezioniSuccRitirato = getLezioniFacade().findLezioniMateriaSuccData(m, ritiratoData);
            for (Lezioni l : listaLezioniSuccRitirato) {
                Long idLezione = l.getIdLezione();
                int ore = l.getOreLezione().intValue();
                if (selectedStudente.getAttivo() == false
                        && l.getDataLezione().after(selectedStudente.getRitiratoData())) {
                    Long idStudente = selectedStudente.getIdStudente();

                    for (int i = 1; i <= ore; i++) {
                        OreAssenze entity;
                        entity = new OreAssenze(idLezione, i, idStudente);
                        entity.setAssenza(true);
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

    public String riepilogoAssenzeStudente() {
        setRecordsAssenze(null);
        return "/registro/RiepilogoAssenze";
    }

    public String riepilogoVotiStudente() {
//        setRecordsAssenze(null);
        return "/registro/RiepilogoVoti";
    }

    public String backToScegliRegistro() {
//        recordsAssenze = null;
//        selectedMateria = null;
        return "/registro/ScegliRegistroScolastico";
    }

    public PeriodiAnnoScolasticoFacade getPeriodiAnnoScolasticoFacade() {
        return periodiAnnoScolasticoFacade;
    }

    public Date getAssenzeDal() {
        if (assenzeDal == null) {
            assenzeDal = retrieveDataInizioAnnoScolastico();
        }
        return assenzeDal;
    }

    public void setAssenzeDal(Date assenzeDal) {
        if (assenzeDal != null && getAssenzeAl() != null && assenzeDal.after(getAssenzeAl())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("DataInizialeAfterDataFinale"));
            this.assenzeDal = retrieveDataInizioAnnoScolastico();
            return;
        }
        this.assenzeDal = assenzeDal;
    }

    public Date getAssenzeAl() {
        if (assenzeAl == null) {
            assenzeAl = retrieveDataFineAnnoScolastico();
        }
        return assenzeAl;
    }

    public void setAssenzeAl(Date assenzeAl) {
        if (assenzeAl != null && getAssenzeDal() != null && getAssenzeDal().after(assenzeAl)) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("DataFinaleBeforeDataIniziale"));
            this.assenzeAl = retrieveDataFineAnnoScolastico();
            return;
        }
        this.assenzeAl = assenzeAl;
    }

    public List<VotiStudenteRecord> getRecordsVotiStudente() {
        if (recordsVotiStudente == null) {
            initRecordsVotiStudente();
        }
        return recordsVotiStudente;
    }

    public void setRecordsVotiStudente(List<VotiStudenteRecord> recordsVotiStudente) {
        this.recordsVotiStudente = recordsVotiStudente;
    }

    public List<AssenzeStudenteRecord> getRecordsAssenze() {
        if (recordsAssenze == null) {
            initRecordsAssenze();
        }
        return recordsAssenze;
    }

    public void setRecordsAssenze(List<AssenzeStudenteRecord> recordsAssenze) {
        this.recordsAssenze = recordsAssenze;
    }

    /**
     * A QUESTO PUNTO DEVO RECUPERARE LE INFORMAZIONI CONCERNENTI I VOTI
     * CONSEGUITI DALLO STUDENTE
     */
    private void initRecordsVotiStudente() {
        if (getAssenzeDal() == null || getAssenzeAl() == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("PeriodoAssenzeNotValorized"));
            return;
        }
        if (getAssenzeDal().after(getAssenzeAl())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("PeriodoAssenzeInvalid"));
            return;
        }
        recordsVotiStudente = new ArrayList<VotiStudenteRecord>();
        Classi classeStudente = getSelectedStudente().getIdClasse();
        //Materie della classe
        List<Materie> materieClasse = getMaterieFacade().findListaMaterieClasse(classeStudente);
        setListaMaterieStudente(materieClasse);
        //Lezioni per ogni materia
        for (Materie m : materieClasse) {
            if (selectedMateria != null) {
                if (m.equals(selectedMateria)) {
                    addVotiLezioneToRecords(m);
                    return;
                }
            } else {
                addVotiLezioneToRecords(m);
            }
        }

    }

    /**
     * A QUESTO PUNTO DEVO RECUPERARE LE INFORMAZIONI CONCERNENTI LE ASSENZE E
     * RITARDI ACCUMULATI DALLO STUDENTE
     */
    private void initRecordsAssenze() {
        if (getAssenzeDal() == null || getAssenzeAl() == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("PeriodoAssenzeNotValorized"));
            return;
        }
        if (getAssenzeDal().after(getAssenzeAl())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("PeriodoAssenzeInvalid"));
            return;
        }
        recordsAssenze = new ArrayList<AssenzeStudenteRecord>();
        //A QUESTO PUNTO DEVO RECUPERARE LE INFORMAZIONI CONCERNENTI LE ASSENZE E RITARDI ACCUMULATI
        //DALLO STUDENTE
        //Classe di appartenenza dello studente
        Classi classeStudente = getSelectedStudente().getIdClasse();
        //Materie della classe
        List<Materie> materieClasse = getMaterieFacade().findListaMaterieClasse(classeStudente);
        setListaMaterieStudente(materieClasse);
        //Lezioni per ogni materia
        for (Materie m : materieClasse) {
            if (selectedMateria != null) {
                if (m.equals(selectedMateria)) {
                    addLezioniMateriaToRecords(m);
                    return;
                }
            } else {
                addLezioniMateriaToRecords(m);
            }
        }
    }

    public List<Materie> getListaMaterieStudente() {
        if (listaMaterieStudente == null) {
            Classi classeStudente = getSelectedStudente().getIdClasse();
            //Materie della classe
            listaMaterieStudente = getMaterieFacade().findListaMaterieClasse(classeStudente);
        }
        return listaMaterieStudente;
    }

    public void setListaMaterieStudente(List<Materie> listaMaterieStudente) {
        this.listaMaterieStudente = listaMaterieStudente;
    }

    public Materie getSelectedMateria() {
        return selectedMateria;
    }

    public void setSelectedMateria(Materie selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    private Date retrieveDataInizioAnnoScolastico() {
        Date inizioAnno = null;
        long idAS = selectedStudente.getIdAnnoScolastico();
        List<PeriodiAnnoScolastico> periodi = getPeriodiAnnoScolasticoFacade().findByIdAnnoScolastico(idAS);
        if (periodi != null) {
            int numPeriodi = periodi.size();
            if (numPeriodi > 0) {
                PeriodiAnnoScolastico firstPeriodo = periodi.get(0);
                inizioAnno = firstPeriodo.getStartDate();
            }
        }
        return inizioAnno;
    }

    private Date retrieveDataFineAnnoScolastico() {
        Date fineAnno = null;
        long idAS = selectedStudente.getIdAnnoScolastico();
        List<PeriodiAnnoScolastico> periodi = getPeriodiAnnoScolasticoFacade().findByIdAnnoScolastico(idAS);
        if (periodi != null) {
            int numPeriodi = periodi.size();
            PeriodiAnnoScolastico lastPeriodo = periodi.get(numPeriodi - 1);
            fineAnno = lastPeriodo.getEndDate();
        }
        return fineAnno;
    }

    /**
     * Reimposta le date del periodo in ragione delle date di inizio e fine anno
     * scolastico
     *
     * @return
     */
    public String resettaDateAnnoScolastico() {
        setAssenzeDal(null);
        setAssenzeAl(null);
        setRecordsAssenze(null);
        setRecordsVotiStudente(null);
        JsfUtil.addSuccessMessage("Reimpostate le date del periodo in ragione delle date di inizio e \n"
                + "fine anno scolastico");
        return "";
    }

    /**
     * Ricarica tutte le assenze o i voti nel periodo scelto, per tutte le
     * materie
     *
     * @return
     */
    public String resettaMaterie() {
        setSelectedMateria(null);
        setRecordsAssenze(null);
        setRecordsVotiStudente(null);
        JsfUtil.addSuccessMessage("Ricaricate tutte le assenze o i voti nel periodo scelto, per tutte le\n"
                + "materie");
        return "";
    }

    /**
     * Recupera le assenze nella materia dello studente
     *
     * @param m
     */
    private void addLezioniMateriaToRecords(Materie m) {
        List<Lezioni> lezioniPeriodo = getLezioniFacade().findLezioniPeriodo(m.getIdMateria(), assenzeDal, assenzeAl);
        //Assenze se esistenti, dello studente dalla lezione
        for (Lezioni l : lezioniPeriodo) {
            List<OreAssenze> oreAssenze = getOreAssenzeFacade().findAssenzeStudenteLezione(
                    l.getIdLezione(), getSelectedStudente().getIdStudente());
            if (oreAssenze.size() > 0) {
                AssenzeStudenteRecord rec = new AssenzeStudenteRecord();
                rec.setIdStudente(selectedStudente.getIdStudente());
                rec.setIdLezione(l.getIdLezione());
                rec.setMateria(m);
                rec.setOreLezione(l.getOreLezione());
                rec.setDataLezione(l.getDataLezione());
                for (OreAssenze ora : oreAssenze) {
                    switch (ora.getOreAssenzePK().getNumOra()) {
                        case 1:
                            rec.setAssenza1aOra(ora.getAssenza());
                            rec.setRitardo(ora.getRitardo());
                            break;
                        case 2:
                            rec.setAssenza2aOra(ora.getAssenza());
                            if (!rec.isRitardo()) {
                                rec.setRitardo(ora.getRitardo());
                            }
                            break;
                        case 3:
                            rec.setAssenza3aOra(ora.getAssenza());
                            if (!rec.isRitardo()) {
                                rec.setRitardo(ora.getRitardo());
                            }
                            break;
                    }
                }
                recordsAssenze.add(rec);
            }
        }
    }

    private void giustificaRitardo(Boolean newValue) {
//        throw new UnsupportedOperationException("Not yet implemented");
        for (AssenzeStudenteRecord rec : recordsAssenze) {
            if (rec.getIdStudente().equals(this.getIdStudente())
                    && rec.getIdLezione().equals(this.getIdLezione())) {
                if (newValue) {
                    if (rec.getMotivoGiustifica() == null || rec.getMotivoGiustifica().length() == 0) {
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("MotivoRitardoNonEspresso"));
                        setRecordsAssenze(null);
                        break;
                    } else {
                        String msg = ResourceBundle.getBundle("/resources/Registro").getString("GiustificaRitardoMsgString")
                                + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                                + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                                + " Materia:" + rec.getMateria().getMateria()
                                + " Data: " + rec.getDataLezione().toString()
                                + " Motivo: " + rec.getMotivoGiustifica();
                        JsfUtil.addSuccessMessage(msg);
                        JsfUtil.registraEventoNelDatabase(msg,
                                ResourceBundle.getBundle("/resources/Registro").getString("GiustificaRitardoTypeString"),
                                getUtentiLoggerFacade());
                        break;
                    }
                } else {
                    String msg = ResourceBundle.getBundle("/resources/Registro").getString("CancellaGiustificaRitardoMsgString")
                            + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                            + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                            + " Materia:" + rec.getMateria().getMateria()
                            + " Data: " + rec.getDataLezione().toString();
                    JsfUtil.addSuccessMessage(msg);
                    JsfUtil.registraEventoNelDatabase(msg,
                            ResourceBundle.getBundle("/resources/Registro").getString("CancellaGiustificaRitardoTypeString"),
                            getUtentiLoggerFacade());
                    break;
                }
            }
        }
    }

    private void giustificaAssenza(Boolean newValue) {
//        throw new UnsupportedOperationException("Not yet implemented");
        for (AssenzeStudenteRecord rec : recordsAssenze) {
            if (rec.getIdStudente().equals(this.getIdStudente())
                    && rec.getIdLezione().equals(this.getIdLezione())) {
                if (newValue) {
                    if (rec.getMotivoGiustifica() == null || rec.getMotivoGiustifica().length() == 0) {
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("MotivoAssenzaNonEspresso"));
                        setRecordsAssenze(null);
                        break;
                    } else {
                        String msg = ResourceBundle.getBundle("/resources/Registro").getString("GiustificaAssenzaMsgString")
                                + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                                + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                                + " Materia:" + rec.getMateria().getMateria()
                                + " Data: " + rec.getDataLezione().toString()
                                + " Motivo: " + rec.getMotivoGiustifica();
                        JsfUtil.addSuccessMessage(msg);
                        JsfUtil.registraEventoNelDatabase(msg,
                                ResourceBundle.getBundle("/resources/Registro").getString("GiustificaAssenzaTypeString"),
                                getUtentiLoggerFacade());
                        break;
                    }
                } else {
                    String msg = ResourceBundle.getBundle("/resources/Registro").getString("CancellaGiustificaAssenzaMsgString")
                            + selectedStudente.getCognome() + " " + selectedStudente.getNome()
                            + " Classe:" + selectedStudente.getIdClasse().getNomeClasse()
                            + " Materia:" + rec.getMateria().getMateria()
                            + " Data: " + rec.getDataLezione().toString();
                    JsfUtil.addSuccessMessage(msg);
                    JsfUtil.registraEventoNelDatabase(msg,
                            ResourceBundle.getBundle("/resources/Registro").getString("CancellaGiustificaAssenzaTypeString"),
                            getUtentiLoggerFacade());
                    break;
                }
            }
        }
    }
    @EJB
    VotiLezioniStudenteFacade votiLezioniStudenteFacade;

    public VotiLezioniStudenteFacade getVotiLezioniStudenteFacade() {
        return votiLezioniStudenteFacade;
    }

    private void addVotiLezioneToRecords(Materie m) {
//        throw new UnsupportedOperationException("Not yet implemented");
        List<Lezioni> lezioniPeriodo = getLezioniFacade().findLezioniPeriodo(m.getIdMateria(), assenzeDal, assenzeAl);
        //Assenze se esistenti, dello studente dalla lezione
        for (Lezioni l : lezioniPeriodo) {
            List<VotiLezioniStudente> votiLezione = getVotiLezioniStudenteFacade().findVotiStudenteLezione(
                    l.getIdLezione(), getSelectedStudente().getIdStudente());
            if (votiLezione.size() > 0) {
                for (VotiLezioniStudente v : votiLezione) {
                    VotiStudenteRecord rec = new VotiStudenteRecord();
                    rec.setIdStudente(v.getVotiLezioniStudentePK().getIdStudente());
                    rec.setIdLezione(v.getVotiLezioniStudentePK().getIdLezione());
                    rec.setMateria(m);
                    rec.setDataLezione(l.getDataLezione());
                    rec.setIdVoto(v.getVotiLezioniStudentePK().getIdVoto());
                    rec.setVotoString(v.getVotoString());
                    rec.setVotoNum(v.getVotoNum());
                    rec.setTipoVoto(v.getTipoVoto());
                    rec.setArgLezione(l.getArgomento());
                    recordsVotiStudente.add(rec);
                }
            }
        }
    }
}
