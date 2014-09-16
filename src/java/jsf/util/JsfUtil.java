package jsf.util;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

    public static void setRequestMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key, value);
    }

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
    private static String emailMessage, emailMsgType;

    public static String getEmailMessage() {
        return emailMessage;
    }

    public static void setEmailMessage(String msg) {
        emailMessage = msg;
    }

    public static String getEmailMsgType() {
        return emailMsgType;
    }

    public static void setEmailMsgType(String emailMsgType) {
        JsfUtil.emailMsgType = emailMsgType;
    }

    public static void sendEmail(UtentiScuola utente, UtentiLoggerFacade utentiLoggerFacade) {
        if (utente == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("InvioEmailFallitoPerUtenteNull"));
            return;
        }
        String email = utente.getEmail();
        String cognome = utente.getCognome();
        String nome = utente.getNome();

        buildEmailMessageWithLog(utente, utentiLoggerFacade);

        try {
            InitialContext ctx = new InitialContext();
            Session session
                    = (Session) ctx.lookup("java:jboss/mail/gmail");
            // Or by injection.  
            //@Resource(name = "mail/<name>")  
            //private Session session;  

            // Create email and headers.  
            Message msg = new MimeMessage(session);
            msg.setSubject("LOG del Registro Scolastico di " + cognome + " " + nome);
            msg.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(
                            email,
                            cognome + " " + nome));
//        msg.setRecipient(Message.RecipientType.CC,
//                new InternetAddress(
//                "rdgmus@hotmail.com",
//                "hotmail"));
            msg.setFrom(new InternetAddress(
                    "rdgmus@gmail.com",
                    "Amministratore del Registro Scolastico"));

            // Body text.  
            BodyPart messageBodyPart = new MimeBodyPart();
            if (getEmailMessage() == null) {
                messageBodyPart.setText("Ciao! " + cognome + " " + nome);
            } else {
                messageBodyPart.setText(getEmailMessage());
            }

            // Multipart message.  
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attachment file from string.  
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName("README1.txt");
//        messageBodyPart.setContent(new String(
//                "file 1 content"),
//                "text/plain");
//        multipart.addBodyPart(messageBodyPart);
//
//        // Attachment file from file.  
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName("README2.txt");
//        DataSource src = new FileDataSource("file.txt");
//        messageBodyPart.setDataHandler(new DataHandler(src));
//        multipart.addBodyPart(messageBodyPart);
//
//        // Attachment file from byte array.  
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName("README3.txt");
//        src = new ByteArrayDataSource(
//                "file 3 content".getBytes(),
//                "text/plain");
//        messageBodyPart.setDataHandler(new DataHandler(src));
//        multipart.addBodyPart(messageBodyPart);
            // Add multipart message to email.  
            msg.setContent(multipart);

            // Send email.  
            Transport.send(msg);
        } catch (MessagingException me) {
            // manage exception
            JsfUtil.addErrorMessage(me, "MessagingException:" + me.getMessage());
            return;
        } catch (UnsupportedEncodingException uee) {
            // manage exception
            JsfUtil.addErrorMessage(uee, "UnsupportedEncodingException:" + uee.getMessage());
            return;
        } catch (NamingException ne) {
            JsfUtil.addErrorMessage(ne, "NamingException:" + ne.getMessage());
            return;
        }
        Calendar cal = Calendar.getInstance();

        JsfUtil.addSuccessMessage("Messaggio inviato a:" + cognome + " " + nome + " Email:" + email
                + " in Data:" + cal.getTime());

        setLogEmailSent(utente, utentiLoggerFacade);
    }

    private static void setLogEmailSent(UtentiScuola utente, UtentiLoggerFacade utentiLoggerFacade) {
        if (utente == null) {
            return;
        }
        utentiLoggerFacade.setUtenteLogsEmailed(utente);
    }

   

    private static void buildEmailMessageWithLog(UtentiScuola utente, UtentiLoggerFacade utentiLoggerFacade) {
        List<UtentiLogger> utenteLogs = utentiLoggerFacade.findUtenteLogs(utente, false);
        StringBuilder buff = new StringBuilder();
        for (UtentiLogger u : utenteLogs) {
            String msg = u.getMessage();
            String type = u.getMsgType();
            Date date = u.getWhenRegistered();

            buff.append("EVENTO:").append(type);
            buff.append("\n");
            buff.append(msg);
            buff.append("\n");
            buff.append("Registrato il:").append(date.toString());
            buff.append("\n******************************************************************\n");
        }
        setEmailMessage(buff.toString());
        setEmailMsgType("INFO");
    }

}
