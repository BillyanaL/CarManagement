package org.fmiplovdiv.carmanagementapi.repository;

import org.fmiplovdiv.carmanagementapi.model.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

//    @Query("SELECT c FROM Car c JOIN c.garages g WHERE g.id = :garageId")
//    List<Car> findByGarageId(@Param("garageId") Long garageId);
//
//    List<Car> findByMake(String make);
//
//    @Query("SELECT c FROM Car c WHERE c.productionYear BETWEEN :startYear AND :endYear")
//    List<Car> findByProductionYearBetween(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

    @Query("SELECT c FROM Car c " +
            "LEFT JOIN c.garages g " +
            "WHERE (:make IS NULL OR c.make = :make) " +
            "AND (:garageId IS NULL OR g.id = :garageId) " +
            "AND (:startYear IS NULL OR :endYear IS NULL OR c.productionYear BETWEEN :startYear AND :endYear)")
    List<Car> findCarsByFilters(
            @Param("make") String make,
            @Param("garageId") Long garageId,
            @Param("startYear") Integer startYear,
            @Param("endYear") Integer endYear
    );


}
