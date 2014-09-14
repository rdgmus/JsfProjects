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
import jpa.entities.VotiLezioniStudente;

/**
 *
 * @author rdgmus
 */
@Stateless
public class VotiLezioniStudenteFacade extends AbstractFacade<VotiLezioniStudente> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VotiLezioniStudenteFacade() {
        super(VotiLezioniStudente.class);
    }

    public List<VotiLezioniStudente> findVotiStudenteLezione(Long idLezione, Long idStudente) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        List<VotiLezioniStudente> voti = null;
        Query query = getEntityManager().createQuery("SELECT v FROM VotiLezioniStudente v "
                + " WHERE v.votiLezioniStudentePK.idLezione = :idLezione"
                + " AND v.votiLezioniStudentePK.idStudente = :idStudente");
        query.setParameter("idLezione", idLezione);
        query.setParameter("idStudente", idStudente);

        voti = query.getResultList();
        return voti;
    }

    /**
     * Il prossimo idVoto tiene conto del fatto che la chiave è [idLezione,
     * idVoto, idStudente] quindi non è necessario incrementare sempre l'idVoto
     * di una unità ma basta controllare quanti voti ha già lo studente per la
     * lezione data e incrementare di una unità tale conto.
     *
     * @return
     */
    public Long getNextIdVoto(Long idLezione, Long idStudente) {
        Query query = getEntityManager().createQuery(
                "SELECT count(v) FROM VotiLezioniStudente v "
                + " WHERE v.votiLezioniStudentePK.idLezione = :idLezione"
                + " AND v.votiLezioniStudentePK.idStudente = :idStudente");
        query.setParameter("idLezione", idLezione);
        query.setParameter("idStudente", idStudente);
        Long count = (Long) query.getSingleResult();

        return new Long(count.longValue() + 1);
    }

    public List<VotiLezioniStudente> findAllVotiLezione(Long idLezione) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("SELECT v FROM VotiLezioniStudente v "
                + " WHERE v.votiLezioniStudentePK.idLezione = :idLezione");
        query.setParameter("idLezione", idLezione);
        return query.getResultList();
    }

}
