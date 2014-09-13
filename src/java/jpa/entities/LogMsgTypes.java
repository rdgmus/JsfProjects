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
import javax.persistence.Lob;
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
@Table(name = "log_msg_types", catalog = "scuola", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogMsgTypes.findAll", query = "SELECT l FROM LogMsgTypes l"),
    @NamedQuery(name = "LogMsgTypes.findByIdMsgType", query = "SELECT l FROM LogMsgTypes l WHERE l.idMsgType = :idMsgType")})
public class LogMsgTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_msg_type", nullable = false)
    private Long idMsgType;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "msg_type", nullable = false, length = 2147483647)
    private String msgType;

    public LogMsgTypes() {
    }

    public LogMsgTypes(Long idMsgType) {
        this.idMsgType = idMsgType;
    }

    public LogMsgTypes(Long idMsgType, String msgType) {
        this.idMsgType = idMsgType;
        this.msgType = msgType;
    }

    public Long getIdMsgType() {
        return idMsgType;
    }

    public void setIdMsgType(Long idMsgType) {
        this.idMsgType = idMsgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMsgType != null ? idMsgType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogMsgTypes)) {
            return false;
        }
        LogMsgTypes other = (LogMsgTypes) object;
        if ((this.idMsgType == null && other.idMsgType != null) || (this.idMsgType != null && !this.idMsgType.equals(other.idMsgType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.LogMsgTypes[ idMsgType=" + idMsgType + " ]";
    }
    
}
