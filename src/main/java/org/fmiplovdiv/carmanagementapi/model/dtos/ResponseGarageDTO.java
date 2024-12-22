package org.fmiplovdiv.carmanagementapi.model.dtos;

public class ResponseGarageDTO {

    private Long id;
    private String name;
    private String location;
    private String city;
    private Integer capacity;

    public ResponseGarageDTO(Long id, String name, String location, String city, Integer capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.city = city;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
