/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "periodi_anno_scolastico", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeriodiAnnoScolastico.findAll", query = "SELECT p FROM PeriodiAnnoScolastico p"),
    @NamedQuery(name = "PeriodiAnnoScolastico.findByIdPeriodo", query = "SELECT p FROM PeriodiAnnoScolastico p WHERE p.periodiAnnoScolasticoPK.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "PeriodiAnnoScolastico.findByIdScuola", query = "SELECT p FROM PeriodiAnnoScolastico p WHERE p.periodiAnnoScolasticoPK.idScuola = :idScuola"),
    @NamedQuery(name = "PeriodiAnnoScolastico.findByIdAnnoScolastico", query = "SELECT p FROM PeriodiAnnoScolastico p WHERE p.periodiAnnoScolasticoPK.idAnnoScolastico = :idAnnoScolastico"),
    @NamedQuery(name = "PeriodiAnnoScolastico.findByPeriodo", query = "SELECT p FROM PeriodiAnnoScolastico p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "PeriodiAnnoScolastico.findByStartDate", query = "SELECT p FROM PeriodiAnnoScolastico p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "PeriodiAnnoScolastico.findByEndDate", query = "SELECT p FROM PeriodiAnnoScolastico p WHERE p.endDate = :endDate")})
public class PeriodiAnnoScolastico implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PeriodiAnnoScolasticoPK periodiAnnoScolasticoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "periodo", nullable = false, length = 20)
    private String periodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AnniScolastici anniScolastici;
    @JoinColumn(name = "id_scuola", referencedColumnName = "id_scuola", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Scuole scuole;

    public PeriodiAnnoScolastico() {
    }

    public PeriodiAnnoScolastico(PeriodiAnnoScolasticoPK periodiAnnoScolasticoPK) {
        this.periodiAnnoScolasticoPK = periodiAnnoScolasticoPK;
    }

    public PeriodiAnnoScolastico(PeriodiAnnoScolasticoPK periodiAnnoScolasticoPK, String periodo, Date startDate, Date endDate) {
        this.periodiAnnoScolasticoPK = periodiAnnoScolasticoPK;
        this.periodo = periodo;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public PeriodiAnnoScolastico(long idPeriodo, long idScuola, long idAnnoScolastico) {
        this.periodiAnnoScolasticoPK = new PeriodiAnnoScolasticoPK(idPeriodo, idScuola, idAnnoScolastico);
    }

    public PeriodiAnnoScolasticoPK getPeriodiAnnoScolasticoPK() {
        return periodiAnnoScolasticoPK;
    }

    public void setPeriodiAnnoScolasticoPK(PeriodiAnnoScolasticoPK periodiAnnoScolasticoPK) {
        this.periodiAnnoScolasticoPK = periodiAnnoScolasticoPK;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AnniScolastici getAnniScolastici() {
        return anniScolastici;
    }

    public void setAnniScolastici(AnniScolastici anniScolastici) {
        this.anniScolastici = anniScolastici;
    }

    public Scuole getScuole() {
        return scuole;
    }

    public void setScuole(Scuole scuole) {
        this.scuole = scuole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodiAnnoScolasticoPK != null ? periodiAnnoScolasticoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodiAnnoScolastico)) {
            return false;
        }
        PeriodiAnnoScolastico other = (PeriodiAnnoScolastico) object;
        if ((this.periodiAnnoScolasticoPK == null && other.periodiAnnoScolasticoPK != null) || (this.periodiAnnoScolasticoPK != null && !this.periodiAnnoScolasticoPK.equals(other.periodiAnnoScolasticoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.PeriodiAnnoScolastico[ periodiAnnoScolasticoPK=" + periodiAnnoScolasticoPK + " ]";
    }
    
}
