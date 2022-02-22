/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import org.springframework.data.jpa.repository.JpaRepository;
import payroll.Entity.Typesignalement;

/**
 *
 * @author Dell
 */
public interface TypeSignalRepository extends JpaRepository <Typesignalement, Integer>{
    
}
