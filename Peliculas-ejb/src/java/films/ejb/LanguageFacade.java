/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.ejb;

import films.entity.Language;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tomas
 */
@Stateless
public class LanguageFacade extends AbstractFacade<Language> {

    @PersistenceContext(unitName = "Peliculas-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LanguageFacade() {
        super(Language.class);
    }
    
     public Language buscarPorNombre(String nombre){
        Query q = this.em.createQuery("SELECT l FROM Language l WHERE l.name = :name");
        q.setParameter("name", nombre);
        Language l = (Language)q.getResultList().get(0);
        return l;
    }
    
}
