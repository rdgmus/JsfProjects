/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "assenze_studenti", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssenzeStudenti.findAll", query = "SELECT a FROM AssenzeStudenti a"),
    @NamedQuery(name = "AssenzeStudenti.findByIdStudente", query = "SELECT a FROM AssenzeStudenti a WHERE a.idStudente = :idStudente"),
    @NamedQuery(name = "AssenzeStudenti.findByCognome", query = "SELECT a FROM AssenzeStudenti a WHERE a.cognome = :cognome"),
    @NamedQuery(name = "AssenzeStudenti.findByNome", query = "SELECT a FROM AssenzeStudenti a WHERE a.nome = :nome"),
    @NamedQuery(name = "AssenzeStudenti.findByIdClasse", query = "SELECT a FROM AssenzeStudenti a WHERE a.idClasse = :idClasse"),
    @NamedQuery(name = "AssenzeStudenti.findByAnnoScolastico", query = "SELECT a FROM AssenzeStudenti a WHERE a.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "AssenzeStudenti.findByNomeClasse", query = "SELECT a FROM AssenzeStudenti a WHERE a.nomeClasse = :nomeClasse"),
    @NamedQuery(name = "AssenzeStudenti.findByIdMateria", query = "SELECT a FROM AssenzeStudenti a WHERE a.idMateria = :idMateria"),
    @NamedQuery(name = "AssenzeStudenti.findByMateria", query = "SELECT a FROM AssenzeStudenti a WHERE a.materia = :materia"),
    @NamedQuery(name = "AssenzeStudenti.findByIdLezione", query = "SELECT a FROM AssenzeStudenti a WHERE a.idLezione = :idLezione"),
    @NamedQuery(name = "AssenzeStudenti.findByDataLezione", query = "SELECT a FROM AssenzeStudenti a WHERE a.dataLezione = :dataLezione"),
    @NamedQuery(name = "AssenzeStudenti.findByOreLezione", query = "SELECT a FROM AssenzeStudenti a WHERE a.oreLezione = :oreLezione"),
    @NamedQuery(name = "AssenzeStudenti.findByArgomento", query = "SELECT a FROM AssenzeStudenti a WHERE a.argomento = :argomento"),
    @NamedQuery(name = "AssenzeStudenti.findByNumOra", query = "SELECT a FROM AssenzeStudenti a WHERE a.numOra = :numOra"),
    @NamedQuery(name = "AssenzeStudenti.findByAssenza", query = "SELECT a FROM AssenzeStudenti a WHERE a.assenza = :assenza"),
    @NamedQuery(name = "AssenzeStudenti.findByGiustificaAssenza", query = "SELECT a FROM AssenzeStudenti a WHERE a.giustificaAssenza = :giustificaAssenza"),
    @NamedQuery(name = "AssenzeStudenti.findByRitardo", query = "SELECT a FROM AssenzeStudenti a WHERE a.ritardo = :ritardo"),
    @NamedQuery(name = "AssenzeStudenti.findByGiustificaRitardo", query = "SELECT a FROM AssenzeStudenti a WHERE a.giustificaRitardo = :giustificaRitardo")})
public class AssenzeStudenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_studente", nullable = false)
    private long idStudente;
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
    @Column(name = "id_classe", nullable = false)
    private long idClasse;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "anno_scolastico", nullable = false, length = 10)
    private String annoScolastico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nome_classe", nullable = false, length = 10)
    private String nomeClasse;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_materia", nullable = false)
    private long idMateria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "materia", nullable = false, length = 50)
    private String materia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lezione", nullable = false)
    private long idLezione;
    @Column(name = "data_lezione")
    @Temporal(TemporalType.DATE)
    private Date dataLezione;
    @Column(name = "ore_lezione")
    private Integer oreLezione;
    @Size(max = 100)
    @Column(name = "argomento", length = 100)
    private String argomento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_ora", nullable = false)
    private int numOra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "assenza", nullable = false)
    private short assenza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "giustifica_assenza", nullable = false)
    private short giustificaAssenza;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ritardo", nullable = false)
    private short ritardo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "giustifica_ritardo", nullable = false)
    private short giustificaRitardo;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "motivo_giustifica", length = 2147483647)
    private String motivoGiustifica;
    @Id
    private Long id;

    public AssenzeStudenti() {
    }

    public long getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(long idStudente) {
        this.idStudente = idStudente;
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

    public long getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(long idClasse) {
        this.idClasse = idClasse;
    }

    public String getAnnoScolastico() {
        return annoScolastico;
    }

    public void setAnnoScolastico(String annoScolastico) {
        this.annoScolastico = annoScolastico;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(long idLezione) {
        this.idLezione = idLezione;
    }

    public Date getDataLezione() {
        return dataLezione;
    }

    public void setDataLezione(Date dataLezione) {
        this.dataLezione = dataLezione;
    }

    public Integer getOreLezione() {
        return oreLezione;
    }

    public void setOreLezione(Integer oreLezione) {
        this.oreLezione = oreLezione;
    }

    public String getArgomento() {
        return argomento;
    }

    public void setArgomento(String argomento) {
        this.argomento = argomento;
    }

    public int getNumOra() {
        return numOra;
    }

    public void setNumOra(int numOra) {
        this.numOra = numOra;
    }

    public short getAssenza() {
        return assenza;
    }

    public void setAssenza(short assenza) {
        this.assenza = assenza;
    }

    public short getGiustificaAssenza() {
        return giustificaAssenza;
    }

    public void setGiustificaAssenza(short giustificaAssenza) {
        this.giustificaAssenza = giustificaAssenza;
    }

    public short getRitardo() {
        return ritardo;
    }

    public void setRitardo(short ritardo) {
        this.ritardo = ritardo;
    }

    public short getGiustificaRitardo() {
        return giustificaRitardo;
    }

    public void setGiustificaRitardo(short giustificaRitardo) {
        this.giustificaRitardo = giustificaRitardo;
    }

    public String getMotivoGiustifica() {
        return motivoGiustifica;
    }

    public void setMotivoGiustifica(String motivoGiustifica) {
        this.motivoGiustifica = motivoGiustifica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
