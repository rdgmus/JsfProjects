/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "lezioni_materia", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LezioniMateria.findAll", query = "SELECT l FROM LezioniMateria l"),
    @NamedQuery(name = "LezioniMateria.findByDataLezione", query = "SELECT l FROM LezioniMateria l WHERE l.dataLezione = :dataLezione"),
    @NamedQuery(name = "LezioniMateria.findByOreLezione", query = "SELECT l FROM LezioniMateria l WHERE l.oreLezione = :oreLezione"),
    @NamedQuery(name = "LezioniMateria.findByArgomento", query = "SELECT l FROM LezioniMateria l WHERE l.argomento = :argomento"),
    @NamedQuery(name = "LezioniMateria.findByFreezeLezione", query = "SELECT l FROM LezioniMateria l WHERE l.freezeLezione = :freezeLezione"),
    @NamedQuery(name = "LezioniMateria.findByIdMateria", query = "SELECT l FROM LezioniMateria l WHERE l.idMateria = :idMateria"),
    @NamedQuery(name = "LezioniMateria.findByIdLezione", query = "SELECT l FROM LezioniMateria l WHERE l.idLezione = :idLezione"),
    @NamedQuery(name = "LezioniMateria.findByIdAnnoScolastico", query = "SELECT l FROM LezioniMateria l WHERE l.idAnnoScolastico = :idAnnoScolastico"),
    @NamedQuery(name = "LezioniMateria.findByMateria", query = "SELECT l FROM LezioniMateria l WHERE l.materia = :materia"),
    @NamedQuery(name = "LezioniMateria.findByAnnoScolastico", query = "SELECT l FROM LezioniMateria l WHERE l.annoScolastico = :annoScolastico"),
    @NamedQuery(name = "LezioniMateria.findByStartDate", query = "SELECT l FROM LezioniMateria l WHERE l.startDate = :startDate"),
    @NamedQuery(name = "LezioniMateria.findByEndDate", query = "SELECT l FROM LezioniMateria l WHERE l.endDate = :endDate"),
    @NamedQuery(name = "LezioniMateria.findByIdInsegnante", query = "SELECT l FROM LezioniMateria l WHERE l.idInsegnante = :idInsegnante"),
    @NamedQuery(name = "LezioniMateria.findByGiudizio", query = "SELECT l FROM LezioniMateria l WHERE l.giudizio = :giudizio"),
    @NamedQuery(name = "LezioniMateria.findByScritto", query = "SELECT l FROM LezioniMateria l WHERE l.scritto = :scritto"),
    @NamedQuery(name = "LezioniMateria.findByOrale", query = "SELECT l FROM LezioniMateria l WHERE l.orale = :orale"),
    @NamedQuery(name = "LezioniMateria.findByPratico", query = "SELECT l FROM LezioniMateria l WHERE l.pratico = :pratico"),
    @NamedQuery(name = "LezioniMateria.findByNomeClasse", query = "SELECT l FROM LezioniMateria l WHERE l.nomeClasse = :nomeClasse"),
    @NamedQuery(name = "LezioniMateria.findByIdClasse", query = "SELECT l FROM LezioniMateria l WHERE l.idClasse = :idClasse"),
    @NamedQuery(name = "LezioniMateria.findByIdScuola", query = "SELECT l FROM LezioniMateria l WHERE l.idScuola = :idScuola"),
    @NamedQuery(name = "LezioniMateria.findByNomeScuola", query = "SELECT l FROM LezioniMateria l WHERE l.nomeScuola = :nomeScuola"),
    @NamedQuery(name = "LezioniMateria.findByTipoScuolaAcronimo", query = "SELECT l FROM LezioniMateria l WHERE l.tipoScuolaAcronimo = :tipoScuolaAcronimo")})
public class LezioniMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "data_lezione")
    @Temporal(TemporalType.DATE)
    private Date dataLezione;
    @Column(name = "ore_lezione")
    private Integer oreLezione;
    @Size(max = 100)
    @Column(name = "argomento", length = 100)
    private String argomento;
    @Column(name = "freeze_lezione")
    private Short freezeLezione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_materia", nullable = false)
    private long idMateria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lezione", nullable = false)
    @Id
    private long idLezione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_anno_scolastico", nullable = false)
    private long idAnnoScolastico;
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
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_insegnante", nullable = false)
    private long idInsegnante;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nome_classe", nullable = false, length = 10)
    private String nomeClasse;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_classe", nullable = false)
    private long idClasse;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_scuola", nullable = false)
    private long idScuola;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nome_scuola", nullable = false, length = 50)
    private String nomeScuola;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipo_scuola_acronimo", nullable = false, length = 10)
    private String tipoScuolaAcronimo;

    public LezioniMateria() {
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

    public Short getFreezeLezione() {
        return freezeLezione;
    }

    public void setFreezeLezione(Short freezeLezione) {
        this.freezeLezione = freezeLezione;
    }

    public long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(long idMateria) {
        this.idMateria = idMateria;
    }

    public long getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(long idLezione) {
        this.idLezione = idLezione;
    }

    public long getIdAnnoScolastico() {
        return idAnnoScolastico;
    }

    public void setIdAnnoScolastico(long idAnnoScolastico) {
        this.idAnnoScolastico = idAnnoScolastico;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getIdInsegnante() {
        return idInsegnante;
    }

    public void setIdInsegnante(long idInsegnante) {
        this.idInsegnante = idInsegnante;
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

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public long getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(long idClasse) {
        this.idClasse = idClasse;
    }

    public long getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(long idScuola) {
        this.idScuola = idScuola;
    }

    public String getNomeScuola() {
        return nomeScuola;
    }

    public void setNomeScuola(String nomeScuola) {
        this.nomeScuola = nomeScuola;
    }

    public String getTipoScuolaAcronimo() {
        return tipoScuolaAcronimo;
    }

    public void setTipoScuolaAcronimo(String tipoScuolaAcronimo) {
        this.tipoScuolaAcronimo = tipoScuolaAcronimo;
    }

    @Override
    public String toString() {
//        return super.toString(); //To change body of generated methods, choose Tools | Templates.
        Date d = (Date) this.getDataLezione();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM yyyy");

        return sdf.format(d);
    }

}
