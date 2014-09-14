/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.AnniScolastici;
import jpa.entities.Scuole;

/**
 *
 * @author rdgmus
 */
@Stateless
public class AnniScolasticiFacade extends AbstractFacade<AnniScolastici> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnniScolasticiFacade() {
        super(AnniScolastici.class);
    }

    public List<AnniScolastici> retrieveAnniScolasticiScuolaOrderedList(Scuole selectedScuola) {
        Query query = em.createQuery("SELECT a FROM AnniScolastici a WHERE a.idScuola = :idScuola "
                + "ORDER BY a.annoScolastico DESC");
        query.setParameter("idScuola", selectedScuola);
        return query.getResultList();
    }

    public void updateAnnoScolastico(AnniScolastici selectedAS) {
//        throw new UnsupportedOperationException("Not yet implemented");

        Query query = getEntityManager().createQuery("UPDATE AnniScolastici a SET "
                + "a.annoScolastico = :annoScolastico,"
                + "a.startDate = :startDate,"
                + "a.endDate = :endDate"
                + " WHERE a.idAnnoScolastico = :idAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS.getIdAnnoScolastico());
        query.setParameter("annoScolastico", getAnnoAscolasticoAsString(selectedAS));
        query.setParameter("startDate", selectedAS.getStartDate());
        query.setParameter("endDate", selectedAS.getEndDate());

        query.executeUpdate();
    }

    /**
     * Costruisce la stringa annoScolastico (tipo 2011/2012) a partire dalle
     * date di inizio e fine anno del parametro
     *
     * @param selectedAS
     * @return
     */
    public String getAnnoAscolasticoAsString(AnniScolastici selectedAS) {
        //Costruisce la stringa 2011/2012
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(selectedAS.getStartDate());

        int startYear = startCal.get(Calendar.YEAR);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(selectedAS.getEndDate());

        int endYear = endCal.get(Calendar.YEAR);

        String annoScolasticoStr = startYear + "/" + endYear;
        return annoScolasticoStr;
    }

    public Long getNextId() {
        Long maxId = null;
        Query query = getEntityManager().createNativeQuery("SELECT nextval('scuola.anni_scolastici_seq')");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }
}
