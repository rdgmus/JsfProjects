/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import jpa.entities.Insegnanti;
import jpa.entities.Materie;
import jpa.session.InsegnantiFacade;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "schedaInsegnanteBean")
@SessionScoped
public class SchedaInsegnanteBean implements Serializable, ValueChangeListener {

    private Insegnanti selectedInsegnante;
    private String cognome, nome;
    private long id;
    private boolean editable = false;
    private boolean cognomeChanged = false;
    private boolean nomeChanged = false;
    private Materie selectedMateria;
    private boolean updatable = false;
    private boolean insertingNew = false;
    @EJB
    InsegnantiFacade insegnantiFacade;
    private Insegnanti oldSelectedInsegnante;

    public SchedaInsegnanteBean() {
    }

    public Insegnanti getSelectedInsegnante() {
        return selectedInsegnante;
    }

    public InsegnantiFacade getInsegnantiFacade() {
        return insegnantiFacade;
    }

    public void setSelectedInsegnante(Insegnanti selectedInsegnante) {
        this.selectedInsegnante = selectedInsegnante;
        if (selectedInsegnante != null) {
            loadFields();
        }
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private void loadFields() {
        if (selectedInsegnante != null) {
            setId(selectedInsegnante.getIdInsegnante());
            setCognome(selectedInsegnante.getCognome());
            setNome(selectedInsegnante.getNome());
        }
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isCognomeChanged() {
        return cognomeChanged;
    }

    public void setCognomeChanged(boolean cognomeChanged) {
        this.cognomeChanged = cognomeChanged;
    }

    public boolean isNomeChanged() {
        return nomeChanged;
    }

    public void setNomeChanged(boolean nomeChanged) {
        this.nomeChanged = nomeChanged;
    }

    public Materie getSelectedMateria() {
        return selectedMateria;
    }

    public void setSelectedMateria(Materie selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    @Override
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        Object source = event.getSource();
        if (source instanceof HtmlInputText) {
            HtmlInputText in = (HtmlInputText) source;
            if (in.getId().equals("cognome")) {
                setCognomeChanged(true);
                selectedInsegnante.setCognome(String.valueOf(event.getNewValue()));
            }
            if (in.getId().equals("nome")) {
                setNomeChanged(true);
                selectedInsegnante.setNome(String.valueOf(event.getNewValue()));
            }
        }
        if (source instanceof HtmlSelectOneMenu) {
            HtmlSelectOneMenu menu = (HtmlSelectOneMenu) source;
            if (menu.getId().equals("materie")) {
//                selectedMateria.setMateria(String.valueOf(event.getNewValue()));
            }
        }
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        if (updatable) {
            setEditable(true);
        } else {
            setEditable(false);
            selectedInsegnante = getInsegnantiFacade().find(selectedInsegnante.getIdInsegnante());
            loadFields();
        }
        this.updatable = updatable;
    }

    public boolean isInsertingNew() {
        return insertingNew;
    }

    public void setInsertingNew(boolean insertingNew) {
        if (insertingNew) {
            setEditable(true);
            Insegnanti newInsegnante = new Insegnanti();
//            Long nextId = getInsegnantiFacade().getNextId();
//            Long nextId = new Long(String.valueOf(maxId.intValue() + 1));
//            newInsegnante.setIdInsegnante(nextId);
            newInsegnante.setCognome("[cognome]");
            newInsegnante.setNome("[nome]");
            newInsegnante.setIdClasse(selectedInsegnante.getIdClasse());
            newInsegnante.setIdAnnoScolastico(selectedInsegnante.getIdAnnoScolastico());
            setOldSelectedInsegnante(selectedInsegnante);
            setSelectedInsegnante(newInsegnante);
        } else {
            setEditable(false);
            if (oldSelectedInsegnante != null) {
                setSelectedInsegnante(oldSelectedInsegnante);
                setOldSelectedInsegnante(null);
            }
        }
        this.insertingNew = insertingNew;
    }

    public void updateInsegnante() {
        if (cognomeChanged || nomeChanged) {
            getInsegnantiFacade().updateCognomeNomeInsegnante(selectedInsegnante);
            selectedInsegnante = getInsegnantiFacade().find(selectedInsegnante.getIdInsegnante());
            cognomeChanged = false;
            nomeChanged = false;
        }

        setUpdatable(false);
        setEditable(false);
        loadFields();
    }

    public void insertNewInsegnante() {
    }

    public Insegnanti getOldSelectedInsegnante() {
        return oldSelectedInsegnante;
    }

    public void setOldSelectedInsegnante(Insegnanti oldSelectedInsegnante) {
        this.oldSelectedInsegnante = oldSelectedInsegnante;
    }
}
