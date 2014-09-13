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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rdgmus
 */
@Entity
@Table(name = "utenti_logger", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UtentiLogger.findAll", query = "SELECT u FROM UtentiLogger u"),
    @NamedQuery(name = "UtentiLogger.findByIdLog", query = "SELECT u FROM UtentiLogger u WHERE u.idLog = :idLog"),
    @NamedQuery(name = "UtentiLogger.findByWhenRegistered", query = "SELECT u FROM UtentiLogger u WHERE u.whenRegistered = :whenRegistered"),
    @NamedQuery(name = "UtentiLogger.findByMsgToUtente", query = "SELECT u FROM UtentiLogger u WHERE u.msgToUtente = :msgToUtente"),
    @NamedQuery(name = "UtentiLogger.findByMsgSentTime", query = "SELECT u FROM UtentiLogger u WHERE u.msgSentTime = :msgSentTime")})
public class UtentiLogger implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_log", nullable = false)
    private Long idLog;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "message", length = 2147483647)
    private String message;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "msg_type", nullable = false, length = 2147483647)
    private String msgType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "when_registered", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date whenRegistered;
    @Basic(optional = false)
    @NotNull
    @Column(name = "msg_to_utente", nullable = false)
    private short msgToUtente;
    @Column(name = "msg_sent_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgSentTime;
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente", nullable = false)
    @ManyToOne(optional = false)
    private UtentiScuola idUtente;

    public UtentiLogger() {
    }

    public UtentiLogger(Long idLog) {
        this.idLog = idLog;
    }

    public UtentiLogger(Long idLog, String msgType, Date whenRegistered, short msgToUtente) {
        this.idLog = idLog;
        this.msgType = msgType;
        this.whenRegistered = whenRegistered;
        this.msgToUtente = msgToUtente;
    }

    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Date getWhenRegistered() {
        return whenRegistered;
    }

    public void setWhenRegistered(Date whenRegistered) {
        this.whenRegistered = whenRegistered;
    }

    public short getMsgToUtente() {
        return msgToUtente;
    }

    public void setMsgToUtente(short msgToUtente) {
        this.msgToUtente = msgToUtente;
    }

    public Date getMsgSentTime() {
        return msgSentTime;
    }

    public void setMsgSentTime(Date msgSentTime) {
        this.msgSentTime = msgSentTime;
    }

    public UtentiScuola getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(UtentiScuola idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UtentiLogger)) {
            return false;
        }
        UtentiLogger other = (UtentiLogger) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.UtentiLogger[ idLog=" + idLog + " ]";
    }
    
}
