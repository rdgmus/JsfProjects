/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.AnniScolastici;

/**
 *
 * @author rdgmus
 */
@Stateless
public class AnniScolasticiFacade extends AbstractFacade<AnniScolastici> {
    @PersistenceContext(unitName = "RegistroWebApp3.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnniScolasticiFacade() {
        super(AnniScolastici.class);
    }
    
}
