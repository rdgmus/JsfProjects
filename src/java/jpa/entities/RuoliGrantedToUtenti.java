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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "ruoli_granted_to_utenti", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RuoliGrantedToUtenti.findAll", query = "SELECT r FROM RuoliGrantedToUtenti r"),
    @NamedQuery(name = "RuoliGrantedToUtenti.findByIdRuoliGranted", query = "SELECT r FROM RuoliGrantedToUtenti r WHERE r.idRuoliGranted = :idRuoliGranted")})
public class RuoliGrantedToUtenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ruoli_granted", nullable = false)
    private Long idRuoliGranted;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "ruolo", nullable = false, length = 2147483647)
    private String ruolo;
    @JoinColumn(name = "id_ruolo", referencedColumnName = "id_ruolo", nullable = false)
    @ManyToOne(optional = false)
    private RuoliUtenti idRuolo;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente", nullable = false)
    @ManyToOne(optional = false)
    private UtentiScuola idUtente;

    public RuoliGrantedToUtenti() {
    }

    public RuoliGrantedToUtenti(Long idRuoliGranted) {
        this.idRuoliGranted = idRuoliGranted;
    }

    public RuoliGrantedToUtenti(Long idRuoliGranted, String ruolo) {
        this.idRuoliGranted = idRuoliGranted;
        this.ruolo = ruolo;
    }

    public Long getIdRuoliGranted() {
        return idRuoliGranted;
    }

    public void setIdRuoliGranted(Long idRuoliGranted) {
        this.idRuoliGranted = idRuoliGranted;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public RuoliUtenti getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(RuoliUtenti idRuolo) {
        this.idRuolo = idRuolo;
    }

    public UtentiScuola getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(UtentiScuola idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRuoliGranted != null ? idRuoliGranted.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuoliGrantedToUtenti)) {
            return false;
        }
        RuoliGrantedToUtenti other = (RuoliGrantedToUtenti) object;
        if ((this.idRuoliGranted == null && other.idRuoliGranted != null) || (this.idRuoliGranted != null && !this.idRuoliGranted.equals(other.idRuoliGranted))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.RuoliGrantedToUtenti[ idRuoliGranted=" + idRuoliGranted + " ]";
    }
    
}
