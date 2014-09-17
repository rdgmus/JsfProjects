/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.DataModel;
import jpa.entities.Classi;
import jpa.entities.Lezioni;
import jpa.entities.LezioniMateria;
import jpa.entities.Materie;
import jpa.entities.OreAssenze;
import jpa.entities.Studenti;
import jpa.entities.VotiLezioniStudente;
import jpa.session.LezioniFacade;
import jpa.session.LezioniMateriaFacade;
import jpa.session.MaterieFacade;
import jpa.session.OreAssenzeFacade;
import jpa.session.UtentiLoggerFacade;
import jpa.session.VotiLezioniStudenteFacade;
import jsf.util.JsfUtil;
import org.richfaces.component.html.HtmlInputNumberSpinner;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "creaLezioniBean")
@SessionScoped
public class CreaLezioniBean implements Serializable, ValueChangeListener {

    private Date selectedDate;
    private Object locale;
    private boolean popup = true;
    private String pattern = "dd MMM yyyy";
    private final boolean showApply = true;
    private Long lezioneDaRimuovere;
    private boolean confermaRimozione = false;
    private boolean confermaCreazione = false;
    private boolean lezioneItemDisabled = false;
    private int oreLezione = 1;
    private String argomentoLezione;
    private int oreLezioneDaRemove;
    private String argLezioneDaRemove;
    private LezioniMateriaFacade lezioniMateriaFacade;
    @EJB
    LezioniFacade lezioniFacade;
    @EJB
    OreAssenzeFacade oreAssenzeFacade;
    @EJB
    VotiLezioniStudenteFacade votiLezioniStudenteFacade;
    private boolean nuovoArgomento = false;
    private DataModel studentiItems;
    private boolean rimuoviLezioneDisabled;
    private boolean updateArgomentoDisabled;
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;
    @EJB
    MaterieFacade materieFacade;

    public CreaLezioniBean() {
    }

    public Date getSelectedDate() {
        if (selectedDate == null) {
            Calendar cal = Calendar.getInstance();
            selectedDate = cal.getTime();
        }
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Object getLocale() {
        if (locale == null) {
            locale = Locale.ITALY;
        }
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

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }

    public void creaLezione() {
        Materie m = null;
        BigInteger idMateria = null;
        RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("registriInsegnanteBean");
        if (controller != null) {
            m = controller.getMateriaSelezionata();
        }
        idMateria = new BigInteger(m.getIdMateria().toString());

        if (lezioniFacade != null) {
            Lezioni nuovaLezione = new Lezioni();
            nuovaLezione.setIdMateria(m);
            nuovaLezione.setDataLezione(selectedDate);
            nuovaLezione.setOreLezione(oreLezione);
            nuovaLezione.setArgomento(argomentoLezione);
            nuovaLezione.setFreezeLezione((short)0);
            try {
//                Long nextId = getLezioniFacade().getNextId();
//                nuovaLezione.setIdLezione(nextId);
                getLezioniFacade().create(nuovaLezione);

                impostaAssenzeRitirati(nuovaLezione);

                rinfrescaLezioniMese();

                String msg = ResourceBundle.getBundle("/resources/Registro").getString("LezioneCreataMsgString")
                        + " Data:" + selectedDate.toString()
                        + " Materia:" + m.getMateria()
                        + " Classe:" + m.getIdClasse().getNomeClasse()
                        + " Ore:" + getOreLezione()
                        + " Argomento:" + getArgomentoLezione();

                JsfUtil.addSuccessMessage(msg);
                JsfUtil.registraEventoNelDatabase(msg,
                        ResourceBundle.getBundle("/resources/Registro").getString("LezioneCreataTypeString"),
                        getUtentiLoggerFacade());

                //Annulla i records di riepilogo in modo che vengano ricaricati
                ricalcolaRiepilogo();

            } catch (Exception ejbex) {
                JsfUtil.addErrorMessage("Non è stato possibile creare la lezione:"
                        + ejbex.getMessage());
            }
        }
        setConfermaCreazione(false);
    }

    public LezioniFacade getLezioniFacade() {
        return lezioniFacade;
    }

    public OreAssenzeFacade getOreAssenzeFacade() {
        return oreAssenzeFacade;
    }

    public VotiLezioniStudenteFacade getVotiLezioniStudenteFacade() {
        return votiLezioniStudenteFacade;
    }

    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public void rimuoviLezione() {
//        Long idLezione = new Long(lezioneDaRimuovere);
        Long idLezione = lezioneDaRimuovere.longValue();
        try {
            Lezioni daRimuovere = getLezioniFacade().find(idLezione);
            //Prima di rimuovere la lezione bisogna rimuovere le assenze legate alla lezione
            //e i voti legati alla lezione
            List<OreAssenze> assenze = getOreAssenzeFacade().findAllAssenzeLezione(idLezione);
            for (OreAssenze a : assenze) {
                getOreAssenzeFacade().remove(a);
            }
            List<VotiLezioniStudente> voti = getVotiLezioniStudenteFacade().findAllVotiLezione(idLezione);
            for (VotiLezioniStudente v : voti) {
                getVotiLezioniStudenteFacade().remove(v);
            }
            Materie materia = getMaterieFacade().findByIdMateria(daRimuovere.getIdMateria().getIdMateria());
            getLezioniFacade().remove(daRimuovere);

            rinfrescaLezioniMese();

            String msg = ResourceBundle.getBundle("/resources/Registro").getString("LezioneRimossaMsgString")
                    + " Data:" + daRimuovere.getDataLezione().toString()
                    + " Materia:" + materia.getMateria()
                    + " Classe:" + materia.getIdClasse().getNomeClasse()
                    + " Ore:" + daRimuovere.getOreLezione()
                    + " Argomento:" + daRimuovere.getArgomento();
            JsfUtil.addSuccessMessage(msg);
            JsfUtil.registraEventoNelDatabase(msg,
                    ResourceBundle.getBundle("/resources/Registro").getString("LezioneRimossaTypeString"),
                    getUtentiLoggerFacade());

            //Annulla i records di riepilogo in modo che vengano ricaricati
            ricalcolaRiepilogo();

        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage(ejbex, ejbex.getMessage());
        }
        setConfermaRimozione(false);
        updateArgomentoDisabled = false;
    }

    public Long getLezioneDaRimuovere() {
        return lezioneDaRimuovere;
    }

    public void setLezioneDaRimuovere(Long lezioneDaRimuovere) {
        if (lezioneDaRimuovere != null) {
            this.lezioneDaRimuovere = lezioneDaRimuovere;
        }
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        //        throw new UnsupportedOperationException("Not supported yet.");
        Object source = event.getSource();
        if (source instanceof HtmlSelectOneMenu) {
            HtmlSelectOneMenu sel = (HtmlSelectOneMenu) source;
            if (sel.getId().equals("lezioneDaRimuovere")) {
                if (event.getNewValue() != null) {
                    LezioniMateria l = null;
                    BigInteger i = new BigInteger(event.getNewValue().toString());
                    l = getLezioniMateriaFacade().find(i);
                    if (l != null) {
                        setOreLezione(l.getOreLezione());
                        setOreLezioneDaRemove(l.getOreLezione());
                        setArgLezioneDaRemove(l.getArgomento());
//                        cancellaRimozione();
                    }
                }
            }
        }
        if (source instanceof HtmlInputNumberSpinner) {
            HtmlInputNumberSpinner spin = (HtmlInputNumberSpinner) source;
            if (spin.getId().equals("oreNuovaLezione")) {
                setOreLezione(Integer.valueOf(String.valueOf(event.getNewValue())).intValue());
            }
        }
    }

    public boolean isConfermaCreazione() {
        return confermaCreazione;
    }

    public void setConfermaCreazione(boolean confermaCreazione) {
//        setOreLezione(getOreLezioneDaRemove());
        this.confermaCreazione = confermaCreazione;
    }

    public void cancellaCreazione() {
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Registro").getString("CreazioneLezioneCancellata"));
        setConfermaCreazione(false);
    }

    public boolean isConfermaRimozione() {
        return confermaRimozione;
    }

    public void setConfermaRimozione(boolean confermaRimozione) {
        this.confermaRimozione = confermaRimozione;
        if (confermaRimozione) {
            updateArgomentoDisabled = true;
        }
    }

    public void cancellaRimozione() {
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Registro").getString("RimozioneLezioneCancellata"));
        setConfermaRimozione(false);
        updateArgomentoDisabled = false;
    }

    public int getOreLezione() {
        return oreLezione;
    }

    public void setOreLezione(int oreLezione) {
        this.oreLezione = oreLezione;
    }

    public String getArgomentoLezione() {
        return argomentoLezione;
    }

    public void setArgomentoLezione(String argomentoLezione) {
        this.argomentoLezione = argomentoLezione;
    }

    public int getOreLezioneDaRemove() {
        return oreLezioneDaRemove;
    }

    public void setOreLezioneDaRemove(int oreLezioneDaRemove) {
        this.oreLezioneDaRemove = oreLezioneDaRemove;
    }

    public String getArgLezioneDaRemove() {
        List<LezioniMateria> lezioniMateriaMese = (List<LezioniMateria>) JsfUtil.getSessionMapValue("lezioniMateriaMese");
        LezioniMateria l = null;
        if (lezioniMateriaMese != null && lezioniMateriaMese.size() > 0) {
            l = lezioniMateriaMese.get(0);
        }
        if (l != null) {
            if (lezioneDaRimuovere == null || notInLezioniMateriaMese(
                    lezioneDaRimuovere, lezioniMateriaMese)) {
                lezioneDaRimuovere = l.getIdLezione();
            }
        }
        if (lezioniMateriaMese.isEmpty()) {
            argLezioneDaRemove = "";
            oreLezioneDaRemove = 0;
            oreLezione = 0;
        } else {
            Lezioni lez = getLezioniFacade().find(lezioneDaRimuovere.longValue());

            argLezioneDaRemove = lez.getArgomento();
            oreLezioneDaRemove = lez.getOreLezione();
            oreLezione = lez.getOreLezione();
        }
        return argLezioneDaRemove;
    }

    public void setArgLezioneDaRemove(String argLezioneDaRemove) {
        this.argLezioneDaRemove = argLezioneDaRemove;
    }

    private void rinfrescaLezioniMese() {
        LezioniMateriaBean controller = (LezioniMateriaBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("lezioniMateriaBean");
        if (controller != null) {
            controller.retrieveLezioniMese();
        }

    }

    public void updateArgomentoLezione() {
//        Long idLezione = new Long(lezioneDaRimuovere);
        Long idLezione = lezioneDaRimuovere.longValue();
        try {
            Lezioni daRimuovere = getLezioniFacade().find(idLezione);

            getLezioniFacade().cambiaArgomento(idLezione, argomentoLezione);

            rinfrescaLezioniMese();


            String msg = ResourceBundle.getBundle("/resources/Registro").getString("ArgomentoCambiatoMsgString")
                    + " Data:" + daRimuovere.getDataLezione().toString()
                    + " idMateria=" + daRimuovere.getIdMateria().toString()
                    + " Ore:" + daRimuovere.getOreLezione()
                    + " Argomento:" + daRimuovere.getArgomento();

            JsfUtil.addSuccessMessage(msg);
            JsfUtil.registraEventoNelDatabase(msg,
                    ResourceBundle.getBundle("/resources/Registro").getString("ArgomentoCambiatoTypeString"),
                    getUtentiLoggerFacade());

        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage(ejbex, ejbex.getMessage());
        }

        setNuovoArgomento(false);
        rimuoviLezioneDisabled = false;
    }

    public boolean isNuovoArgomento() {
        return nuovoArgomento;
    }

    public void setNuovoArgomento(boolean nuovoArgomento) {
        setArgomentoLezione(argLezioneDaRemove);
        this.nuovoArgomento = nuovoArgomento;
        rimuoviLezioneDisabled = true;
    }

    public void cancellaNuovoArgomento() {
        JsfUtil.addSuccessMessage("Nuovo Argomento non inserito");
        setNuovoArgomento(false);
        rimuoviLezioneDisabled = false;
    }

    private boolean notInLezioniMateriaMese(Long lezioneDaRimuovere, List<LezioniMateria> lezioniMateriaMese) {
        boolean notIn = true;
        for (LezioniMateria lm : lezioniMateriaMese) {
            if (lezioneDaRimuovere.equals(lm.getIdLezione())) {
                notIn = false;
                break;
            }
        }

        return notIn;
    }

    public boolean isLezioneItemDisabled() {
        lezioneItemDisabled = nuovoArgomento || confermaRimozione;
        return lezioneItemDisabled;
    }

    public void setLezioneItemDisabled(boolean lezioneItemDisabled) {
        this.lezioneItemDisabled = lezioneItemDisabled;
    }

    private void impostaAssenzeRitirati(Lezioni nuovaLezione) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Long idLezione = nuovaLezione.getIdLezione();
        int ore = nuovaLezione.getOreLezione();
        getStudentiItems();
//        Iterator iterator = studentiItems.iterator();
        for (int j=0; j < studentiItems.getRowCount(); j++) {
            studentiItems.setRowIndex(j);
            Studenti studente = (Studenti) studentiItems.getRowData();
            if (studente.getAttivo() == 0
                    && (nuovaLezione.getDataLezione().after(studente.getRitiratoData()))
                    || (nuovaLezione.getDataLezione().equals(studente.getRitiratoData()))) {
                Long idStudente = studente.getIdStudente();

                for (int i = 1; i <= ore; i++) {
                    OreAssenze entity;
                    entity = new OreAssenze(idLezione, i, idStudente);
                    entity.setAssenza((short)1);
                    entity.setRitardo((short)0);
                    entity.setGiustificaAssenza((short)0);
                    entity.setGiustificaRitardo((short)0);
                    getOreAssenzeFacade().create(entity);
                }

            }

        }
    }

    public DataModel getStudentiItems() {
        studentiItems = (DataModel) JsfUtil.getSessionMapValue("studentiItems");
        return studentiItems;
    }

    public boolean isRimuoviLezioneDisabled() {
        if (lezioneDaRimuovere == null) {
            rimuoviLezioneDisabled = true;
        }
        return rimuoviLezioneDisabled;
    }

    public void setRimuoviLezioneDisabled(boolean rimuoviLezioneDisabled) {
        this.rimuoviLezioneDisabled = rimuoviLezioneDisabled;
    }

    public boolean isUpdateArgomentoDisabled() {
        if (lezioneDaRimuovere == null) {
            updateArgomentoDisabled = true;
        }
        return updateArgomentoDisabled;
    }

    public void setUpdateArgomentoDisabled(boolean updateArgomentoDisabled) {
        this.updateArgomentoDisabled = updateArgomentoDisabled;
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

    private void ricalcolaRiepilogo() {
//        throw new UnsupportedOperationException("Not yet implemented");
        RisultatiPeriodoBean controller = (RisultatiPeriodoBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("risultatiPeriodoBean");
        if (controller != null) {
            controller.setRecords(null);
        }
    }
}
