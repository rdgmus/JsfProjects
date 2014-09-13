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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "classi", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome_classe", "anno_scolastico"}),
    @UniqueConstraint(columnNames = {"id_classe"}),
    @UniqueConstraint(columnNames = {"id_classe", "anno_scolastico", "id_anno_scolastico"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classi.findAll", query = "SELECT c FROM Classi c"),
    @NamedQuery(name = "Classi.findByNomeClasse", query = "SELECT c FROM Classi c WHERE c.nomeClasse = :nomeClasse"),
    @NamedQuery(name = "Classi.findByIdClasse", query = "SELECT c FROM Classi c WHERE c.idClasse = :idClasse"),
    @NamedQuery(name = "Classi.findByAnnoScolastico", query = "SELECT c FROM Classi c WHERE c.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "Classi.findBySpecializzazione", query = "SELECT c FROM Classi c WHERE c.specializzazione = :specializzazione")})
public class Classi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nome_classe", nullable = false, length = 10)
    private String nomeClasse;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_classe", nullable = false)
    private Long idClasse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "anno_scolastico", nullable = false, length = 10)
    private String annoScolastico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "specializzazione", nullable = false, length = 50)
    private String specializzazione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClasse")
    private Collection<Insegnanti> insegnantiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClasse")
    private Collection<Studenti> studentiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClasse")
    private Collection<Materie> materieCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClasse")
    private Collection<OrarioLezioniAs> orarioLezioniAsCollection;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @ManyToOne(optional = false)
    private AnniScolastici idAnnoScolastico;
    @JoinColumn(name = "id_scuola", referencedColumnName = "id_scuola", nullable = false)
    @ManyToOne(optional = false)
    private Scuole idScuola;

    public Classi() {
    }

    public Classi(Long idClasse) {
        this.idClasse = idClasse;
    }

    public Classi(Long idClasse, String nomeClasse, String annoScolastico, String specializzazione) {
        this.idClasse = idClasse;
        this.nomeClasse = nomeClasse;
        this.annoScolastico = annoScolastico;
        this.specializzazione = specializzazione;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public Long getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Long idClasse) {
        this.idClasse = idClasse;
    }

    public String getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(String annoScolastico) {
        this.annoScolastico = annoScolastico;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @XmlTransient
    public Collection<Insegnanti> getInsegnantiCollection() {
        return insegnantiCollection;
    }

    public void setInsegnantiCollection(Collection<Insegnanti> insegnantiCollection) {
        this.insegnantiCollection = insegnantiCollection;
    }

    @XmlTransient
    public Collection<Studenti> getStudentiCollection() {
        return studentiCollection;
    }

    public void setStudentiCollection(Collection<Studenti> studentiCollection) {
        this.studentiCollection = studentiCollection;
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

    public AnniScolastici getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(AnniScolastici idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public Scuole getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(Scuole idScuola) {
        this.idScuola = idScuola;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClasse != null ? idClasse.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classi)) {
            return false;
        }
        Classi other = (Classi) object;
        if ((this.idClasse == null && other.idClasse != null) || (this.idClasse != null && !this.idClasse.equals(other.idClasse))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Classi[ idClasse=" + idClasse + " ]";
    }
    
}
