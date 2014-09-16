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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "parametri_orario_as", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_anno_scolastico"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametriOrarioAs.findByIdAnnoScolastico", query = "SELECT p FROM ParametriOrarioAs p WHERE p.idAnnoScolastico = :idAnnoScolastico"),
    @NamedQuery(name = "ParametriOrarioAs.findAll", query = "SELECT p FROM ParametriOrarioAs p"),
    @NamedQuery(name = "ParametriOrarioAs.findByIdParamOrario", query = "SELECT p FROM ParametriOrarioAs p WHERE p.idParamOrario = :idParamOrario"),
    @NamedQuery(name = "ParametriOrarioAs.findByInizioLezioni", query = "SELECT p FROM ParametriOrarioAs p WHERE p.inizioLezioni = :inizioLezioni"),
    @NamedQuery(name = "ParametriOrarioAs.findByDurataOraMinuti", query = "SELECT p FROM ParametriOrarioAs p WHERE p.durataOraMinuti = :durataOraMinuti"),
    @NamedQuery(name = "ParametriOrarioAs.findByDurataIntervalloMinuti", query = "SELECT p FROM ParametriOrarioAs p WHERE p.durataIntervalloMinuti = :durataIntervalloMinuti")})
public class ParametriOrarioAs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param_orario", nullable = false)
    private Long idParamOrario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inizio_lezioni", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date inizioLezioni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "durata_ora_minuti", nullable = false)
    private int durataOraMinuti;
    @Basic(optional = false)
    @NotNull
    @Column(name = "durata_intervallo_minuti", nullable = false)
    private int durataIntervalloMinuti;
    @JoinColumn(name = "id_anno_scolastico", referencedColumnName = "id_anno_scolastico", nullable = false)
    @OneToOne(optional = false)
    private AnniScolastici idAnnoScolastico;

    public ParametriOrarioAs() {
    }

    public ParametriOrarioAs(Long idParamOrario) {
        this.idParamOrario = idParamOrario;
    }

    public ParametriOrarioAs(Long idParamOrario, Date inizioLezioni, int durataOraMinuti, int durataIntervalloMinuti) {
        this.idParamOrario = idParamOrario;
        this.inizioLezioni = inizioLezioni;
        this.durataOraMinuti = durataOraMinuti;
        this.durataIntervalloMinuti = durataIntervalloMinuti;
    }

    public Long getIdParamOrario() {
        return idParamOrario;
    }

    public void setIdParamOrario(Long idParamOrario) {
        this.idParamOrario = idParamOrario;
    }

    public Date getInizioLezioni() {
        return inizioLezioni;
    }

    public void setInizioLezioni(Date inizioLezioni) {
        this.inizioLezioni = inizioLezioni;
    }

    public int getDurataOraMinuti() {
        return durataOraMinuti;
    }

    public void setDurataOraMinuti(int durataOraMinuti) {
        this.durataOraMinuti = durataOraMinuti;
    }

    public int getDurataIntervalloMinuti() {
        return durataIntervalloMinuti;
    }

    public void setDurataIntervalloMinuti(int durataIntervalloMinuti) {
        this.durataIntervalloMinuti = durataIntervalloMinuti;
    }

    public AnniScolastici getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(AnniScolastici idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParamOrario != null ? idParamOrario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametriOrarioAs)) {
            return false;
        }
        ParametriOrarioAs other = (ParametriOrarioAs) object;
        if ((this.idParamOrario == null && other.idParamOrario != null) || (this.idParamOrario != null && !this.idParamOrario.equals(other.idParamOrario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.ParametriOrarioAs[ idParamOrario=" + idParamOrario + " ]";
    }
    
}
