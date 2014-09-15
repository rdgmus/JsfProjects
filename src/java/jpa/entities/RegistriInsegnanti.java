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
@Table(name = "registri_insegnanti", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistriInsegnanti.findAll", query = "SELECT r FROM RegistriInsegnanti r"),
    @NamedQuery(name = "RegistriInsegnanti.findByIdMateria", query = "SELECT r FROM RegistriInsegnanti r WHERE r.idMateria = :idMateria"),
    @NamedQuery(name = "RegistriInsegnanti.findByTipoScuolaAcronimo", query = "SELECT r FROM RegistriInsegnanti r WHERE r.tipoScuolaAcronimo = :tipoScuolaAcronimo"),
    @NamedQuery(name = "RegistriInsegnanti.findByNomeScuola", query = "SELECT r FROM RegistriInsegnanti r WHERE r.nomeScuola = :nomeScuola"),
    @NamedQuery(name = "RegistriInsegnanti.findByAnnoScolastico", query = "SELECT r FROM RegistriInsegnanti r WHERE r.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "RegistriInsegnanti.findByNomeClasse", query = "SELECT r FROM RegistriInsegnanti r WHERE r.nomeClasse = :nomeClasse"),
    @NamedQuery(name = "RegistriInsegnanti.findByCognome", query = "SELECT r FROM RegistriInsegnanti r WHERE r.cognome = :cognome"),
    @NamedQuery(name = "RegistriInsegnanti.findByNome", query = "SELECT r FROM RegistriInsegnanti r WHERE r.nome = :nome"),
    @NamedQuery(name = "RegistriInsegnanti.findByMateria", query = "SELECT r FROM RegistriInsegnanti r WHERE r.materia = :materia"),
    @NamedQuery(name = "RegistriInsegnanti.findByIdInsegnante", query = "SELECT r FROM RegistriInsegnanti r WHERE r.idInsegnante = :idInsegnante"),
    @NamedQuery(name = "RegistriInsegnanti.findByIdAnnoScolastico", query = "SELECT r FROM RegistriInsegnanti r WHERE r.idAnnoScolastico = :idAnnoScolastico"),
    @NamedQuery(name = "RegistriInsegnanti.findByIdScuola", query = "SELECT r FROM RegistriInsegnanti r WHERE r.idScuola = :idScuola"),
    @NamedQuery(name = "RegistriInsegnanti.findByIdClasse", query = "SELECT r FROM RegistriInsegnanti r WHERE r.idClasse = :idClasse")})
public class RegistriInsegnanti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_materia", nullable = false)
    @Id
    private long idMateria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_scuola_acronimo", nullable = false, length = 10)
    private String tipoScuolaAcronimo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_scuola", nullable = false, length = 50)
    private String nomeScuola;
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
    @Size(min = 1, max = 50)
    @Column(name = "materia", nullable = false, length = 50)
    private String materia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_insegnante", nullable = false)
    private long idInsegnante;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anno_scolastico", nullable = false)
    private long idAnnoScolastico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_scuola", nullable = false)
    private long idScuola;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_classe", nullable = false)
    private long idClasse;
//    @Id
//    private Long id;

    public RegistriInsegnanti() {
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }

    public String getTipoScuolaAcronimo() {
        return tipoScuolaAcronimo;
    }

    public void setTipoScuolaAcronimo(String tipoScuolaAcronimo) {
        this.tipoScuolaAcronimo = tipoScuolaAcronimo;
    }

    public String getNomeScuola() {
        return nomeScuola;
    }

    public void setNomeScuola(String nomeScuola) {
        this.nomeScuola = nomeScuola;
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public long getIdInsegnante() {
        return idInsegnante;
    }

    public void setIdInsegnante(long idInsegnante) {
        this.idInsegnante = idInsegnante;
    }

    public long getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(long idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public long getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(long idScuola) {
        this.idScuola = idScuola;
    }

    public long getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(long idClasse) {
        this.idClasse = idClasse;
    }

    
}
