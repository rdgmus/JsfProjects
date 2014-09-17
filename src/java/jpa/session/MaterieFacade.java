/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.math.BigInteger;
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
import jpa.entities.Materie;

/**
 *
 * @author rdgmus
 */
@Stateless
public class MaterieFacade extends AbstractFacade<Materie> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaterieFacade() {
        super(Materie.class);
    }

    /**
     * Ottiene la lista delle materie dell'insegnante dato ordinate per nome
     * materia.
     *
     * @param selectedAS
     * @param selectedClasse
     * @param selectedInsegnante
     * @return
     */
    public List<Materie> retrieveMaterieInsegnanteOrderedList(AnniScolastici selectedAS, Classi selectedClasse, Insegnanti selectedInsegnante) {
        Query query = getEntityManager().createQuery("SELECT m FROM Materie m WHERE m.idAnnoScolastico = :idAnnoScolastico "
                + "AND m.idClasse = :idClasse AND m.idInsegnante = :idInsegnante "
                + "ORDER BY m.materia ASC");
        query.setParameter("idAnnoScolastico", selectedAS);
        query.setParameter("idClasse", selectedClasse);
        query.setParameter("idInsegnante", selectedInsegnante);
        return query.getResultList();
    }

    /**
     * Ottiene la lista delle materie dell'insegnante dato ordinate per nome
     * materia.
     *
     * @param insegnante
     * @return
     */
    public List<Materie> retrieveAllMaterieInsegnanteOrderedList(Insegnanti insegnante) {
        Query query = getEntityManager().createQuery("SELECT m FROM Materie m WHERE m.idInsegnante = :idInsegnante "
                + "ORDER BY m.materia ASC");
        query.setParameter("idInsegnante", insegnante);
        return query.getResultList();
    }

    /**
     * Ottiene la materia tramite il suo identificativo
     *
     * @param idMateria
     * @return
     */
    public Materie findByIdMateria(Long idMateria) {
        Query query = getEntityManager().createNamedQuery("Materie.findByIdMateria");
        query.setParameter("idMateria", idMateria);
        return (Materie) query.getSingleResult();
    }

    /**
     * Ottiene la lista delle materie associate alla classe data.
     *
     * @param idClasse
     * @return
     */
    public List<Materie> findListaMaterieClasse(Classi idClasse) {
        Query query = getEntityManager().createQuery("SELECT m FROM Materie m WHERE m.idClasse = :idClasse"
                + " ORDER BY m.materia ASC");
        query.setParameter("idClasse", idClasse);
        return query.getResultList();
    }
    @EJB
    ClassiFacade classiFacade;
    @EJB
    AnniScolasticiFacade anniScolasticiFacade;
    @EJB
    InsegnantiFacade insegnantiFacade;

    public ClassiFacade getClassiFacade() {
        return classiFacade;
    }

    public AnniScolasticiFacade getAnniScolasticiFacade() {
        return anniScolasticiFacade;
    }

    public InsegnantiFacade getInsegnantiFacade() {
        return insegnantiFacade;
    }

    public List<Materie> retrieveAllMaterieDistinctOrderedList(List<Materie> listaMaterie) {
//        throw new UnsupportedOperationException("Not yet implemented");
        ArrayList<Materie> resultList = new ArrayList<Materie>();
        List<Object[]> objList = null;
        Query query = getEntityManager().createNativeQuery(
                "SELECT DISTINCT ON (materie.materia) "
                + " materie.id_materia,  "//0
                + " materie.materia,  "//1
                + " materie.id_classe,  "//2
                + " materie.anno_scolastico,  "//3
                + " materie.id_insegnante,  "//4
                + " materie.giudizio,  "//5
                + " materie.scritto,  "//6
                + " materie.orale,  "//7
                + " materie.pratico,  "//8
                + " materie.id_anno_scolastico "//9
                + " FROM scuola.materie "
                + " ORDER BY materie.materia ASC");
        objList = query.getResultList();

        for (Object[] obj : objList) {
            Materie altraMateria = new Materie();
            altraMateria.setIdMateria(Long.valueOf(String.valueOf(obj[0])));
            altraMateria.setMateria(String.valueOf(obj[1]));

            Classi nuovaClasse = getClassiFacade().find(Long.valueOf(String.valueOf(obj[2])));

            altraMateria.setIdClasse(nuovaClasse);

            altraMateria.setAnnoScolastico(String.valueOf(obj[3]));

            Insegnanti nuovoInsegnante = getInsegnantiFacade().find(Long.valueOf(String.valueOf(obj[4])));

            altraMateria.setIdInsegnante(nuovoInsegnante);

            altraMateria.setGiudizio(Boolean.parseBoolean(String.valueOf(obj[5]))?(short)1:(short)0);
            altraMateria.setScritto(Boolean.parseBoolean(String.valueOf(obj[6]))?(short)1:(short)0);
            altraMateria.setOrale(Boolean.parseBoolean(String.valueOf(obj[7]))?(short)1:(short)0);
            altraMateria.setPratico(Boolean.parseBoolean(String.valueOf(obj[8]))?(short)1:(short)0);

            AnniScolastici nuovoAS = getAnniScolasticiFacade().find(Long.valueOf(String.valueOf(obj[9])));

            altraMateria.setIdAnnoScolastico(nuovoAS);

            if (materiaNotIn(altraMateria, listaMaterie)) {
                resultList.add(altraMateria);
            }
        }
        return resultList;
    }

    private boolean materiaNotIn(Materie altraMateria, List<Materie> listaMaterie) {
//        throw new UnsupportedOperationException("Not yet implemented");
        for (Materie i : listaMaterie) {
            if (i.getMateria().equals(altraMateria.getMateria())) {
                return false;
            }
        }
        return true;
    }

    

    public void updateMateria(Materie selectedMateria) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE Materie m"
                + " SET m.materia = :materia,"
                + " m.giudizio = :giudizio,"
                + " m.scritto = :scritto,"
                + " m.orale = :orale,"
                + " m.pratico = :pratico"
                + " WHERE m.idMateria = :idMateria");
        query.setParameter("idMateria", selectedMateria.getIdMateria());
        query.setParameter("materia", selectedMateria.getMateria().toUpperCase());
        query.setParameter("giudizio", selectedMateria.getGiudizio());
        query.setParameter("scritto", selectedMateria.getScritto());
        query.setParameter("orale", selectedMateria.getOrale());
        query.setParameter("pratico", selectedMateria.getPratico());
        query.executeUpdate();

    }

    public boolean isTipoVotoEnabled(Long idMateria, String tipoVoto) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query;
        query = getEntityManager().createQuery(
                "SELECT m FROM Materie m WHERE m.idMateria = :idMateria");
        query.setParameter("idMateria", idMateria);
        Materie m = (Materie) query.getSingleResult();
        if (tipoVoto.equals("G")) {
            return m.getGiudizio()!=0;
        } else if (tipoVoto.equals("S")) {
            return m.getScritto()!=0;
        } else if (tipoVoto.equals("O")) {
            return m.getOrale()!=0;
        } else if (tipoVoto.equals("P")) {
            return m.getPratico()!=0;
        } else {
            return false;
        }
    }

    public Character getFirstTipoVotoEnabled(Long idMateria) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query;
        query = getEntityManager().createQuery(
                "SELECT m FROM Materie m WHERE m.idMateria = :idMateria");
        query.setParameter("idMateria", idMateria);
        Materie m = (Materie) query.getSingleResult();

        if (m.getGiudizio()!=0) {
            return 'G';
        } else if (m.getPratico()!=0) {
            return 'P';
        } else if (m.getOrale()!=0) {
            return 'O';
        } else if (m.getScritto()!=0) {
            return 'S';
        } else {
            return 'N';
        }
    }
}
