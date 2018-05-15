package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.EnteredSearchDTO;
import com.agoldberg.hercules.dto.ProcessedRevenueDataAndSummaryDTO;
import com.agoldberg.hercules.event.RevenueEnteredEvent;

public interface ProcessedRevenueService {
    ProcessedRevenueDataAndSummaryDTO getProcessedRevenues(EnteredSearchDTO dto);

    void onApplicationEvent(RevenueEnteredEvent revenueEnteredEvent);
}
