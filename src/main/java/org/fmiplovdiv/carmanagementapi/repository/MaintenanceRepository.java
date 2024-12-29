package org.fmiplovdiv.carmanagementapi.repository;

import org.fmiplovdiv.carmanagementapi.model.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance,Long> {

    @Query("SELECT m FROM Maintenance m " +
            "WHERE (:carId IS NULL OR m.car.id = :carId) " +
            "AND (:garageId IS NULL OR m.garage.id = :garageId) " +
            "AND (:startDate IS NULL OR m.scheduledDate >= :startDate) " +
            "AND (:endDate IS NULL OR m.scheduledDate <= :endDate)")
    List<Maintenance> findMaintenanceByFilters(
            @Param("carId") Long carId,
            @Param("garageId") Long garageId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.garage.id = :garageId AND m.scheduledDate = :scheduledDate")
    long countByGarageIdAndScheduledDate(@Param("garageId") Long garageId, @Param("scheduledDate") LocalDate scheduledDate);

    @Query("SELECT FUNCTION('DATE_FORMAT', m.scheduledDate, '%Y-%m') AS yearMonth, COUNT(m) " +
            "FROM Maintenance m " +
            "WHERE m.garage.id = :garageId " +
            "AND m.scheduledDate >= :startDate AND m.scheduledDate <= :endDate " +
            "GROUP BY FUNCTION('DATE_FORMAT', m.scheduledDate, '%Y-%m')")
    List<Object[]> findMaintenanceStatsByMonth(@Param("garageId") Long garageId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);


}
