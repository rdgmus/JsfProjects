/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.AnniScolastici;
import jpa.entities.ScansioneOrarioAs;

/**
 *
 * @author rdgmus
 */
@Stateless
public class ScansioneOrarioAsFacade extends AbstractFacade<ScansioneOrarioAs> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ScansioneOrarioAsFacade() {
        super(ScansioneOrarioAs.class);
    }

    /**
     * Ritorna le ore di lezione per l'anno scolastico e il giorno della
     * settimana indicati nei parametri
     *
     * @param selectedAS
     * @param giornoSettimana
     * @return
     */
    public List<ScansioneOrarioAs> findOrario(AnniScolastici selectedAS, String giornoSettimana) {
//        throw new UnsupportedOperationException("Not yet implemented");
        if (selectedAS == null) {
            return null;
        } else {
            Query query = getEntityManager().createQuery("SELECT s FROM ScansioneOrarioAs s "
                    + " WHERE s.idAnnoScolastico = :idAnnoScolastico"
                    + " AND s.giornoSettimana = :giornoSettimana"
                    + " ORDER BY s.inizia ASC");
            query.setParameter("idAnnoScolastico", selectedAS);
            query.setParameter("giornoSettimana", giornoSettimana);
            return query.getResultList();
        }
    }

    /**
     * Numero delle ore di lezione già presenti per l'anno scolastico e il
     * giorno della settimana indicati nei parametri
     *
     * @param giornoSettimana
     * @return
     */
    public int numOreLezione(AnniScolastici selectedAS, String giornoSettimana) {
        int num = 0;
        Query query = getEntityManager().createQuery("SELECT count(s) FROM ScansioneOrarioAs s "
                + " WHERE s.idAnnoScolastico = :idAnnoScolastico"
                + " AND s.giornoSettimana = :giornoSettimana"
                + " AND s.numOraLezione != null");
        query.setParameter("idAnnoScolastico", selectedAS);
        query.setParameter("giornoSettimana", giornoSettimana);
        num = ((Integer) query.getSingleResult()).intValue();

        return num;
    }

    /**
     * Return lista dei giorni di orario già inseriti
     *
     * @param selectedAS
     * @return
     */
    public List<String> giorniOrario(AnniScolastici selectedAS) {
        Query query = getEntityManager().createQuery("SELECT DISTINCT s.giornoSettimana FROM ScansioneOrarioAs s "
                + " WHERE s.idAnnoScolastico = :idAnnoScolastico");
        query.setParameter("idAnnoScolastico", selectedAS);
        return query.getResultList();
    }

    /**
     * Copia orario del giorno 'dalGiorno' e lo sostituisce nel giorno
     * 'alGiorno' per l'anno scolastico del parametro selectedAS
     *
     * @param selectedAS
     * @param dalGiorno
     * @param alGiorno
     */
    public void copiaOrario(AnniScolastici selectedAS, String dalGiorno, String alGiorno) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        List<ScansioneOrarioAs> orarioEsistente = findOrario(selectedAS, alGiorno);
        if (orarioEsistente.size() > 0) {
            for (ScansioneOrarioAs rigaOrario : orarioEsistente) {
                getEntityManager().remove(rigaOrario);
            }
        }
        List<ScansioneOrarioAs> orarioDaCopiare = findOrario(selectedAS, dalGiorno);
        if (orarioDaCopiare.size() > 0) {
            for (ScansioneOrarioAs rigaOrario : orarioDaCopiare) {
                ScansioneOrarioAs nuovaRiga = new ScansioneOrarioAs();
                nuovaRiga.setIdAnnoScolastico(rigaOrario.getIdAnnoScolastico());
                nuovaRiga.setGiornoSettimana(alGiorno);
                nuovaRiga.setNumOraLezione(rigaOrario.getNumOraLezione());
                nuovaRiga.setInizia(rigaOrario.getInizia());
                nuovaRiga.setFinisce(rigaOrario.getFinisce());
                nuovaRiga.setLezione(rigaOrario.getLezione());
                nuovaRiga.setIntervallo(rigaOrario.getIntervallo());
                create(nuovaRiga);
            }
        }
    }

    /**
     * Cambia il tipo dell'ora tra LEZIONE/INTERVALLO al record con
     * id=idScansione e rinumera le ore di lezione in base all'orario chiamando
     *
     * @param idScansione
     * @param lezione
     */
    public void updateTipoAttivita(long idScansione, Boolean lezione) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Boolean intervallo = !lezione;
        Query query = getEntityManager().createQuery("UPDATE ScansioneOrarioAs s"
                + " SET s.lezione = :lezione,"
                + " s.intervallo = :intervallo"
                + " WHERE s.idScansione = :idScansione");
        query.setParameter("lezione", lezione);
        query.setParameter("intervallo", intervallo);
        query.setParameter("idScansione", idScansione);
        query.executeUpdate();
    }

    /**
     * Rienumera le ore di lezione in base all'orario.
     *
     * @param selectedAS
     * @param selectedGiorno
     */
    public void enumOreLezione(AnniScolastici selectedAS, String selectedGiorno) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        List<ScansioneOrarioAs> oreDaRienumerare = findOrario(selectedAS, selectedGiorno);
        int numOra = 1;
        for (ScansioneOrarioAs ora : oreDaRienumerare) {
            if (ora.getLezione()!=0) {
                ora.setNumOraLezione(numOra++);
            } else {
                ora.setNumOraLezione(null);
            }
        }
    }

    /**
     * Update dell'orario finale della riga orario
     *
     * @param idScansione
     * @param finisce
     */
    public void updateFineOrario(long idScansione, Time finisce) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE ScansioneOrarioAs s"
                + " SET s.finisce = :finisce"
                + " WHERE s.idScansione = :idScansione");
        query.setParameter("finisce", finisce);
        query.setParameter("idScansione", idScansione);
        query.executeUpdate();
    }

    /**
     * Update dell'orario iniziale della riga orario
     *
     * @param idScansione
     * @param inizia
     */
    public void updateInizioOrario(long idScansione, Time inizia) {
//        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE ScansioneOrarioAs s"
                + " SET s.inizia = :inizia"
                + " WHERE s.idScansione = :idScansione");
        query.setParameter("inizia", inizia);
        query.setParameter("idScansione", idScansione);
        query.executeUpdate();
    }

    /**
     * Aggiusta l'orario finale dell'ora precedente all'ora con id = idScansione
     *
     * @param selectedAS
     * @param selectedGiorno
     * @param idScansione
     */
    public void compattaOraPrecedente(AnniScolastici selectedAS, String selectedGiorno, long idScansione) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        ScansioneOrarioAs ora = find(idScansione);
        Date oraIniziale = ora.getInizia();
        String string = null;
        Date d = (Date) oraIniziale;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        string = sdf.format(d);
        Time newTime = Time.valueOf(string + ":00");
        //Carica l'orario del giorno
        ScansioneOrarioAs precOra = null;
        List<ScansioneOrarioAs> orario = findOrario(selectedAS, selectedGiorno);
        for (ScansioneOrarioAs o : orario) {
            if (o.getIdScansione() == idScansione) {
                if (precOra != null) {
                    updateFineOrario(precOra.getIdScansione(), newTime);
                }
                break;
            } else {
                precOra = o;
            }
        }
    }

    /**
     * Aggiusta l'orario iniziale dell'ora successiva all'ora con id =
     * idScansione
     *
     * @param selectedAS
     * @param selectedGiorno
     * @param longValue
     */
    public void compattaOraSuccessiva(AnniScolastici selectedAS, String selectedGiorno, long idScansione) {
//        throw new UnsupportedOperationException("Not yet implemented");
        ScansioneOrarioAs ora = find(idScansione);
        Date oraFinale = ora.getFinisce();
        String string = null;
        Date d = (Date) oraFinale;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        string = sdf.format(d);
        Time newTime = Time.valueOf(string + ":00");
        //Carica l'orario del giorno
        List<ScansioneOrarioAs> orario = findOrario(selectedAS, selectedGiorno);
        boolean trovato = false;
        for (ScansioneOrarioAs o : orario) {
            if (o.getIdScansione() == idScansione) {
                trovato = true;
                continue;
            }
            if (trovato) {
                updateInizioOrario(o.getIdScansione(), newTime);
                break;
            }
        }
    }

    public Integer getMaxNumeroOra(AnniScolastici selectedAS, String giorno) {
        int num = 0;
        Query query = getEntityManager().createQuery("SELECT MAX(s.numOraLezione) FROM ScansioneOrarioAs s "
                + " WHERE s.idAnnoScolastico = :idAnnoScolastico"
                + " AND s.giornoSettimana = :giornoSettimana");
        query.setParameter("idAnnoScolastico", selectedAS);
        query.setParameter("giornoSettimana", giorno);
        num = ((Integer) query.getSingleResult());

        return num;
    }

    public void aggiungiOra(AnniScolastici selectedAS, String giorno, Integer durataOra, Date inizioLezioni) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        List<ScansioneOrarioAs> orario = findOrario(selectedAS, giorno);
        ScansioneOrarioAs ora = new ScansioneOrarioAs();
        Calendar cal = Calendar.getInstance();
        Integer numOra = null;

        if (orario.isEmpty()) {
            ora.setIdAnnoScolastico(selectedAS);
            ora.setGiornoSettimana(giorno);
            ora.setNumOraLezione(1);
            ora.setInizia(inizioLezioni);

            cal.setTime(inizioLezioni);
            cal.add(Calendar.MINUTE, durataOra);

            ora.setFinisce(cal.getTime());
            ora.setLezione((short)1);
            ora.setIntervallo((short)0);

            create(ora);
        } else {

            ScansioneOrarioAs ultimaOra = orario.get(orario.size() - 1);

            ora.setIdAnnoScolastico(selectedAS);
            ora.setGiornoSettimana(giorno);

            numOra = getMaxNumeroOra(selectedAS, giorno);

            ora.setNumOraLezione(numOra + 1);
            ora.setInizia(ultimaOra.getFinisce());

            cal.setTime(ultimaOra.getFinisce());
            cal.add(Calendar.MINUTE, durataOra);

            ora.setFinisce(cal.getTime());
            ora.setLezione((short)1);
            ora.setIntervallo((short)0);

            create(ora);
        }
    }

    public boolean aggiungiIntervallo(AnniScolastici selectedAS, String giorno, Integer durataIntervallo) {
        List<ScansioneOrarioAs> orario = findOrario(selectedAS, giorno);
        ScansioneOrarioAs ora = new ScansioneOrarioAs();
        Calendar cal = Calendar.getInstance();

        if (orario.size() > 0) {
            ScansioneOrarioAs ultimaOra = orario.get(orario.size() - 1);

            ora.setIdAnnoScolastico(selectedAS);
            ora.setGiornoSettimana(giorno);
            ora.setNumOraLezione(null);
            ora.setInizia(ultimaOra.getFinisce());

            cal.setTime(ultimaOra.getFinisce());
            cal.add(Calendar.MINUTE, durataIntervallo);

            ora.setFinisce(cal.getTime());
            ora.setLezione((short)0);
            ora.setIntervallo((short)1);

            create(ora);
            return true;
        }
        return false;
    }
}
