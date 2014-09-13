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
public class PeriodiAnnoScolasticoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_periodo", nullable = false)
    private long idPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_scuola", nullable = false)
    private long idScuola;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anno_scolastico", nullable = false)
    private long idAnnoScolastico;

    public PeriodiAnnoScolasticoPK() {
    }

    public PeriodiAnnoScolasticoPK(long idPeriodo, long idScuola, long idAnnoScolastico) {
        this.idPeriodo = idPeriodo;
        this.idScuola = idScuola;
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public long getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(long idScuola) {
        this.idScuola = idScuola;
    }

    public long getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(long idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPeriodo;
        hash += (int) idScuola;
        hash += (int) idAnnoScolastico;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodiAnnoScolasticoPK)) {
            return false;
        }
        PeriodiAnnoScolasticoPK other = (PeriodiAnnoScolasticoPK) object;
        if (this.idPeriodo != other.idPeriodo) {
            return false;
        }
        if (this.idScuola != other.idScuola) {
            return false;
        }
        if (this.idAnnoScolastico != other.idAnnoScolastico) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.PeriodiAnnoScolasticoPK[ idPeriodo=" + idPeriodo + ", idScuola=" + idScuola + ", idAnnoScolastico=" + idAnnoScolastico + " ]";
    }
    
}
