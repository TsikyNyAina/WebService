/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import java.util.function.Supplier;

/**
 *
 * @author Dell
 */
public class RegionNotFoundException extends RuntimeException  {

     RegionNotFoundException(Integer id) {
    super("Could not find Region " + id);
  }
}
