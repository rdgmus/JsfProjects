/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.Lezioni;
import jpa.entities.Materie;

/**
 *
 * @author rdgmus
 */
@Stateless
public class LezioniFacade extends AbstractFacade<Lezioni> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LezioniFacade() {
        super(Lezioni.class);
    }

    public Long getMaxIdLezione() {
        Long maxId = null;
        Query query = getEntityManager().createQuery("SELECT max(l.idLezione) FROM Lezioni l");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }

    /**
     * Update dell'argomento della lezione
     *
     * @param idLezione
     * @param argomentoLezione
     */
    public void cambiaArgomento(Long idLezione, String argomentoLezione) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE Lezioni l SET l.argomento = :argomento"
                + " WHERE l.idLezione = :idLezione");
        query.setParameter("idLezione", idLezione);
        query.setParameter("argomento", argomentoLezione);
        query.executeUpdate();
    }

    /**
     * Calcolo delle ore totali della materia nel periodo indicato dalle due
     * date
     *
     * @param idMateria
     * @param startDate
     * @param endDate
     * @return
     */
    public Integer countOreMateriaPeriodo(Materie idMateria, Date startDate, Date endDate) {
//        throw new UnsupportedOperationException("Not yet implemented");
        int count = 0;
        Query query = getEntityManager().createQuery("SELECT l FROM Lezioni l WHERE l.idMateria = :idMateria"
                + " AND l.dataLezione BETWEEN :startDate AND :endDate");
        query.setParameter("idMateria", idMateria);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List lessons = query.getResultList();
        Iterator iter = lessons.iterator();
        while (iter.hasNext()) {
            Lezioni lez = (Lezioni) iter.next();
            int ore = lez.getOreLezione();
            count += ore;
        }
        return count;
    }

    /**
     * Ritorna le lezioni della materia nel periodo indicato dalle due date
     *
     * @param idMateria
     * @param startDate
     * @param endDate
     * @return
     */
    public List findLezioniPeriodo(Materie idMateria, Date startDate, Date endDate) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT l FROM Lezioni l WHERE l.idMateria = :idMateria"
                + " AND l.dataLezione BETWEEN :startDate AND :endDate"
                + " ORDER BY l.dataLezione ASC");
        query.setParameter("idMateria", idMateria);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    /**
     * Cerca se esiete, la lezione della materia e data indicate.
     *
     * @param idMateria
     * @param dataLezione
     * @return
     */
    public List<Lezioni> findLezione(Long idMateria, Date dataLezione) {
        Query query = getEntityManager().createQuery("SELECT l FROM Lezioni l WHERE l.idMateria = :idMateria"
                + " AND l.dataLezione = :dataLezione");
        query.setParameter("idMateria", idMateria);
        query.setParameter("dataLezione", dataLezione);
        return query.getResultList();
    }

    public List<Lezioni> findLezioniMateriaPrecData(Materie m, Date dataEntrata) {
        Query query = getEntityManager().createQuery("SELECT l FROM Lezioni l WHERE l.idMateria = :idMateria"
                + " AND l.dataLezione < :dataLezione");
        query.setParameter("idMateria", m);
        query.setParameter("dataLezione", dataEntrata);
        return query.getResultList();
    }

    public List<Lezioni> findLezioniMateriaSuccData(Materie m, Date ritiratoData) {
        Query query = getEntityManager().createQuery("SELECT l FROM Lezioni l WHERE l.idMateria = :idMateria"
                + " AND l.dataLezione >= :dataLezione");
        query.setParameter("idMateria", m);
        query.setParameter("dataLezione", ritiratoData);
        return query.getResultList();
    }

    @Override
    public void remove(Lezioni entity) {
//        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        Query query = getEntityManager().createQuery("DELETE FROM Lezioni l "
                + "WHERE l.idLezione = :idLezione");
        query.setParameter("idLezione", entity.getIdLezione());
        query.executeUpdate();
    }

}
