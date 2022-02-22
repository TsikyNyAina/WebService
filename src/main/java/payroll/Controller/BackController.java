/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

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
import javax.servlet.http.HttpServletRequest;
import model.Allsignal;
import model.Stat;
import org.apache.commons.validator.routines.EmailValidator;
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
//import static payroll.Controller.FrontController.verifierTokenAdmin;
import payroll.Entity.Admin;
import payroll.Entity.Adminregion;
import payroll.Entity.Region;
import payroll.Entity.Signalement;
import payroll.Entity.Statut;
import payroll.Entity.Typesignalement;

/**
 *
 * @author aro
 */
@CrossOrigin
@RestController
public class BackController {
        private final AdminRepository repository;
        private final SignalRepository repository1;
        private final StatutRepository repository2;
        private final BackRepository repository3;
        private final RegionRepository repository4;
        private final TypeSignalRepository repository5;
        

        BackController(AdminRepository repository,SignalRepository repository1,StatutRepository repository2,BackRepository repository3,RegionRepository repository4,TypeSignalRepository repository5){
            this.repository=repository;
            this.repository1 = repository1;
            this.repository2 = repository2;
            this.repository3 = repository3;
            this.repository4 = repository4;
            this.repository5 = repository5;
        }
        
        @PostMapping("/backLogin")
    ResponseEntity<Map<String,String>> newSignal(@RequestBody Admin newSignal) {
        
        Map<String,String> map = new HashMap<>();
        try {
                verifierMail(newSignal.getUsername());
                verifierMdp(newSignal.getMdp());
                Admin admin= repository.findByUsernameAndMdp(newSignal.getUsername(), newSignal.getMdp());
                map=generateJWTToken(admin);
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
  }
    
    
    
    @GetMapping("/regionstat")
  ResponseEntity<Map<String,Object>> statu(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        List<Stat> liste= repository3.regionstat();
        Integer[] nbregion=new Stat().separ(liste);
        String[] nomregion=new Stat().separnom(liste);
        
        List<Stat> liste1= repository3.statutstat();
        Integer[] nbstatut=new Stat().separ(liste1);
        String[] nomstatut=new Stat().separnom(liste1);
        
        List<Stat> liste2= repository3.typestat();
        Integer[] nbtype=new Stat().separ(liste2);
        String[] nomtype=new Stat().separnom(liste2);
        
        map.put("message","liste region stat");
                    map.put("status", "200");
                    map.put("nbregion",nbregion);
                    map.put("nomregion",nomregion);
                    map.put("nbstatut",nbstatut);
                    map.put("nomstatut",nomstatut);
                    map.put("nbtype",nbtype);
                    map.put("nomtype",nomtype);
                    return new ResponseEntity<>(map,HttpStatus.OK);
      }catch(Exception e){
          map.put("status", "400");
          map.put("message", e.getMessage());
          return new ResponseEntity<>(map,HttpStatus.OK);
      }
  }
    
    private Map<String,String> generateJWTToken(Admin user){
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
              throw new Exception("Mot de passe invalide");
          }
      }
      
      if(!mail.matches(pattern)){
          throw new Exception("Mot de passe invalide");
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
                    // TODO: handle exception
                    throw new Exception("Token invalid/expired 1");
            }
        }else {
                throw new Exception("Token invalid/expired 2");
        }
    }
    
    @PutMapping("/affecter/{id}")
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
    
    @GetMapping("/signals")
  ResponseEntity<Map<String,Object>> listenouveau(HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        List<Signalement> liste= repository1.findByIdstatut(1);
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

  @GetMapping("/types")
  ResponseEntity<Map<String,Object>> listeTypes(HttpServletRequest request) {
      Map<String,Object> map = new HashMap<>();
      try{
        //verifierTokenAdmin(authHeader, request);
        List<Typesignalement> liste= repository5.findAll();
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
  
  @GetMapping("/status")
  ResponseEntity<Map<String,Object>> listeStatus(HttpServletRequest request) {
      Map<String,Object> map = new HashMap<>();
      try{
        //verifierTokenAdmin(authHeader, request);
        List<Statut> liste= repository2.findAll();
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
  
  
  
  @GetMapping("/region/{id}")
  String nomRegion(@PathVariable Integer id,HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        return repository4.getOne(id).getNom();
      }catch(Exception e){
          return null;
      }
  }
  
  @GetMapping("/statut/{id}")
  String nomStatut(@PathVariable Integer id,HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        return repository2.getOne(id).getNomstatut();
      }catch(Exception e){
          return null;
      }
  }
  
  @GetMapping("/type/{id}")
  String nomType(@PathVariable Integer id,HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
      Map<String,Object> map = new HashMap<>();
      try{
        verifierTokenAdmin(authHeader, request);
        return repository5.getOne(id).getTypesignalement();
      }catch(Exception e){
          return null;
      }
  }
}