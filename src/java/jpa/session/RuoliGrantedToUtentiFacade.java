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
import jpa.entities.RuoliGrantedToUtenti;
import jpa.entities.UtentiScuola;

/**
 *
 * @author rdgmus
 */
@Stateless
public class RuoliGrantedToUtentiFacade extends AbstractFacade<RuoliGrantedToUtenti> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RuoliGrantedToUtentiFacade() {
        super(RuoliGrantedToUtenti.class);
    }

    public Long getNextId() {
        Long maxId = null;
        Query query = getEntityManager().createNativeQuery("SELECT nextval('scuola.ruoli_granted_seq')");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }

    public List<RuoliGrantedToUtenti> findByUtente(UtentiScuola selectedUtente) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT r FROM RuoliGrantedToUtenti r "
                + "WHERE r.idUtente = :idUtente ");
        query.setParameter("idUtente", selectedUtente.getIdUtente());

        return query.getResultList();
    }
}
