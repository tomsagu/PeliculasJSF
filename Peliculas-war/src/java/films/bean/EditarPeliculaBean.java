/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package films.bean;

import films.ejb.FilmFacade;
import films.ejb.LanguageFacade;
import films.ejb.RatingFacade;
import films.entity.Film;
import films.entity.Language;
import films.entity.Rating;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Tomas
 */
@Named(value = "editarPeliculaBean")
@RequestScoped
public class EditarPeliculaBean {

    @Inject
    private PeliculasBean peliculasBean;
    
    @EJB
    private FilmFacade filmFacade;
    
    @EJB
    private LanguageFacade languageFacade;
    
    @EJB
    private RatingFacade ratingFacade;
    
    protected Film pelicula;
    private List<Language> languageList = new ArrayList<>();
    private List<Rating> ratingList = new ArrayList<>();
    private short languageSeleccionado;
    private short ratingSeleccionado;

    public short getLanguageSeleccionado() {
        return languageSeleccionado;
    }

    public void setLanguageSeleccionado(short languageSeleccionado) {
        this.languageSeleccionado = languageSeleccionado;
    }

    public short getRatingSeleccionado() {
        return ratingSeleccionado;
    }

    public void setRatingSeleccionado(short ratingSeleccionado) {
        this.ratingSeleccionado = ratingSeleccionado;
    }
    

    public List<Language> getLanguageList() {
        return languageList;
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
    

    public Film getPelicula() {
        return pelicula;
    }

    public void setPelicula(Film pelicula) {
        this.pelicula = pelicula;
    }
    
    /**
     * Creates a new instance of editarPeliculaBean
     */
    public EditarPeliculaBean() {
    }
    
    @PostConstruct
    public void init(){
        short id = this.peliculasBean.getIdFilmSeleccionado();
        if(id != -1){
           this.pelicula = this.filmFacade.find(id);
            languageList = this.languageFacade.findAll();
            ratingList = this.ratingFacade.findAll();
            this.ratingSeleccionado = pelicula.getRatingId().getRatingId();
            this.languageSeleccionado = pelicula.getLanguageId().getLanguageId();
        }  
    }
    
    public String doGuardar(){
        Language l = this.languageFacade.find(this.languageSeleccionado);
        Rating r = this.ratingFacade.find(this.ratingSeleccionado);
        this.pelicula.setLanguageId(l);
        this.pelicula.setRatingId(r);
        this.pelicula.setLastUpdate(new Date());
        this.filmFacade.edit(pelicula);
        String categoriaActual = this.peliculasBean.getCategoriaActual();
        this.peliculasBean.init();
        this.peliculasBean.setCategoriaActual(categoriaActual);
        return "peliculas";
    }
    
    public String doCancelar(){
        return "peliculas";
    }
}
