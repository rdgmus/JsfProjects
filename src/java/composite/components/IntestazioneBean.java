/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
//@ManagedBean(name = "intestazioneBean")
//@SessionScoped
@Stateless
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

    /**
     * Invia Email con i messaggi di log all'utente del Registro Scolastico
     */
    public void sendEmail() {
        if (getUtente() == null) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("InvioEmailFallitoPerUtenteNull"));
            return;
        }
        String email = utente.getEmail();
        String cognome = utente.getCognome();
        String nome = utente.getNome();

        buildEmailMessageWithLog();

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

        setLogEmailSent();
    }

    private void buildEmailMessageWithLog() {
        List<UtentiLogger> utenteLogs = getUtentiLoggerFacade().findUtenteLogs(utente, false);
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

    private void setLogEmailSent() {
        if (utente == null) {
            return;
        }
        getUtentiLoggerFacade().setUtenteLogsEmailed(utente);
    }

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
