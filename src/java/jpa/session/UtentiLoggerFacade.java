/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.session;

import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.UtentiLogger;
import jpa.entities.UtentiScuola;

/**
 *
 * @author rdgmus
 */
@Stateless
public class UtentiLoggerFacade extends AbstractFacade<UtentiLogger> {
    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtentiLoggerFacade() {
        super(UtentiLogger.class);
    }

    public Timestamp getCurrentTimeStamp() {
        Timestamp now = null;
        Query query = getEntityManager().createNativeQuery("SELECT current_timestamp");
        now = (Timestamp) query.getSingleResult();
        return now;
    }
    public List<UtentiLogger> findUtenteLogs(UtentiScuola idUtente, Boolean state) {
        Query query = getEntityManager().createQuery("SELECT u FROM UtentiLogger u WHERE u.idUtente = :idUtente"
                + " AND u.msgToUtente = :msgToUtente");
        query.setParameter("idUtente", idUtente);
        query.setParameter("msgToUtente",(short) (state?1:0));
        return query.getResultList();
    }

    public void setUtenteLogsEmailed(UtentiScuola idUtente) {
        List<UtentiLogger> logs = findUtenteLogs(idUtente, false);
        for (UtentiLogger u : logs) {
            Query query = getEntityManager().createQuery("UPDATE UtentiLogger u"
                    + " SET u.msgToUtente = :msgToUtente,"
                    + " u.msgSentTime = :msgSentTime"
                    + " WHERE u.idLog = :idLog");
            query.setParameter("idLog", u.getIdLog());
            query.setParameter("msgToUtente", (short)1);
            query.setParameter("msgSentTime", getCurrentTimeStamp());

            query.executeUpdate();
        }
    }

    public void insertLogMessage(UtentiLogger entity) {
//        Query query = getEntityManager().createNativeQuery("INSERT INTO `scuola`.`utenti_logger`\n"
//                + "(`id_log`,\n"
//                + "`id_utente`,\n"
//                + "`message`,\n"
//                + "`msg_type`,\n"
//                + "`when_registered`,\n"
//                + "`msg_to_utente`,\n"
//                + "`msg_sent_time`)\n"
//                + "VALUES\n"
//                + "(<{id_log: }>,\n"
//                + "<{id_utente: }>,\n"
//                + "<{message: }>,\n"
//                + "<{msg_type: }>,\n"
//                + "<{when_registered: }>,\n"
//                + "<{msg_to_utente: 0}>,\n"
//                + "<{msg_sent_time: }>);");
    }
}
