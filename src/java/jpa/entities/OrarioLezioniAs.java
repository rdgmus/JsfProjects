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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "orario_lezioni_as", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_scansione", "id_aula"}),
    @UniqueConstraint(columnNames = {"id_materia", "id_scansione"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrarioLezioniAs.findAll", query = "SELECT o FROM OrarioLezioniAs o"),
    @NamedQuery(name = "OrarioLezioniAs.findByIdOrarioLezioni", query = "SELECT o FROM OrarioLezioniAs o WHERE o.idOrarioLezioni = :idOrarioLezioni"),
    @NamedQuery(name = "OrarioLezioniAs.findByOraInizio", query = "SELECT o FROM OrarioLezioniAs o WHERE o.oraInizio = :oraInizio"),
    @NamedQuery(name = "OrarioLezioniAs.findByOraFine", query = "SELECT o FROM OrarioLezioniAs o WHERE o.oraFine = :oraFine")})
public class OrarioLezioniAs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orario_lezioni", nullable = false)
    private Long idOrarioLezioni;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "materia", nullable = false, length = 2147483647)
    private String materia;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "giorno", nullable = false, length = 2147483647)
    private String giorno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ora_inizio", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date oraInizio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ora_fine", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date oraFine;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome_aula", nullable = false, length = 2147483647)
    private String nomeAula;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome_classe", nullable = false, length = 2147483647)
    private String nomeClasse;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @ManyToOne(optional = false)
    private AnniScolastici idAnnoScolastico;
    @JoinColumn(name = "id_aula", referencedColumnName = "id_aula", nullable = false)
    @ManyToOne(optional = false)
    private Aule idAula;
    @JoinColumn(name = "id_classe", referencedColumnName = "id_classe", nullable = false)
    @ManyToOne(optional = false)
    private Classi idClasse;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia", nullable = false)
    @ManyToOne(optional = false)
    private Materie idMateria;
    @JoinColumn(name = "id_scansione", referencedColumnName = "id_scansione", nullable = false)
    @ManyToOne(optional = false)
    private ScansioneOrarioAs idScansione;

    public OrarioLezioniAs() {
    }

    public OrarioLezioniAs(Long idOrarioLezioni) {
        this.idOrarioLezioni = idOrarioLezioni;
    }

    public OrarioLezioniAs(Long idOrarioLezioni, String materia, String giorno, Date oraInizio, Date oraFine, String nomeAula, String nomeClasse) {
        this.idOrarioLezioni = idOrarioLezioni;
        this.materia = materia;
        this.giorno = giorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.nomeAula = nomeAula;
        this.nomeClasse = nomeClasse;
    }

    public Long getIdOrarioLezioni() {
        return idOrarioLezioni;
    }

    public void setIdOrarioLezioni(Long idOrarioLezioni) {
        this.idOrarioLezioni = idOrarioLezioni;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public Date getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Date oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Date getOraFine() {
        return oraFine;
    }

    public void setOraFine(Date oraFine) {
        this.oraFine = oraFine;
    }

    public String getNomeAula() {
        return nomeAula;
    }

    public void setNomeAula(String nomeAula) {
        this.nomeAula = nomeAula;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public AnniScolastici getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(AnniScolastici idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    public Aule getIdAula() {
        return idAula;
    }

    public void setIdAula(Aule idAula) {
        this.idAula = idAula;
    }

    public Classi getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Classi idClasse) {
        this.idClasse = idClasse;
    }

    public Materie getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Materie idMateria) {
        this.idMateria = idMateria;
    }

    public ScansioneOrarioAs getIdScansione() {
        return idScansione;
    }

    public void setIdScansione(ScansioneOrarioAs idScansione) {
        this.idScansione = idScansione;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrarioLezioni != null ? idOrarioLezioni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrarioLezioniAs)) {
            return false;
        }
        OrarioLezioniAs other = (OrarioLezioniAs) object;
        if ((this.idOrarioLezioni == null && other.idOrarioLezioni != null) || (this.idOrarioLezioni != null && !this.idOrarioLezioni.equals(other.idOrarioLezioni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.OrarioLezioniAs[ idOrarioLezioni=" + idOrarioLezioni + " ]";
    }
    
}
