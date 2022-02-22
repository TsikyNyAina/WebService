/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import payroll.Entity.Typesignalement;

/**
 *
 * @author Dell
 */
public class TypeSignalNotFoundException extends RuntimeException{
    TypeSignalNotFoundException(Integer id) {
    super("Could not find TypeSignal" +id); 
    }
    
}
