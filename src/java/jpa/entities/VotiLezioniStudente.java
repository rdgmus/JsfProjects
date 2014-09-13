/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "voti_lezioni_studente", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotiLezioniStudente.findAll", query = "SELECT v FROM VotiLezioniStudente v"),
    @NamedQuery(name = "VotiLezioniStudente.findByIdLezione", query = "SELECT v FROM VotiLezioniStudente v WHERE v.votiLezioniStudentePK.idLezione = :idLezione"),
    @NamedQuery(name = "VotiLezioniStudente.findByIdVoto", query = "SELECT v FROM VotiLezioniStudente v WHERE v.votiLezioniStudentePK.idVoto = :idVoto"),
    @NamedQuery(name = "VotiLezioniStudente.findByIdStudente", query = "SELECT v FROM VotiLezioniStudente v WHERE v.votiLezioniStudentePK.idStudente = :idStudente"),
    @NamedQuery(name = "VotiLezioniStudente.findByVotoString", query = "SELECT v FROM VotiLezioniStudente v WHERE v.votoString = :votoString"),
    @NamedQuery(name = "VotiLezioniStudente.findByVotoNum", query = "SELECT v FROM VotiLezioniStudente v WHERE v.votoNum = :votoNum"),
    @NamedQuery(name = "VotiLezioniStudente.findByGiudizio", query = "SELECT v FROM VotiLezioniStudente v WHERE v.giudizio = :giudizio"),
    @NamedQuery(name = "VotiLezioniStudente.findByTipoVoto", query = "SELECT v FROM VotiLezioniStudente v WHERE v.tipoVoto = :tipoVoto")})
public class VotiLezioniStudente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotiLezioniStudentePK votiLezioniStudentePK;
    @Size(max = 15)
    @Column(name = "voto_string", length = 15)
    private String votoString;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "voto_num", precision = 22)
    private Double votoNum;
    @Column(name = "giudizio")
    private Short giudizio;
    @Column(name = "tipo_voto")
    private Character tipoVoto;
    @JoinColumn(name = "id_lezione", referencedColumnName = "id_lezione", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lezioni lezioni;
    @JoinColumn(name = "id_studente", referencedColumnName = "id_studente", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Studenti studenti;

    public VotiLezioniStudente() {
    }

    public VotiLezioniStudente(VotiLezioniStudentePK votiLezioniStudentePK) {
        this.votiLezioniStudentePK = votiLezioniStudentePK;
    }

    public VotiLezioniStudente(long idLezione, long idVoto, long idStudente) {
        this.votiLezioniStudentePK = new VotiLezioniStudentePK(idLezione, idVoto, idStudente);
    }

    public VotiLezioniStudentePK getVotiLezioniStudentePK() {
        return votiLezioniStudentePK;
    }

    public void setVotiLezioniStudentePK(VotiLezioniStudentePK votiLezioniStudentePK) {
        this.votiLezioniStudentePK = votiLezioniStudentePK;
    }

    public String getVotoString() {
        return votoString;
    }

    public void setVotoString(String votoString) {
        this.votoString = votoString;
    }

    public Double getVotoNum() {
        return votoNum;
    }

    public void setVotoNum(Double votoNum) {
        this.votoNum = votoNum;
    }

    public Short getGiudizio() {
        return giudizio;
    }

    public void setGiudizio(Short giudizio) {
        this.giudizio = giudizio;
    }

    public Character getTipoVoto() {
        return tipoVoto;
    }

    public void setTipoVoto(Character tipoVoto) {
        this.tipoVoto = tipoVoto;
    }

    public Lezioni getLezioni() {
        return lezioni;
    }

    public void setLezioni(Lezioni lezioni) {
        this.lezioni = lezioni;
    }

    public Studenti getStudenti() {
        return studenti;
    }

    public void setStudenti(Studenti studenti) {
        this.studenti = studenti;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votiLezioniStudentePK != null ? votiLezioniStudentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotiLezioniStudente)) {
            return false;
        }
        VotiLezioniStudente other = (VotiLezioniStudente) object;
        if ((this.votiLezioniStudentePK == null && other.votiLezioniStudentePK != null) || (this.votiLezioniStudentePK != null && !this.votiLezioniStudentePK.equals(other.votiLezioniStudentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.VotiLezioniStudente[ votiLezioniStudentePK=" + votiLezioniStudentePK + " ]";
    }
    
}
