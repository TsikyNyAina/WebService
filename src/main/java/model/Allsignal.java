/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author ITU
 */
public class Allsignal {

    Integer id;
    String nompersonne;
    String nomregion;
    Date dateheure;
    String designation;
    String nomstatut;
    String typesignalement;
    @Basic
    byte[] image;
    String latitude;
    String longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNompersonne() {
        return nompersonne;
    }

    public void setNompersonne(String nompersonne) {
        this.nompersonne = nompersonne;
    }

    public Date getDateheure() {
        return dateheure;
    }

    public void setDateheure(Date dateheure) {
        this.dateheure = dateheure;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNomstatut() {
        return nomstatut;
    }

    public void setNomstatut(String nomstatut) {
        this.nomstatut = nomstatut;
    }

    public String getTypesignalement() {
        return typesignalement;
    }

    public void setTypesignalement(String typesignalement) {
        this.typesignalement = typesignalement;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNomregion() {
        return nomregion;
    }

    public void setNomregion(String nomregion) {
        this.nomregion = nomregion;
    }

    
    
    public Allsignal() {
    }

    public Allsignal(Integer id, String nompersonne, String nomregion, Date dateheure, String designation, String nomstatut, String typesignalement, byte[] image, String latitude, String longitude) {
        this.id = id;
        this.nompersonne = nompersonne;
        this.nomregion = nomregion;
        this.dateheure = dateheure;
        this.designation = designation;
        this.nomstatut = nomstatut;
        this.typesignalement = typesignalement;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    
    
    
}
