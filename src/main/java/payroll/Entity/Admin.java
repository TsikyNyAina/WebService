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
 * @author Dell
 */
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer id;
    String nom;
    String prenom;
    String username;
    String mdp;

    public Admin() {
    }

    public Admin(Integer id, String nom, String prenom, String username, String mdp) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.mdp = mdp;
        
    }

    public Admin(int id, String login, String mdp) throws Exception{
        this.setId(id);
        this.setUsername(login);
        this.setMdp(mdp);
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
}
