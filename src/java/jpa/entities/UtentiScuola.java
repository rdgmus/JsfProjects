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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "utenti_scuola", catalog = "scuola", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "password"}),
    @UniqueConstraint(columnNames = {"cognome", "nome"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UtentiScuola.findAll", query = "SELECT u FROM UtentiScuola u"),
    @NamedQuery(name = "UtentiScuola.findByEmail", query = "SELECT u FROM UtentiScuola u WHERE u.email = :email"),
    @NamedQuery(name = "UtentiScuola.findByPassword", query = "SELECT u FROM UtentiScuola u WHERE u.password = :password"),
    @NamedQuery(name = "UtentiScuola.findByIdUtente", query = "SELECT u FROM UtentiScuola u WHERE u.idUtente = :idUtente")})
public class UtentiScuola implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_utente", nullable = false)
    private Long idUtente;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "cognome", length = 2147483647)
    private String cognome;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "nome", length = 2147483647)
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Lob
    @Size(max = 2147483647)
    @Column(name = "email", length = 2147483647)
    private String email;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "password", length = 2147483647)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtente")
    private Collection<RegistriGranted> registriGrantedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtente")
    private Collection<UtentiLogger> utentiLoggerCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtente")
    private Collection<RuoliGrantedToUtenti> ruoliGrantedToUtentiCollection;

    public UtentiScuola() {
    }

    public UtentiScuola(Long idUtente) {
        this.idUtente = idUtente;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<RegistriGranted> getRegistriGrantedCollection() {
        return registriGrantedCollection;
    }

    public void setRegistriGrantedCollection(Collection<RegistriGranted> registriGrantedCollection) {
        this.registriGrantedCollection = registriGrantedCollection;
    }

    @XmlTransient
    public Collection<UtentiLogger> getUtentiLoggerCollection() {
        return utentiLoggerCollection;
    }

    public void setUtentiLoggerCollection(Collection<UtentiLogger> utentiLoggerCollection) {
        this.utentiLoggerCollection = utentiLoggerCollection;
    }

    @XmlTransient
    public Collection<RuoliGrantedToUtenti> getRuoliGrantedToUtentiCollection() {
        return ruoliGrantedToUtentiCollection;
    }

    public void setRuoliGrantedToUtentiCollection(Collection<RuoliGrantedToUtenti> ruoliGrantedToUtentiCollection) {
        this.ruoliGrantedToUtentiCollection = ruoliGrantedToUtentiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtente != null ? idUtente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UtentiScuola)) {
            return false;
        }
        UtentiScuola other = (UtentiScuola) object;
        if ((this.idUtente == null && other.idUtente != null) || (this.idUtente != null && !this.idUtente.equals(other.idUtente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "jpa.entities.UtentiScuola[ idUtente=" + idUtente + " ]";
        return cognome+" "+nome+" ["+email+"]";
    }
    
}
