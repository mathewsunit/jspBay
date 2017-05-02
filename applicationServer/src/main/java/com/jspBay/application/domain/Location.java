package com.jspBay.application.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sunit on 3/19/17.
 */

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @Column(name = "lattitude", nullable = false)
    private Double lattitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "lastDate", nullable = true)
    private Date lastDate;

    @Column(name = "date", nullable = false)
    private Date date;

    public Location(User user, Double lattitude, Double longitude, String city, String region,Date lastDate, Date date) {
        this.user = user;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.city = city;
        this.region = region;
        this.lastDate = lastDate;
        this.date = date;
    }

    public Location(Long id, User user, Double lattitude, Double longitude, String city, String region,Date lastDate, Date date) {
        this.id = id;
        this.user = user;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.city = city;
        this.region = region;
        this.lastDate = lastDate;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location(){}

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
