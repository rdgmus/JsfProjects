/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "orario_insegnante", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_orario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrarioInsegnante.findAll", query = "SELECT o FROM OrarioInsegnante o"),
    @NamedQuery(name = "OrarioInsegnante.findByIdOrario", query = "SELECT o FROM OrarioInsegnante o WHERE o.idOrario = :idOrario")})
public class OrarioInsegnante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orario", nullable = false)
    private Long idOrario;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @ManyToOne(optional = false)
    private AnniScolastici idAnnoScolastico;
    @JoinColumn(name = "id_insegnante", referencedColumnName = "id_insegnante", nullable = false)
    @ManyToOne(optional = false)
    private Insegnanti idInsegnante;

    public OrarioInsegnante() {
    }

    public OrarioInsegnante(Long idOrario) {
        this.idOrario = idOrario;
    }

    public Long getIdOrario() {
        return idOrario;
    }

    public void setIdOrario(Long idOrario) {
        this.idOrario = idOrario;
    }

    public AnniScolastici getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(AnniScolastici idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public Insegnanti getIdInsegnante() {
        return idInsegnante;
    }

    public void setIdInsegnante(Insegnanti idInsegnante) {
        this.idInsegnante = idInsegnante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrario != null ? idOrario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrarioInsegnante)) {
            return false;
        }
        OrarioInsegnante other = (OrarioInsegnante) object;
        if ((this.idOrario == null && other.idOrario != null) || (this.idOrario != null && !this.idOrario.equals(other.idOrario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.OrarioInsegnante[ idOrario=" + idOrario + " ]";
    }
    
}
