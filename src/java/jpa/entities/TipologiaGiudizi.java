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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "tipologia_giudizi", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"giudizio", "value_giudizio"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipologiaGiudizi.findAll", query = "SELECT t FROM TipologiaGiudizi t"),
    @NamedQuery(name = "TipologiaGiudizi.findByIdGiudizio", query = "SELECT t FROM TipologiaGiudizi t WHERE t.idGiudizio = :idGiudizio"),
    @NamedQuery(name = "TipologiaGiudizi.findByGiudizio", query = "SELECT t FROM TipologiaGiudizi t WHERE t.giudizio = :giudizio"),
    @NamedQuery(name = "TipologiaGiudizi.findByValueGiudizio", query = "SELECT t FROM TipologiaGiudizi t WHERE t.valueGiudizio = :valueGiudizio")})
public class TipologiaGiudizi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_giudizio", nullable = false)
    private Long idGiudizio;
    @Size(max = 15)
    @Column(name = "giudizio", length = 15)
    private String giudizio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value_giudizio", precision = 22)
    private Double valueGiudizio;

    public TipologiaGiudizi() {
    }

    public TipologiaGiudizi(Long idGiudizio) {
        this.idGiudizio = idGiudizio;
    }

    public Long getIdGiudizio() {
        return idGiudizio;
    }

    public void setIdGiudizio(Long idGiudizio) {
        this.idGiudizio = idGiudizio;
    }

    public String getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(String giudizio) {
        this.giudizio = giudizio;
    }

    public Double getValueGiudizio() {
        return valueGiudizio;
    }

    public void setValueGiudizio(Double valueGiudizio) {
        this.valueGiudizio = valueGiudizio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGiudizio != null ? idGiudizio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipologiaGiudizi)) {
            return false;
        }
        TipologiaGiudizi other = (TipologiaGiudizi) object;
        if ((this.idGiudizio == null && other.idGiudizio != null) || (this.idGiudizio != null && !this.idGiudizio.equals(other.idGiudizio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.TipologiaGiudizi[ idGiudizio=" + idGiudizio + " ]";
    }
    
}
