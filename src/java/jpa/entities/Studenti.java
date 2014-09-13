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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "studenti", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studenti.findAll", query = "SELECT s FROM Studenti s"),
    @NamedQuery(name = "Studenti.findByAnnoScolastico", query = "SELECT s FROM Studenti s WHERE s.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "Studenti.findByCognome", query = "SELECT s FROM Studenti s WHERE s.cognome = :cognome"),
    @NamedQuery(name = "Studenti.findByNome", query = "SELECT s FROM Studenti s WHERE s.nome = :nome"),
    @NamedQuery(name = "Studenti.findByIdStudente", query = "SELECT s FROM Studenti s WHERE s.idStudente = :idStudente"),
    @NamedQuery(name = "Studenti.findByAttivo", query = "SELECT s FROM Studenti s WHERE s.attivo = :attivo"),
    @NamedQuery(name = "Studenti.findByRitiratoData", query = "SELECT s FROM Studenti s WHERE s.ritiratoData = :ritiratoData"),
    @NamedQuery(name = "Studenti.findByIdAnnoScolastico", query = "SELECT s FROM Studenti s WHERE s.idAnnoScolastico = :idAnnoScolastico"),
    @NamedQuery(name = "Studenti.findByDataEntrata", query = "SELECT s FROM Studenti s WHERE s.dataEntrata = :dataEntrata")})
public class Studenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "anno_scolastico", nullable = false, length = 10)
    private String annoScolastico;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_studente", nullable = false)
    private Long idStudente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "attivo", nullable = false)
    private short attivo;
    @Column(name = "ritirato_data")
    @Temporal(TemporalType.DATE)
    private Date ritiratoData;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anno_scolastico", nullable = false)
    private long idAnnoScolastico;
    @Column(name = "data_entrata")
    @Temporal(TemporalType.DATE)
    private Date dataEntrata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studenti")
    private Collection<OreAssenze> oreAssenzeCollection;
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    @ManyToOne(optional = false)
    private Classi idClasse;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studenti")
    private Collection<VotiLezioniStudente> votiLezioniStudenteCollection;

    public Studenti() {
    }

    public Studenti(Long idStudente) {
        this.idStudente = idStudente;
    }

    public Studenti(Long idStudente, String annoScolastico, String cognome, String nome, short attivo, long idAnnoScolastico) {
        this.idStudente = idStudente;
        this.annoScolastico = annoScolastico;
        this.cognome = cognome;
        this.nome = nome;
        this.attivo = attivo;
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public String getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(String annoScolastico) {
        this.annoScolastico = annoScolastico;
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

    public Long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(Long idStudente) {
        this.idStudente = idStudente;
    }

    public short getAttivo() {
        return attivo;
    }

    public void setAttivo(short attivo) {
        this.attivo = attivo;
    }

    public Date getRitiratoData() {
        return ritiratoData;
    }

    public void setRitiratoData(Date ritiratoData) {
        this.ritiratoData = ritiratoData;
    }

    public long getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(long idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public Date getDataEntrata() {
        return dataEntrata;
    }

    public void setDataEntrata(Date dataEntrata) {
        this.dataEntrata = dataEntrata;
    }

    @XmlTransient
    public Collection<OreAssenze> getOreAssenzeCollection() {
        return oreAssenzeCollection;
    }

    public void setOreAssenzeCollection(Collection<OreAssenze> oreAssenzeCollection) {
        this.oreAssenzeCollection = oreAssenzeCollection;
    }

    public Classi getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Classi idClasse) {
        this.idClasse = idClasse;
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
        hash += (idStudente != null ? idStudente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studenti)) {
            return false;
        }
        Studenti other = (Studenti) object;
        if ((this.idStudente == null && other.idStudente != null) || (this.idStudente != null && !this.idStudente.equals(other.idStudente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Studenti[ idStudente=" + idStudente + " ]";
    }
    
}
