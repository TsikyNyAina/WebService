/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import model.Allsignal;
import model.Stat;
import org.apache.tomcat.util.codec.binary.Base64;
import payroll.Entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aro
 */
@Repository
public class BackRepos implements BackRepository {
    @Autowired
    JdbcTemplate jdbc;
    
    private static final  String sign = "SELECT * FROM regionstat";
    private static final  String sign1 = "SELECT * FROM statutstat";
    private static final  String sign2 = "SELECT * FROM typestat";
	
    @Override
    public List<Stat> regionstat() throws Exception{
        try {
            
            List<Stat> admin = jdbc.query(sign,rowMapperStat);
            System.out.println("idddddddddd"+admin);

            return admin;
        } catch (Exception e) {
                throw e;
        }
       
            
    }
    
    @Override
    public List<Stat> statutstat() throws Exception{
        try {
            
            List<Stat> admin = jdbc.query(sign1,rowMapperStat);
            System.out.println("idddddddddd"+admin);

            return admin;
        } catch (Exception e) {
                throw e;
        }
       
            
    }
    
    @Override
    public List<Stat> typestat() throws Exception{
        try {
            
            List<Stat> admin = jdbc.query(sign2,rowMapperStat);
            System.out.println("idddddddddd"+admin);

            return admin;
        } catch (Exception e) {
                throw e;
        }
       
            
    }
    private RowMapper<Stat> rowMapperStat = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            return new Stat (rs.getInt("nombre"),rs.getInt("id"),rs.getString("nom"));
        } catch (Exception e) {
            return null;
        }
          
    });
    
    
    
    private RowMapper<Allsignal> rowMapperSign = ((rs,rowNum)->{
        try {
            System.err.println(rs);
            byte[] h;
            h = rs.getBigDecimal("image").toBigInteger().toByteArray();
            System.out.println("                                              "+h);
            return new Allsignal (rs.getInt("id"),rs.getString("nompersonne"),rs.getString("nomregion"),rs.getDate("dateheure"),rs.getString("designation"),rs.getString("nomstatut"),rs.getString("typesignalement"),h,rs.getString("latitude"),rs.getString("longitude"));
        } catch (Exception e) {
            return null;
        }
          
    });
    
}

