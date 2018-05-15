package com.agoldberg.hercules.dto;

import java.util.List;

public class ProcessedRevenueDataAndSummaryDTO {
    List<ProcessedRevenueDTO> data;
    ProcessedRevenueDTO summary;

    public ProcessedRevenueDataAndSummaryDTO(List<ProcessedRevenueDTO> data, ProcessedRevenueDTO summary) {
        this.data = data;
        this.summary = summary;
    }

    public List<ProcessedRevenueDTO> getData() {
        return data;
    }

    public void setData(List<ProcessedRevenueDTO> data) {
        this.data = data;
    }

    public ProcessedRevenueDTO getSummary() {
        return summary;
    }

    public void setSummary(ProcessedRevenueDTO summary) {
        this.summary = summary;
    }
}
