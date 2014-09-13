/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.UtentiLogger;

/**
 *
 * @author rdgmus
 */
@Stateless
public class UtentiLoggerFacade extends AbstractFacade<UtentiLogger> {
    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtentiLoggerFacade() {
        super(UtentiLogger.class);
    }
    
}
