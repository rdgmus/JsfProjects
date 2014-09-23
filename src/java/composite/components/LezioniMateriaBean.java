/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;
import jpa.entities.AnniScolastici;
import jpa.entities.Lezioni;
import jpa.entities.LezioniMateria;
import jpa.entities.Materie;
import jpa.entities.PeriodiAnnoScolastico;
import jpa.session.LezioniFacade;
import jpa.session.LezioniMateriaFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.PeriodiAnnoScolasticoFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.UITabPanel;
import org.richfaces.event.ItemChangeEvent;
import org.richfaces.event.ItemChangeListener;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "lezioniMateriaBean")
@SessionScoped
public class LezioniMateriaBean implements Serializable, ItemChangeListener, ValueChangeListener {

    @EJB
    PeriodiAnnoScolasticoFacade periodiAnnoScolasticoFacade;
    OreAssenzeFacade oreAssenzeFacade;
    private List<PeriodiAnnoScolastico> periodiAnnoScolastico;
    private PeriodiAnnoScolastico periodoSelected;
    private ArrayList<String> mesi;
    private ArrayList<Date> mesiCalendar;
    private long periodSelected;
    private Object monthSelected;
    private AnniScolastici annoScolasticoSelected;
    private Materie materiaSelected;
    private List<LezioniMateria> lezioniMateriaMese;
    private int countLezioni;
    private Object lezioneSelected;
    private DataModel studentiItems;
    private ArrayList<Integer> oreLezione;
    private Long idLezione;
    private boolean expandAllPanels = true;
    private String argomentoLezione;

    public LezioniMateriaBean() {
        getPeriodiFromRegistri();
    }

    public boolean isExpandAllPanels() {
        return expandAllPanels;
    }

    public void setExpandAllPanels(boolean expandAllPanels) {
        this.expandAllPanels = expandAllPanels;
    }

    public List<LezioniMateria> getLezioniMateriaMese() {
        return lezioniMateriaMese;
    }

    public void setLezioniMateriaMese(List<LezioniMateria> lezioniMateriaMese) {
        this.lezioniMateriaMese = lezioniMateriaMese;
        JsfUtil.setSessionMapValue("lezioniMateriaMese", lezioniMateriaMese);;
    }

    public Materie getMateriaSelected() {
        return materiaSelected;
    }

    public void setMateriaSelected(Materie materiaSelected) {
        this.materiaSelected = materiaSelected;
    }

    public AnniScolastici getAnnoScolasticoSelected() {
        return annoScolasticoSelected;
    }

    /**
     * In base all'anno scolastico selezionato, annoScolasticoSelected, e in
     * base alla data attuale, cioè di apertura della maschera, seleziona il
     * periodo, trimestre, quadrimestre, etc. in cui ci si trova, recupera
     * l'elenco dei mesi contenuti nel periodo selezionato, scorre l'elenco dei
     * mesi e seleziona quello corrispondente alla data attuale
     *
     * @param annoScolasticoSelected
     */
    public void setAnnoScolasticoSelected(AnniScolastici annoScolasticoSelected) {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        if (this.annoScolasticoSelected == null
                || !this.annoScolasticoSelected.equals(annoScolasticoSelected)) {
            for (PeriodiAnnoScolastico p : periodiAnnoScolastico) {
                if (now.after(p.getStartDate()) && now.before(p.getEndDate())) {
                    setPeriodoSelected(p);//seleziona il periodo, trimestre, quadrimestre, etc. in cui ci si trova
                    setPeriodSelected(p.getPeriodiAnnoScolasticoPK().getIdPeriodo());
                    mesi = getMesiLezioniIntestazioni(//recupera l'elenco dei mesi contenuti nel periodo selezionato
                            periodoSelected.getStartDate(), periodoSelected.getEndDate());

                    Calendar endMese = Calendar.getInstance();
                    for (Date d : mesiCalendar) {
                        endMese.setTime(d);
                        endMese.add(Calendar.MONTH, 1);
                        endMese.set(Calendar.DAY_OF_MONTH, 1);
                        if (now.after(d) && now.before(endMese.getTime())) {
                            setMonthSelected(d.toString());//scorre l'elenco dei mesi e seleziona quello corrispondente alla data attuale
                            this.annoScolasticoSelected = annoScolasticoSelected;
                            return;
                        }
                    }
                }
            }
            setPeriodoSelected(getPeriodiAnnoScolastico().get(0));
            setMonthSelected(mesiCalendar.get(0).toString());
        }
        if (this.annoScolasticoSelected != null && this.annoScolasticoSelected.equals(annoScolasticoSelected)) {
            mesi = getMesiLezioniIntestazioni(
                    periodoSelected.getStartDate(), periodoSelected.getEndDate());
            Calendar endMese = Calendar.getInstance();
            for (Date d : mesiCalendar) {
                endMese.setTime(d);
                endMese.add(Calendar.MONTH, 1);
                endMese.set(Calendar.DAY_OF_MONTH, 1);
                if (now.after(d) && now.before(endMese.getTime())) {
                    setMonthSelected(d.toString());
                    this.annoScolasticoSelected = annoScolasticoSelected;
                    return;
                }
            }
            setMonthSelected(mesiCalendar.get(0).toString());
            this.annoScolasticoSelected = annoScolasticoSelected;
        }
    }

    public long getPeriodSelected() {
        return periodSelected;
    }

    public void setPeriodSelected(long periodSelected) {
        this.periodSelected = periodSelected;
    }

    public Object getMonthSelected() {
        return monthSelected;
    }

    public void setMonthSelected(Object monthSelected) {
        this.monthSelected = monthSelected;
        retrieveLezioniMese();
    }

    public ArrayList<Date> getMesiCalendar() {
        return mesiCalendar;
    }

    public void setMesiCalendar(ArrayList<Date> mesiCalendar) {
        this.mesiCalendar = mesiCalendar;
    }

    /**
     * Ritorna una lista di stringhe contenenti i nomi dei mesi contenuti nel
     * periodo indicato dalle due date: Date start_date, Date end_date
     *
     * @param start_date
     * @param end_date
     * @return
     */
    public ArrayList<String> getMesiLezioniIntestazioni(Date start_date, Date end_date) {
        mesi = null;
        mesiCalendar = null;

        mesi = new ArrayList<String>();
        mesiCalendar = new ArrayList<Date>();

        Calendar cal = Calendar.getInstance();
        Calendar end_cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MMMMM");
        SimpleDateFormat sdfMeseNum = new SimpleDateFormat("MM");

        cal.setTime(start_date);
        end_cal.setTime(end_date);

        /**
         * *************************************************
         */
        Calendar actual_cal = Calendar.getInstance();
        SimpleDateFormat sdf_actual = new SimpleDateFormat("MM");

        /**
         * *************************************************
         */
        do {
            int year = cal.get(Calendar.YEAR);
            int mese = Integer.valueOf(sdfMeseNum.format(cal.getTime()));
            mesi.add(sdf.format(cal.getTime()) + " - " + year);
            mesiCalendar.add(cal.getTime());
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);

        } while (cal.getTimeInMillis() < end_cal.getTimeInMillis());

        return mesi;
    }

    /**
     * in base alla lista dei periodi in cui è stato diviso l'anno scolastico
     * seleziona la lista dei nomi dei mesi per default contenuti nel periodo
     * relativo alla prima suddivisione dell'anno scolastico
     *
     * @param periodiAnnoScolastico
     */
    public void setPeriodiAnnoScolastico(List<PeriodiAnnoScolastico> periodiAnnoScolastico) {
        this.periodiAnnoScolastico = periodiAnnoScolastico;
        //Setta i mesi supponendo che venga selezionata la tab del primo periodo ma deve essere resa dinamica
        mesi = getMesiLezioniIntestazioni(
                periodiAnnoScolastico.get(0).getStartDate(), periodiAnnoScolastico.get(0).getEndDate());
    }

    /**
     * Ritorna la lista dei periodi in cui è stato suddiviso l'anno scolastico
     *
     * @return
     */
    public List<PeriodiAnnoScolastico> getPeriodiAnnoScolastico() {
        return periodiAnnoScolastico;
    }

    private void getPeriodiFromRegistri() {
        RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("registriInsegnanteBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            setPeriodiAnnoScolastico(controller.getPeriodiAnnoScolastico());
            setAnnoScolasticoSelected(controller.getAnnoScolastico());
            setMateriaSelected(controller.getMateriaSelezionata());
        }
    }

    @Override
    public void processItemChange(ItemChangeEvent ice) throws AbortProcessingException {
        //        throw new UnsupportedOperationException("Not supported yet.");
        //Riceve periodiAnnoScolasticoPK.idPeriodo e mese in formato="Mon Oct 01 00:00:00 CEST 2012"
        Object source = ice.getSource();
        if (source instanceof UITabPanel) {
            UITabPanel sourcePanel = (UITabPanel) source;//UITabPanel

            String id = sourcePanel.getId();
            String changeOrigin = ice.getNewItemName();

            if (id.equals("mesePeriodo")) {
//                setMonthSelected(changeOrigin);
                updateArgomentiLezioniDaRimuovere();

            } else if (id.equals("periodoScolastico")) {
                long idPeriodo = Integer.valueOf(changeOrigin).longValue();
                setPeriodoSelected(periodiAnnoScolasticoFacade.findByIdPeriod(idPeriodo));

                mesi = getMesiLezioniIntestazioni(
                        periodoSelected.getStartDate(), periodoSelected.getEndDate());
                setMonthSelected(mesiCalendar.get(0).toString());
                updateArgomentiLezioniDaRimuovere();
            } else if (id.equals("lezioniMese")) {
                setLezioneSelected((LezioniMateria) lezioneSelected);
            }
        }
    }

    public PeriodiAnnoScolastico getPeriodoSelected() {
        return periodoSelected;
    }

    public void setPeriodoSelected(PeriodiAnnoScolastico periodoSelected) {
        JsfUtil.setSessionMapValue("periodoSelected", periodoSelected);
        this.periodoSelected = periodoSelected;
    }

    public ArrayList<String> getMesi() {
        return mesi;
    }

    public void setMesi(ArrayList<String> mesi) {
        this.mesi = mesi;
    }
    @EJB
    LezioniMateriaFacade lezioniMateriaFacade;

    public LezioniMateriaFacade getLezioniMateriaFacade() {
        if (lezioniMateriaFacade == null) {
            RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().get("registriInsegnanteBean");

            if (controller != null) {
                lezioniMateriaFacade = controller.getLezioniMateriaFacade();
            }
        }
        return lezioniMateriaFacade;
    }

    /**
     * Ritorna la lista delle lezioni presenti nel mese selezionato,
     * monthSelected, oppure null se la lista è ancora vuota, cioè non ci sono
     * lezioni, oppure la materia non è stata selezionata. Essendo monthSelected
     * un oggetto contenente una data relativa al mese selezionato, il parametro
     * mesiCalendar serve a scorrere la lista dei mesi contenuti nel periodo
     * scolastico selezionato precedentemente, onde individuare in base al
     * confronto delle date nella lista, con quella selezionata, la lista delle
     * lezioni già presenti nel database tramite un'interrogazione del
     * LezioniMateriaFacade.
     *
     * @param mesiCalendar
     * @param monthSelected
     * @return
     */
    public List<LezioniMateria> getLezioniMateriaMese(ArrayList<Date> mesiCalendar, Object monthSelected) {
        if (getMateriaSelected() == null) {
            return null;
        }
        for (Date d : mesiCalendar) {
            if (String.valueOf(d).equals(monthSelected)) {
                Calendar start_cal = Calendar.getInstance();
                Calendar end_cal = Calendar.getInstance();
                start_cal.setTime(d);
                start_cal.set(Calendar.DAY_OF_MONTH, 1);
                end_cal.setTime(d);
                end_cal.set(Calendar.DAY_OF_MONTH, 1);
                end_cal.add(Calendar.MONTH, 1);
                end_cal.add(Calendar.DAY_OF_MONTH, -1);
                return getLezioniMateriaFacade().findByAsMateriaMese(
                        getAnnoScolasticoSelected(),
                        getMateriaSelected(),
                        start_cal.getTime(), end_cal.getTime());
            }
        }
        return null;//ARRAY VUOTA non ci sono lezioni
    }

    /**
     * Recupera le lezioni della materia selezionata, nel mese selezionato
     */
    public void retrieveLezioniMese() {
//        throw new UnsupportedOperationException("Not yet implemented");

        RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("registriInsegnanteBean");//recupera l'oggetto registriInsegnanteBean

        if (controller != null) {//se l'oggetto registriInsegnanteBean è stato correttamente recuperato...
            if (annoScolasticoSelected == null
                    || !annoScolasticoSelected.equals(controller.getAnnoScolastico())) {
                annoScolasticoSelected = controller.getAnnoScolastico();//recupera l'anno scolastico e valorizza la relativa variabile
            }
            if (materiaSelected == null
                    || !materiaSelected.equals(controller.getMateriaSelezionata())) {
                materiaSelected = controller.getMateriaSelezionata();//recupera la materia selezionata e valorizza la relativa variabile
            }
        }
        if (materiaSelected == null) {
            return; //se la materia selezionata è null ritorna.
        }
        setLezioniMateriaMese(getLezioniMateriaMese(mesiCalendar, monthSelected));//recupera l'elenco delle lezioni nel mese selezionato
        setCountLezioni(getLezioniMateriaMese().size());//conta il numero di lezioni
        if (getCountLezioni() > 0) {//se il numero di lezioni è maggiore di zero...
            boolean done = false;
            Calendar cal = Calendar.getInstance();

            for (LezioniMateria l : lezioniMateriaMese) {//scorre l'elenco delle lezioni e seleziona quella corrispondente alla data attuale
                Calendar lezioneCal = Calendar.getInstance();
                lezioneCal.setTime(l.getDataLezione());
                if (cal.get(Calendar.DAY_OF_YEAR) == lezioneCal.get(Calendar.DAY_OF_YEAR)) {
                    setLezioneSelected(l);
                    done = true;
                    break;
                }
            }
            if (!done) {//altrimenti seleziona la prima lezione del mese (potrebbe fare altrimenti !!!)
                setLezioneSelected(getLezioniMateriaMese().get(0));
            }
        } else {//se non ci sono lezioni nel mese lancia un messaggio di informazione per l'utente
            String msg = "Non ci sono lezioni per la Materia: "
                    + materiaSelected.getMateria() + " della Classe: "
                    + materiaSelected.getIdClasse().getNomeClasse()
                    + ", nel mese corrente: '" + mesiCalendarConvert((String) monthSelected)+"'";
            Iterator<FacesMessage> listMessages = FacesContext.getCurrentInstance().getMessages();

            while (listMessages.hasNext()) {
                FacesMessage nextMessage = listMessages.next();
                if (nextMessage.getDetail().equals(msg)) {
                    return;
                }
            }

            JsfUtil.addErrorMessage(msg);
        }
    }

    private String mesiCalendarConvert(String value) {
        String string = null;

        for (Date d : mesiCalendar) {
            if (d.toString().equals(value)) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMMMM - yyyy");
                string = sdf.format(d.getTime());

                break;
            }
        }
        return string.toUpperCase();

    }

    public int getCountLezioni() {
        return countLezioni;
    }

    public void setCountLezioni(int countLezioni) {
        this.countLezioni = countLezioni;
    }

    public Object getLezioneSelected() {
        return lezioneSelected;
    }

    /**
     * Valorizza la variabile globale lezioneSelected in base all'oggetto nel
     * parametro, scorrendo l'elenco delle lezioni nel mese selezionato.
     *
     * @param lezioneSelected
     */
    public void setLezioneSelected(Object lezioneSelected) {
        if (lezioneSelected instanceof java.lang.String) {
            for (LezioniMateria l : lezioniMateriaMese) {
                if (l.toString().equals(String.valueOf(lezioneSelected))) {
                    this.lezioneSelected = l;
                    break;
                }
            }
        } else {
            this.lezioneSelected = lezioneSelected;
        }
        JsfUtil.setSessionMapValue("lezioneSelected", this.lezioneSelected);
    }

    public DataModel getStudentiItems() {
        return studentiItems;
    }

    public void setStudentiItems(DataModel studentiItems) {
        this.studentiItems = studentiItems;
    }

    public ArrayList<Integer> getOreLezione() {
        return oreLezione;
    }

    public void setOreLezione(ArrayList<Integer> oreLezione) {
        this.oreLezione = oreLezione;
    }

    public Long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(Long idLezione) {
        this.idLezione = idLezione;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        Object source = event.getSource();
        // PARAMETERS FOR UPDATING ASSENZE

    }

    public String refresh() {
        return "/registro/RegistroScolastico";
    }

    private void updateArgomentiLezioniDaRimuovere() {
        CreaLezioniBean controller = (CreaLezioniBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("creaLezioniBean");
        if (controller != null) {
            if (getCountLezioni() == 0) {
                controller.setArgLezioneDaRemove("");
                controller.setOreLezioneDaRemove(0);
            } else {
                controller.setArgLezioneDaRemove(getLezioniMateriaMese().get(0).getArgomento());
                controller.setOreLezioneDaRemove(getLezioniMateriaMese().get(0).getOreLezione());
                controller.setOreLezione(getLezioniMateriaMese().get(0).getOreLezione());
            }
        }

    }
    @EJB
    LezioniFacade lezioniFacade;

    public LezioniFacade getLezioniFacade() {
        return lezioniFacade;
    }

    /**
     * Recupera l'argomento relativo alla lezione selezionata e valorizza la
     * relativa variabile.
     *
     * @return
     */
    public String getArgomentoLezione() {
        if (getLezioniFacade() != null && getLezioneSelected() != null) {
            LezioniMateria l = (LezioniMateria) getLezioneSelected();
            Lezioni lez = getLezioniFacade().find(l.getIdLezione());
            if (lez != null) {
                argomentoLezione = lez.getArgomento();
            }
        }
        return argomentoLezione;
    }

    /**
     * Setta l'argomento della lezione attiva al momento, valorizzando la
     * relativa variabile
     *
     * @param argomentoLezione
     */
    public void setArgomentoLezione(String argomentoLezione) {
        this.argomentoLezione = argomentoLezione;
    }
}
