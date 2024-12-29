package org.fmiplovdiv.carmanagementapi.service.impl;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.GarageDailyAvailabilityReportDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.entities.Garage;
import org.fmiplovdiv.carmanagementapi.repository.GarageRepository;
import org.fmiplovdiv.carmanagementapi.service.GarageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;

    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public List<ResponseGarageDTO> getAllGarages(String city){

        List<Garage> garages;

        if (city != null && !city.isEmpty()) {
            garages = garageRepository.findByCity(city);
        } else {
            garages = garageRepository.findAll();
        }


        return garages.stream().map(this::mapGarageToDTO).toList();
    }

    public Optional<ResponseGarageDTO> findGarageById(Long id) {

        return garageRepository.findById(id).map(this::mapGarageToDTO);
    }

    public ResponseGarageDTO createNewGarage(CreateGarageDTO garageDTO){

        Garage newGarage = this.mapGarageDtoToGarage(garageDTO);
        newGarage = this.garageRepository.save(newGarage);

        return this.mapGarageToDTO(newGarage);

    }

    public ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO updateGarageDTO) {

        Optional<Garage> existingGarage = this.garageRepository.findById(id);

        if (existingGarage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found");
        }

        Garage garage = existingGarage.get();
        garage.setName(updateGarageDTO.getName());
        garage.setLocation(updateGarageDTO.getLocation());
        garage.setCity(updateGarageDTO.getCity());
        garage.setCapacity(updateGarageDTO.getCapacity());

        garage = this.garageRepository.save(garage);

        return this.mapGarageToDTO(garage);
    }

    public ResponseGarageDTO deleteGarage(Long id){

        Optional<Garage> existingGarage = this.garageRepository.findById(id);

        if (existingGarage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage not found");
        }

        Garage garage = existingGarage.get();

        this.garageRepository.delete(garage);

        return this.mapGarageToDTO(garage);

    }

    public List<GarageDailyAvailabilityReportDTO> getDailyAvailabilityReport(Long garageId, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if (start.isAfter(end)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be before or equal to end date.");
        }

        Integer garageCapacity = garageRepository.getGarageCapacity(garageId);
        List<Object[]> dailyRequests = garageRepository.getDailyRequests(garageId, start, end);

        return mapToAvailabilityReport(dailyRequests, garageCapacity);
    }

    private List<GarageDailyAvailabilityReportDTO> mapToAvailabilityReport(List<Object[]> dailyRequests, Integer garageCapacity) {
        List<GarageDailyAvailabilityReportDTO> reportList = new ArrayList<>();

        for (Object[] result : dailyRequests) {
            String date = result[0].toString();
            Integer requests = ((Number) result[1]).intValue();
            Integer availableCapacity = garageCapacity - requests;

            reportList.add(new GarageDailyAvailabilityReportDTO(date, requests, availableCapacity));
        }

        return reportList;
    }



    private ResponseGarageDTO mapGarageToDTO (Garage garage){

        return new ResponseGarageDTO(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
        );

    }

    private Garage mapGarageDtoToGarage (CreateGarageDTO newGarageDTO){

        return new Garage(
                newGarageDTO.getName(),
                newGarageDTO.getLocation(),
                newGarageDTO.getCity(),
                newGarageDTO.getCapacity()
        );


    }
}
