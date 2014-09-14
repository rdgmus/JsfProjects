/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.AnniScolastici;
import jpa.entities.Classi;
import jpa.entities.Insegnanti;

/**
 *
 * @author rdgmus
 */
@Stateless
public class InsegnantiFacade extends AbstractFacade<Insegnanti> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InsegnantiFacade() {
        super(Insegnanti.class);
    }
    @EJB
    ClassiFacade classiFacade;
    @EJB
    AnniScolasticiFacade anniScolasticiFacade;

    public ClassiFacade getClassiFacade() {
        return classiFacade;
    }

    public AnniScolasticiFacade getAnniScolasticiFacade() {
        return anniScolasticiFacade;
    }

    /**
     * Tutti gli insegnanti della classe dell'anno scolastico in ordine
     * alfabetico
     *
     * @param selectedAS
     * @param selectedClasse
     * @return
     */
    public List<Insegnanti> retrieveInsegnantiClasseOrderedList(AnniScolastici selectedAS, Classi selectedClasse) {
        Query query = em.createQuery("SELECT i FROM Insegnanti i "
                + "WHERE i.idAnnoScolastico = :idAnnoScolastico "
                + "AND i.idClasse = :idClasse "
                + "ORDER BY i.cognome, i.nome ASC");
        query.setParameter("idAnnoScolastico", selectedAS);
        query.setParameter("idClasse", selectedClasse);
        return query.getResultList();

    }

    /**
     * Tutti gli insegnanti del database in ordine alfabetico
     *
     * @return
     */
    public List<Insegnanti> retrieveAllInsegnantiDistinctOrderedList(List<Insegnanti> listaInsegnanti) {
        ArrayList<Insegnanti> resultList = new ArrayList<Insegnanti>();
        List<Object[]> objList = null;
        Query query = getEntityManager().createNativeQuery("SELECT  DISTINCT  "
                + " ON (insegnanti.cognome,insegnanti.nome)"
                + " insegnanti.id_insegnante, "
                + " insegnanti.cognome, "
                + " insegnanti.nome, "
                + " insegnanti.id_classe, "
                + " insegnanti.anno_scolastico, "
                + " insegnanti.id_anno_scolastico"
                + " FROM scuola.insegnanti"
                + " ORDER BY insegnanti.cognome ASC, "
                + " insegnanti.nome ASC");
        objList = query.getResultList();

        for (Object[] obj : objList) {
            Insegnanti altroInsegnante = new Insegnanti();
            altroInsegnante.setIdInsegnante(Long.valueOf(String.valueOf(obj[0])));
            altroInsegnante.setCognome(String.valueOf(obj[1]));
            altroInsegnante.setNome(String.valueOf(obj[2]));

            Classi nuovaClasse = getClassiFacade().find(Long.valueOf(String.valueOf(obj[3])));

            altroInsegnante.setIdClasse(nuovaClasse);

            altroInsegnante.setAnnoScolastico(String.valueOf(obj[4]));

            AnniScolastici nuovoAS = getAnniScolasticiFacade().find(Long.valueOf(String.valueOf(obj[5])));

            altroInsegnante.setIdAnnoScolastico(nuovoAS);
            if (insegnanteNotIn(altroInsegnante, listaInsegnanti)) {
                resultList.add(altroInsegnante);
            }
        }
        return resultList;
    }

    public void updateCognomeNomeInsegnante(Insegnanti selectedInsegnante) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (selectedInsegnante != null) {
            if (selectedInsegnante.getCognome() == null || selectedInsegnante.getCognome().length() == 0
                    || selectedInsegnante.getCognome().equals("[cognome]")) {
                return;
            }
            if (selectedInsegnante.getNome() == null || selectedInsegnante.getNome().length() == 0
                    || selectedInsegnante.getNome().equals("[nome]")) {
                return;
            }
            Query query = getEntityManager().createQuery("UPDATE Insegnanti s"
                    + " SET s.cognome = :cognome,"
                    + " s.nome = :nome"
                    + " WHERE s.idInsegnante = :idInsegnante");
            query.setParameter("cognome", selectedInsegnante.getCognome().toUpperCase());
            query.setParameter("nome", selectedInsegnante.getNome().toUpperCase());
            query.setParameter("idInsegnante", selectedInsegnante.getIdInsegnante());
            query.executeUpdate();
        }

    }

    public Long getMaxIdInsegnante() {
        Long maxId = null;
        Query query = getEntityManager().createQuery("SELECT max(s.idInsegnante) FROM Insegnanti s");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }

    public Long getNextId() {
        Long maxId = null;
        Query query = getEntityManager().createNativeQuery("SELECT nextval('scuola.insegnanti_seq')");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }

    private boolean insegnanteNotIn(Insegnanti altroInsegnante, List<Insegnanti> listaInsegnanti) {
//        throw new UnsupportedOperationException("Not yet implemented");
        for (Insegnanti i : listaInsegnanti) {
            if (i.getCognome().equals(altroInsegnante.getCognome())
                    && i.getNome().equals(altroInsegnante.getNome())) {
                return false;
            }
        }
        return true;
    }
}
