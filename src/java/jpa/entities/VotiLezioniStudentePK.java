/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rdgmus
 */
@Embeddable
public class VotiLezioniStudentePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lezione", nullable = false)
    private long idLezione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_voto", nullable = false)
    private long idVoto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_studente", nullable = false)
    private long idStudente;

    public VotiLezioniStudentePK() {
    }

    public VotiLezioniStudentePK(long idLezione, long idVoto, long idStudente) {
        this.idLezione = idLezione;
        this.idVoto = idVoto;
        this.idStudente = idStudente;
    }

    public long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(long idLezione) {
        this.idLezione = idLezione;
    }

    public long getIdVoto() {
        return idVoto;
    }

    public void setIdVoto(long idVoto) {
        this.idVoto = idVoto;
    }

    public long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(long idStudente) {
        this.idStudente = idStudente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLezione;
        hash += (int) idVoto;
        hash += (int) idStudente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotiLezioniStudentePK)) {
            return false;
        }
        VotiLezioniStudentePK other = (VotiLezioniStudentePK) object;
        if (this.idLezione != other.idLezione) {
            return false;
        }
        if (this.idVoto != other.idVoto) {
            return false;
        }
        if (this.idStudente != other.idStudente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.VotiLezioniStudentePK[ idLezione=" + idLezione + ", idVoto=" + idVoto + ", idStudente=" + idStudente + " ]";
    }
    
}
