package org.fmiplovdiv.carmanagementapi.service;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateGarageDTO;

import java.util.List;
import java.util.Optional;

public interface GarageService {


    List<ResponseGarageDTO> getAllGarages(String city);

    Optional<ResponseGarageDTO> findGarageById(Long id);

    ResponseGarageDTO createNewGarage(CreateGarageDTO garageDTO);

    ResponseGarageDTO updateGarage(Long id, UpdateGarageDTO updateGarageDTO);

    ResponseGarageDTO deleteGarage(Long id);
}
