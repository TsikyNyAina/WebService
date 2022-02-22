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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static payroll.Controller.FrontController.verifierTokenAdmin;
import payroll.Entity.Personne;
import payroll.Entity.Region;
import payroll.Entity.Signalement;
/**
 *
 * @author Dell
 */
@CrossOrigin
@RestController
public class MobileController {
    @Autowired
    private final PersonneRepository repository;
    @Autowired
    private final SignalRepository repository1;
    @Autowired
    private final RegionRepository repository2;
    @Autowired
    private final StatutRepository repository3;
    @Autowired
    private final TypeSignalRepository repository4;
    
  MobileController(PersonneRepository repository,SignalRepository repository1,RegionRepository repository2,StatutRepository repository3,TypeSignalRepository repository4) {
    this.repository = repository;
    this.repository1 = repository1;
    this.repository2 = repository2;
    this.repository3 = repository3;
    this.repository4 = repository4;
    
  }
  @PostMapping("/newPersonne")
  ResponseEntity<Map<String,String>> newPersonne(@RequestBody Personne newPersonne) {
      Personne p = new Personne();
      Map<String,String> map = new HashMap<>();
      try{
          verifierMail(newPersonne.getUsername());
          verifierMdp(newPersonne.getMdp());
          p=repository.save(newPersonne);
          map.put("message", "Inscription reussi");
          map.put("status", "200");
          return new ResponseEntity<>(map, HttpStatus.OK);
      }catch(Exception e)
      {
          map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
      }
  }

  @GetMapping("/regions/{id}")
  ResponseEntity<Map<String,Object>> nomRegion(@PathVariable Integer id,HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        String p= repository2.getOne(id).getNom();
        map.put("region",p);
        return new ResponseEntity<>(map, HttpStatus.OK);
      }catch(Exception e){
          map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
      }
  }

  @GetMapping("/statuts/{id}")
  ResponseEntity<Map<String,Object>> nomStatut(@PathVariable Integer id,HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        String p= repository3.getOne(id).getNomstatut();
        map.put("region",p);
        return new ResponseEntity<>(map, HttpStatus.OK);
      }catch(Exception e){
          map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
      }
  }

  @GetMapping("/types/{id}")
  ResponseEntity<Map<String,Object>> nomType(@PathVariable Integer id,HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        String p= repository4.getOne(id).getTypesignalement();
        map.put("region",p);
        return new ResponseEntity<>(map, HttpStatus.OK);
      }catch(Exception e){
          map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
      }
  }

  @PostMapping("/MobileLogin")
  ResponseEntity<Map<String,String>> newSignal(@RequestBody Personne newSignal) {
    Map<String,String> map = new HashMap<>();
    try {
        verifierMail(newSignal.getUsername());
        verifierMdp(newSignal.getMdp());
                Personne log= repository.findByUsernameAndMdp(newSignal.getUsername(), newSignal.getMdp());
                map=generateJWTToken(log);
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
  }
  
  private Map<String,String> generateJWTToken(Personne user){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp+30000000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"token")
            .setIssuedAt(new Date(timestamp))
            .claim("id",user.getId())
            .claim("username",user.getUsername())
            .claim("mdp",user.getMdp())
            .compact();
        Map<String,String> map = new HashMap<>();
        map.put("message", "Loggin successfully");
        map.put("status", "200");
        map.put("datas", token);
        return map;
   }
  
  @PostMapping("/newsignal")
  ResponseEntity<Map<String,Object>> newSignal(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader,@RequestParam MultipartFile multipartImage,@RequestParam String newSignal) throws Exception {
    Map<String,Object> map = new HashMap<>();
    Signalement nouv = new Signalement();
    
      try{
        verifierTokenAdmin(authHeader, request);
        ObjectMapper objm=new ObjectMapper();
        nouv = objm.readValue(newSignal, Signalement.class);
        nouv.setIdpersonne(Integer.parseInt(request.getAttribute("id").toString()));
        nouv.setDateheure(new Date());
        nouv.setImage(multipartImage.getBytes());
        System.out.println(multipartImage.getBytes());
        Signalement liste= repository1.save(nouv);
        map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
    }
    catch(Exception exce){
      map.put("status", "400");
          map.put("message", exce.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
    }
  }
  
  @GetMapping("/signalss")
  ResponseEntity<Map<String,Object>> listes(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) throws Exception {
    Map<String,Object> map = new HashMap<>();
    Signalement nouv = new Signalement();
    
      try{
        verifierTokenAdmin(authHeader, request);
        ObjectMapper objm=new ObjectMapper();
        List<Signalement> liste= repository1.findByIdpersonne(Integer.parseInt(request.getAttribute("id").toString()));
        map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
    }
    catch(Exception exce){
      map.put("status", "400");
          map.put("message", exce.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
    }
  }
  
  @GetMapping("/regions")
  ResponseEntity<Map<String,Object>> regionray(HttpServletRequest request) {
      Map<String,Object> map = new HashMap<>();
      try{
          //verifierTokenAdmin(authHeader, request);
          
            List<Region> liste= repository2.findAll();
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
  
  public static void verifierTokenAdmin(String authHeader,HttpServletRequest request)throws Exception{
        String[] authHeaderArr = authHeader.split("Bearer");
        System.err.println("--"+authHeaderArr[1]);
        if(authHeaderArr.length>1 && authHeaderArr[1]!=null) {
            String token = authHeaderArr[1];
            try {
                
                    Claims claims = Jwts.parser().setSigningKey("token")
                                    .parseClaimsJws(token).getBody();
                    request.setAttribute("id", Integer.parseInt(claims.get("id").toString()));
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
                    throw new Exception("Token invalid/expired 1");
            }
        }else {
                throw new Exception("Token invalid/expired 2");
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
