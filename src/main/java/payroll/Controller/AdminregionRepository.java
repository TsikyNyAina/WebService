/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import payroll.Entity.Adminregion;
import payroll.Entity.Signalement;

/**
 *
 * @author ITU
 */
public interface AdminregionRepository extends JpaRepository <Adminregion, Integer>  {
    Adminregion findByUsernameAndMdp(String idstatut,String idregion);
}
