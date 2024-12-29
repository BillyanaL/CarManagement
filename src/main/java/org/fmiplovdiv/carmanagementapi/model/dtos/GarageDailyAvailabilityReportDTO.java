package org.fmiplovdiv.carmanagementapi.model.dtos;

public class GarageDailyAvailabilityReportDTO {

    private String date;
    private Integer requests;
    private Integer availableCapacity;

    public GarageDailyAvailabilityReportDTO(String date, Integer requests, Integer availableCapacity) {
        this.date = date;
        this.requests = requests;
        this.availableCapacity = availableCapacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRequests() {
        return requests;
    }

    public void setRequests(Integer requests) {
        this.requests = requests;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }
}
