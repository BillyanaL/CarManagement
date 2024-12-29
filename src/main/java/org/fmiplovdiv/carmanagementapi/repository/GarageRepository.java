package org.fmiplovdiv.carmanagementapi.repository;

import org.fmiplovdiv.carmanagementapi.model.entities.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage,Long> {

    List<Garage> findByCity(String city);
    List<Garage> findAll();

    @Query("SELECT m.scheduledDate AS date, COUNT(m) AS totalRequests " +
            "FROM Maintenance m " +
            "WHERE m.garage.id = :garageId AND m.scheduledDate BETWEEN :startDate AND :endDate " +
            "GROUP BY m.scheduledDate")
    List<Object[]> getDailyRequests(
            @Param("garageId") Long garageId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT g.capacity FROM Garage g WHERE g.id = :garageId")
    Integer getGarageCapacity(@Param("garageId") Long garageId);



}
