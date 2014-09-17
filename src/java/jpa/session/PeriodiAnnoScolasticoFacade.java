/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.AnniScolastici;
import jpa.entities.PeriodiAnnoScolastico;

/**
 *
 * @author rdgmus
 */
@Stateless
public class PeriodiAnnoScolasticoFacade extends AbstractFacade<PeriodiAnnoScolastico> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodiAnnoScolasticoFacade() {
        super(PeriodiAnnoScolastico.class);
    }

    public List<PeriodiAnnoScolastico> findByAnnoScolastico(AnniScolastici selectedAS) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = em.createNamedQuery("PeriodiAnnoScolastico.findByIdAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS.getIdAnnoScolastico());
        return query.getResultList();
    }

    public PeriodiAnnoScolastico findByIdPeriod(long idPeriod) {
        Query query = em.createNamedQuery("PeriodiAnnoScolastico.findByIdPeriodo");
        query.setParameter("idPeriodo", idPeriod);
        return (PeriodiAnnoScolastico) query.getSingleResult();
    }

    public List<PeriodiAnnoScolastico> findByIdAnnoScolastico(long idAS) {
        Query query = em.createNamedQuery("PeriodiAnnoScolastico.findByIdAnnoScolastico");
        query.setParameter("idAnnoScolastico", idAS);
        return query.getResultList();

    }

    public void updatePeriodoAnnoScolastico(PeriodiAnnoScolastico selectedPeriod) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE PeriodiAnnoScolastico p SET "
                + "p.periodo = :periodo,"
                + "p.endDate = :endDate"
                + " WHERE p.periodiAnnoScolasticoPK = :periodiAnnoScolasticoPK");
        query.setParameter("periodiAnnoScolasticoPK", selectedPeriod.getPeriodiAnnoScolasticoPK());
        query.setParameter("periodo", selectedPeriod.getPeriodo());
        query.setParameter("endDate", selectedPeriod.getEndDate());
        query.executeUpdate();
    }
}
