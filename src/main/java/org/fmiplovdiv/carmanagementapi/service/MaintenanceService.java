package org.fmiplovdiv.carmanagementapi.service;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateMaintenanceDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseMaintenanceDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateMaintenanceDTO;

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
}
