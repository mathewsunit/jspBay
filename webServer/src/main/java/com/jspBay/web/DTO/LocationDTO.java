package com.jspBay.web.DTO;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by sunit on 3/19/17.
 */

public class LocationDTO {

    @NotNull
    private Date lastDate;
    @NotNull
    private Double lattitude;
    @NotNull
    private Double longitude;
    @NotNull
    private String city;
    @NotNull
    private String region;
    @NotNull
    private String user;

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public LocationDTO(Double lattitude, Double longitude, String city, String region, String user, Date date) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.city = city;
        this.region = region;
        this.user = user;
        this.lastDate = date;
    }

    public LocationDTO(Double lattitude, Double longitude, String city, String region, String user) {
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.city = city;
        this.region = region;
        this.user = user;
    }

    public LocationDTO(String user) {
        this.user = user;
    }

    public LocationDTO(){

    }
}
