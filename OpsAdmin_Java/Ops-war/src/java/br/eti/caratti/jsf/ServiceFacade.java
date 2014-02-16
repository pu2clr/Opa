/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.caratti.jsf;

import br.eti.caratti.ops.entity.Service;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rcaratti
 */
@Stateless
public class ServiceFacade extends AbstractFacade<Service> {
    @PersistenceContext(unitName = "Ops-warPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceFacade() {
        super(Service.class);
    }
    
}
