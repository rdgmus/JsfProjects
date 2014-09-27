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
import jpa.entities.Studenti;

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

    /**
     * Ritorna le assenze dello studente nella lezione che appare come parametro.
     * Una lezione può durare una o più ore.
     * @param idLezione
     * @param idStudente
     * @return
     */
    public List<OreAssenze> findAssenzeStudenteLezione(long idLezione, long idStudente) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT o FROM OreAssenze o "
                + "WHERE o.oreAssenzePK.idLezione = :idLezione "
                + "AND o.oreAssenzePK.idStudente = :idStudente "
                + "AND o.assenza = 1 "
                + "ORDER BY o.oreAssenzePK.numOra ASC");
        query.setParameter("idLezione", idLezione);
        query.setParameter("idStudente", idStudente);
        return query.getResultList();
    }

    /**
     * Ritorna tutte le assenze in tabell riguardanti la lezione il cui
     * id viene fornito come parametro
     * @param idLezione
     * @return
     */
    public List<OreAssenze> findAllAssenzeLezione(Long idLezione) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idLezione = :idLezione ");
        query.setParameter("idLezione", idLezione);
        return query.getResultList();
    }

    /**
     * Controlla che esista il record come quello fornito dal parametro entity
     * @param entity
     * @return
     */
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

    /**
     * Update del ritardo come quello fornito dal parametro entity
     * @param entity
     */
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

    /**
     * Controlla che il record di assenza esista già con assenza differente da
     * quella dell'entity fornita come parametro
     *
     * @param entity
     * @return
     */
    public boolean entityExistsWithDifferentAssenza(OreAssenze entity) {
        Query query = getEntityManager().createQuery(
                "SELECT o FROM OreAssenze o WHERE o.oreAssenzePK.idLezione = :idLezione "
                + " AND o.oreAssenzePK.numOra = :numOra"
                + " AND o.oreAssenzePK.idStudente = :idStudente"
                + " AND o.assenza = :assenza");
        query.setParameter("idLezione", entity.getOreAssenzePK().getIdLezione());
        query.setParameter("numOra", entity.getOreAssenzePK().getNumOra());
        query.setParameter("idStudente", entity.getOreAssenzePK().getIdStudente());
        query.setParameter("assenza", entity.getAssenza() == (short)0 ? (short)1 : (short)0);

        List<OreAssenze> oreAssenza = query.getResultList();
        return oreAssenza.size() == 1;
    }

    /**
     * Setta l'assenza dell'allievo nell'ora di lezione se il record gia' esiste
     *
     * @param entity
     * @param value
     */
    public void updateAssenza(OreAssenze entity, short value) {
        Query query = getEntityManager().createQuery("UPDATE OreAssenze o"
                + " SET o.assenza = :assenza"
                + " WHERE o.oreAssenzePK.idLezione = :idLezione "
                + " AND o.oreAssenzePK.numOra = :numOra"
                + " AND o.oreAssenzePK.idStudente = :idStudente");
        query.setParameter("idLezione", entity.getOreAssenzePK().getIdLezione());
        query.setParameter("numOra", entity.getOreAssenzePK().getNumOra());
        query.setParameter("idStudente", entity.getOreAssenzePK().getIdStudente());
        query.setParameter("assenza", value);
        query.executeUpdate();
    }

    @Override
    public void remove(OreAssenze entity) {
//        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        Query query = getEntityManager().createQuery("DELETE FROM OreAssenze o"
                + " WHERE o.oreAssenzePK.idLezione = :idLezione "
                + " AND o.oreAssenzePK.numOra = :numOra"
                + " AND o.oreAssenzePK.idStudente = :idStudente");
        query.setParameter("idLezione", entity.getOreAssenzePK().getIdLezione());
        query.setParameter("numOra", entity.getOreAssenzePK().getNumOra());
        query.setParameter("idStudente", entity.getOreAssenzePK().getIdStudente());
        query.executeUpdate();
    }

    public void removeAssenzeStudente(Studenti entity) {
        Query query = getEntityManager().createQuery("DELETE FROM OreAssenze o"
                + " WHERE o.oreAssenzePK.idStudente = :idStudente");
        query.setParameter("idStudente", entity.getIdStudente());
        query.executeUpdate();
    }
    
    
}
