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
import jpa.entities.Classi;

/**
 *
 * @author rdgmus
 */
@Stateless
public class ClassiFacade extends AbstractFacade<Classi> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClassiFacade() {
        super(Classi.class);
    }

    public List<Classi> retrieveClassiAnnoScolasticoOrderedList(AnniScolastici selectedAS) {
        Query query = em.createQuery("SELECT c FROM Classi c WHERE c.idAnnoScolastico = :idAnnoScolastico "
                + "ORDER BY c.annoScolastico ASC");
        query.setParameter("idAnnoScolastico", selectedAS);
        return query.getResultList();
    }

   

    public void updateDenominazioneClasse(Classi selectedClasse) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE Classi c "
                + "SET c.nomeClasse = :nomeClasse,"
                + "c.specializzazione = :specializzazione"
                + " WHERE c.idClasse = :idClasse");
        query.setParameter("idClasse", selectedClasse.getIdClasse());
        query.setParameter("nomeClasse", selectedClasse.getNomeClasse().toUpperCase());
        query.setParameter("specializzazione", selectedClasse.getSpecializzazione().toUpperCase());
        query.executeUpdate();
    }
}
