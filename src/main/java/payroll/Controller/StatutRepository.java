/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import org.springframework.data.jpa.repository.JpaRepository;
import payroll.Entity.Adminregion;
import payroll.Entity.Statut;

/**
 *
 * @author Dell
 */
public interface StatutRepository extends JpaRepository <Statut, Integer>{
   Statut findByNomstatut(String nomstatut);
}
