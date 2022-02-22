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
public class Statut {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    int id;
    String nomstatut;

    public Statut() {
    }

    public Statut(int id, String nomStatut) {
        this.id = id;
        this.nomstatut = nomStatut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomstatut() {
        return nomstatut;
    }

    public void setNomstatut(String nomStatut) {
        this.nomstatut = nomStatut;
    }
    
}
