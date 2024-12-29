package org.fmiplovdiv.carmanagementapi.web;

import org.fmiplovdiv.carmanagementapi.model.dtos.*;
import org.fmiplovdiv.carmanagementapi.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<ResponseMaintenanceDTO>> getAllMaintenances(@RequestParam(required = false) Long carId,
                                                                           @RequestParam(required = false) Long garageId,
                                                                           @RequestParam(required = false) String startDate,
                                                                           @RequestParam(required = false) String endDate) {

        return ResponseEntity.ok(maintenanceService.getAllMaintenance(Optional.ofNullable(carId),
                Optional.ofNullable(garageId),Optional.ofNullable(startDate),Optional.ofNullable(endDate)));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> getMaintenanceById(@PathVariable("id") Long id){

        Optional<ResponseMaintenanceDTO> theMaintenance = maintenanceService.findMaintenanceById(id);

        return theMaintenance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ResponseMaintenanceDTO> createMaintenance(@RequestBody CreateMaintenanceDTO createMaintenanceDTO){

        ResponseMaintenanceDTO responseDTO = this.maintenanceService.createNewMaintenance(createMaintenanceDTO);

        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> updateMaintenance(@PathVariable("id") Long id,
                                                                    @RequestBody UpdateMaintenanceDTO updateMaintenanceDTO) {

        ResponseMaintenanceDTO responseDTO = this.maintenanceService.updateMaintenance(id,updateMaintenanceDTO);

        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping({"/{id}"})
    public ResponseEntity<ResponseMaintenanceDTO> deleteMaintenance(@PathVariable("id") Long id){

        ResponseMaintenanceDTO responseDTO = this.maintenanceService.deleteMaintenance(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/monthlyRequestsReport")
    public ResponseEntity<List<MonthlyRequestsReportDTO>> getMonthlyReport(
            @RequestParam(required = false) Long garageId,
            @RequestParam(value = "startMonth",defaultValue = "2024-01") String startMonth,
            @RequestParam(value = "endMonth",defaultValue = "2024-12") String endMonth) {


        List<MonthlyRequestsReportDTO> responseDTO = maintenanceService.generateMonthlyReport(garageId, startMonth, endMonth);
        return ResponseEntity.ok(responseDTO);
    }







}
