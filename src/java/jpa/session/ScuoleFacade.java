/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.entities.Scuole;

/**
 *
 * @author rdgmus
 */
@Stateless
public class ScuoleFacade extends AbstractFacade<Scuole> {

    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ScuoleFacade() {
        super(Scuole.class);
    }

    public void updateScuola(Scuole selectedScuola) {
        //        throw new UnsupportedOperationException("Not yet implemented");
        Query query = getEntityManager().createQuery("UPDATE Scuole s SET s.nomeScuola = :nomeScuola,"
                + "s.tipoScuolaAcronimo = :tipoScuolaAcronimo,"
                + "s.indirizzo = :indirizzo,"
                + "s.cap = :cap,"
                + "s.città = :città,"
                + "s.provincia = :provincia,"
                + "s.telefono = :telefono,"
                + "s.fax = :fax,"
                + "s.email = :email,"
                + "s.web = :web"
                + " WHERE s.idScuola = :idScuola");
        query.setParameter("idScuola", selectedScuola.getIdScuola());
        query.setParameter("nomeScuola", selectedScuola.getNomeScuola());
        query.setParameter("tipoScuolaAcronimo", selectedScuola.getTipoScuolaAcronimo());
        query.setParameter("indirizzo", selectedScuola.getIndirizzo());
        query.setParameter("cap", selectedScuola.getCap());
        query.setParameter("città", selectedScuola.getCittà());
        query.setParameter("provincia", selectedScuola.getProvincia());
        query.setParameter("telefono", selectedScuola.getTelefono());
        query.setParameter("fax", selectedScuola.getFax());
        query.setParameter("email", selectedScuola.getEmail());
        query.setParameter("web", selectedScuola.getWeb());
        query.executeUpdate();
    }

    public Long getNextId() {
        Long maxId = null;
        Query query = getEntityManager().createNativeQuery("SELECT nextval('scuola.scuole_seq')");
        maxId = (Long) query.getSingleResult();
        return maxId;
    }
}
