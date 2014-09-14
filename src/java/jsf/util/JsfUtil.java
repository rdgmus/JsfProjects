package jsf.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import jpa.entities.UtentiLogger;
import jpa.entities.UtentiScuola;
import jpa.session.UtentiLoggerFacade;
import org.apache.commons.codec.binary.Base64;

public class JsfUtil {

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static String codificaBase64(String daCodificare) {
        //encoding  byte array into base 64
        byte[] encoded = Base64.encodeBase64(daCodificare.getBytes());
        encoded = Base64.encodeBase64(encoded);

//        JsfUtil.addSuccessMessage("Password Codificata!");
        return new String(encoded);

    }

    public static String decodificaBase64(String codificata) {
        //decoding byte array into base64 byte[] decoded =
        byte[] decoded = Base64.decodeBase64(codificata);
        decoded = Base64.decodeBase64(decoded);
        return new String(decoded);

//        JsfUtil.addSuccessMessage("Password Decodificata!");
    }

    // Getters -----------------------------------------------------------------------------------
    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static Object getRequestMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
    }
    // Setters -----------------------------------------------------------------------------------

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static void removeSessionMapValue(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }

    public static void invalidateSession() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        map.clear();
    }

    public static void registraEventoNelDatabase(
            String msg,
            String msgType,
            UtentiLoggerFacade utentiLoggerFacade) {

        UtentiLogger entity = new UtentiLogger();
        UtentiScuola utente = (UtentiScuola) JsfUtil.getSessionMapValue("utenteLoggedIn");
        if (utente == null) {
            JsfUtil.addErrorMessage("Utente is null!");
            return;
        }
        if (utentiLoggerFacade == null) {
            JsfUtil.addErrorMessage("Non posso accedere alla tabella 'utenti_logger' del databse 'scuola'");
            return;
        }
        entity.setIdUtente(utente);
        entity.setMsgType(msgType);
        entity.setMessage(msg);
        try {
//            Long nextId = utentiLoggerFacade.getNextId();
//            entity.setIdLog(nextId);
            Timestamp date = utentiLoggerFacade.getCurrentTimeStamp();
            entity.setWhenRegistered(date);

            utentiLoggerFacade.create(entity);
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ex.getMessage());
        }

    }
}
