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
@Table(name = "materie", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"materia", "id_classe", "anno_scolastico", "id_anno_scolastico"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materie.findAll", query = "SELECT m FROM Materie m"),
    @NamedQuery(name = "Materie.findByIdMateria", query = "SELECT m FROM Materie m WHERE m.idMateria = :idMateria"),
    @NamedQuery(name = "Materie.findByMateria", query = "SELECT m FROM Materie m WHERE m.materia = :materia"),
    @NamedQuery(name = "Materie.findByAnnoScolastico", query = "SELECT m FROM Materie m WHERE m.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "Materie.findByGiudizio", query = "SELECT m FROM Materie m WHERE m.giudizio = :giudizio"),
    @NamedQuery(name = "Materie.findByScritto", query = "SELECT m FROM Materie m WHERE m.scritto = :scritto"),
    @NamedQuery(name = "Materie.findByOrale", query = "SELECT m FROM Materie m WHERE m.orale = :orale"),
    @NamedQuery(name = "Materie.findByPratico", query = "SELECT m FROM Materie m WHERE m.pratico = :pratico")})
public class Materie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_materia", nullable = false)
    private Long idMateria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "materia", nullable = false, length = 50)
    private String materia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "anno_scolastico", nullable = false, length = 10)
    private String annoScolastico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "giudizio", nullable = false)
    private short giudizio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scritto", nullable = false)
    private short scritto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orale", nullable = false)
    private short orale;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pratico", nullable = false)
    private short pratico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMateria")
    private Collection<Lezioni> lezioniCollection;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @ManyToOne(optional = false)
    private AnniScolastici idAnnoScolastico;
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    @ManyToOne(optional = false)
    private Classi idClasse;
    @JoinColumn(name = "id_insegnante", referencedColumnName = "id_insegnante", nullable = false)
    @ManyToOne(optional = false)
    private Insegnanti idInsegnante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMateria")
    private Collection<OrarioLezioniAs> orarioLezioniAsCollection;

    public Materie() {
    }

    public Materie(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Materie(Long idMateria, String materia, String annoScolastico, short giudizio, short scritto, short orale, short pratico) {
        this.idMateria = idMateria;
        this.materia = materia;
        this.annoScolastico = annoScolastico;
        this.giudizio = giudizio;
        this.scritto = scritto;
        this.orale = orale;
        this.pratico = pratico;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(String annoScolastico) {
        this.annoScolastico = annoScolastico;
    }

    public short getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(short giudizio) {
        this.giudizio = giudizio;
    }

    public short getScritto() {
        return scritto;
    }

    public void setScritto(short scritto) {
        this.scritto = scritto;
    }

    public short getOrale() {
        return orale;
    }

    public void setOrale(short orale) {
        this.orale = orale;
    }

    public short getPratico() {
        return pratico;
    }

    public void setPratico(short pratico) {
        this.pratico = pratico;
    }

    @XmlTransient
    public Collection<Lezioni> getLezioniCollection() {
        return lezioniCollection;
    }

    public void setLezioniCollection(Collection<Lezioni> lezioniCollection) {
        this.lezioniCollection = lezioniCollection;
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

    public Insegnanti getIdInsegnante() {
        return idInsegnante;
    }

    public void setIdInsegnante(Insegnanti idInsegnante) {
        this.idInsegnante = idInsegnante;
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
        hash += (idMateria != null ? idMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materie)) {
            return false;
        }
        Materie other = (Materie) object;
        if ((this.idMateria == null && other.idMateria != null) || (this.idMateria != null && !this.idMateria.equals(other.idMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.Materie[ idMateria=" + idMateria + " ]";
        return materia;
    }
    
}
