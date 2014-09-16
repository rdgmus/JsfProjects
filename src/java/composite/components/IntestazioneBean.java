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
import jpa.entities.UtentiLogger;
import jpa.entities.UtentiScuola;
import jpa.session.UtentiLoggerFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "intestazioneBean")
@SessionScoped
public class IntestazioneBean implements Serializable {

    private UtentiScuola utente;
    private String emailMessage, emailMsgType;
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;
    private boolean renderCancellaLogs;
    private int countMyLogs;

    public IntestazioneBean() {

    }

    public void setUtente(UtentiScuola utente) {
        this.utente = utente;
    }

    public UtentiScuola getUtente() {
        utente = (UtentiScuola) JsfUtil.getSessionMapValue("utenteLoggedIn");
        return utente;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getEmailMsgType() {
        return emailMsgType;
    }

    public void setEmailMsgType(String emailMsgType) {
        this.emailMsgType = emailMsgType;
    }

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }
    public void sendEmail(){
        JsfUtil.sendEmail(getUtente(), getUtentiLoggerFacade());
    }
    

    /**
     * CANCELLA DALLA TABELLA UtentiLogger I RECORDS PER I QUALI
     * E' STATA INVIATA LA NOTIFICA ALL'UTENTE DEL LOG
     * 
     */
    public void cancellaMyLogRicevuti() {
        if (utente == null) {
            return;
        }
        List<UtentiLogger> utenteLogs = getUtentiLoggerFacade().findUtenteLogs(utente, true);
        for (UtentiLogger ul : utenteLogs) {
            getUtentiLoggerFacade().remove(ul);
        }
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Registro").getString("CancellatiMessaggiLogUtente"));

    }

    /**
     * Boolean usato per il rendering del bottone per la cancellazione dei LOGs
     * dell'utente
     *
     * @return
     */
    public boolean isRenderCancellaLogs() {
        if (utente == null) {
            return false;
        }
        List<UtentiLogger> utenteLogs = getUtentiLoggerFacade().findUtenteLogs(utente, true);
        renderCancellaLogs = utenteLogs.size() > 0;
        return renderCancellaLogs;
    }

    public void setRenderCancellaLogs(boolean renderCancellaLogs) {
        this.renderCancellaLogs = renderCancellaLogs;
    }

    /**
     * Conta i messaggi di LOG dell'utente non ancora inviati al medesimo
     *
     * @return
     */
    public int getCountMyLogs() {
        List<UtentiLogger> utenteLogs = getUtentiLoggerFacade().findUtenteLogs(utente, true);
        countMyLogs = utenteLogs.size();
        return countMyLogs;
    }

    public void setCountMyLogs(int countMyLogs) {
        this.countMyLogs = countMyLogs;
    }
    
}
