/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

/**
 *
 * @author ITU
 */
public class AdminregionNotFoundException extends RuntimeException{
    AdminregionNotFoundException(Integer id) {
    super("Could not find Adminregion" + id);
  }
}
