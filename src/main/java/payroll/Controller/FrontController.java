/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.imageio.spi.ServiceRegistry;
import javax.security.auth.login.Configuration;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import model.Recherche;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import payroll.Entity.Adminregion;
import payroll.Entity.Region;
import payroll.Entity.Signalement;
import payroll.Entity.Statut;
import payroll.Entity.Typesignalement;

/**
 *
 * @author ITU
 */
@CrossOrigin
@RestController
public class FrontController {
    private final AdminregionRepository repository;
    private final SignalRepository repository1;
    private final StatutRepository repository2;
    private final RegionRepository repository3;
    
  FrontController(AdminregionRepository repository,SignalRepository repository1,StatutRepository repository2,RegionRepository repository3) {
    this.repository = repository;
    this.repository1 = repository1;
    this.repository2 = repository2;
    this.repository3 = repository3;
  }
  
  
  
  
  @PostMapping("/frontLogin")
  ResponseEntity<Map<String,String>> newSignal(@RequestBody Adminregion newSignal) {
    Map<String,String> map = new HashMap<>();
    try {
                verifierMail(newSignal.getUsername());
                verifierMdp(newSignal.getMdp());
                Adminregion admin= repository.findByUsernameAndMdp(newSignal.getUsername(), newSignal.getMdp());
                map=generateJWTToken(admin);
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
  }
  
  
  
  @GetMapping("/signalements/{idregion}/{statut}")
  ResponseEntity<Map<String,Object>> allMiandry(HttpServletRequest request,@PathVariable Integer idregion,@PathVariable String statut,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
          verifierTokenAdmin(authHeader, request);
          Statut statu= repository2.findByNomstatut(statut);
            List<Signalement> liste= repository1.findByIdstatutAndIdregion(idregion,statu.getId());
            map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }
      catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
      
  }
  
  @GetMapping("/signalements")
  ResponseEntity<Map<String,Object>> allMiandry(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
          verifierTokenAdmin(authHeader, request);
          
            List<Signalement> liste= repository1.findByIdregion(Integer.parseInt(request.getAttribute("idregion").toString()));
            map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }
      catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
      
  }
  
  @GetMapping("/region")
  ResponseEntity<Map<String,Object>> regionray(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
          verifierTokenAdmin(authHeader, request);
          
            Optional<Region> liste= repository3.findById(Integer.parseInt(request.getAttribute("idregion").toString()));
            map.put("message", "Region");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }
      catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
      
  }
  
  
  
  @GetMapping("/signe/{idstatut}")
  ResponseEntity<Map<String,Object>> statu(HttpServletRequest request,@PathVariable Integer idstatut,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        Optional<Signalement> liste= repository1.findById(idstatut);
        map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
  }
  
   @GetMapping("/recherche/{newR}")
  ResponseEntity<Map<String,Object>> allMiandryBe(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader,@PathVariable String newR) {
      Recherche nouv = new Recherche();
      
      
      Map<String,Object> map = new HashMap<>();
      
      try{
          ObjectMapper objm=new ObjectMapper();
          nouv = objm.readValue(newR, Recherche.class);
          String sDate1=nouv.getDate1();
      String sDate2=nouv.getDate2();
      Integer ids=nouv.getIdstatut();
      Integer idt=nouv.getIdtype();
      
      System.out.println(ids+","+idt+","+sDate1+","+sDate2);
      
      List<Signalement> sign=new ArrayList<Signalement>();
          verifierTokenAdmin(authHeader, request);
          
          Integer idregion = Integer.parseInt(request.getAttribute("idregion").toString());
          
          if(sDate1!=null && sDate2!=null){
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);  
            Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);
                if(ids!=null){
                    //System.out.println("miditra");
                    if(idt!=null){
                        sign=repository1.getSign(idregion,ids,idt,date1,date2);
                    }
                    else{
                        sign=repository1.getSignDateStatut(idregion,ids,date1,date2);
                    }           
                }
                else{
                    if(idt!=null){
                        sign=repository1.getSignDateType(idregion,idt,date1,date2);
                    }
                    else{
                        sign=repository1.getSignDate(idregion,date1,date2);
                    }      
                }
          }
          else {
              
              if(ids!=null){
                    if(idt!=null){
                        sign=repository1.getSignSansDate(idregion,ids,idt);
                    }
                    else{
                        sign=repository1.getSignSansDateStatut(idregion,ids);
                    }           
                }
                else{
                     if(idt!=null){
                        sign=repository1.getSignSansDatetype(idregion,idt);
                    }
                    else{
                        sign=repository1.findByIdregion(idregion);
                    }      
                }
          }
            map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",sign);
                    
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
  }
  
  private Map<String,String> generateJWTToken(Adminregion user){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp+30000000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"token")
            .setIssuedAt(new Date(timestamp))
            .claim("id",user.getId())
            .claim("username",user.getUsername())
            .claim("mdp",user.getMdp())
            .claim("idregion",user.getIdregion())
            .compact();
        Map<String,String> map = new HashMap<>();
        map.put("message", "Loggin successfully");
        map.put("status", "200");
        map.put("datas", token);
        return map;
    }
    
  public static void verifierTokenAdmin(String authHeader,HttpServletRequest request)throws Exception{
        String[] authHeaderArr = authHeader.split("Bearer");
        System.err.println("--"+authHeaderArr[1]);
        if(authHeaderArr.length>1 && authHeaderArr[1]!=null) {
            String token = authHeaderArr[1];
            try {
                
                    Claims claims = Jwts.parser().setSigningKey("token")
                                    .parseClaimsJws(token).getBody();
                    request.setAttribute("id", Integer.parseInt(claims.get("id").toString()));
                    request.setAttribute("idregion", Integer.parseInt(claims.get("idregion").toString()));
                    //System.out.println(Integer.parseInt(claims.get("id").toString()));
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
                    // TODO: handle exception
                    throw new Exception("Token invalid/expired 1");
            }
        }else {
                throw new Exception("Token invalid/expired 2");
        }
    }
  
  
  @PutMapping("/signalement/{id}")
  ResponseEntity<Map<String,Object>> replaceSignalement(@RequestHeader(name = "Authorization") String authHeader,HttpServletRequest request,@RequestBody Statut newSignal,@PathVariable Integer id) {
        Map<String,Object> map = new HashMap<>();
      try{
          verifierTokenAdmin(authHeader, request);
          Statut statu= repository2.findByNomstatut(newSignal.getNomstatut());
    Signalement ha= repository1.findById(id)
      .map(signal -> {
        signal.setIdpersonne(signal.getIdpersonne());
        signal.setDateheure(signal.getDateheure());
        signal.setDesignation(signal.getDesignation());
        signal.setIdregion(signal.getIdregion());
        signal.setIdtypesignalement(signal.getIdtypesignalement());
        signal.setLatitude(signal.getLatitude());
        signal.setLongitude(signal.getLongitude());
        signal.setIdstatut(statu.getId());
        return repository1.save(signal);
      })
      .orElseGet(() -> {
        Signalement newSign = new Signalement();
        newSign.setId(id);
        newSign.setIdstatut(statu.getId());
        return repository1.save(newSign);
      });
            map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",ha);
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
  }
  public static void verifierMail(String mail) throws Exception{
        boolean valid = EmailValidator.getInstance().isValid(mail);
        if(valid==false){
            throw new Exception("Email non valide");
        }
    }
    
    public static void verifierMdp(String mail) throws Exception{
         
      //String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
      String pattern = "().{8,}";
      String[] charSearch = {"â","ä","à","ç","é","ê","ë","è","î","ï","ô","ö","û","ü","ù"};
      for (int i=0;i<charSearch.length;i++){
          if(mail.contains(charSearch[i])){
              throw new Exception("Mot de passe invalide(contient accents)");
          }
      }
      
      if(!mail.matches(pattern)){
          throw new Exception("Mot de passe invalide: moins de 8 caracteres");
      }
    }
}