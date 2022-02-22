/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import payroll.Entity.Personne;

/**
 *
 * @author Dell
 */
public class PersonneNotFoundException extends RuntimeException{

    PersonneNotFoundException(Integer id) {
    super("Could not find Personne " + id);
  }
    
}
