package org.fmiplovdiv.carmanagementapi.repository;

import org.fmiplovdiv.carmanagementapi.model.entities.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage,Long> {


    List<Garage> findByCity(String city);
    List<Garage> findAll();



}
