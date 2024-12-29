package org.fmiplovdiv.carmanagementapi.model.dtos;


public class MonthlyRequestsReportDTO {
    private String yearMonth;

    private Long requests;

    public MonthlyRequestsReportDTO(String yearMonth, Long requests) {
        this.yearMonth = yearMonth;
        this.requests = requests;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Long getRequests() {
        return requests;
    }

    public void setRequests(Long requests) {
        this.requests = requests;
    }
}
