package org.fmiplovdiv.carmanagementapi.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Car car;

    private String serviceType;
    private LocalDate scheduledDate;
    @ManyToOne
    private Garage garage;

    public Maintenance() {
    }

    public Maintenance(Long id, Car car, String serviceType, LocalDate scheduledDate, Garage garage) {
        this.id = id;
        this.car = car;
        this.serviceType = serviceType;
        this.scheduledDate = scheduledDate;
        this.garage = garage;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
