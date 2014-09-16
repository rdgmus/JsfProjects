/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import jpa.entities.UtentiScuola;
import jpa.session.UtentiScuolaFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@ManagedBean
@RequestScoped
public class MyNuovoUtenteBean {

    private String nome, cognome, email, password, confirmPassword;
    @EJB
    UtentiScuolaFacade utentiScuolaFacade;

    public MyNuovoUtenteBean() {
    }

    public MyNuovoUtenteBean(String nome, String cognome, String email, String password) {
        this.nome = nome.toUpperCase();
        this.cognome = cognome.toUpperCase();
        this.email = email.toLowerCase();
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome.toUpperCase();
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome.toUpperCase();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UtentiScuolaFacade getUtentiScuolaFacade() {
        return utentiScuolaFacade;
    }

    public String register() {
        boolean existsUserEmail = false;

        existsUserEmail = getUtentiScuolaFacade().existsUserEmail(email);
        if (existsUserEmail) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("EmailNuovoUtenteAlreadyExits"));
            return "";
        }
        if (!getConfirmPassword().equals(getPassword())) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/resources/Registro").getString("PasswordAndConfirmPasswordNotEqual"));
            return "";
        }


        if (!existsUserEmail) {
            try {
                UtentiScuola utenteNuovo = new UtentiScuola();
//                Long nextId = getUtentiScuolaFacade().getNextId();
//                utenteNuovo.setIdUtente(nextId);
                utenteNuovo.setCognome(cognome);
                utenteNuovo.setNome(nome);
                utenteNuovo.setEmail(email);
                utenteNuovo.setPassword(JsfUtil.codificaBase64(password));
                getUtentiScuolaFacade().create(utenteNuovo);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/resources/Registro").getString("NuovoUtenteRegistroCreated"));
            } catch (EJBException ex) {
                String msg = ex.getMessage()
                        + ". " + ResourceBundle.getBundle("/resources/Registro").getString("UtenteRegistroAlreadyCreated");
                JsfUtil.addErrorMessage(msg);
                return "";
            }
        }
        return "/authenticate/loginRegistro";
    }
}
