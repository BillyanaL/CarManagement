package org.fmiplovdiv.carmanagementapi.service;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateCarDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseCarDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateCarDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<ResponseCarDTO> getAllCars(String make, Long garageId, Integer startYear, Integer endYear);
    Optional<ResponseCarDTO> findCarById(Long id);

    ResponseCarDTO createNewCar(CreateCarDTO carDTO);

    ResponseCarDTO updateCar(Long id, UpdateCarDTO updateCarDTO);

    ResponseCarDTO deleteCar(Long id);
}
