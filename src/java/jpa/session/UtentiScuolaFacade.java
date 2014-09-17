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
import jpa.entities.UtentiScuola;

/**
 *
 * @author rdgmus
 */
@Stateless
public class UtentiScuolaFacade extends AbstractFacade<UtentiScuola> {
    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtentiScuolaFacade() {
        super(UtentiScuola.class);
    }

    public UtentiScuola FindByEmailPassword(String email, String password) {
        UtentiScuola utente = null;
        Query query = em.createNamedQuery("UtentiScuola.findAll");
        List<UtentiScuola> listUtenti = query.getResultList();
        for (UtentiScuola c : listUtenti) {
            if (c.getEmail().equals(email) && c.getPassword().equals(password)) {
                utente = c;
                break;
            }
        }
        return utente;
    }

    

    public boolean existsUserEmail(String email) {
        Query query = getEntityManager().createNamedQuery("UtentiScuola.findByEmail");
        query.setParameter("email", email);
        List listWithEmail = query.getResultList();
        return listWithEmail.size() > 0;
    }

    public boolean existsUserPassword(String password) {
        Query query = getEntityManager().createNamedQuery("UtentiScuola.findByPassword");
        query.setParameter("password", password);
        List listWithPassword = query.getResultList();
        return listWithPassword.size() > 0;
    }
    
}
