package org.fmiplovdiv.carmanagementapi.web;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateCarDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseCarDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateCarDTO;
import org.fmiplovdiv.carmanagementapi.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<ResponseCarDTO>> getCars(@RequestParam(required = false) String carMake,
                                                        @RequestParam(required = false) Long garageId,
                                                        @RequestParam(required = false) Integer fromYear,
                                                        @RequestParam(required = false) Integer toYear){

        return ResponseEntity.ok(carService.getAllCars(carMake,garageId,fromYear,toYear));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> getCarById(@PathVariable("id") Long id){

        Optional<ResponseCarDTO> theCar = carService.findCarById(id);

        return theCar.
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping()
    public ResponseEntity<ResponseCarDTO> createCar (@RequestBody CreateCarDTO carDTO){

        ResponseCarDTO responseDTO = this.carService.createNewCar(carDTO);

        return ResponseEntity.ok(responseDTO);


    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> updateCar(@PathVariable("id") Long id,
                                                    @RequestBody UpdateCarDTO updateCarDTO){
        ResponseCarDTO responseDTO = this.carService.updateCar(id,updateCarDTO);

        return ResponseEntity.ok(responseDTO);

    }


    @DeleteMapping({"/{id}"})
    public ResponseEntity<ResponseCarDTO> deleteCar(@PathVariable("id") Long id){

        ResponseCarDTO responseDTO = this.carService.deleteCar(id);

        return ResponseEntity.ok(responseDTO);
    }



}
