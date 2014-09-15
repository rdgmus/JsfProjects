/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import jpa.entities.AnniScolastici;
import jpa.entities.Classi;
import jpa.entities.Insegnanti;
import jpa.entities.Materie;
import jpa.entities.PeriodiAnnoScolastico;
import jpa.entities.Scuole;
import jpa.entities.Studenti;
import jpa.session.AnniScolasticiFacade;
import jpa.session.ClassiFacade;
import jpa.session.InsegnantiFacade;
import jpa.session.MaterieFacade;
import jpa.session.PeriodiAnnoScolasticoFacade;
import jpa.session.ScuoleFacade;
import jpa.session.StudentiFacade;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@ManagedBean(name = "scuolaStructureBean")
@SessionScoped
public class ScuolaStructureBean implements ValueChangeListener, Serializable {

    @EJB
    ScuoleFacade scuoleFacade;
    @EJB
    AnniScolasticiFacade anniScolasticiFacade;
    @EJB
    ClassiFacade classiFacade;
    @EJB
    InsegnantiFacade insegnantiFacade;
    @EJB
    StudentiFacade studentiFacade;
    @EJB
    MaterieFacade materieFacade;
    @EJB
    PeriodiAnnoScolasticoFacade periodiAnnoScolasticoFacade;

    public ScuoleFacade getScuoleFacade() {
        return scuoleFacade;
    }

    public AnniScolasticiFacade getAnniScolasticiFacade() {
        return anniScolasticiFacade;
    }

    public ClassiFacade getClassiFacade() {
        return classiFacade;
    }

    public InsegnantiFacade getInsegnantiFacade() {
        return insegnantiFacade;
    }

    public StudentiFacade getStudentiFacade() {
        return studentiFacade;
    }

    public MaterieFacade getMaterieFacade() {
        return materieFacade;
    }

    public PeriodiAnnoScolasticoFacade getPeriodiAnnoScolasticoFacade() {
        return periodiAnnoScolasticoFacade;
    }

    private List<Scuole> scuole;
    private List<AnniScolastici> as;
    private List<Classi> classi;
    private List<Insegnanti> insegnanti;
    private List<Materie> materie;
    private List<Studenti> studenti;
    private List<PeriodiAnnoScolastico> periodiAnnoScolastico;
    private Scuole selectedScuola;
    private AnniScolastici selectedAS;
    private Classi selectedClasse;
    private Insegnanti selectedInsegnante;
    private Studenti selectedStudente;
    private Materie selectedMateria;

    public ScuolaStructureBean() {
    }

    public Materie getSelectedMateria() {
        if (selectedMateria == null) {
            materie = retrieveMaterie();
            if (materie.size() > 0) {
                setSelectedMateria(materie.get(0));
            }
        } else if (selectedMateriaNotIn(materie)) {
            setSelectedMateria(materie.get(0));
        }
        return selectedMateria;
    }

    public void setSelectedMateria(Materie selectedMateria) {
        this.selectedMateria = selectedMateria;
    }

    public Studenti getSelectedStudente() {
        if (selectedStudente == null) {
            studenti = retrieveStudenti();
            if (studenti.size() > 0) {
                setSelectedStudente(studenti.get(0));
            }
        } else if (selectedStudenteNotIn(studenti)) {
            setSelectedStudente(studenti.get(0));
        }
        return selectedStudente;
    }

    public void setSelectedStudente(Studenti selectedStudente) {
        JsfUtil.setSessionMapValue("selectedStudente", selectedStudente);
        this.selectedStudente = selectedStudente;
    }

    public Insegnanti getSelectedInsegnante() {
        if (selectedInsegnante == null) {
            insegnanti = retrieveInsegnanti();
            if (insegnanti.size() > 0) {
                setSelectedInsegnante(insegnanti.get(0));
            }
        } else if (selectedInsegnanteNotIn(insegnanti)) {
            setSelectedInsegnante(insegnanti.get(0));
        }

        return selectedInsegnante;
    }

    public void setSelectedInsegnante(Insegnanti selectedInsegnante) {
        this.selectedInsegnante = selectedInsegnante;
        //PUT INSEGNANTE SELEZIONATO INTO SESSION
        setInsegnanteForRegistri();
    }

    public void setInsegnanteForRegistri() {
        RegistriInsegnanteBean controller = (RegistriInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("registriInsegnanteBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setInsegnante(selectedInsegnante);
            controller.setAnnoScolastico(selectedAS);
            controller.setPeriodiAnnoScolastico(periodiAnnoScolastico);
            controller.prepareView();
        }
    }

    public Classi getSelectedClasse() {
        if (selectedClasse == null) {
            classi = retrieveClassi();
            if (classi.size() > 0) {
                setSelectedClasse(classi.get(0));
            }
        } else if (selectedClasseNotIn(classi)) {
            setSelectedClasse(classi.get(0));
        }

        return selectedClasse;
    }

    public void setSelectedClasse(Classi selectedClasse) {
        this.selectedClasse = selectedClasse;
    }

    public AnniScolastici getSelectedAS() {
        if (selectedAS == null) {
            as = retrieveAS();
            if (as.size() > 0) {
                setSelectedAS(as.get(0));
            }
        } else if (selectedAnnoScolasticoNotIn(as)) {
            setSelectedAS(as.get(0));
        }
        return selectedAS;
    }

    public void setSelectedAS(AnniScolastici selectedAS) {
        this.selectedAS = selectedAS;
        periodiAnnoScolastico = retrievePeriodiAnnoScolastico(selectedAS);
    }

    public Scuole getSelectedScuola() {
        if (selectedScuola == null) {
            scuole = retrieveScuole();
            if (scuole.size() > 0) {
                setSelectedScuola(scuole.get(0));
            }
        } else if (selectedScuolaNotIn(scuole)) {
            setSelectedScuola(scuole.get(0));
        }
        return selectedScuola;
    }

    public void setSelectedScuola(Scuole selectedScuola) {
        this.selectedScuola = selectedScuola;
    }

    public List<Scuole> getScuole() {
        return scuole;
    }

    public void setScuole(List<Scuole> scuole) {
        this.scuole = scuole;
    }

    public List<AnniScolastici> getAs() {
        return as;
    }

    public void setAs(List<AnniScolastici> as) {
        this.as = as;
    }

    public List<Classi> getClassi() {
        return classi;
    }

    public void setClassi(List<Classi> classi) {
        this.classi = classi;
    }

    public List<Insegnanti> getInsegnanti() {
        return insegnanti;
    }

    public void setInsegnanti(List<Insegnanti> insegnanti) {
        this.insegnanti = insegnanti;
    }

    public List<Materie> getMaterie() {
        return materie;
    }

    public void setMaterie(List<Materie> materie) {
        this.materie = materie;
    }

    public List<Studenti> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Studenti> studenti) {
        this.studenti = studenti;
    }

    /*
     * SCUOLE
     */
    public List<Scuole> retrieveScuole() {
        try {
            scuole = scuoleFacade.findAll();
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista delle SCUOLE:"
                    + ejbex.getMessage());
        }
        return scuole;
    }

    public int scuoleSize() {
        return scuole.size();
    }

    /*
     * ANNI SCOLASTICI
     */
    public List<AnniScolastici> retrieveAS() {
        try {
            setAs(getAnniScolasticiFacade().retrieveAnniScolasticiScuolaOrderedList(selectedScuola));
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista ANNI SCOLASTICI:"
                    + ejbex.getMessage());
        }
        return as;
    }

    public int anniScolasticiSize() {
        return as.size();
    }

    /*
     * CLASSI
     */
    public List<Classi> retrieveClassi() {
        try {
            setClassi(getClassiFacade().retrieveClassiAnnoScolasticoOrderedList(selectedAS));
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista CLASSI:"
                    + ejbex.getMessage());
        }
        return classi;
    }

    public int classiSize() {
        return classi.size();
    }

    /*
     * INSEGNANTI
     */
    public List<Insegnanti> retrieveInsegnanti() {
        try {
            setInsegnanti(getInsegnantiFacade().retrieveInsegnantiClasseOrderedList(selectedAS, selectedClasse));
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista INSEGNANTI:"
                    + ejbex.getMessage());
        }
        return insegnanti;
    }

    public int insegnantiSize() {
        return insegnanti.size();
    }

    /*
     * STUDENTI
     */
    public List<Studenti> retrieveStudenti() {
        try {
            setStudenti(getStudentiFacade().retrieveStudentiClasseOrderedList(selectedClasse));
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista STUDENTI:"
                    + ejbex.getMessage());
        }
        return studenti;
    }

    public int studentiSize() {
        return studenti.size();
    }

    /*
     * MATERIE
     */
    public List<Materie> retrieveMaterie() {
        try {
            setMaterie(getMaterieFacade().retrieveMaterieInsegnanteOrderedList(
                    selectedAS, selectedClasse, selectedInsegnante));
        } catch (Exception ejbex) {
            JsfUtil.addErrorMessage("Non è stato possibile estrarre la lista MATERIE:"
                    + ejbex.getMessage());
        }
        return materie;
    }

    public int materieSize() {
        return materie.size();
    }

    /*
     * VALUE CHANGE LISTENER
     */
    @Override
    @SuppressWarnings("IncompatibleEquals")
    public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        //        throw new UnsupportedOperationException("Not supported yet.");
        HtmlSelectOneMenu source = (HtmlSelectOneMenu) event.getSource();
        if (source.getId().equals("selectedScuola")) {
            setSelectedScuola((Scuole) event.getNewValue());
        }
        if (source.getId().equals("selectedAS")) {
            setSelectedAS((AnniScolastici) event.getNewValue());
        }
        if (source.getId().equals("selectedClasse")) {
            setSelectedClasse((Classi) event.getNewValue());
            chiudiSchedaStudente();
            chiudiSchedaInsegnante();
        }
        if (source.getId().equals("selectedInsegnante")) {
            setSelectedInsegnante((Insegnanti) event.getNewValue());
            schedaInsegnante(selectedInsegnante);
        }
        if (source.getId().equals("selectedStudente")) {
            setSelectedStudente((Studenti) event.getNewValue());
            schedaStudente(selectedStudente);
        }
        if (source.getId().equals("selectedMateria")) {
            setSelectedMateria((Materie) event.getNewValue());
        }
    }

    private boolean selectedClasseNotIn(List<Classi> classi) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (classiSize() == 0) {
            selectedClasse = null;
            return false;
        }
        for (Classi c : classi) {
            if (c.equals(selectedClasse)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    private boolean selectedInsegnanteNotIn(List<Insegnanti> insegnanti) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (insegnantiSize() == 0) {
            selectedInsegnante = null;
            return false;
        }
        for (Insegnanti c : insegnanti) {
            if (c.equals(selectedInsegnante)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    private boolean selectedStudenteNotIn(List<Studenti> studenti) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (studentiSize() == 0) {
            selectedStudente = null;
            return false;
        }
        for (Studenti c : studenti) {
            if (c.equals(selectedStudente)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    private boolean selectedMateriaNotIn(List<Materie> materie) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (materieSize() == 0) {
            selectedMateria = null;
            return false;
        }
        for (Materie c : materie) {
            if (c.equals(selectedMateria)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    private boolean selectedAnnoScolasticoNotIn(List<AnniScolastici> as) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (anniScolasticiSize() == 0) {
            selectedAS = null;
            return false;
        }
        for (AnniScolastici c : as) {
            if (c.equals(selectedAS)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    private boolean selectedScuolaNotIn(List<Scuole> scuole) {
//        throw new UnsupportedOperationException("Not yet implemented");
        boolean esito = true;
        if (scuoleSize() == 0) {
            selectedScuola = null;
            return false;
        }
        for (Scuole c : scuole) {
            if (c.equals(selectedScuola)) {
                esito = false;
                break;
            }
        }

        return esito;
    }

    public List<PeriodiAnnoScolastico> getPeriodiAnnoScolastico() {
        return periodiAnnoScolastico;
    }

    public void setPeriodiAnnoScolastico(List<PeriodiAnnoScolastico> periodiAnnoScolastico) {
        this.periodiAnnoScolastico = periodiAnnoScolastico;
    }

    private List<PeriodiAnnoScolastico> retrievePeriodiAnnoScolastico(AnniScolastici selectedAS) {
        setPeriodiAnnoScolastico(getPeriodiAnnoScolasticoFacade().findByAnnoScolastico(selectedAS));
        return periodiAnnoScolastico;
    }

    public void schedaStudente(Studenti studente) {
        JsfUtil.addSuccessMessage("Scheda studente:" + studente.getCognome() + " " + studente.getNome());
        SchedaStudenteBean controller = (SchedaStudenteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("schedaStudenteBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setSelectedStudente(studente);
            controller.setInsertingNew(false);
            controller.setUpdatable(false);
        }

    }

    public void schedaInsegnante(Insegnanti insegnante) {
        JsfUtil.addSuccessMessage("Scheda insegnante:" + insegnante.getCognome() + " " + insegnante.getNome());
        SchedaInsegnanteBean controller = (SchedaInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("schedaInsegnanteBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setSelectedInsegnante(insegnante);
        }

    }

    public void tabelloneDeiVoti(Classi classe) {
        JsfUtil.addSuccessMessage("Tabellone dei Voti della classe:" + classe.getNomeClasse());
    }

    private void chiudiSchedaStudente() {
//        throw new UnsupportedOperationException("Not yet implemented");
        SchedaStudenteBean controller = (SchedaStudenteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("schedaStudenteBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setSelectedStudente(null);
        }

    }

    private void chiudiSchedaInsegnante() {
//        throw new UnsupportedOperationException("Not yet implemented");
        SchedaInsegnanteBean controller = (SchedaInsegnanteBean) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("schedaInsegnanteBean");

        // This only works if myBean2 is request scoped and already created.
        if (controller != null) {
            controller.setSelectedInsegnante(null);
        }

    }
}
