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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "allievi_risultati_periodo", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AllieviRisultatiPeriodo.findAll", query = "SELECT a FROM AllieviRisultatiPeriodo a"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByIdPeriodo", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.idPeriodo = :idPeriodo"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByIdMateria", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.idMateria = :idMateria"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByIdStudente", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.idStudente = :idStudente"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByNumAssenze", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.numAssenze = :numAssenze"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByTotaleOrePeriodo", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.totaleOrePeriodo = :totaleOrePeriodo"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByPercentualeAssenzePeriodo", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.percentualeAssenzePeriodo = :percentualeAssenzePeriodo"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByGiudizio", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.giudizio = :giudizio"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByScritto", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.scritto = :scritto"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByOrale", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.orale = :orale"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByPratico", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.pratico = :pratico"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByCondotta", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.condotta = :condotta"),
    @NamedQuery(name = "AllieviRisultatiPeriodo.findByIdRisultato", query = "SELECT a FROM AllieviRisultatiPeriodo a WHERE a.idRisultato = :idRisultato")})
public class AllieviRisultatiPeriodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_periodo", nullable = false)
    private long idPeriodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_materia", nullable = false)
    private long idMateria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_studente", nullable = false)
    private long idStudente;
    @Column(name = "num_assenze")
    private Integer numAssenze;
    @Column(name = "totale_ore_periodo")
    private Integer totaleOrePeriodo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percentuale_assenze_periodo", precision = 22)
    private Double percentualeAssenzePeriodo;
    @Size(max = 15)
    @Column(name = "giudizio", length = 15)
    private String giudizio;
    @Size(max = 10)
    @Column(name = "scritto", length = 10)
    private String scritto;
    @Size(max = 10)
    @Column(name = "orale", length = 10)
    private String orale;
    @Size(max = 10)
    @Column(name = "pratico", length = 10)
    private String pratico;
    @Size(max = 10)
    @Column(name = "condotta", length = 10)
    private String condotta;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_risultato", nullable = false)
    private Integer idRisultato;

    public AllieviRisultatiPeriodo() {
    }

    public AllieviRisultatiPeriodo(Integer idRisultato) {
        this.idRisultato = idRisultato;
    }

    public AllieviRisultatiPeriodo(Integer idRisultato, long idPeriodo, long idMateria, long idStudente) {
        this.idRisultato = idRisultato;
        this.idPeriodo = idPeriodo;
        this.idMateria = idMateria;
        this.idStudente = idStudente;
    }

    public long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }

    public long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(long idStudente) {
        this.idStudente = idStudente;
    }

    public Integer getNumAssenze() {
        return numAssenze;
    }

    public void setNumAssenze(Integer numAssenze) {
        this.numAssenze = numAssenze;
    }

    public Integer getTotaleOrePeriodo() {
        return totaleOrePeriodo;
    }

    public void setTotaleOrePeriodo(Integer totaleOrePeriodo) {
        this.totaleOrePeriodo = totaleOrePeriodo;
    }

    public Double getPercentualeAssenzePeriodo() {
        return percentualeAssenzePeriodo;
    }

    public void setPercentualeAssenzePeriodo(Double percentualeAssenzePeriodo) {
        this.percentualeAssenzePeriodo = percentualeAssenzePeriodo;
    }

    public String getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(String giudizio) {
        this.giudizio = giudizio;
    }

    public String getScritto() {
        return scritto;
    }

    public void setScritto(String scritto) {
        this.scritto = scritto;
    }

    public String getOrale() {
        return orale;
    }

    public void setOrale(String orale) {
        this.orale = orale;
    }

    public String getPratico() {
        return pratico;
    }

    public void setPratico(String pratico) {
        this.pratico = pratico;
    }

    public String getCondotta() {
        return condotta;
    }

    public void setCondotta(String condotta) {
        this.condotta = condotta;
    }

    public Integer getIdRisultato() {
        return idRisultato;
    }

    public void setIdRisultato(Integer idRisultato) {
        this.idRisultato = idRisultato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRisultato != null ? idRisultato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AllieviRisultatiPeriodo)) {
            return false;
        }
        AllieviRisultatiPeriodo other = (AllieviRisultatiPeriodo) object;
        if ((this.idRisultato == null && other.idRisultato != null) || (this.idRisultato != null && !this.idRisultato.equals(other.idRisultato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.AllieviRisultatiPeriodo[ idRisultato=" + idRisultato + " ]";
    }
    
}
