/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package composite.components.records;

import java.util.Date;
import jpa.entities.Materie;

/**
 *
 * @author rdgmus
 */
public class AssenzeStudenteRecord {

    private Materie materia;
    private Date dataLezione;
    private int oreLezione;
    private boolean assenza1aOra;
    private boolean assenza2aOra;
    private boolean assenza3aOra;
    private boolean ritardo;
    private boolean giustificaAssenza, giustificaRitardo;
    private String motivoGiustifica;
    private Long idLezione, idStudente;

    public AssenzeStudenteRecord() {
    }

    public Materie getMateria() {
        return materia;
    }

    public void setMateria(Materie materia) {
        this.materia = materia;
    }

    public Date getDataLezione() {
        return dataLezione;
    }

    public void setDataLezione(Date dataLezione) {
        this.dataLezione = dataLezione;
    }

    public int getOreLezione() {
        return oreLezione;
    }

    public void setOreLezione(int oreLezione) {
        this.oreLezione = oreLezione;
    }

    public boolean isAssenza1aOra() {
        return assenza1aOra;
    }

    public void setAssenza1aOra(boolean assenza1aOra) {
        this.assenza1aOra = assenza1aOra;
    }

    public boolean isAssenza2aOra() {
        return assenza2aOra;
    }

    public void setAssenza2aOra(boolean assenza2aOra) {
        this.assenza2aOra = assenza2aOra;
    }

    public boolean isAssenza3aOra() {
        return assenza3aOra;
    }

    public void setAssenza3aOra(boolean assenza3aOra) {
        this.assenza3aOra = assenza3aOra;
    }

    public boolean isRitardo() {
        return ritardo;
    }

    public void setRitardo(boolean ritardo) {
        this.ritardo = ritardo;
    }

    public boolean isGiustificaAssenza() {
        return giustificaAssenza;
    }

    public void setGiustificaAssenza(boolean giustificaAssenza) {
        this.giustificaAssenza = giustificaAssenza;
    }

    public boolean isGiustificaRitardo() {
        return giustificaRitardo;
    }

    public void setGiustificaRitardo(boolean giustificaRitardo) {
        this.giustificaRitardo = giustificaRitardo;
    }

    public String getMotivoGiustifica() {
        return motivoGiustifica;
    }

    public void setMotivoGiustifica(String motivoGiustifica) {
        this.motivoGiustifica = motivoGiustifica;
    }

    public Long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(Long idLezione) {
        this.idLezione = idLezione;
    }

    public Long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(Long idStudente) {
        this.idStudente = idStudente;
    }
    
}
