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
public class VotiStudenteRecord {

    private Materie materia;
    private Date dataLezione;
    private Long idLezione, idVoto, idStudente;
    private String votoString;
    private Double votoNum;
    private boolean giudizio;
    private Character tipoVoto;
    private String argLezione;

    public VotiStudenteRecord() {
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

    public Long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(Long idLezione) {
        this.idLezione = idLezione;
    }

    public Long getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(Long idVoto) {
        this.idVoto = idVoto;
    }

    public Long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(Long idStudente) {
        this.idStudente = idStudente;
    }

    public String getVotoString() {
        return votoString;
    }

    public void setVotoString(String votoString) {
        this.votoString = votoString;
    }

    public Double getVotoNum() {
        return votoNum;
    }

    public void setVotoNum(Double votoNum) {
        this.votoNum = votoNum;
    }

    public boolean isGiudizio() {
        return giudizio;
    }

    public void setGiudizio(boolean giudizio) {
        this.giudizio = giudizio;
    }

    public Character getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(Character tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public String getArgLezione() {
        return argLezione;
    }

    public void setArgLezione(String argLezione) {
        this.argLezione = argLezione;
    }
    
}
