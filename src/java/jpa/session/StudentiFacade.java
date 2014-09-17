/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.Classi;
import jpa.entities.Studenti;

/**
 *
 * @author rdgmus
 */
@Stateless
public class StudentiFacade extends AbstractFacade<Studenti> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentiFacade() {
        super(Studenti.class);
    }

    public java.util.List<Studenti> retrieveStudentiClasseOrderedList(Classi classe) {
        Query query = getEntityManager().createQuery("SELECT s FROM Studenti s WHERE s.idClasse = :idClasse "
                + "ORDER BY s.cognome  , s.nome ASC");
        query.setParameter("idClasse", classe);
        return query.getResultList();
    }

    

    public Long getMaxIdStudente() {
        Long maxId = null;
        Query query = getEntityManager().createQuery("SELECT max(s.idStudente) FROM Studenti s");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }

    public void updateDataEntrata(Studenti selectedStudente, Date dataEntrata) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (selectedStudente != null) {
            Query query = getEntityManager().createQuery("UPDATE Studenti s"
                    + " SET s.dataEntrata = :dataEntrata"
                    + " WHERE s.idStudente = :idStudente");
            query.setParameter("dataEntrata", dataEntrata);
            query.setParameter("idStudente", selectedStudente.getIdStudente());
            query.executeUpdate();
        }
    }

    public void updateRitiratoData(Studenti selectedStudente, Date ritiratoData) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (selectedStudente != null) {
            Query query = getEntityManager().createQuery("UPDATE Studenti s"
                    + " SET s.ritiratoData = :ritiratoData,"
                    + " s.attivo = :attivo"
                    + " WHERE s.idStudente = :idStudente");
            query.setParameter("ritiratoData", ritiratoData);
            if (ritiratoData == null) {
                query.setParameter("attivo", true);
            } else {
                query.setParameter("attivo", false);
            }
            query.setParameter("idStudente", selectedStudente.getIdStudente());
            query.executeUpdate();
        }
    }

    public void updateCognomeNomeStudente(Studenti selectedStudente) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (selectedStudente != null) {
            if (selectedStudente.getCognome() == null || selectedStudente.getCognome().length() == 0
                    || selectedStudente.getCognome().equals("[cognome]")) {
                return;
            }
            if (selectedStudente.getNome() == null || selectedStudente.getNome().length() == 0
                    || selectedStudente.getNome().equals("[nome]")) {
                return;
            }
            Query query = getEntityManager().createQuery("UPDATE Studenti s"
                    + " SET s.cognome = :cognome,"
                    + " s.nome = :nome"
                    + " WHERE s.idStudente = :idStudente");
            query.setParameter("cognome", selectedStudente.getCognome().toUpperCase());
            query.setParameter("nome", selectedStudente.getNome().toUpperCase());
            query.setParameter("idStudente", selectedStudente.getIdStudente());
            query.executeUpdate();
        }
    }
    @EJB
    ClassiFacade classiFacade;

    public ClassiFacade getClassiFacade() {
        return classiFacade;
    }

    public List<Studenti> retrieveAllStudentiDistinctOrderedList(List<Studenti> listaStudenti) {
//        throw new UnsupportedOperationException("Not yet implemented");        
        ArrayList<Studenti> resultList = new ArrayList<Studenti>();
        List<Object[]> objList = null;
        Query query = getEntityManager().createNativeQuery("SELECT  DISTINCT  "
                + " ON (studenti.cognome,studenti.nome)"
                + " studenti.id_classe, "
                + " studenti.anno_scolastico, "
                + " studenti.cognome, "
                + " studenti.nome, "
                + " studenti.id_studente,"
                + " studenti.attivo,"
                + " studenti.id_anno_scolastico"
                + " FROM scuola.studenti"
                + " ORDER BY studenti.cognome ASC, "
                + " studenti.nome ASC");
        objList = query.getResultList();

        for (Object[] obj : objList) {
            Studenti altroStudente = new Studenti();

            Classi nuovaClasse = getClassiFacade().find(Long.valueOf(String.valueOf(obj[0])));
            altroStudente.setIdClasse(nuovaClasse);

            altroStudente.setAnnoScolastico(String.valueOf(obj[1]));
            altroStudente.setCognome(String.valueOf(obj[2]));
            altroStudente.setNome(String.valueOf(obj[3]));

            altroStudente.setIdStudente(Long.valueOf(String.valueOf(obj[4])));
            altroStudente.setAttivo(
                    Boolean.parseBoolean(String.valueOf(obj[5]))?(short)1:(short)0
                    );

            altroStudente.setIdAnnoScolastico(Long.valueOf(String.valueOf(obj[6])));
            if (studenteNotIn(altroStudente, listaStudenti)) {
                resultList.add(altroStudente);
            }
        }
        return resultList;
    }

    private boolean studenteNotIn(Studenti altroStudente, List<Studenti> listaStudenti) {
//        throw new UnsupportedOperationException("Not yet implemented");
        for (Studenti i : listaStudenti) {
            if (i.getCognome().equals(altroStudente.getCognome())
                    && i.getNome().equals(altroStudente.getNome())) {
                return false;
            }
        }
        return true;
    }
}
