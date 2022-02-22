/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import payroll.Entity.Statut;

/**
 *
 * @author Dell
 */
public class StatutNotFoundException extends RuntimeException{
    StatutNotFoundException(Integer id) {
    super("Could not find Statut" +id); 
    }
}
