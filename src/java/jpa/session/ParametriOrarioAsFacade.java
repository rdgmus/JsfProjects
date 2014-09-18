/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.AnniScolastici;
import jpa.entities.ParametriOrarioAs;
import jsf.util.JsfUtil;

/**
 *
 * @author rdgmus
 */
@Stateless
public class ParametriOrarioAsFacade extends AbstractFacade<ParametriOrarioAs> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametriOrarioAsFacade() {
        super(ParametriOrarioAs.class);
    }

    /**
     * Durata ora di lezione in minuti
     *
     * @param selectedAS
     * @return
     */
    public Integer findDurataOra(AnniScolastici selectedAS) {
        Integer value = null;
        Query query = getEntityManager().createQuery("SELECT p.durataOraMinuti FROM ParametriOrarioAs p WHERE p.idAnnoScolastico = :idAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS);
        value = Integer.valueOf(String.valueOf(query.getSingleResult()));

        return value;
    }

    /**
     * Durata intervalli fra le lezioni in minuti
     *
     * @param selectedAS
     * @return
     */
    public Integer findDurataIntervallo(AnniScolastici selectedAS) {
        Integer value = null;
        Query query = getEntityManager().createQuery("SELECT p.durataIntervalloMinuti FROM ParametriOrarioAs p WHERE p.idAnnoScolastico = :idAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS);
        value = Integer.valueOf(String.valueOf(query.getSingleResult()));

        return value;
    }

    /**
     * Ora di inizio delle lezioni
     *
     * @param selectedAS
     * @return
     */
    public Time findInizioLezioni(AnniScolastici selectedAS) {
        Time inizio = null;
        Date inizioDate = null;
        Query query = getEntityManager().createQuery("SELECT p.inizioLezioni FROM ParametriOrarioAs p WHERE p.idAnnoScolastico = :idAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS);
        inizioDate = (Date) query.getSingleResult();

        String string = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        string = sdf.format(inizioDate);
        inizio = Time.valueOf(string + ":00");

        return inizio;
    }

    public Long findIdParamOrario(AnniScolastici selectedAS) {
        Long value = null;
        Query query = getEntityManager().createQuery("SELECT p.idParamOrario FROM ParametriOrarioAs p WHERE p.idAnnoScolastico = :idAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS);
        value = Long.valueOf(String.valueOf(query.getSingleResult()));

        return value;
    }

    public ParametriOrarioAs findParametriOrarioAS(AnniScolastici selectedAS) {
//        throw new UnsupportedOperationException("Not yet implemented");
        ParametriOrarioAs params = null;
        Query query = getEntityManager().createNamedQuery("ParametriOrarioAs.findByIdAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS);
        try {
            params = (ParametriOrarioAs) query.getSingleResult();
        } catch (NoResultException ejbex) {
//            JsfUtil.addErrorMessage("Non è stato possibile estrarre i PARAMETRI ORARIO:"
//                    + ejbex.getMessage());
        }
        return params;
    }

    public void updateParamsOrario(ParametriOrarioAs parametriOrarioAs) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE ParametriOrarioAs p SET "
                + "p.inizioLezioni = :inizioLezioni,"
                + "p.durataOraMinuti = :durataOraMinuti,"
                + "p.durataIntervalloMinuti = :durataIntervalloMinuti"
                + " WHERE p.idParamOrario = :idParamOrario");
        query.setParameter("idParamOrario", parametriOrarioAs.getIdParamOrario());
        query.setParameter("inizioLezioni", parametriOrarioAs.getInizioLezioni());
        query.setParameter("durataOraMinuti", parametriOrarioAs.getDurataOraMinuti());
        query.setParameter("durataIntervalloMinuti", parametriOrarioAs.getDurataIntervalloMinuti());
        query.executeUpdate();
    }

    @Override
    public void remove(ParametriOrarioAs entity) {
//        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        Query query = getEntityManager().createQuery("DELETE FROM ParametriOrarioAs p "
                + " WHERE p.idParamOrario = :idParamOrario");
        query.setParameter("idParamOrario", entity.getIdParamOrario());
        query.executeUpdate();
    }

}
