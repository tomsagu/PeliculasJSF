/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.ejb;

import films.entity.Category;
import films.entity.Film;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tomas
 */
@Stateless
public class FilmFacade extends AbstractFacade<Film> {

    @PersistenceContext(unitName = "Peliculas-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilmFacade() {
        super(Film.class);
    }
    
     public List<Film> buscarPeliculasPorCategoria(Category c){
        Query q = this.em.createQuery("select f from Film f JOIN f.filmCategoryList fl where fl.category.categoryId = :idCategoria");
        q.setParameter("idCategoria", c.getCategoryId());
        return q.getResultList();
    }
     
     public Film buscar(Integer id){
         Query q = this.em.createQuery("select f from Film f where f.filmId = :id");
        q.setParameter("id", id);
        Film f = (Film)q.getResultList().get(0);
        return f;
     }
    
}
