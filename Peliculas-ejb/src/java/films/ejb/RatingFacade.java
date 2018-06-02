/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.ejb;

import films.entity.Rating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tomas
 */
@Stateless
public class RatingFacade extends AbstractFacade<Rating> {

    @PersistenceContext(unitName = "Peliculas-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RatingFacade() {
        super(Rating.class);
    }

    public Rating buscarPorNombre(String nombre) {
        Query q = this.em.createNamedQuery("Rating.findByName");
        q.setParameter("name", nombre);
        Rating r = (Rating) q.getResultList().get(0);
        return r;
    }

}
