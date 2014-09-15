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
import jpa.entities.Insegnanti;
import jpa.entities.RegistriInsegnanti;

/**
 *
 * @author rdgmus
 */
@Stateless
public class RegistriInsegnantiFacade extends AbstractFacade<RegistriInsegnanti> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegistriInsegnantiFacade() {
        super(RegistriInsegnanti.class);
    }

    public List<RegistriInsegnanti> findByInsegnanteAnnoScolastico(AnniScolastici as, Insegnanti insegnante) {
        if (insegnante == null) {
            return null;
        }
        Query query = em.createQuery("SELECT r FROM RegistriInsegnanti r "
                + "WHERE r.idAnnoScolastico = :idAnnoScolastico "
                + "AND r.cognome = :cognome "
                + "AND r.nome = :nome "
                + "ORDER BY r.nomeClasse ASC");
        query.setParameter("idAnnoScolastico", as.getIdAnnoScolastico());
        query.setParameter("cognome", insegnante.getCognome());
        query.setParameter("nome", insegnante.getNome());

        return query.getResultList();
    }

}
