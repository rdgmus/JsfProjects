/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "scuole", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Scuole.findAll", query = "SELECT s FROM Scuole s"),
    @NamedQuery(name = "Scuole.findByNomeScuola", query = "SELECT s FROM Scuole s WHERE s.nomeScuola = :nomeScuola"),
    @NamedQuery(name = "Scuole.findByTipoScuolaAcronimo", query = "SELECT s FROM Scuole s WHERE s.tipoScuolaAcronimo = :tipoScuolaAcronimo"),
    @NamedQuery(name = "Scuole.findByIdScuola", query = "SELECT s FROM Scuole s WHERE s.idScuola = :idScuola"),
    @NamedQuery(name = "Scuole.findByIndirizzo", query = "SELECT s FROM Scuole s WHERE s.indirizzo = :indirizzo"),
    @NamedQuery(name = "Scuole.findByCap", query = "SELECT s FROM Scuole s WHERE s.cap = :cap"),
    @NamedQuery(name = "Scuole.findByCitt\u00e0", query = "SELECT s FROM Scuole s WHERE s.citt\u00e0 = :citt\u00e0"),
    @NamedQuery(name = "Scuole.findByProvincia", query = "SELECT s FROM Scuole s WHERE s.provincia = :provincia"),
    @NamedQuery(name = "Scuole.findByTelefono", query = "SELECT s FROM Scuole s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "Scuole.findByFax", query = "SELECT s FROM Scuole s WHERE s.fax = :fax"),
    @NamedQuery(name = "Scuole.findByEmail", query = "SELECT s FROM Scuole s WHERE s.email = :email"),
    @NamedQuery(name = "Scuole.findByWeb", query = "SELECT s FROM Scuole s WHERE s.web = :web")})
public class Scuole implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_scuola", nullable = false)
    private Long idScuola;
    @Size(max = 50)
    @Column(name = "indirizzo", length = 50)
    private String indirizzo;
    @Size(max = 6)
    @Column(name = "cap", length = 6)
    private String cap;
    @Size(max = 30)
    @Column(name = "citt\u00e0", length = 30)
    private String città;
    @Size(max = 15)
    @Column(name = "provincia", length = 15)
    private String provincia;
    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "fax", length = 20)
    private String fax;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "email", length = 30)
    private String email;
    @Size(max = 30)
    @Column(name = "web", length = 30)
    private String web;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idScuola")
    private Collection<Aule> auleCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idScuola")
    private Collection<AnniScolastici> anniScolasticiCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scuole")
    private Collection<PeriodiAnnoScolastico> periodiAnnoScolasticoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idScuola")
    private Collection<Classi> classiCollection;

    public Scuole() {
    }

    public Scuole(Long idScuola) {
        this.idScuola = idScuola;
    }

    public Scuole(Long idScuola, String nomeScuola, String tipoScuolaAcronimo) {
        this.idScuola = idScuola;
        this.nomeScuola = nomeScuola;
        this.tipoScuolaAcronimo = tipoScuolaAcronimo;
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

    public Long getIdScuola() {
        return idScuola;
    }

    public void setIdScuola(Long idScuola) {
        this.idScuola = idScuola;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @XmlTransient
    public Collection<Aule> getAuleCollection() {
        return auleCollection;
    }

    public void setAuleCollection(Collection<Aule> auleCollection) {
        this.auleCollection = auleCollection;
    }

    @XmlTransient
    public Collection<AnniScolastici> getAnniScolasticiCollection() {
        return anniScolasticiCollection;
    }

    public void setAnniScolasticiCollection(Collection<AnniScolastici> anniScolasticiCollection) {
        this.anniScolasticiCollection = anniScolasticiCollection;
    }

    @XmlTransient
    public Collection<PeriodiAnnoScolastico> getPeriodiAnnoScolasticoCollection() {
        return periodiAnnoScolasticoCollection;
    }

    public void setPeriodiAnnoScolasticoCollection(Collection<PeriodiAnnoScolastico> periodiAnnoScolasticoCollection) {
        this.periodiAnnoScolasticoCollection = periodiAnnoScolasticoCollection;
    }

    @XmlTransient
    public Collection<Classi> getClassiCollection() {
        return classiCollection;
    }

    public void setClassiCollection(Collection<Classi> classiCollection) {
        this.classiCollection = classiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScuola != null ? idScuola.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Scuole)) {
            return false;
        }
        Scuole other = (Scuole) object;
        if ((this.idScuola == null && other.idScuola != null) || (this.idScuola != null && !this.idScuola.equals(other.idScuola))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.Scuole[ idScuola=" + idScuola + " ]";
        return tipoScuolaAcronimo + " " + nomeScuola;
    }
    
}
