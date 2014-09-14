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
import jpa.entities.AllieviRisultatiPeriodo;

/**
 *
 * @author rdgmus
 */
@Stateless
public class AllieviRisultatiPeriodoFacade extends AbstractFacade<AllieviRisultatiPeriodo> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AllieviRisultatiPeriodoFacade() {
        super(AllieviRisultatiPeriodo.class);
    }

    /**
     * Risultati in tabellone per un singolo studente, materia e periodo.
     *
     * @param idPeriodo
     * @param idMateria
     * @param idStudente
     * @return
     */
    public List<AllieviRisultatiPeriodo> getRisultatiFor(Long idPeriodo, Long idMateria, Long idStudente) {
        Query query = getEntityManager().createQuery("SELECT a FROM AllieviRisultatiPeriodo a "
                + " WHERE a.allieviRisultatiPeriodoPK.idPeriodo = :idPeriodo"
                + " AND a.allieviRisultatiPeriodoPK.idMateria = :idMateria"
                + " AND a.allieviRisultatiPeriodoPK.idStudente = :idStudente");

        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idMateria", idMateria);
        query.setParameter("idStudente", idStudente);
        return query.getResultList();
    }

    /**
     * Risultati in tabellone per tutti gli studenti della materia nel periodo.
     *
     * @param idPeriodo
     * @param idMateria
     * @return
     */
    public List<AllieviRisultatiPeriodo> getRisultatiMateriaPeriodo(Long idPeriodo, Long idMateria) {
        Query query = getEntityManager().createQuery("SELECT a FROM AllieviRisultatiPeriodo a "
                + " WHERE a.allieviRisultatiPeriodoPK.idPeriodo = :idPeriodo"
                + " AND a.allieviRisultatiPeriodoPK.idMateria = :idMateria");

        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idMateria", idMateria);

        return query.getResultList();
    }

    public Double findCondottaStudentePeriodo(Long idPeriodo, Long idStudente) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT a FROM AllieviRisultatiPeriodo a "
                + " WHERE a.allieviRisultatiPeriodoPK.idPeriodo = :idPeriodo"
                + " AND a.allieviRisultatiPeriodoPK.idStudente = :idStudente");

        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idStudente", idStudente);
        List<AllieviRisultatiPeriodo> listCondotta = query.getResultList();
        if (listCondotta.size() > 0) {
            String condotta = listCondotta.get(0).getCondotta();
            if (condotta.equals("n.c.")) {
                return 0.0;
            } else {
                return Double.valueOf(condotta);
            }
        } else {
            return 0.0;
        }
    }

    public void updateCondotta(Long idPeriodo, Long idStudente, String condotta) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE AllieviRisultatiPeriodo a "
                + " SET a.condotta = :condotta"
                + " WHERE a.allieviRisultatiPeriodoPK.idPeriodo = :idPeriodo"
                + " AND a.allieviRisultatiPeriodoPK.idStudente = :idStudente");

        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idStudente", idStudente);
        query.setParameter("condotta", condotta);
        query.executeUpdate();

    }

    public AllieviRisultatiPeriodo findRisultati(Long idPeriodo, Long idMateria, Long idStudente) {

        Query query = getEntityManager().createQuery("SELECT a FROM AllieviRisultatiPeriodo a "
                + " WHERE a.idPeriodo = :idPeriodo"
                + " AND a.idMateria = :idMateria"
                + " AND a.idStudente = :idStudente");

        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idMateria", idMateria);
        query.setParameter("idStudente", idStudente);

        return (AllieviRisultatiPeriodo) query.getSingleResult();
    }
}
