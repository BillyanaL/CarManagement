package org.fmiplovdiv.carmanagementapi.service;

import org.fmiplovdiv.carmanagementapi.model.dtos.*;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {

    List<ResponseMaintenanceDTO> getAllMaintenance(Optional<Long> carId,
                                                   Optional<Long> garageId,
                                                   Optional<String> startDate,
                                                   Optional<String> endDate);

    ResponseMaintenanceDTO createNewMaintenance(CreateMaintenanceDTO createDTO);

    Optional<ResponseMaintenanceDTO> findMaintenanceById(Long id);

    ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateDTO);
    ResponseMaintenanceDTO deleteMaintenance(Long id);
    List<MonthlyRequestsReportDTO> generateMonthlyReport(Long garageId, String startMonth, String endMonth);

}
