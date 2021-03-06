/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payroll.Entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ASUS
 */

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String name;
    private String role;
    
    Employee() {}

    Employee(String name, String role) {

      this.name = name;
      this.role = role;
    }

    public Integer getId() {
      return this.id;
    }

    public String getName() {
      return this.name;
    }

    public String getRole() {
      return this.role;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setRole(String role) {
      this.role = role;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (!(o instanceof Employee))
        return false;
      Employee employee = (Employee) o;
      return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
          && Objects.equals(this.role, employee.role);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.name, this.role);
    }

    @Override
    public String toString() {
      return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
