/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Lob;
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
@Table(name = "scansione_orario_as", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScansioneOrarioAs.findAll", query = "SELECT s FROM ScansioneOrarioAs s"),
    @NamedQuery(name = "ScansioneOrarioAs.findByIdScansione", query = "SELECT s FROM ScansioneOrarioAs s WHERE s.idScansione = :idScansione"),
    @NamedQuery(name = "ScansioneOrarioAs.findByNumOraLezione", query = "SELECT s FROM ScansioneOrarioAs s WHERE s.numOraLezione = :numOraLezione"),
    @NamedQuery(name = "ScansioneOrarioAs.findByInizia", query = "SELECT s FROM ScansioneOrarioAs s WHERE s.inizia = :inizia"),
    @NamedQuery(name = "ScansioneOrarioAs.findByFinisce", query = "SELECT s FROM ScansioneOrarioAs s WHERE s.finisce = :finisce"),
    @NamedQuery(name = "ScansioneOrarioAs.findByLezione", query = "SELECT s FROM ScansioneOrarioAs s WHERE s.lezione = :lezione"),
    @NamedQuery(name = "ScansioneOrarioAs.findByIntervallo", query = "SELECT s FROM ScansioneOrarioAs s WHERE s.intervallo = :intervallo")})
public class ScansioneOrarioAs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_scansione", nullable = false)
    private Long idScansione;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "giorno_settimana", nullable = false, length = 2147483647)
    private String giornoSettimana;
    @Column(name = "num_ora_lezione")
    private Integer numOraLezione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inizia", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date inizia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "finisce", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date finisce;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lezione", nullable = false)
    private short lezione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "intervallo", nullable = false)
    private short intervallo;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @ManyToOne(optional = false)
    private AnniScolastici idAnnoScolastico;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idScansione")
    private Collection<OrarioLezioniAs> orarioLezioniAsCollection;

    public ScansioneOrarioAs() {
    }

    public ScansioneOrarioAs(Long idScansione) {
        this.idScansione = idScansione;
    }

    public ScansioneOrarioAs(Long idScansione, String giornoSettimana, Date inizia, Date finisce, short lezione, short intervallo) {
        this.idScansione = idScansione;
        this.giornoSettimana = giornoSettimana;
        this.inizia = inizia;
        this.finisce = finisce;
        this.lezione = lezione;
        this.intervallo = intervallo;
    }

    public Long getIdScansione() {
        return idScansione;
    }

    public void setIdScansione(Long idScansione) {
        this.idScansione = idScansione;
    }

    public String getGiornoSettimana() {
        return giornoSettimana;
    }

    public void setGiornoSettimana(String giornoSettimana) {
        this.giornoSettimana = giornoSettimana;
    }

    public Integer getNumOraLezione() {
        return numOraLezione;
    }

    public void setNumOraLezione(Integer numOraLezione) {
        this.numOraLezione = numOraLezione;
    }

    public Date getInizia() {
        return inizia;
    }

    public void setInizia(Date inizia) {
        this.inizia = inizia;
    }

    public Date getFinisce() {
        return finisce;
    }

    public void setFinisce(Date finisce) {
        this.finisce = finisce;
    }

    public short getLezione() {
        return lezione;
    }

    public void setLezione(short lezione) {
        this.lezione = lezione;
    }

    public short getIntervallo() {
        return intervallo;
    }

    public void setIntervallo(short intervallo) {
        this.intervallo = intervallo;
    }

    public AnniScolastici getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(AnniScolastici idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
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
        hash += (idScansione != null ? idScansione.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScansioneOrarioAs)) {
            return false;
        }
        ScansioneOrarioAs other = (ScansioneOrarioAs) object;
        if ((this.idScansione == null && other.idScansione != null) || (this.idScansione != null && !this.idScansione.equals(other.idScansione))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.ScansioneOrarioAs[ idScansione=" + idScansione + " ]";
        String stringInizia = null;
        String stringFinisce = null;
        Date i = (Date) inizia;
        Date f = (Date) finisce;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        stringInizia = sdf.format(i);
        stringFinisce = sdf.format(f);

        return numOraLezione == null ? "[X]"
                + " - " + stringInizia
                + " - " + stringFinisce
                + " - Int."
                : "[" + numOraLezione + "a]"
                + " - " + stringInizia
                + " - " + stringFinisce
                + " - Lez.";
    }

}
