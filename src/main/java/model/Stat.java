/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author aro
 */
public class Stat {
    Integer nombre;
    Integer id;
    String nom;

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Stat() {
    }

    public Stat(Integer nombre, Integer id, String nom) {
        this.nombre = nombre;
        this.id = id;
        this.nom = nom;
    }
    
    public Integer[] separ(List<Stat> liste){
        Integer[] nb=new Integer[liste.size()];
        for (int i=0;i<liste.size();i++){
            nb[i]=liste.get(i).getNombre();
        }
        
        return nb;
    }
    
    public String[] separnom(List<Stat> liste){
        String[] nom=new String[liste.size()];
        for (int i=0;i<liste.size();i++){
            nom[i]=liste.get(i).getNom();
        }
        
        return nom;
    }
}
