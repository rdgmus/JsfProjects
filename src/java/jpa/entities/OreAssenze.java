/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ore_assenze", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OreAssenze.findAll", query = "SELECT o FROM OreAssenze o"),
    @NamedQuery(name = "OreAssenze.findByIdLezione", query = "SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idLezione = :idLezione"),
    @NamedQuery(name = "OreAssenze.findByNumOra", query = "SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.numOra = :numOra"),
    @NamedQuery(name = "OreAssenze.findByIdStudente", query = "SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idStudente = :idStudente"),
    @NamedQuery(name = "OreAssenze.findByAssenza", query = "SELECT o FROM OreAssenze o WHERE o.assenza = :assenza"),
    @NamedQuery(name = "OreAssenze.findByGiustificaAssenza", query = "SELECT o FROM OreAssenze o WHERE o.giustificaAssenza = :giustificaAssenza"),
    @NamedQuery(name = "OreAssenze.findByRitardo", query = "SELECT o FROM OreAssenze o WHERE o.ritardo = :ritardo"),
    @NamedQuery(name = "OreAssenze.findByGiustificaRitardo", query = "SELECT o FROM OreAssenze o WHERE o.giustificaRitardo = :giustificaRitardo")})
public class OreAssenze implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OreAssenzePK oreAssenzePK;
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
    @JoinColumn(name = "id_lezione", referencedColumnName = "id_lezione", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lezioni lezioni;
    @JoinColumn(name = "id_studente", referencedColumnName = "id_studente", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Studenti studenti;

    public OreAssenze() {
    }

    public OreAssenze(OreAssenzePK oreAssenzePK) {
        this.oreAssenzePK = oreAssenzePK;
    }

    public OreAssenze(OreAssenzePK oreAssenzePK, short assenza, short giustificaAssenza, short ritardo, short giustificaRitardo) {
        this.oreAssenzePK = oreAssenzePK;
        this.assenza = assenza;
        this.giustificaAssenza = giustificaAssenza;
        this.ritardo = ritardo;
        this.giustificaRitardo = giustificaRitardo;
    }

    public OreAssenze(long idLezione, int numOra, long idStudente) {
        this.oreAssenzePK = new OreAssenzePK(idLezione, numOra, idStudente);
    }

    public OreAssenzePK getOreAssenzePK() {
        return oreAssenzePK;
    }

    public void setOreAssenzePK(OreAssenzePK oreAssenzePK) {
        this.oreAssenzePK = oreAssenzePK;
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
        hash += (oreAssenzePK != null ? oreAssenzePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OreAssenze)) {
            return false;
        }
        OreAssenze other = (OreAssenze) object;
        if ((this.oreAssenzePK == null && other.oreAssenzePK != null) || (this.oreAssenzePK != null && !this.oreAssenzePK.equals(other.oreAssenzePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.OreAssenze[ oreAssenzePK=" + oreAssenzePK + " ]";
    }
    
}
