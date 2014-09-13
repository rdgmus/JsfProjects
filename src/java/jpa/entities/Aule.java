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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "aule", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aule.findAll", query = "SELECT a FROM Aule a"),
    @NamedQuery(name = "Aule.findByIdAula", query = "SELECT a FROM Aule a WHERE a.idAula = :idAula"),
    @NamedQuery(name = "Aule.findByNomeAula", query = "SELECT a FROM Aule a WHERE a.nomeAula = :nomeAula")})
public class Aule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aula", nullable = false)
    private Long idAula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nome_aula", nullable = false, length = 20)
    private String nomeAula;
    @JoinColumn(name = "id_scuola", referencedColumnName = "id_scuola", nullable = false)
    @ManyToOne(optional = false)
    private Scuole idScuola;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAula")
    private Collection<OrarioLezioniAs> orarioLezioniAsCollection;

    public Aule() {
    }

    public Aule(Long idAula) {
        this.idAula = idAula;
    }

    public Aule(Long idAula, String nomeAula) {
        this.idAula = idAula;
        this.nomeAula = nomeAula;
    }

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public String getNomeAula() {
        return nomeAula;
    }

    public void setNomeAula(String nomeAula) {
        this.nomeAula = nomeAula;
    }

    public Scuole getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(Scuole idScuola) {
        this.idScuola = idScuola;
    }

    @XmlTransient
    public Collection<OrarioLezioniAs> getOrarioLezioniAsCollection() {
        return orarioLezioniAsCollection;
    }

    public void setOrarioLezioniAsCollection(Collection<OrarioLezioniAs> orarioLezioniAsCollection) {
        this.orarioLezioniAsCollection = orarioLezioniAsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAula != null ? idAula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aule)) {
            return false;
        }
        Aule other = (Aule) object;
        if ((this.idAula == null && other.idAula != null) || (this.idAula != null && !this.idAula.equals(other.idAula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Aule[ idAula=" + idAula + " ]";
    }
    
}
