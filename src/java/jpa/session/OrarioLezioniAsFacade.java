/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.OrarioLezioniAs;

/**
 *
 * @author rdgmus
 */
@Stateless
public class OrarioLezioniAsFacade extends AbstractFacade<OrarioLezioniAs> {
    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrarioLezioniAsFacade() {
        super(OrarioLezioniAs.class);
    }
    
}
