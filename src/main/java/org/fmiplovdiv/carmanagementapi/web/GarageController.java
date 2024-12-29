package org.fmiplovdiv.carmanagementapi.web;

import org.fmiplovdiv.carmanagementapi.model.dtos.CreateGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.GarageDailyAvailabilityReportDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.ResponseGarageDTO;
import org.fmiplovdiv.carmanagementapi.model.dtos.UpdateGarageDTO;
import org.fmiplovdiv.carmanagementapi.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/garages")
public class GarageController {


    @Autowired
    private GarageService garageService;


    @GetMapping()
    public ResponseEntity<List<ResponseGarageDTO>> getGarages(@RequestParam(value = "city",required = false) String city) {


        return ResponseEntity.ok(garageService.getAllGarages(city));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> getGarageById(@PathVariable("id") Long id){

        Optional<ResponseGarageDTO> theGarage = garageService.findGarageById(id);

        return theGarage.
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<ResponseGarageDTO> createGarage(@RequestBody CreateGarageDTO garageDTO){

        ResponseGarageDTO responseDTO = this.garageService.createNewGarage(garageDTO);

        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> updateGarage(@PathVariable("id") Long id ,
                                                          @RequestBody UpdateGarageDTO updateGarageDTO){
        ResponseGarageDTO responseDTO = this.garageService.updateGarage(id, updateGarageDTO);

        return ResponseEntity.ok(responseDTO);

    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<ResponseGarageDTO> deleteGarage(@PathVariable("id") Long id){

        ResponseGarageDTO responseDTO = this.garageService.deleteGarage(id);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/dailyAvailabilityReport")
    public List<GarageDailyAvailabilityReportDTO> getGarageAvailabilityReport(@RequestParam Long garageId,
                                                                              @RequestParam String startDate,
                                                                              @RequestParam String endDate) {

        return garageService.getDailyAvailabilityReport(garageId, startDate, endDate);
    }

}
