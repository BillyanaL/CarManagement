package org.fmiplovdiv.carmanagementapi.service.impl;

import jakarta.transaction.Transactional;
import org.fmiplovdiv.carmanagementapi.model.dtos.CreateCarDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseCarDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateCarDTO;
import org.fmiplovdiv.carmanagementapi.model.entities.Car;
import org.fmiplovdiv.carmanagementapi.model.entities.Garage;
import org.fmiplovdiv.carmanagementapi.repository.CarRepository;
import org.fmiplovdiv.carmanagementapi.repository.GarageRepository;
import org.fmiplovdiv.carmanagementapi.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CarServiceImpl implements CarService {


    private final GarageRepository garageRepository;

    private final CarRepository carRepository;

    public CarServiceImpl(GarageRepository garageRepository, CarRepository carRepository) {
        this.garageRepository = garageRepository;
        this.carRepository = carRepository;
    }

    @Override
    public List<ResponseCarDTO> getAllCars(String make, Long garageId, Integer startYear, Integer endYear) {
        List<Car> cars = carRepository.findCarsByFilters(make, garageId, startYear, endYear);
        return cars.stream().map(this::mapCarToDTO).toList();
    }

    public Optional<ResponseCarDTO> findCarById(Long id){

        return carRepository.findById(id).map(this::mapCarToDTO);
    }
    public ResponseCarDTO createNewCar(CreateCarDTO carDTO){

        Car newCar = this.mapCarDtoToCar(carDTO);
        newCar = this.carRepository.save(newCar);

        return this.mapCarToDTO(newCar);
    }

    public ResponseCarDTO updateCar(Long id, UpdateCarDTO updateCarDTO) {
        Optional<Car> existingCar = this.carRepository.findById(id);

        if (existingCar.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }

        Car car = existingCar.get();
        car.setMake(updateCarDTO.getMake());
        car.setModel(updateCarDTO.getModel());
        car.setLicensePlate(updateCarDTO.getLicensePlate());
        car.setProductionYear(updateCarDTO.getProductionYear());

        if (updateCarDTO.getGarageIds() != null) {
            Set<Garage> garages = new HashSet<>();
            for (Long garageId : updateCarDTO.getGarageIds()) {
                Optional<Garage> garageOptional = garageRepository.findById(garageId);
                if (garageOptional.isPresent()) {
                    garages.add(garageOptional.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Garage with ID " + garageId + " not found");
                }
            }
            car.setGarages(garages);

        }

        car = this.carRepository.save(car);

        return this.mapCarToDTO(car);

    }

    @Transactional
    public ResponseCarDTO deleteCar(Long id){

        Optional<Car> existingCar = this.carRepository.findById(id);

        if (existingCar.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found");
        }

        Car car = existingCar.get();

        this.carRepository.delete(car);

        return this.mapCarToDTO(car);

    }

    public ResponseCarDTO mapCarToDTO (Car car){


        Set<ResponseGarageDTO> garageDTOs = new HashSet<>();
        if (car.getGarages() != null) {

            for (Garage garage : car.getGarages()) {
                garageDTOs.add(new ResponseGarageDTO(
                        garage.getId(),
                        garage.getName(),
                        garage.getLocation(),
                        garage.getCity(),
                        garage.getCapacity()
                ));
            }
        }


        return new ResponseCarDTO(
                car.getId(),
                car.getMake(),
                car.getModel(),
                car.getProductionYear(),
                car.getLicensePlate(),
                garageDTOs
        );
    }

    private Car mapCarDtoToCar(CreateCarDTO carDTO){

        Set<Garage> garages = new HashSet<>();

        if (carDTO.getGarageIds() != null) {

            for (Long garageId : carDTO.getGarageIds()) {
                Optional<Garage> garageOptional = garageRepository.findById(garageId);

                if (garageOptional.isPresent()) {
                    garages.add(garageOptional.get());
                } else {

                    throw new RuntimeException("Garage with ID " + garageId + " not found");
                }
            }
        }

        return new Car(

                carDTO.getMake(),
                carDTO.getModel(),
                carDTO.getProductionYear(),
                carDTO.getLicensePlate(),
                garages
        );


    }


}
