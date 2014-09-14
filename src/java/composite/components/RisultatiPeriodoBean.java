/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import composite.components.records.RisultatiPeriodoRecord;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;
import jpa.entities.AllieviRisultatiPeriodo;
//import jpa.entities.AllieviRisultatiPeriodoPK;
import jpa.entities.Lezioni;
import jpa.entities.Materie;
import jpa.entities.OreAssenze;
import jpa.entities.PeriodiAnnoScolastico;
import jpa.entities.Studenti;
import jpa.entities.TipologiaGiudizi;
import jpa.entities.VotiLezioniStudente;
import jpa.session.AllieviRisultatiPeriodoFacade;
import jpa.session.LezioniFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.StudentiFacade;
import jpa.session.TipologiaGiudiziFacade;
import jpa.session.VotiLezioniStudenteFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.html.HtmlInputNumberSpinner;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "risultatiPeriodoBean")
@SessionScoped
public class RisultatiPeriodoBean implements Serializable, ValueChangeListener {

    private List<RisultatiPeriodoRecord> records = null;
    @EJB
    AllieviRisultatiPeriodoFacade allieviRisultatiPeriodoFacade;
    @EJB
    LezioniFacade lezioniFacade;
    @EJB
    OreAssenzeFacade oreAssenzeFacade;
    @EJB
    VotiLezioniStudenteFacade votiLezioniStudenteFacade;
    @EJB
    TipologiaGiudiziFacade tipologiaGiudiziFacade;
    private DataModel studentiItems;
    private PeriodiAnnoScolastico periodo;
    Long idPeriodo;
    Long idMateria;
    private Materie materia;
    private int row = 0;
    private int pageSize = 10;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private ArrayList<Double> scritto, orale, pratico, giudizio, condotta;
//    private List<AllieviRisultatiPeriodo> dbResult;
    private boolean viewAsProgressBar;
    private boolean insertingCondotta = false;

    public RisultatiPeriodoBean() {
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
        hasPreviousPage = (row > 0 || (records != null && (row + pageSize > records.size())));
        hasNextPage = !hasPreviousPage;
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
        hasPreviousPage = !hasNextPage;
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public AllieviRisultatiPeriodoFacade getAllieviRisultatiPeriodoFacade() {
        return allieviRisultatiPeriodoFacade;
    }

    public List<RisultatiPeriodoRecord> getRecords() {
        retrieveStudentiLezione();
        getPeriodo();
        getMateria();
        initRecords();
        return records;
    }

    public void setRecords(List<RisultatiPeriodoRecord> records) {
        this.records = records;
    }

    public PeriodiAnnoScolastico getPeriodo() {
        periodo = (PeriodiAnnoScolastico) JsfUtil.getSessionMapValue("periodoSelected");
        idPeriodo = periodo.getPeriodiAnnoScolasticoPK().getIdPeriodo();

        return periodo;
    }

    public Materie getMateria() {
        materia = (Materie) JsfUtil.getSessionMapValue("materiaSelected");
        idMateria = materia.getIdMateria();
        return materia;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public Long getIdMateria() {
        return idMateria;
    }
    /**
     * MEDIA PESATA
     */
    private boolean mediaPesata = false;

    public boolean isMediaPesata() {
        return mediaPesata;
    }

    public void setMediaPesata(boolean mediaPesata) {
        this.mediaPesata = mediaPesata;
    }

    /**
     * Calcola le assenze e le medie di tutti gli studenti della classe nella
     * materia e nel periodo selezionato
     */
    public void initRecords() {
        if (checkAlreadyLoaded()) {// && !isMediaPesata()) {
            return;
        }
//        records = new ArrayList<RisultatiPeriodoRecord>();

        /**
         * **************************
         */
        //Controlla se i record della materia e del periodo esistono già nella tabella
        //allievi_risultati periodo . Se esistono carica la ArrayList<RisultatiPeriodoRecord>
        //con tali record velocizzando l'operazione
//        if (!isMediaPesata()) {
//            if ((records == null) || !(records.size() == studentiItems.getRowCount())) {
//                records = new ArrayList<RisultatiPeriodoRecord>();
//                caricaRisultatiMateriaPeriodo();
//                JsfUtil.addSuccessMessage("Sono stati caricati i Records già presenti nella tabella allievi_risultati_periodo");
//            }
//            return;
//        }
        /**
         * **************************
         */
        if ((records != null) && (records.size() == studentiItems.getRowCount())) {
//            return;
        }
        records = new ArrayList<RisultatiPeriodoRecord>();
//        Iterator iterator;
//        iterator = studentiItems.iterator();
        Integer totOre = calcTotOrePeriodo(periodo, materia);
        Integer totGiudizi = calcMaxNumVotazioniTipo(periodo, materia, 'G');
        Integer totScritti = calcMaxNumVotazioniTipo(periodo, materia, 'S');
        Integer totOrali = calcMaxNumVotazioniTipo(periodo, materia, 'O');
        Integer totPratico = calcMaxNumVotazioniTipo(periodo, materia, 'P');

        int count = 1;
        for (int j = 0; j < studentiItems.getRowCount(); j++) {
            studentiItems.setRowIndex(j);
            Studenti studente = (Studenti) studentiItems.getRowData();
//        while (iterator.hasNext()) {
//            Studenti studente = (Studenti) iterator.next();
            //Carica le assenze dello studente
            Long idStudente = studente.getIdStudente();

            Integer assenzeStudente = countAssenze(studente, periodo, materia);

            RisultatiPeriodoRecord rec;
            rec = new RisultatiPeriodoRecord();
            //VALORIZZA I CAMPI DEL RECORD
            rec.setRegistroIndex(count++);
            rec.setIdStudente(idStudente);
            rec.setIdPeriodo(idPeriodo);
            rec.setIdMateria(idMateria);
            rec.setCognome(studente.getCognome());
            rec.setNome(studente.getNome());
            rec.setTotaleOrePeriodo(totOre);
            rec.setCountMaxNumGiudizio(totGiudizi);
            rec.setCountMaxNumScritto(totScritti);
            rec.setCountMaxNumPratico(totPratico);
            rec.setCountMaxNumOrale(totOrali);
            rec.setNumAssenze(assenzeStudente);
            rec.setDataEntrata(studente.getDataEntrata());
            rec.setRitiratoData(studente.getRitiratoData());
            double percAssenze;
            if (totOre == 0) {
                percAssenze = 0.0;
            } else {
                percAssenze = assenzeStudente.doubleValue() / totOre.doubleValue() * 100;
            }
            rec.setPercentualeAssenzePeriodo(roundDouble(percAssenze, 1));

            /*
             * Recupera in arraylists i voti dello studente nel periodo per la materia 
             */
            retrieveVotiStudente(studente, periodo, materia);

            rec.setScritto(calcVoto(scritto, 1, 'S', isMediaPesata(), rec.getCountMaxNumScritto()));

            rec.setOrale(calcVoto(orale, 1, 'O', isMediaPesata(), rec.getCountMaxNumOrale()));

            rec.setPratico(calcVoto(pratico, 1, 'P', isMediaPesata(), rec.getCountMaxNumPratico()));

            Double calcGiudizio = calcVoto(giudizio, 1, 'G', isMediaPesata(), rec.getCountMaxNumGiudizio());
            rec.setGiudizio(calcGiudizio);
            rec.setGiudizioAsString(renderAsStringGiudizio(calcGiudizio));

            rec.setCondotta(retrieveCondotta(idPeriodo, idStudente));//Condotta da decidere in sede di scrutinio
            rec.setCondottaStr(String.valueOf(rec.getCondotta().doubleValue()));
            //Controlla se il record è stato salvato nel database
            List<AllieviRisultatiPeriodo> result = getAllieviRisultatiPeriodoFacade().getRisultatiFor(idPeriodo, idMateria, idStudente);

            rec.setRecordWasSaved(result.size() == 1 && !foundDifferenceBetween(result.get(0), rec));

            records.add(rec);
        }
    }

    private void retrieveStudentiLezione() {
//        throw new UnsupportedOperationException("Not yet implemented");
        setStudentiItems((DataModel) JsfUtil.getSessionMapValue("studentiItems"));
    }

    public DataModel getStudentiItems() {
        return studentiItems;
    }

    public void setStudentiItems(DataModel studentiItems) {
        if (this.studentiItems == null || !this.studentiItems.equals(studentiItems)) {
            setRow(0);
            setHasNextPage(true);
            setHasPreviousPage(false);
        }
        this.studentiItems = studentiItems;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
//        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        if (source instanceof HtmlInputNumberSpinner) {//UPDATE NUMBER STUDENTS PAGE SIZE 
            HtmlInputNumberSpinner cb = (HtmlInputNumberSpinner) source;
            if (cb.getId().equals("numeroStudentiPerPage")) {//AFGGIUSTAMENTO NUMERO STUDENTI PER PAGINA
                setPageSize(Integer.valueOf(String.valueOf(event.getNewValue())).intValue());
            }
        }
        if (source instanceof HtmlSelectOneMenu) {
            HtmlSelectOneMenu inMenu = (HtmlSelectOneMenu) source;
            if (inMenu.getId().equals("condottaStudentePeriodo")) {
                List<UIComponent> childrens = inMenu.getChildren();
                Long idPeriodo = null, idStudente = null;
                RisultatiPeriodoRecord record = null;
                for (UIComponent c : childrens) {
                    if (c instanceof UIParameter) {
                        UIParameter p = (UIParameter) c;
                        if (p.getId().equals("idPeriodo")) {
                            idPeriodo = (Long) p.getValue();
                        }
                        if (p.getId().equals("idStudente")) {
                            idStudente = (Long) p.getValue();
                        }
                        if (p.getId().equals("record")) {
                            record = (RisultatiPeriodoRecord) p.getValue();
                        }
                    }
                }
                String newValue = (String) event.getNewValue();

                getAllieviRisultatiPeriodoFacade().updateCondotta(idPeriodo, idStudente,
                        newValue.equals("0.0") ? "n.c." : newValue);
                record.setCondotta(Double.valueOf(newValue));//Condotta da decidere in sede di scrutinio
                record.setCondottaStr(newValue.equals("0.0") ? "n.c." : newValue);

            }
        }
    }

    public LezioniFacade getLezioniFacade() {
        return lezioniFacade;
    }

    private Integer calcTotOrePeriodo(PeriodiAnnoScolastico periodo, Materie materia) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Integer countOre = null;
        Date startDate = periodo.getStartDate();
        Date endDate = periodo.getEndDate();
        countOre = getLezioniFacade().countOreMateriaPeriodo(materia.getIdMateria(), startDate, endDate);
        return countOre;
    }

    public OreAssenzeFacade getOreAssenzeFacade() {
        return oreAssenzeFacade;
    }

    private Integer countAssenze(Studenti studente, PeriodiAnnoScolastico periodo, Materie materia) {
//        throw new UnsupportedOperationException("Not yet implemented");
        int countAssenze = 0;
        Date startDate = periodo.getStartDate();
        Date endDate = periodo.getEndDate();
        List lezioniMateriaPeriodo
                = getLezioniFacade().findLezioniPeriodo(materia.getIdMateria(), startDate, endDate);
        Long idStudente = studente.getIdStudente();

        Iterator iter = lezioniMateriaPeriodo.iterator();
        while (iter.hasNext()) {
            Lezioni lez = (Lezioni) iter.next();
            Long idLezione = lez.getIdLezione();
            List<OreAssenze> oreAssenze = getOreAssenzeFacade().findAssenzeStudenteLezione(idLezione, idStudente);
            for (OreAssenze ore : oreAssenze) {
                if (ore.getAssenza()!=0) {
                    countAssenze++;
                }
            }
        }

        return countAssenze;
    }

    public VotiLezioniStudenteFacade getVotiLezioniStudenteFacade() {
        return votiLezioniStudenteFacade;
    }

    /**
     * Recupera in arraylists i voti dello studente nel periodo per la materia
     *
     * @param studente
     * @param periodo
     * @param materia
     */
    private void retrieveVotiStudente(Studenti studente, PeriodiAnnoScolastico periodo, Materie materia) {
//        throw new UnsupportedOperationException("Not yet implemented");
        scritto = new ArrayList<Double>();
        orale = new ArrayList<Double>();
        pratico = new ArrayList<Double>();
        giudizio = new ArrayList<Double>();
        condotta = new ArrayList<Double>();

        Date startDate = periodo.getStartDate();
        Date endDate = periodo.getEndDate();
        Long idStudente = studente.getIdStudente();

        List lezioniMateriaPeriodo
                = getLezioniFacade().findLezioniPeriodo(materia.getIdMateria(), startDate, endDate);

        Iterator iter = lezioniMateriaPeriodo.iterator();
        while (iter.hasNext()) {
            Lezioni lez = (Lezioni) iter.next();
            Long idLezione = lez.getIdLezione();
            List<VotiLezioniStudente> voti = getVotiLezioniStudenteFacade().findVotiStudenteLezione(idLezione, idStudente);

            for (VotiLezioniStudente votoStudente : voti) {
                switch (votoStudente.getTipoVoto()) {
                    case 'S':
                        scritto.add(votoStudente.getVotoNum());
                        break;
                    case 'O':
                        orale.add(votoStudente.getVotoNum());
                        break;
                    case 'P':
                        pratico.add(votoStudente.getVotoNum());
                        break;
                    case 'G':
                        giudizio.add(votoStudente.getVotoNum());
                        break;
                }
            }
        }
    }

    private String getVotoAsString(double voto) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (voto == 0.0) {
            return "n.c.";
        } else {
            return String.valueOf(voto);
        }
    }

    private double roundDouble(double num, int decimals) {
        BigDecimal bd = null;
        try {
            bd = new BigDecimal(Double.toString(num));
            bd = bd.setScale(decimals, BigDecimal.ROUND_HALF_UP);

        } catch (java.lang.NumberFormatException ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
        }
        if (bd == null) {
            return num;
        } else {
            return bd.doubleValue();
        }
    }

    public ArrayList<Double> getScritto() {
        return scritto;
    }

    public void setScritto(ArrayList<Double> scritto) {
        this.scritto = scritto;
    }

    public ArrayList<Double> getOrale() {
        return orale;
    }

    public void setOrale(ArrayList<Double> orale) {
        this.orale = orale;
    }

    public ArrayList<Double> getPratico() {
        return pratico;
    }

    public void setPratico(ArrayList<Double> pratico) {
        this.pratico = pratico;
    }

    public ArrayList<Double> getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(ArrayList<Double> giudizio) {
        this.giudizio = giudizio;
    }

    public ArrayList<Double> getCondotta() {
        return condotta;
    }

    public void setCondotta(ArrayList<Double> condotta) {
        this.condotta = condotta;
    }

    /**
     * Calcola la media aritmetica dei voti nella ArrayList oppure la media
     * pesata.
     *
     * @param voti
     * @param decimali
     * @param tipoVoto
     * @return
     */
    private Double calcVoto(ArrayList<Double> voti, int decimali, char tipoVoto, boolean isMediaPesata, Integer countMaxNumVoti) {
//        throw new UnsupportedOperationException("Not yet implemented");
        double voto = 0.0;
        int countVoti = 0;
        if (isMediaPesata == false) {
            countVoti = voti.size();
        } else {
            countVoti = countMaxNumVoti;
        }
        if (countVoti == 0) {
            return voto;
        }
        for (Double voti1 : voti) {
            voto += voti1;
        }
        voto = roundDouble(voto / countVoti, decimali);
        return voto;

    }

    public TipologiaGiudiziFacade getTipologiaGiudiziFacade() {
        return tipologiaGiudiziFacade;
    }

    private String renderAsStringGiudizio(Double calcGiudizio) {
//        throw new UnsupportedOperationException("Not yet implemented");
        String giudizioAsString = "n.c.";
        List<TipologiaGiudizi> tipi = getTipologiaGiudiziFacade().findAll();

        for (TipologiaGiudizi t : tipi) {
            if (Math.abs(t.getValueGiudizio() - calcGiudizio) <= 0.4) {
                giudizioAsString = t.getGiudizio();
                break;
            }
        }
        return giudizioAsString;
    }

    /**
     * INSERISSCE I RISULTATI DEL PERIODO NELLA TABELLA
     * allievi_risultati_periodo
     *
     * @param rec
     */
    public void inserisciRecordNelTabellone(RisultatiPeriodoRecord rec) {
//        AllieviRisultatiPeriodoPK pk = new AllieviRisultatiPeriodoPK(
//                rec.getIdPeriodo(), 
//                rec.getIdMateria(), 
//                rec.getIdStudente());
        AllieviRisultatiPeriodo result = getAllieviRisultatiPeriodoFacade().findRisultati(
                rec.getIdPeriodo(), 
                rec.getIdMateria(), 
                rec.getIdStudente());
        if (result != null) {
            getAllieviRisultatiPeriodoFacade().remove(result);//lo rimuove
        }
        AllieviRisultatiPeriodo entity = new AllieviRisultatiPeriodo();
        entity.setIdPeriodo(rec.getIdPeriodo());
        entity.setIdMateria(rec.getIdMateria());
        entity.setIdStudente(rec.getIdStudente());
        entity.setNumAssenze(rec.getNumAssenze());
        entity.setTotaleOrePeriodo(rec.getTotaleOrePeriodo());
        entity.setPercentualeAssenzePeriodo(rec.getPercentualeAssenzePeriodo());
        entity.setScritto(getAsString(rec.getScritto()));
        entity.setOrale(getAsString(rec.getOrale()));
        entity.setPratico(getAsString(rec.getPratico()));
        entity.setGiudizio(getAsString(rec.getGiudizio()));
        entity.setCondotta(getAsString(rec.getCondotta()));
        getAllieviRisultatiPeriodoFacade().create(entity);//e poi lo ricrea
        rec.setRecordWasSaved(true);

        JsfUtil.addSuccessMessage("Inserito record di:" + rec.getCognome() + " " + rec.getNome()
                + " nel Tabellone Generale dei voti");
    }
    private boolean tuttiRecordsInseritiNelTabellone;

    public boolean isTuttiRecordsInseritiNelTabellone() {
        tuttiRecordsInseritiNelTabellone = true;
        if (records != null) {
            for (RisultatiPeriodoRecord r : records) {
                if (r.isRecordWasSaved() == false) {
                    tuttiRecordsInseritiNelTabellone = false;
                    break;
                }
            }
        }
        return tuttiRecordsInseritiNelTabellone;
    }

    public void setTuttiRecordsInseritiNelTabellone(boolean tuttiRecordsInseritiNelTabellone) {
        this.tuttiRecordsInseritiNelTabellone = tuttiRecordsInseritiNelTabellone;
    }

    /**
     * Inserisce i risultati del periodo scolastico nella tabella
     * allievi_risultati_periodo del database.
     *
     * @param rec
     */
    public void inserisciAllRecordsNelTabellone(List<RisultatiPeriodoRecord> rec) {
        int countInserimenti = 0;
        for (RisultatiPeriodoRecord oneRec : rec) {
            if (!oneRec.isRecordWasSaved()) {
                inserisciRecordNelTabellone(oneRec);
                countInserimenti++;
            }
        }

        JsfUtil.addSuccessMessage("Inseriti " + countInserimenti + " records, della"
                + " Classe:" + materia.getIdClasse().getNomeClasse()
                + " Materia:" + materia.getMateria() + " nel tabellone"
                + " per il Periodo:" + periodo.getPeriodo() + " dal " + periodo.getStartDate() + " al " + periodo.getEndDate());

    }

    private boolean foundDifferenceBetween(AllieviRisultatiPeriodo get, RisultatiPeriodoRecord rec) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean isDifferent = false;
        isDifferent = (get.getTotaleOrePeriodo().intValue() != rec.getTotaleOrePeriodo().intValue())
                || (get.getNumAssenze().intValue() != rec.getNumAssenze().intValue())
                || (!get.getScritto().equals(getAsString(rec.getScritto())))
                || (!get.getOrale().equals(getAsString(rec.getOrale())))
                || (!get.getPratico().equals(getAsString(rec.getPratico())))
                || (!get.getGiudizio().equals(getAsString(rec.getGiudizio())))
                || (!get.getCondotta().equals(getAsString(rec.getCondotta())));

        return isDifferent;
    }

    public String getAsString(Double d) {
//        throw new UnsupportedOperationException("Not supported yet.");
        String string = null;
        if (d != null) {
            if (d == 0.0) {
                string = "n.c.";
            } else {
                string = String.valueOf(d);
            }
        }
        return string;
    }

    @SuppressWarnings({"BoxedValueEquality", "NumberEquality"})
    private boolean checkAlreadyLoaded() {
        if (records == null || records.size() != studentiItems.getRowCount()) {
            return false;
        }
//        Iterator iterator = studentiItems.iterator();
        int index = 0;
        for (int j = 0; j < studentiItems.getRowCount(); j++) {
            studentiItems.setRowIndex(j);
            Studenti studente = (Studenti) studentiItems.getRowData();
            RisultatiPeriodoRecord rec = records.get(index++);
            if (studente.getIdStudente() != rec.getIdStudente()
                    || materia.getIdMateria() != rec.getIdMateria()
                    || periodo.getPeriodiAnnoScolasticoPK().getIdPeriodo() != rec.getIdPeriodo()) {
                return false;
            }
        }

        return true;
    }

    public boolean isViewAsProgressBar() {
        return viewAsProgressBar;
    }

    public void setViewAsProgressBar(boolean viewAsProgressBar) {
        this.viewAsProgressBar = viewAsProgressBar;
    }

    public void switchAssenzePercView() {
        if (viewAsProgressBar) {
            setViewAsProgressBar(false);
            JsfUtil.addSuccessMessage("Visualizzazione assenze come Progress Bar disabilitata");

        } else {
            setViewAsProgressBar(true);
            JsfUtil.addSuccessMessage("Visualizzazione assenze come Progress Bar abilitata");
        }
    }

    public void switchTipoMedia() {
        if (mediaPesata == true) {
            setMediaPesata(false);
            JsfUtil.addSuccessMessage("La media pesata è stata disabilitata");
        } else {
            setMediaPesata(true);
            JsfUtil.addSuccessMessage("La media pesata è stata abilitata");
        }
        records = null;
        initRecords();
    }

    /**
     * Calcola il numero massimo di votazioni del tipo
     *
     * @param tipoVoto, esperite in assoluto nel periodo
     * @param periodo e nella materia
     * @param materia. Ritorna un Integer.
     *
     * @param periodo
     * @param materia
     * @param tipoVoto
     * @return
     */
    private Integer calcMaxNumVotazioniTipo(PeriodiAnnoScolastico periodo, Materie materia, char tipoVoto) {
//        throw new UnsupportedOperationException("Not yet implemented");
        int countVoti = 0;

        Date startDate = periodo.getStartDate();
        Date endDate = periodo.getEndDate();
        List lezioniMateriaPeriodo
                = getLezioniFacade().findLezioniPeriodo(materia.getIdMateria(), startDate, endDate);
        Collection<Studenti> studenti = materia.getIdClasse().getStudentiCollection();

        Iterator iter = lezioniMateriaPeriodo.iterator();
        //ITERA SULLE LEZIONI DEL PERIODO NELLA MATERIA SELEZIONATA
        while (iter.hasNext()) {
            Lezioni lez = (Lezioni) iter.next();
            Long idLezione = lez.getIdLezione();

            Iterator iterStudenti = studenti.iterator();
            //ITERA SUGLI STUDENTI PER SAPERE IL NUMERO MASSIMO DI VOTI ESPERITI NELLA LEZIONE DA CIASCUN STUDENTE
            int maxNumVotiLezione = 0;
            while (iterStudenti.hasNext()) {
                Studenti studente = (Studenti) iterStudenti.next();
                Long idStudente = studente.getIdStudente();
                List<VotiLezioniStudente> listVoti = getVotiLezioniStudenteFacade().findVotiStudenteLezione(idLezione, idStudente);

                int numVotiStudente = 0;
                for (VotiLezioniStudente v : listVoti) {
                    if (v.getTipoVoto().equals(tipoVoto)) {
                        numVotiStudente++;
                    }
                }

                if (numVotiStudente > maxNumVotiLezione) {
                    maxNumVotiLezione = numVotiStudente;
                }
            }
            countVoti += maxNumVotiLezione;
        }

        return countVoti;
    }

    public boolean isInsertingCondotta() {
        return insertingCondotta;
    }

    public void setInsertingCondotta(boolean insertingCondotta) {
        this.insertingCondotta = insertingCondotta;
    }

    public void switchToInsertingCondotta() {
        if (isInsertingCondotta()) {
            setInsertingCondotta(false);
            JsfUtil.addSuccessMessage("Inserimento del voto di CONDOTTA disabilitato!");
        } else {
            setInsertingCondotta(true);
            JsfUtil.addSuccessMessage("Inserimento del voto di CONDOTTA abilitato!");
        }
    }

    private Double retrieveCondotta(Long idPeriodo, Long idStudente) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Double condotta = getAllieviRisultatiPeriodoFacade().findCondottaStudentePeriodo(idPeriodo, idStudente);
        return condotta;
    }
    @EJB
    StudentiFacade studentiFacade;

    public StudentiFacade getStudentiFacade() {
        return studentiFacade;
    }

    /**
     * Carica la ArrayList<RisultatiPeriodoRecord> con i records gia presenti
     * nella tabella allievi_risultati_periodo velocizzando in tal modo
     * l'operazione di caricamento
     *
     */
    private void caricaRisultatiMateriaPeriodo() {
        //        throw new UnsupportedOperationException("Not yet implemented");
        List<AllieviRisultatiPeriodo> list = getAllieviRisultatiPeriodoFacade().getRisultatiMateriaPeriodo(idPeriodo, idMateria);
        if (list.size() == studentiItems.getRowCount()) {
            Integer totGiudizi = calcMaxNumVotazioniTipo(periodo, materia, 'G');
            Integer totScritti = calcMaxNumVotazioniTipo(periodo, materia, 'S');
            Integer totOrali = calcMaxNumVotazioniTipo(periodo, materia, 'O');
            Integer totPratico = calcMaxNumVotazioniTipo(periodo, materia, 'P');
            setTuttiRecordsInseritiNelTabellone(true);

            int count = 1;

            for (AllieviRisultatiPeriodo result : list) {
                RisultatiPeriodoRecord rec;
                rec = new RisultatiPeriodoRecord();
                //VALORIZZA I CAMPI DEL RECORD
                rec.setRegistroIndex(count++);

                Studenti studente = getStudentiFacade().find(result.getIdStudente());

                rec.setIdStudente(studente.getIdStudente());
                rec.setIdPeriodo(idPeriodo);
                rec.setIdMateria(idMateria);

                rec.setCognome(studente.getCognome());
                rec.setNome(studente.getNome());
                rec.setTotaleOrePeriodo(result.getTotaleOrePeriodo());

                rec.setCountMaxNumGiudizio(totGiudizi);
                rec.setCountMaxNumScritto(totScritti);
                rec.setCountMaxNumPratico(totPratico);
                rec.setCountMaxNumOrale(totOrali);

                rec.setNumAssenze(result.getNumAssenze());
                rec.setDataEntrata(studente.getDataEntrata());
                rec.setRitiratoData(studente.getRitiratoData());
                rec.setPercentualeAssenzePeriodo(result.getPercentualeAssenzePeriodo());

                rec.setScritto(Double.valueOf(result.getScritto().equals("n.c.") ? "0.0" : result.getScritto()));

                rec.setOrale(Double.valueOf(result.getOrale().equals("n.c.") ? "0.0" : result.getOrale()));

                rec.setPratico(Double.valueOf(result.getPratico().equals("n.c.") ? "0.0" : result.getPratico()));

                rec.setGiudizio(Double.valueOf(result.getGiudizio().equals("n.c.") ? "0.0" : result.getGiudizio()));
                rec.setGiudizioAsString(result.getGiudizio());

                rec.setCondotta(Double.valueOf(result.getCondotta().equals("n.c.") ? "0.0" : result.getCondotta()));//Condotta da decidere in sede di scrutinio
                rec.setCondottaStr(result.getCondotta());
                //Controlla se il record è stato salvato nel database
                List<AllieviRisultatiPeriodo> resultStudente = getAllieviRisultatiPeriodoFacade().getRisultatiFor(
                        idPeriodo, idMateria, studente.getIdStudente());
                boolean foundDifference = foundDifferenceBetween(resultStudente.get(0), rec);
                if (foundDifference) {
                    setTuttiRecordsInseritiNelTabellone(false);
                }
                rec.setRecordWasSaved(resultStudente.size() == 1 && !foundDifference);

                records.add(rec);
            }
        }
    }
}
