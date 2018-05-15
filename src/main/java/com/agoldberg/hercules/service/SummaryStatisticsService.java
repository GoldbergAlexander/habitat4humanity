package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.SummarySearchDTO;
import com.agoldberg.hercules.dto.SummaryStatsDTO;

public interface SummaryStatisticsService {
    SummaryStatsDTO getSummaryStats(SummarySearchDTO search);
}
