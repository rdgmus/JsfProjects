/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "ruoli_utenti", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RuoliUtenti.findAll", query = "SELECT r FROM RuoliUtenti r"),
    @NamedQuery(name = "RuoliUtenti.findByIdRuolo", query = "SELECT r FROM RuoliUtenti r WHERE r.idRuolo = :idRuolo")})
public class RuoliUtenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ruolo", nullable = false)
    private Long idRuolo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "ruolo", nullable = false, length = 2147483647)
    private String ruolo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRuolo")
    private Collection<RuoliGrantedToUtenti> ruoliGrantedToUtentiCollection;

    public RuoliUtenti() {
    }

    public RuoliUtenti(Long idRuolo) {
        this.idRuolo = idRuolo;
    }

    public RuoliUtenti(Long idRuolo, String ruolo) {
        this.idRuolo = idRuolo;
        this.ruolo = ruolo;
    }

    public Long getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(Long idRuolo) {
        this.idRuolo = idRuolo;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @XmlTransient
    public Collection<RuoliGrantedToUtenti> getRuoliGrantedToUtentiCollection() {
        return ruoliGrantedToUtentiCollection;
    }

    public void setRuoliGrantedToUtentiCollection(Collection<RuoliGrantedToUtenti> ruoliGrantedToUtentiCollection) {
        this.ruoliGrantedToUtentiCollection = ruoliGrantedToUtentiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRuolo != null ? idRuolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuoliUtenti)) {
            return false;
        }
        RuoliUtenti other = (RuoliUtenti) object;
        if ((this.idRuolo == null && other.idRuolo != null) || (this.idRuolo != null && !this.idRuolo.equals(other.idRuolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.RuoliUtenti[ idRuolo=" + idRuolo + " ]";
        return ruolo;
    }
    
}
