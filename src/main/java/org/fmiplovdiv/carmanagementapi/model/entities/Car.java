package org.fmiplovdiv.carmanagementapi.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;

    private String model;

    private Integer productionYear;

    private String licensePlate;

    @ManyToMany
    private Set<Garage> garages;

    public Car() {

        this.garages = new HashSet<>();
    }

    public Car(Long id, String make, String model, Integer productionYear, String licensePlate, Set<Garage> garages) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;

        if (garages == null){

            this.garages = new HashSet<>();
        }else {

            this.garages = garages;
        }
    }

    public Car(String make, String model, Integer productionYear, String licensePlate, Set<Garage> garages) {
        this.make = make;
        this.model = model;
        this.productionYear = productionYear;
        this.licensePlate = licensePlate;

        if (garages == null){

            this.garages = new HashSet<>();

        }else {

            this.garages = garages;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Set<Garage> getGarages() {
        return garages;
    }

    public void setGarages(Set<Garage> garages) {
        this.garages = garages;
    }
}
