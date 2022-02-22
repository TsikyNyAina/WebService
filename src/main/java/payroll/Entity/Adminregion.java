/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ITU
 */
@Entity
public class Adminregion {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;
    String nom;
    String prenom;
    String username;
    String mdp;
    Integer idregion;

    public Adminregion() {
    }

    public Adminregion(Integer id, String nom, String prenom, String username, String mdp, Integer idregion) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.mdp = mdp;
        this.idregion = idregion;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Integer getIdregion() {
        return idregion;
    }

    public void setIdregion(Integer idregion) {
        this.idregion = idregion;
    }
    
    
    
}
