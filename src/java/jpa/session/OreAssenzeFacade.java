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
import jpa.entities.OreAssenze;

/**
 *
 * @author rdgmus
 */
@Stateless
public class OreAssenzeFacade extends AbstractFacade<OreAssenze> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OreAssenzeFacade() {
        super(OreAssenze.class);
    }

    public List<OreAssenze> findAssenzeStudenteLezione(long idLezione, long idStudente) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idLezione = :idLezione "
                + "AND o.oreAssenzePK.idStudente = :idStudente "
                + "ORDER BY o.oreAssenzePK.numOra ASC");
        query.setParameter("idLezione", idLezione);
        query.setParameter("idStudente", idStudente);
        return query.getResultList();
    }

    public List<OreAssenze> findAllAssenzeLezione(Long idLezione) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idLezione = :idLezione ");
        query.setParameter("idLezione", idLezione);
        return query.getResultList();
    }

    public boolean existsEntity(OreAssenze entity) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery(
                "SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idLezione = :idLezione "
                + " AND o.oreAssenzePK.numOra = :numOra"
                + " AND o.oreAssenzePK.idStudente = :idStudente"
                + " AND o.assenza = :assenza");
        query.setParameter("idLezione", entity.getOreAssenzePK().getIdLezione());
        query.setParameter("numOra", entity.getOreAssenzePK().getNumOra());
        query.setParameter("idStudente", entity.getOreAssenzePK().getIdStudente());
        query.setParameter("assenza", entity.getAssenza());

        List<OreAssenze> oreAssenza = query.getResultList();
        return oreAssenza.size() == 1;
    }

    public void updateRitardo(OreAssenze entity) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE OreAssenze o"
                + " SET o.ritardo = :ritardo"
                + " WHERE o.oreAssenzePK.idLezione = :idLezione "
                + " AND o.oreAssenzePK.numOra = :numOra"
                + " AND o.oreAssenzePK.idStudente = :idStudente");
        query.setParameter("idLezione", entity.getOreAssenzePK().getIdLezione());
        query.setParameter("numOra", entity.getOreAssenzePK().getNumOra());
        query.setParameter("idStudente", entity.getOreAssenzePK().getIdStudente());
        query.setParameter("ritardo", entity.getRitardo());
        query.executeUpdate();

    }
}
