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
import payroll.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ASUS
 */
interface AdminRepository extends JpaRepository <Admin, Integer> {
    Admin findByUsernameAndMdp(String username,String mdp);
}