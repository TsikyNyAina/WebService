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
public class Typesignalement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int id;
    String typesignalement;

    public Typesignalement() {
    }

    public Typesignalement(int id, String nomTypeSignal) {
        this.id = id;
        this.typesignalement = nomTypeSignal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypesignalement() {
        return typesignalement;
    }

    public void setTypesignalement(String nomTypeSignal) {
        this.typesignalement = nomTypeSignal;
    }
    
}
