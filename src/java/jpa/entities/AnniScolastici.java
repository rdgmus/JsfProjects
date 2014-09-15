/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "anni_scolastici", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnniScolastici.findAll", query = "SELECT a FROM AnniScolastici a"),
    @NamedQuery(name = "AnniScolastici.findByIdAnnoScolastico", query = "SELECT a FROM AnniScolastici a WHERE a.idAnnoScolastico = :idAnnoScolastico"),
    @NamedQuery(name = "AnniScolastici.findByAnnoScolastico", query = "SELECT a FROM AnniScolastici a WHERE a.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "AnniScolastici.findByStartDate", query = "SELECT a FROM AnniScolastici a WHERE a.startDate = :startDate"),
    @NamedQuery(name = "AnniScolastici.findByEndDate", query = "SELECT a FROM AnniScolastici a WHERE a.endDate = :endDate")})
public class AnniScolastici implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_anno_scolastico", nullable = false)
    private Long idAnnoScolastico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "anno_scolastico", nullable = false, length = 10)
    private String annoScolastico;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private Collection<Insegnanti> insegnantiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private Collection<OrarioInsegnante> orarioInsegnanteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private Collection<ScansioneOrarioAs> scansioneOrarioAsCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private ParametriOrarioAs parametriOrarioAs;
    @JoinColumn(name = "id_scuola", referencedColumnName = "id_scuola", nullable = false)
    @ManyToOne(optional = false)
    private Scuole idScuola;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anniScolastici")
    private Collection<PeriodiAnnoScolastico> periodiAnnoScolasticoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private Collection<Materie> materieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private Collection<OrarioLezioniAs> orarioLezioniAsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAnnoScolastico")
    private Collection<Classi> classiCollection;

    public AnniScolastici() {
    }

    public AnniScolastici(Long idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public AnniScolastici(Long idAnnoScolastico, String annoScolastico, Date startDate, Date endDate) {
        this.idAnnoScolastico = idAnnoScolastico;
        this.annoScolastico = annoScolastico;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(Long idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public String getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(String annoScolastico) {
        this.annoScolastico = annoScolastico;
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

    @XmlTransient
    public Collection<Insegnanti> getInsegnantiCollection() {
        return insegnantiCollection;
    }

    public void setInsegnantiCollection(Collection<Insegnanti> insegnantiCollection) {
        this.insegnantiCollection = insegnantiCollection;
    }

    @XmlTransient
    public Collection<OrarioInsegnante> getOrarioInsegnanteCollection() {
        return orarioInsegnanteCollection;
    }

    public void setOrarioInsegnanteCollection(Collection<OrarioInsegnante> orarioInsegnanteCollection) {
        this.orarioInsegnanteCollection = orarioInsegnanteCollection;
    }

    @XmlTransient
    public Collection<ScansioneOrarioAs> getScansioneOrarioAsCollection() {
        return scansioneOrarioAsCollection;
    }

    public void setScansioneOrarioAsCollection(Collection<ScansioneOrarioAs> scansioneOrarioAsCollection) {
        this.scansioneOrarioAsCollection = scansioneOrarioAsCollection;
    }

    public ParametriOrarioAs getParametriOrarioAs() {
        return parametriOrarioAs;
    }

    public void setParametriOrarioAs(ParametriOrarioAs parametriOrarioAs) {
        this.parametriOrarioAs = parametriOrarioAs;
    }

    public Scuole getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(Scuole idScuola) {
        this.idScuola = idScuola;
    }

    @XmlTransient
    public Collection<PeriodiAnnoScolastico> getPeriodiAnnoScolasticoCollection() {
        return periodiAnnoScolasticoCollection;
    }

    public void setPeriodiAnnoScolasticoCollection(Collection<PeriodiAnnoScolastico> periodiAnnoScolasticoCollection) {
        this.periodiAnnoScolasticoCollection = periodiAnnoScolasticoCollection;
    }

    @XmlTransient
    public Collection<Materie> getMaterieCollection() {
        return materieCollection;
    }

    public void setMaterieCollection(Collection<Materie> materieCollection) {
        this.materieCollection = materieCollection;
    }

    @XmlTransient
    public Collection<OrarioLezioniAs> getOrarioLezioniAsCollection() {
        return orarioLezioniAsCollection;
    }

    public void setOrarioLezioniAsCollection(Collection<OrarioLezioniAs> orarioLezioniAsCollection) {
        this.orarioLezioniAsCollection = orarioLezioniAsCollection;
    }

    @XmlTransient
    public Collection<Classi> getClassiCollection() {
        return classiCollection;
    }

    public void setClassiCollection(Collection<Classi> classiCollection) {
        this.classiCollection = classiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnnoScolastico != null ? idAnnoScolastico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnniScolastici)) {
            return false;
        }
        AnniScolastici other = (AnniScolastici) object;
        if ((this.idAnnoScolastico == null && other.idAnnoScolastico != null) || (this.idAnnoScolastico != null && !this.idAnnoScolastico.equals(other.idAnnoScolastico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.AnniScolastici[ idAnnoScolastico=" + idAnnoScolastico + " ]";
        return annoScolastico;
    }
    
}
