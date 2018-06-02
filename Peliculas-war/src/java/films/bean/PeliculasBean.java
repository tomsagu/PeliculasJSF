/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.bean;

import films.ejb.CategoryFacade;
import films.ejb.FilmFacade;
import films.entity.Category;
import films.entity.Film;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Tomas
 */
@Named(value = "peliculasBean")
@SessionScoped
public class PeliculasBean implements Serializable{

    @EJB
    private CategoryFacade categoryFacade;
    
    @EJB
    private FilmFacade filmFacade;   
    
    private String categoriaActual;
    private Map<Category, List<Film>> categoriasMap;
    private List<Category> listaCategorias;
    private List<String> nombreCategorias;
    
    private short idFilmSeleccionado;

    public short getIdFilmSeleccionado() {
        return idFilmSeleccionado;
    }

    public void setIdFilmSeleccionado(short idFilmSeleccionado) {
        this.idFilmSeleccionado = idFilmSeleccionado;
    }

    public List<String> getNombreCategorias() {
        return nombreCategorias;
    }

    public void setNombreCategorias(List<String> nombreCategorias) {
        this.nombreCategorias = nombreCategorias;
    }

    public String getCategoriaActual() {
        return categoriaActual;
    }

    public void setCategoriaActual(String categoriaActual) {
        this.categoriaActual = categoriaActual;
    }

    public Map<Category, List<Film>> getCategoriasMap() {
        return categoriasMap;
    }

    public void setCategoriasMap(Map<Category, List<Film>> categoriasMap) {
        this.categoriasMap = categoriasMap;
    }


    public List<Category> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Category> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }
    
    
    public List<Film> getListaPeliculas(){
        return categoriasMap.get(this.categoryFacade.buscarPorNombre(categoriaActual));
        
    }
    /**
     * Creates a new instance of peliculasBean
     */
    public PeliculasBean() {
    }
    
    @PostConstruct
    public void init(){
        idFilmSeleccionado = -1;
        categoriasMap  = new HashMap<>();
        listaCategorias = new ArrayList<>();
        nombreCategorias = new ArrayList<>();
        listaCategorias = this.categoryFacade.findAll();
        for(Category c : listaCategorias){
            this.categoriasMap.put(c,this.filmFacade.buscarPeliculasPorCategoria(c));
            nombreCategorias.add(c.getName()); 
        }
        categoriaActual = nombreCategorias.get(0);
    }
    
    public String doBorrar(short idFilm){
        Film f = this.filmFacade.find(idFilm);
        this.filmFacade.remove(f);
        String ca = this.categoriaActual;
        this.init();
        this.categoriaActual = ca;
        return "peliculas";
    }
    
    public String doEditar(short idFilm){
        this.idFilmSeleccionado = idFilm;
        return "editarPelicula";
    }    
    
}
