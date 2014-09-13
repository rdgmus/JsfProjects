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
@Table(name = "registri_granted", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistriGranted.findAll", query = "SELECT r FROM RegistriGranted r"),
    @NamedQuery(name = "RegistriGranted.findByIdMateria", query = "SELECT r FROM RegistriGranted r WHERE r.idMateria = :idMateria"),
    @NamedQuery(name = "RegistriGranted.findByIdGrant", query = "SELECT r FROM RegistriGranted r WHERE r.idGrant = :idGrant"),
    @NamedQuery(name = "RegistriGranted.findByReadGranted", query = "SELECT r FROM RegistriGranted r WHERE r.readGranted = :readGranted"),
    @NamedQuery(name = "RegistriGranted.findByWriteGranted", query = "SELECT r FROM RegistriGranted r WHERE r.writeGranted = :writeGranted")})
public class RegistriGranted implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "ruolo", nullable = false, length = 2147483647)
    private String ruolo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_materia", nullable = false)
    private long idMateria;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_grant", nullable = false)
    private Long idGrant;
    @Basic(optional = false)
    @NotNull
    @Column(name = "read_granted", nullable = false)
    private short readGranted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "write_granted", nullable = false)
    private short writeGranted;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente", nullable = false)
    @ManyToOne(optional = false)
    private UtentiScuola idUtente;

    public RegistriGranted() {
    }

    public RegistriGranted(Long idGrant) {
        this.idGrant = idGrant;
    }

    public RegistriGranted(Long idGrant, String ruolo, long idMateria, short readGranted, short writeGranted) {
        this.idGrant = idGrant;
        this.ruolo = ruolo;
        this.idMateria = idMateria;
        this.readGranted = readGranted;
        this.writeGranted = writeGranted;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdGrant() {
        return idGrant;
    }

    public void setIdGrant(Long idGrant) {
        this.idGrant = idGrant;
    }

    public short getReadGranted() {
        return readGranted;
    }

    public void setReadGranted(short readGranted) {
        this.readGranted = readGranted;
    }

    public short getWriteGranted() {
        return writeGranted;
    }

    public void setWriteGranted(short writeGranted) {
        this.writeGranted = writeGranted;
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
        hash += (idGrant != null ? idGrant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistriGranted)) {
            return false;
        }
        RegistriGranted other = (RegistriGranted) object;
        if ((this.idGrant == null && other.idGrant != null) || (this.idGrant != null && !this.idGrant.equals(other.idGrant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.RegistriGranted[ idGrant=" + idGrant + " ]";
    }
    
}
