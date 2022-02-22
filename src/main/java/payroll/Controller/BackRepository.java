/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import java.util.List;
import model.Allsignal;
import model.Stat;
import payroll.Entity.Admin;

/**
 *
 * @author ITU
 */
public interface BackRepository {
    List<Stat> regionstat() throws Exception;
    List<Stat> statutstat() throws Exception;
    List<Stat> typestat() throws Exception;
    
}
