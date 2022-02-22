/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

/**
 *
 * @author Dell
 */
class AdminNotFoundException extends RuntimeException{
    AdminNotFoundException(Integer id) {
    super("Could not find Admin" + id);
  }
}
