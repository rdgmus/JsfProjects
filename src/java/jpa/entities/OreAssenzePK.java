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
public class OreAssenzePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lezione", nullable = false)
    private long idLezione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_ora", nullable = false)
    private int numOra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_studente", nullable = false)
    private long idStudente;

    public OreAssenzePK() {
    }

    public OreAssenzePK(long idLezione, int numOra, long idStudente) {
        this.idLezione = idLezione;
        this.numOra = numOra;
        this.idStudente = idStudente;
    }

    public long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(long idLezione) {
        this.idLezione = idLezione;
    }

    public int getNumOra() {
        return numOra;
    }

    public void setNumOra(int numOra) {
        this.numOra = numOra;
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
        hash += (int) numOra;
        hash += (int) idStudente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OreAssenzePK)) {
            return false;
        }
        OreAssenzePK other = (OreAssenzePK) object;
        if (this.idLezione != other.idLezione) {
            return false;
        }
        if (this.numOra != other.numOra) {
            return false;
        }
        if (this.idStudente != other.idStudente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.OreAssenzePK[ idLezione=" + idLezione + ", numOra=" + numOra + ", idStudente=" + idStudente + " ]";
    }
    
}
