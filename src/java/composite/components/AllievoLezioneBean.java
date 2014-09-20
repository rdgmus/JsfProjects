/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import composite.components.records.AssenzePerOra;
import composite.components.records.RigaStudenteRecord;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import jpa.entities.LezioniMateria;
import jpa.entities.OreAssenze;
import jpa.entities.Studenti;
import jpa.entities.TipologiaGiudizi;
import jpa.entities.VotiLezioniStudente;
import jpa.session.LezioniMateriaFacade;
import jpa.session.MaterieFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.StudentiFacade;
import jpa.session.TipologiaGiudiziFacade;
import jpa.session.UtentiLoggerFacade;
import jpa.session.VotiLezioniStudenteFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.html.HtmlInputNumberSpinner;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "allievoLezioneBean")
@SessionScoped
public class AllievoLezioneBean implements Serializable, ValueChangeListener {

    private DataModel studentiItems;
    private LezioniMateria lezioneSelected;
    private int oreLezione;
    private List<RigaStudenteRecord> records = null;
    @EJB
    OreAssenzeFacade oreAssenzeFacade;
    private DataModel recordsModel;
    private Long idLezione;
    private Long idStudente;
    private int idOra;
    StudentiFacade studentiFacade;
    @EJB
    LezioniMateriaFacade lezioniMateriaFacade;
    private int row = 0;
    private int pageSize = 8;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    @EJB
    VotiLezioniStudenteFacade votiLezioniStudenteFacade;
    private Character tipoVoto = 'N';
    private boolean tipoVotoGiudizio = false;
    private double nuovoVotoValue = 6.0;
    private Studenti studenteAttivo;
    private List<VotiLezioniStudente> votiStudenteAttivo;
    private VotiLezioniStudente votoDaRemove;
    private boolean renderGestioneLezioni = true;
    private boolean renderGestioneVoti = false;
    @EJB
    TipologiaGiudiziFacade tipologiaGiudiziFacade;
    private List<TipologiaGiudizi> tipologiaGiudizi;
    private TipologiaGiudizi giudizioSelezionato;
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;

    public AllievoLezioneBean() {
    }

    public Character getTipoVoto() {
        if (tipoVoto == 'N') {
            this.tipoVoto = getMaterieFacade().getFirstTipoVotoEnabled(lezioneSelected.getIdMateria());
            tipoVotoGiudizio = (tipoVoto == 'G');
            JsfUtil.addSuccessMessage("Tipo voto settato a: " + tipoVoto);

        }
        return tipoVoto;
    }
    /**
     * Gestione delle tipologie di voto ammesse nella tabella MATERIE del
     * database
     *
     * @param tipoVoto
     * @return
     */
    private boolean tipVotoGiudizioEnabled = false;
    private boolean tipVotoScrittoEnabled = false;
    private boolean tipVotoOraleEnabled = false;
    private boolean tipVotoPraticoEnabled = false;
    private boolean someTipoVotoEnabled;
    @EJB
    MaterieFacade materieFacade;

    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public boolean isTipVotoGiudizioEnabled() {
        tipVotoGiudizioEnabled = getMaterieFacade().isTipoVotoEnabled(lezioneSelected.getIdMateria(), "G");
        return tipVotoGiudizioEnabled;
    }

    public void setTipVotoGiudizioEnabled(boolean tipVotoGiudizioEnabled) {
        this.tipVotoGiudizioEnabled = tipVotoGiudizioEnabled;
    }

    public boolean isTipVotoScrittoEnabled() {
        tipVotoScrittoEnabled = getMaterieFacade().isTipoVotoEnabled(lezioneSelected.getIdMateria(), "S");
        return tipVotoScrittoEnabled;
    }

    public void setTipVotoScrittoEnabled(boolean tipVotoScrittoEnabled) {
        this.tipVotoScrittoEnabled = tipVotoScrittoEnabled;
    }

    public boolean isTipVotoOraleEnabled() {
        tipVotoOraleEnabled = getMaterieFacade().isTipoVotoEnabled(lezioneSelected.getIdMateria(), "O");
        return tipVotoOraleEnabled;
    }

    public void setTipVotoOraleEnabled(boolean tipVotoOraleEnabled) {
        this.tipVotoOraleEnabled = tipVotoOraleEnabled;
    }

    public boolean isTipVotoPraticoEnabled() {
        tipVotoPraticoEnabled = getMaterieFacade().isTipoVotoEnabled(lezioneSelected.getIdMateria(), "P");
        return tipVotoPraticoEnabled;
    }

    public void setTipVotoPraticoEnabled(boolean tipVotoPraticoEnabled) {
        this.tipVotoPraticoEnabled = tipVotoPraticoEnabled;
    }

    public boolean isSomeTipoVotoEnabled() {
        someTipoVotoEnabled = isTipVotoGiudizioEnabled()
                || isTipVotoOraleEnabled()
                || isTipVotoPraticoEnabled()
                || isTipVotoScrittoEnabled();
        return someTipoVotoEnabled;
    }

    public void setSomeTipoVotoEnabled(boolean someTipoVotoEnabled) {
        this.someTipoVotoEnabled = someTipoVotoEnabled;
    }

    /**
     * Setta il tipo voto
     *
     * @param tipoVoto
     */
    public void setTipoVoto(Character tipoVoto) {
        if (this.tipoVoto.equals(tipoVoto)) {
            return;
        }
        tipoVotoGiudizio = (tipoVoto == 'G');
        this.tipoVoto = tipoVoto;
        JsfUtil.addSuccessMessage("Tipo voto cambiato a: " + tipoVoto);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void nextPage() {
        if ((row + pageSize) < records.size()) {
            row += pageSize;
        }
    }

    public void previousPage() {
        if ((row - pageSize) >= 0) {
            row -= pageSize;
        }
        if ((row - pageSize) < 0) {
            row = 0;
        }
    }

    public boolean isHasPreviousPage() {
        hasPreviousPage = (row > 0);
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        if (records != null) {
            hasNextPage = ((row + pageSize) < records.size());
        } else {
            hasNextPage = true;
        }
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public LezioniMateria getLezioneSelected() {
        return lezioneSelected;
    }

    public void setLezioneSelected(LezioniMateria lezioneSelected) {
        if (this.lezioneSelected == null || !this.lezioneSelected.equals(lezioneSelected)) {
            records = null;
        }
        if (lezioneSelected != null) {
            setOreLezione(lezioneSelected.getOreLezione());
        }

        this.lezioneSelected = lezioneSelected;
    }

    private void retrieveLezioneSelected() {
//        throw new UnsupportedOperationException("Not yet implemented");
        setLezioneSelected((LezioniMateria) JsfUtil.getSessionMapValue("lezioneSelected"));

    }

    private void retrieveStudentiLezione() {
//        throw new UnsupportedOperationException("Not yet implemented");
        setStudentiItems((DataModel) JsfUtil.getSessionMapValue("studentiItems"));
    }

    public DataModel getStudentiItems() {
        return studentiItems;
    }

    public void setStudentiItems(DataModel studentiItems) {
        this.studentiItems = studentiItems;
    }

    public int getOreLezione() {
        return oreLezione;
    }

    public void setOreLezione(int oreLezione) {
        this.oreLezione = oreLezione;
    }

    public List<RigaStudenteRecord> getRecords() {
        retrieveLezioneSelected();
        retrieveStudentiLezione();
        initRecords();
        return records;
    }

    public void setRecords(List<RigaStudenteRecord> records) {
        this.records = records;
    }

    private void initRecords() {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (lezioneSelected == null) {
            return;
        }
        records = new ArrayList<RigaStudenteRecord>();
//        Iterator iterator = studentiItems.iterator();
        int count = 1;
        for (int j = 0; j < studentiItems.getRowCount(); j++) {
            studentiItems.setRowIndex(j);
            Studenti studente = (Studenti) studentiItems.getRowData();
            //Carica le assenze dello studente

            List<OreAssenze> oreAssenze = getOreAssenzeFacade().findAssenzeStudenteLezione(
                    getLezioneSelected().getIdLezione(),
                    studente.getIdStudente());

            RigaStudenteRecord rec;
            rec = new RigaStudenteRecord();
            //VALORIZZA I CAMPI DEL RECORD
            rec.setRegistroIndex(count++);
            rec.setIdLezione(getLezioneSelected().getIdLezione());
            rec.setIdStudente(studente.getIdStudente());
            rec.setDataEntrata(studente.getDataEntrata());
            rec.setRitiratoData(studente.getRitiratoData());
            rec.setCognome(studente.getCognome());
            rec.setNome(studente.getNome());
            rec.setNota("");
            rec.setOreAssenze(buildAssenzePerOra(oreAssenze));
            rec.setVoti(buildListaVoti(getLezioneSelected().getIdLezione(), studente.getIdStudente()));
            //Aggiunto il 17/04/2013
            rec.setRitardo(existsRitardoInOra(oreAssenze, 1));
//            rec.setRenderRitardo(existsAssenzaSoloPrimaOra(oreAssenze));
            records.add(rec);
        }
    }

    public StudentiFacade getStudentiFacade() {
        if (studentiFacade == null) {
            RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().get("registriInsegnanteBean");

            // This only works if myBean2 is request scoped and already created.
            if (controller != null) {
                studentiFacade = controller.getStudentiFacade();
            }
        }
        return studentiFacade;
    }

    public OreAssenzeFacade getOreAssenzeFacade() {
        if (oreAssenzeFacade == null) {
            RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().get("registriInsegnanteBean");

            // This only works if myBean2 is request scoped and already created.
            if (controller != null) {
                oreAssenzeFacade = controller.getOreAssenzeFacade();
            }
        }
        return oreAssenzeFacade;
    }

    public void setOreAssenzeFacade(OreAssenzeFacade oreAssenzeFacade) {
        this.oreAssenzeFacade = oreAssenzeFacade;
    }

    private ArrayList<AssenzePerOra> buildAssenzePerOra(List<OreAssenze> oreAssenze) {
//            throw new UnsupportedOperationException("Not yet implemented");
        ArrayList<AssenzePerOra> assenzePerOra = new ArrayList<AssenzePerOra>();
        for (int n = 1; n <= getOreLezione(); n++) {
            boolean assenza = existsAssenzaInOra(oreAssenze, n);
            AssenzePerOra nuovaAssenza = new AssenzePerOra(n, assenza);
            assenzePerOra.add(nuovaAssenza);
        }
        return assenzePerOra;
    }

    private boolean existsAssenzaInOra(List<OreAssenze> oreAssenze, int n) {
//            throw new UnsupportedOperationException("Not yet implemented");
        boolean exists = false;
        for (OreAssenze oa : oreAssenze) {
            if (oa.getOreAssenzePK().getNumOra() == n) {
                exists = oa.getAssenza() != 0;
                break;
            }
        }

        return exists;
    }

    public DataModel getRecordsModel() {
        recordsModel = new ListDataModel(getRecords());
        return recordsModel;
    }

    public void setRecordsModel(DataModel recordsModel) {
        this.recordsModel = recordsModel;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
//        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        // PARAMETERS FOR UPDATING ASSENZE
        if (source instanceof HtmlSelectOneMenu) {//UPDATE ASSENZA
            HtmlSelectOneMenu cb = (HtmlSelectOneMenu) source;
            if (cb.getId().equals("selectOreAssenze")) {
                List<UIComponent> childrens = cb.getChildren();
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
                        if (name.equals("idOra")) {
                            Integer i = (Integer) value;
                            setIdOra(i);
                        }
                    }
                }
                Boolean newValue = (Boolean) event.getNewValue();
                updateAssenza(newValue);
            }
            if (cb.getId().equals("selectRitardo")) {
                List<UIComponent> childrens = cb.getChildren();
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
                        if (name.equals("idOra")) {
                            Integer i = new Integer(String.valueOf(value));
                            setIdOra(i);
                        }
                    }
                }
                Boolean newValue = (Boolean) event.getNewValue();
                updateRitardo(newValue);
            }
            if (cb.getId().equals("selectVotoDaRemove")) {
            }
            if (cb.getId().equals("selectGiudizio")) {
            }
        }
        if (source instanceof HtmlInputTextarea) {//UPDATE NOTA
            HtmlInputTextarea cb = (HtmlInputTextarea) source;
            List<UIComponent> childrens = cb.getChildren();
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
            String note = String.valueOf(event.getNewValue());
            updateNotaStudente(note);
        }
        if (source instanceof HtmlInputNumberSpinner) {//UPDATE NUMBER STUDENTS PAGE SIZE 
            HtmlInputNumberSpinner cb = (HtmlInputNumberSpinner) source;
            if (cb.getId().equals("numeroStudentiPerPage")) {//AFGGIUSTAMENTO NUMERO STUDENTI PER PAGINA
                setPageSize(Integer.parseInt(String.valueOf(event.getNewValue())));
            }
            if (cb.getId().equals("votoStudente")) {//INIZIALIZZA VOTO DA INFLIGGERE ALLO STUDENTE
                setNuovoVotoValue(Double.parseDouble(String.valueOf(event.getNewValue())));
            }
        }
        if (source instanceof HtmlSelectOneRadio) {//UPDATE TIPO VOTO
            HtmlSelectOneRadio oneRadio = (HtmlSelectOneRadio) source;
            if (oneRadio.getId().equals("tipoVoto")) {//AFGGIUSTAMENTO NUMERO STUDENTI PER PAGINA
                setTipoVoto((Character) event.getNewValue());
            }
        }
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

    public int getIdOra() {
        return idOra;
    }

    public void setIdOra(int idOra) {
        this.idOra = idOra;
    }

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }

    private void updateAssenza(Boolean newValue) {
//        throw new UnsupportedOperationException("Not yet implemented");
//        if (oreAssenzeFacade == null) {
//            getOreAssenzeFacade();
//        }
//        if (studentiFacade == null) {
//            getStudentiFacade();
//        }
        OreAssenze entity;
        entity = new OreAssenze(idLezione, idOra, idStudente);
        if (newValue) {
            try {
                entity.setAssenza(newValue ? (short) 1 : (short) 0);
                if (getOreAssenzeFacade().entityExistsWithDifferentAssenza(entity)) {
                    getOreAssenzeFacade().updateAssenza(entity, entity.getAssenza());
                } else {
                    getOreAssenzeFacade().create(entity);
                }
                Studenti studente = studentiFacade.find(idStudente);
                LezioniMateria lezione = lezioniMateriaFacade.find(idLezione);
                String msg = ResourceBundle.getBundle("/resources/Registro").getString("CreataAssenzaMsgString")
                        + studente.getCognome() + " " + studente.getNome()
                        + " Classe:" + lezione.getNomeClasse()
                        + " Materia:" + lezione.getMateria()
                        + " in Data: " + lezione.getDataLezione().toString()
                        + " alla " + getIdOra() + "a Ora";
                JsfUtil.addSuccessMessage(msg);
                JsfUtil.registraEventoNelDatabase(msg,
                        ResourceBundle.getBundle("/resources/Registro").getString("CreataAssenzaTypeString"),
                        getUtentiLoggerFacade());
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, e.getMessage());
            }
        } else {
            try {
                oreAssenzeFacade.remove(entity);
                Studenti studente = studentiFacade.find(idStudente);
                LezioniMateria lezione = lezioniMateriaFacade.find(idLezione);
                String msg = ResourceBundle.getBundle("/resources/Registro").getString("RimossaAssenzaMsgString")
                        + studente.getCognome() + " " + studente.getNome()
                        + " Classe:" + lezione.getNomeClasse()
                        + " Materia:" + lezione.getMateria()
                        + " in Data: " + lezione.getDataLezione().toString()
                        + " alla " + getIdOra() + "a Ora";
                JsfUtil.addSuccessMessage(msg);
                JsfUtil.registraEventoNelDatabase(msg,
                        ResourceBundle.getBundle("/resources/Registro").getString("RimossaAssenzaTypeString"),
                        getUtentiLoggerFacade());
            } catch (Exception e) {
                JsfUtil.addErrorMessage(e, e.getMessage());
            }
        }

        ricalcolaRiepilogo();
    }

    private List<VotiLezioniStudente> buildListaVoti(Long idLezione, Long idStudente) {
//        throw new UnsupportedOperationException("Not yet implemented");
        List<VotiLezioniStudente> votiLezioniStudente = null;
        votiLezioniStudente = votiLezioniStudenteFacade.findVotiStudenteLezione(idLezione, idStudente);

        return votiLezioniStudente;
    }

    private void updateNotaStudente(String note) {
//        throw new UnsupportedOperationException("Not yet implemented");
        JsfUtil.addErrorMessage("updateNotaStudente Not yet implemented");
    }

    public TipologiaGiudiziFacade getTipologiaGiudiziFacade() {
        return tipologiaGiudiziFacade;
    }

    public List<TipologiaGiudizi> getTipologiaGiudizi() {
        tipologiaGiudizi = getTipologiaGiudiziFacade().findAll();
        return tipologiaGiudizi;
    }

    public TipologiaGiudizi getGiudizioSelezionato() {
        return giudizioSelezionato;
    }

    public void setGiudizioSelezionato(TipologiaGiudizi giudizioSelezionato) {
        this.giudizioSelezionato = giudizioSelezionato;
    }

    public boolean isTipoVotoGiudizio() {
        return tipoVotoGiudizio;
    }

    public void setTipoVotoGiudizio(boolean tipoVotoGiudizio) {
        this.tipoVotoGiudizio = tipoVotoGiudizio;
    }

    public void inserisciVoto() {
        try {
            Long nextIdVoto = getVotiLezioniStudenteFacade().getNextIdVoto(
                    idLezione, idStudente);
            VotiLezioniStudente nuovoVoto = new VotiLezioniStudente(idLezione.longValue(), nextIdVoto, idStudente);
            nuovoVoto.setTipoVoto(tipoVoto);
            switch (tipoVoto) {
                case 'G':
                    nuovoVoto.setGiudizio((short) 1);
                    nuovoVoto.setVotoString(getGiudizioSelezionato().getGiudizio());
                    nuovoVoto.setVotoNum(getGiudizioSelezionato().getValueGiudizio());
                    break;
                default:
                    nuovoVoto.setGiudizio((short) 0);
                    nuovoVoto.setVotoNum(nuovoVotoValue);
                    break;
            }
            getVotiLezioniStudenteFacade().create(nuovoVoto);
            initRecords();
            String msg = ResourceBundle.getBundle("/resources/Registro").getString("AggiuntoVotoMsgString")
                    + (nuovoVoto.getVotoString() == null ? "" : nuovoVoto.getVotoString())
                    + " " + nuovoVoto.getVotoNum()
                    + " (" + nuovoVoto.getTipoVoto() + ") "
                    + " per lo Studente:" + getStudenteAttivo().getCognome()
                    + " " + getStudenteAttivo().getNome()
                    + " nella Lezione:" + getLezioneSelected().getDataLezione().toString()
                    + " della Materia:" + getLezioneSelected().getMateria();
            JsfUtil.addSuccessMessage(msg);
            JsfUtil.registraEventoNelDatabase(msg,
                    ResourceBundle.getBundle("/resources/Registro").getString("AggiuntoVotoTypeString"),
                    getUtentiLoggerFacade());

            ricalcolaRiepilogo();

        } catch (EJBException ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage() + " Cause:" + ex.getCausedByException());
        }
    }

    private void ricalcolaRiepilogo() {
//        throw new UnsupportedOperationException("Not yet implemented");
        RisultatiPeriodoBean controller = (RisultatiPeriodoBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("risultatiPeriodoBean");
        if (controller != null) {
            controller.setRecords(null);
        }
    }

    public VotiLezioniStudenteFacade getVotiLezioniStudenteFacade() {
        return votiLezioniStudenteFacade;
    }

    public void cancellaVoto() {
        if (votoDaRemove == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("NoVotiDaCancellare"));
        } else {
            try {
                getVotiLezioniStudenteFacade().remove(votoDaRemove);
                initRecords();
                String msg = ResourceBundle.getBundle("/resources/Registro").getString("CancellatoVotoMsgString")
                        + votoDaRemove.getVotoNum()
                        + " (" + votoDaRemove.getTipoVoto() + ") "
                        + " per lo Studente:" + getStudenteAttivo().getCognome()
                        + " " + getStudenteAttivo().getNome()
                        + " nella Lezione:" + getLezioneSelected().getDataLezione().toString()
                        + " della Materia:" + getLezioneSelected().getMateria();
                JsfUtil.addSuccessMessage(msg);
                JsfUtil.registraEventoNelDatabase(msg,
                        ResourceBundle.getBundle("/resources/Registro").getString("CancellatoVotoTypeString"),
                        getUtentiLoggerFacade());

                ricalcolaRiepilogo();

            } catch (EJBException ex) {
                JsfUtil.addErrorMessage(ex, ex.getMessage() + " Cause:" + ex.getCausedByException());
            }
        }
    }

    public VotiLezioniStudente getVotoDaRemove() {
        return votoDaRemove;
    }

    public void setVotoDaRemove(VotiLezioniStudente votoDaRemove) {
        this.votoDaRemove = votoDaRemove;
    }

    public void gestisciVotiStudente(Long idLezione, Long idStudente) {
        setIdLezione(idLezione);
        setIdStudente(idStudente);
        setRenderGestioneLezioni(false);
        setRenderGestioneVoti(true);
    }

    public List<VotiLezioniStudente> getVotiStudenteAttivo() {

        for (RigaStudenteRecord v : records) {
            if (v.getIdLezione() == getIdLezione() && v.getIdStudente() == getIdStudente()) {
                votiStudenteAttivo = v.getVoti();
                break;
            }
        }
        return votiStudenteAttivo;
    }

    public void setVotiStudenteAttivo(List<VotiLezioniStudente> votiStudenteAttivo) {
        this.votiStudenteAttivo = votiStudenteAttivo;
    }

    public double getNuovoVotoValue() {
        return nuovoVotoValue;
    }

    public void setNuovoVotoValue(double nuovoVotoValue) {
        this.nuovoVotoValue = nuovoVotoValue;
    }

    public void terminaGestisciVoti() {
        setRenderGestioneLezioni(true);
        setRenderGestioneVoti(false);
    }

    public Studenti getStudenteAttivo() {
        if (idStudente != null) {
            studenteAttivo = getStudentiFacade().find(idStudente);
        }

        return studenteAttivo;
    }

    public void setStudenteAttivo(Studenti studenteAttivo) {
        this.studenteAttivo = studenteAttivo;
    }

    public boolean isRenderGestioneLezioni() {
        return renderGestioneLezioni;
    }

    public void setRenderGestioneLezioni(boolean renderGestioneLezioni) {
        this.renderGestioneLezioni = renderGestioneLezioni;
    }

    public boolean isRenderGestioneVoti() {
        return renderGestioneVoti;
    }

    public void setRenderGestioneVoti(boolean renderGestioneVoti) {
        this.renderGestioneVoti = renderGestioneVoti;
    }

    private Boolean ritardoInOreAssenza(List<OreAssenze> oreAssenze) {
//        throw new UnsupportedOperationException("Not yet implemented");
        for (OreAssenze ora : oreAssenze) {
            if (ora.getRitardo() != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean existsRitardoInOra(List<OreAssenze> oreAssenze, int n) {
        boolean exists = false;
        for (OreAssenze oa : oreAssenze) {
            if (oa.getOreAssenzePK().getNumOra() == n) {
                exists = oa.getRitardo() != 0;
                break;
            }
        }

        return exists;

    }

    private void updateRitardo(Boolean newValue) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (oreAssenzeFacade == null) {
            getOreAssenzeFacade();
        }
        if (studentiFacade == null) {
            getStudentiFacade();
        }

        OreAssenze entity;
        entity = new OreAssenze(idLezione, idOra, idStudente);

        try {
            entity.setRitardo(newValue ? (short) 1 : (short) 0);
            oreAssenzeFacade.updateRitardo(entity);

            Studenti studente = studentiFacade.find(idStudente);
            LezioniMateria lezione = lezioniMateriaFacade.find(idLezione);
            if (newValue) {
                String msg = ResourceBundle.getBundle("/resources/Registro").getString("CreatoRitardoMsgString")
                        + studente.getCognome() + " " + studente.getNome()
                        + " Classe:" + lezione.getNomeClasse()
                        + " Materia:" + lezione.getMateria()
                        + " in Data: " + lezione.getDataLezione().toString()
                        + " alla " + getIdOra() + "a Ora";
                JsfUtil.addSuccessMessage(msg);
                JsfUtil.registraEventoNelDatabase(msg,
                        ResourceBundle.getBundle("/resources/Registro").getString("CreatoRitardoTypeString"),
                        getUtentiLoggerFacade());
            } else {
                String msg = ResourceBundle.getBundle("/resources/Registro").getString("RimossoRitardoMsgString")
                        + studente.getCognome() + " " + studente.getNome()
                        + " Classe:" + lezione.getNomeClasse()
                        + " Materia:" + lezione.getMateria()
                        + " in Data: " + lezione.getDataLezione().toString()
                        + " alla " + getIdOra() + "a Ora";
                JsfUtil.addSuccessMessage(msg);
                JsfUtil.registraEventoNelDatabase(msg,
                        ResourceBundle.getBundle("/resources/Registro").getString("RimossoRitardoTypeString"),
                        getUtentiLoggerFacade());
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
        }

    }

    private Boolean existsAssenzaSoloPrimaOra(List<OreAssenze> oreAssenze) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (oreAssenze.isEmpty()) {
            return false;
        }
        switch (getOreLezione()) {
            case 1:
                if (oreAssenze.get(0).getAssenza() != 0) {
                    return true;
                }
                break;
            case 2:
                if (oreAssenze.get(0).getAssenza() == (short) 1
                        && oreAssenze.get(1).getAssenza() == (short) 0) {
                    return true;
                }
                break;
            case 3:
                if (oreAssenze.get(0).getAssenza() == (short) 1
                        && oreAssenze.get(1).getAssenza() == (short) 0
                        && oreAssenze.get(2).getAssenza() == (short) 0) {
                    return true;
                }
                break;
        }
        return false;
    }
}
