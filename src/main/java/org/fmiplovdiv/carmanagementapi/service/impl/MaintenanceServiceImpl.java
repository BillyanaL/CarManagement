package org.fmiplovdiv.carmanagementapi.service.impl;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateMaintenanceDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseMaintenanceDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateMaintenanceDTO;
import org.fmiplovdiv.carmanagementapi.model.entities.Car;
import org.fmiplovdiv.carmanagementapi.model.entities.Garage;
import org.fmiplovdiv.carmanagementapi.model.entities.Maintenance;
import org.fmiplovdiv.carmanagementapi.repository.CarRepository;
import org.fmiplovdiv.carmanagementapi.repository.GarageRepository;
import org.fmiplovdiv.carmanagementapi.repository.MaintenanceRepository;
import org.fmiplovdiv.carmanagementapi.service.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    private final GarageRepository garageRepository;

    private final CarRepository carRepository;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, GarageRepository garageRepository, CarRepository carRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.garageRepository = garageRepository;
        this.carRepository = carRepository;
    }


    public List<ResponseMaintenanceDTO> getAllMaintenance(Optional<Long> carId,
                                                          Optional<Long> garageId,
                                                          Optional<String> startDate,
                                                          Optional<String> endDate){


        LocalDate start = startDate.map(LocalDate::parse).orElse(null);
        LocalDate end = endDate.map(LocalDate::parse).orElse(null);

        // range validity check
        if (start != null && end != null && start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be before or equal to end date.");
        }

        List<Maintenance> maintenances = maintenanceRepository.findMaintenanceByFilters(
                carId.orElse(null),
                garageId.orElse(null),
                start,
                end
        );

        return maintenances.stream().map(this::mapMaintenanceToDTO).toList();

    }

    public Optional<ResponseMaintenanceDTO> findMaintenanceById(Long id){

        return maintenanceRepository.findById(id).map(this::mapMaintenanceToDTO);

    }

    public ResponseMaintenanceDTO createNewMaintenance(CreateMaintenanceDTO createDTO){

        LocalDate scheduledDate = LocalDate.parse(createDTO.getScheduledDate());

        if (!hasAvailableCapacity(createDTO.getGarageId(), scheduledDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Garage is at full capacity for the selected date");
        }

        Maintenance newMaintenance = this.mapDtoToMaintenance(createDTO);
        newMaintenance = maintenanceRepository.save(newMaintenance);

        return mapMaintenanceToDTO(newMaintenance);

    }


    public ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateDTO){

        Optional<Maintenance> existingMaintenance = this.maintenanceRepository.findById(id);

        if (existingMaintenance.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance not found");
        }

        LocalDate scheduledDate = LocalDate.parse(updateDTO.getScheduledDate());

        if (!hasAvailableCapacity(updateDTO.getGarageId(), scheduledDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Garage is at full capacity for the selected date");
        }

        Maintenance maintenance = existingMaintenance.get();
        maintenance.setServiceType(updateDTO.getServiceType());
        maintenance.setScheduledDate(scheduledDate);

        Car car = carRepository.findById(updateDTO.getCarId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found with ID: " + updateDTO.getCarId()));
        maintenance.setCar(car);

        Garage garage = garageRepository.findById(updateDTO.getGarageId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found with ID: " + updateDTO.getGarageId()));
        maintenance.setGarage(garage);

        maintenance = this.maintenanceRepository.save(maintenance);

        return this.mapMaintenanceToDTO(maintenance);
    }

    public ResponseMaintenanceDTO deleteMaintenance(Long id){

        Optional<Maintenance> existingMaintenance = this.maintenanceRepository.findById(id);

        if (existingMaintenance.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance not found");
        }

        Maintenance maintenance = existingMaintenance.get();

        this.maintenanceRepository.delete(maintenance);
        return this.mapMaintenanceToDTO(maintenance);
    }

    private boolean hasAvailableCapacity(Long garageId, LocalDate scheduledDate) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found"));

        long scheduledCount = maintenanceRepository.countByGarageIdAndScheduledDate(garageId, scheduledDate);

        return scheduledCount < garage.getCapacity();
    }

    private ResponseMaintenanceDTO mapMaintenanceToDTO(Maintenance maintenance){

        ResponseMaintenanceDTO dto = new ResponseMaintenanceDTO();

        dto.setId(maintenance.getId());
        dto.setCarId(maintenance.getCar().getId());
        dto.setCarName(maintenance.getCar().getMake());
        dto.setServiceType(maintenance.getServiceType());
        dto.setGarageId(maintenance.getGarage().getId());
        dto.setGarageName(maintenance.getGarage().getName());

        if (maintenance.getScheduledDate() != null) {
            String formattedDate = maintenance.getScheduledDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
       //     String formattedDate = maintenance.getScheduledDate().toString();
            dto.setScheduledDate(formattedDate);
        } else {
            dto.setScheduledDate(null);
        }

        return dto;

    }

    private Maintenance mapDtoToMaintenance(CreateMaintenanceDTO createMaintenanceDTO){


        Maintenance maintenance = new Maintenance();
        maintenance.setServiceType(createMaintenanceDTO.getServiceType());

        if (createMaintenanceDTO.getScheduledDate() != null) {
            maintenance.setScheduledDate(LocalDate.parse(createMaintenanceDTO.getScheduledDate()));
        } else {
            maintenance.setScheduledDate(null);
        }

        Car car = carRepository.findById(createMaintenanceDTO.getCarId()).orElse(null);
        Garage garage = garageRepository.findById(createMaintenanceDTO.getGarageId()).orElse(null);

        maintenance.setCar(car);
        maintenance.setGarage(garage);

        return maintenance;

    }
}
