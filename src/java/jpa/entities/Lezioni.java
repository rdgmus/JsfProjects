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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "lezioni", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lezioni.findAll", query = "SELECT l FROM Lezioni l"),
    @NamedQuery(name = "Lezioni.findByIdLezione", query = "SELECT l FROM Lezioni l WHERE l.idLezione = :idLezione"),
    @NamedQuery(name = "Lezioni.findByDataLezione", query = "SELECT l FROM Lezioni l WHERE l.dataLezione = :dataLezione"),
    @NamedQuery(name = "Lezioni.findByOreLezione", query = "SELECT l FROM Lezioni l WHERE l.oreLezione = :oreLezione"),
    @NamedQuery(name = "Lezioni.findByArgomento", query = "SELECT l FROM Lezioni l WHERE l.argomento = :argomento"),
    @NamedQuery(name = "Lezioni.findByFreezeLezione", query = "SELECT l FROM Lezioni l WHERE l.freezeLezione = :freezeLezione")})
public class Lezioni implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_lezione", nullable = false)
    private Long idLezione;
    @Column(name = "data_lezione")
    @Temporal(TemporalType.DATE)
    private Date dataLezione;
    @Column(name = "ore_lezione")
    private Integer oreLezione;
    @Size(max = 100)
    @Column(name = "argomento", length = 100)
    private String argomento;
    @Column(name = "freeze_lezione")
    private Short freezeLezione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lezioni")
    private Collection<OreAssenze> oreAssenzeCollection;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia", nullable = false)
    @ManyToOne(optional = false)
    private Materie idMateria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lezioni")
    private Collection<VotiLezioniStudente> votiLezioniStudenteCollection;

    public Lezioni() {
    }

    public Lezioni(Long idLezione) {
        this.idLezione = idLezione;
    }

    public Long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(Long idLezione) {
        this.idLezione = idLezione;
    }

    public Date getDataLezione() {
        return dataLezione;
    }

    public void setDataLezione(Date dataLezione) {
        this.dataLezione = dataLezione;
    }

    public Integer getOreLezione() {
        return oreLezione;
    }

    public void setOreLezione(Integer oreLezione) {
        this.oreLezione = oreLezione;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public Short getFreezeLezione() {
        return freezeLezione;
    }

    public void setFreezeLezione(Short freezeLezione) {
        this.freezeLezione = freezeLezione;
    }

    @XmlTransient
    public Collection<OreAssenze> getOreAssenzeCollection() {
        return oreAssenzeCollection;
    }

    public void setOreAssenzeCollection(Collection<OreAssenze> oreAssenzeCollection) {
        this.oreAssenzeCollection = oreAssenzeCollection;
    }

    public Materie getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Materie idMateria) {
        this.idMateria = idMateria;
    }

    @XmlTransient
    public Collection<VotiLezioniStudente> getVotiLezioniStudenteCollection() {
        return votiLezioniStudenteCollection;
    }

    public void setVotiLezioniStudenteCollection(Collection<VotiLezioniStudente> votiLezioniStudenteCollection) {
        this.votiLezioniStudenteCollection = votiLezioniStudenteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLezione != null ? idLezione.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lezioni)) {
            return false;
        }
        Lezioni other = (Lezioni) object;
        if ((this.idLezione == null && other.idLezione != null) || (this.idLezione != null && !this.idLezione.equals(other.idLezione))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Lezioni[ idLezione=" + idLezione + " ]";
    }
    
}
