/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.AnniScolastici;
import jpa.entities.LezioniMateria;
import jpa.entities.Materie;

/**
 *
 * @author rdgmus
 */
@Stateless
public class LezioniMateriaFacade extends AbstractFacade<LezioniMateria> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LezioniMateriaFacade() {
        super(LezioniMateria.class);
    }

    public List<LezioniMateria> findByAsMateriaMese(AnniScolastici annoScolasticoSelected, Materie materiaSelected, Date startDate, Date endDate) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = em.createQuery("SELECT l FROM LezioniMateria l "
                + "WHERE l.idAnnoScolastico = :idAnnoScolastico "
                + "AND l.idMateria = :idMateria "
                + "AND l.dataLezione >=  :startDataLezione "
                + "AND l.dataLezione <=  :endDataLezione "
                + "ORDER BY l.dataLezione ASC");
        query.setParameter("idAnnoScolastico", annoScolasticoSelected.getIdAnnoScolastico());
        query.setParameter("idMateria", materiaSelected.getIdMateria());
        query.setParameter("startDataLezione", startDate);
        query.setParameter("endDataLezione", endDate);

        return query.getResultList();
    }
}
