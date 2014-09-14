/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.entities.UtentiLogger;
import jpa.entities.UtentiScuola;
import jpa.session.UtentiLoggerFacade;
import jpa.session.UtentiScuolaFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@ManagedBean
@SessionScoped
public class MyLoginBean implements Serializable {

    @EJB
    IntestazioneBean intestazioneBean;

    public IntestazioneBean getIntestazioneBean() {
        return intestazioneBean;
    }

    private void sendEmail() {
        getIntestazioneBean().sendEmail();
    }

    private enum logEventsTypes {

        SUCCESS, FAILURE
    }
    @EJB
    UtentiScuolaFacade utentiScuola;
    private String email;// = "rdgmus@gmail.com";
    private String password;
    private UtentiScuola utente;
    @EJB
    UtentiLoggerFacade utentiLoggerFacade;

    public MyLoginBean() {
    }

    public UtentiScuola getUtente() {
        return utente;
    }

    public void setUtente(UtentiScuola utente) {
        this.utente = utente;
    }

    public void MyloginBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newValue) {
        password = newValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }

    public UtentiScuolaFacade getUtentiScuola() {
        return utentiScuola;
    }

    public UtentiLoggerFacade getUtentiLoggerFacade() {
        return utentiLoggerFacade;
    }

    public String login() {
        setUtente(getUtentiScuola().FindByEmailPassword(getEmail(), JsfUtil.codificaBase64(getPassword())));
        if (utente != null) {
            String msg = "Successo! "
                    + " Benvenuto " + utente.getNome() + " " + utente.getCognome();
            JsfUtil.addSuccessMessage(msg);
            impostaUtenteSessione();
            registraLoginEvent(logEventsTypes.SUCCESS);
            return "/registro/ScegliRegistroScolastico";
        } else {
            String msg = "Login fallito. Le credenziali inserite "
                    + "non corrispondono ad alcun utente registrato!";
            JsfUtil.addErrorMessage(msg);
            JsfUtil.invalidateSession();
            setUtente(null);
            registraLoginEvent(logEventsTypes.FAILURE);
            return "/authenticate/loginRegistro";
        }
    }

    public String logout() {
        registraLogoutEvent();
        sendEmail();
        JsfUtil.invalidateSession();
        utente = null;
        JsfUtil.invalidateSession();
        return "/intestazione";
    }

    private void impostaUtenteSessione() {
        JsfUtil.setSessionMapValue("utenteLoggedIn", utente);
    }

    private void registraLoginEvent(logEventsTypes type) {
        Timestamp date = getUtentiLoggerFacade().getCurrentTimeStamp();
        UtentiLogger entity = new UtentiLogger();
//
        entity.setIdUtente(utente);
        entity.setWhenRegistered(date);
        switch (type) {
            case SUCCESS:
                entity.setMsgType(ResourceBundle.getBundle("/resources/Registro").getString("LoginSuccessTypeString"));
                entity.setMessage(ResourceBundle.getBundle("/resources/Registro").getString("LoginMsgString") + type.toString());
                break;
            case FAILURE:
                entity.setMsgType(ResourceBundle.getBundle("/resources/Registro").getString("LoginFailureTypeString"));
                entity.setMessage(ResourceBundle.getBundle("/resources/Registro").getString("LoginMsgString") + type.toString());
                break;
        }
        getUtentiLoggerFacade().create(entity);
        sendEmail();
    }

    private void registraLogoutEvent() {
//        UtentiLogger entity = new UtentiLogger();
//        utente = (UtentiScuola) JsfUtil.getSessionMapValue("utenteLoggedIn");
//        entity.setIdUtente(utente);
//        entity.setMsgType(ResourceBundle.getBundle("/resources/Registro").getString("LogoutTypeString"));
//        entity.setMessage(ResourceBundle.getBundle("/resources/Registro").getString("LogoutMsgString"));
//        try {
////            Long nextId = getUtentiLoggerFacade().getNextId();
////            entity.setIdLog(nextId);
//            Timestamp date = getUtentiLoggerFacade().getCurrentTimeStamp();
//            entity.setWhenRegistered(date);
//
//            getUtentiLoggerFacade().create(entity);
//        } catch (Exception ex) {
//            JsfUtil.addErrorMessage(ex, ex.getMessage());
//        }

    }

    public String backToIntestazione() {
        return "/intestazione";
    }

    public String backToScegliRegistro() {
//        AllievoLezioneBean allievoLezioneBeanController = (AllievoLezioneBean) FacesContext.getCurrentInstance().getExternalContext()
//                .getSessionMap().get("allievoLezioneBean");
//
//        // This only works if myBean2 is request scoped and already created.
//        if (allievoLezioneBeanController != null) {
//            allievoLezioneBeanController.setTipoVoto('N');
//        }
//        RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
//                .getSessionMap().get("registriInsegnanteBean");
//
//        // This only works if myBean2 is request scoped and already created.
//        if (controller != null) {
//            controller.closeRegistro();
//        }
        return "/registro/ScegliRegistroScolastico";
    }

}
