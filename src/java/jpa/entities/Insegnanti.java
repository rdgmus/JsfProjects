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
@Table(name = "insegnanti", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_insegnante", "id_classe"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Insegnanti.findAll", query = "SELECT i FROM Insegnanti i"),
    @NamedQuery(name = "Insegnanti.findByIdInsegnante", query = "SELECT i FROM Insegnanti i WHERE i.idInsegnante = :idInsegnante"),
    @NamedQuery(name = "Insegnanti.findByCognome", query = "SELECT i FROM Insegnanti i WHERE i.cognome = :cognome"),
    @NamedQuery(name = "Insegnanti.findByNome", query = "SELECT i FROM Insegnanti i WHERE i.nome = :nome"),
    @NamedQuery(name = "Insegnanti.findByAnnoScolastico", query = "SELECT i FROM Insegnanti i WHERE i.annoScolastico = :annoScolastico")})
public class Insegnanti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_insegnante", nullable = false)
    private Long idInsegnante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "anno_scolastico", nullable = false, length = 10)
    private String annoScolastico;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @ManyToOne(optional = false)
    private AnniScolastici idAnnoScolastico;
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    @ManyToOne(optional = false)
    private Classi idClasse;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInsegnante")
    private Collection<OrarioInsegnante> orarioInsegnanteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idInsegnante")
    private Collection<Materie> materieCollection;

    public Insegnanti() {
    }

    public Insegnanti(Long idInsegnante) {
        this.idInsegnante = idInsegnante;
    }

    public Insegnanti(Long idInsegnante, String cognome, String nome, String annoScolastico) {
        this.idInsegnante = idInsegnante;
        this.cognome = cognome;
        this.nome = nome;
        this.annoScolastico = annoScolastico;
    }

    public Long getIdInsegnante() {
        return idInsegnante;
    }

    public void setIdInsegnante(Long idInsegnante) {
        this.idInsegnante = idInsegnante;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(String annoScolastico) {
        this.annoScolastico = annoScolastico;
    }

    public AnniScolastici getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(AnniScolastici idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public Classi getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Classi idClasse) {
        this.idClasse = idClasse;
    }

    @XmlTransient
    public Collection<OrarioInsegnante> getOrarioInsegnanteCollection() {
        return orarioInsegnanteCollection;
    }

    public void setOrarioInsegnanteCollection(Collection<OrarioInsegnante> orarioInsegnanteCollection) {
        this.orarioInsegnanteCollection = orarioInsegnanteCollection;
    }

    @XmlTransient
    public Collection<Materie> getMaterieCollection() {
        return materieCollection;
    }

    public void setMaterieCollection(Collection<Materie> materieCollection) {
        this.materieCollection = materieCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInsegnante != null ? idInsegnante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insegnanti)) {
            return false;
        }
        Insegnanti other = (Insegnanti) object;
        if ((this.idInsegnante == null && other.idInsegnante != null) || (this.idInsegnante != null && !this.idInsegnante.equals(other.idInsegnante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.Insegnanti[ idInsegnante=" + idInsegnante + " ]";
        return cognome+" "+nome;
    }
    
}
