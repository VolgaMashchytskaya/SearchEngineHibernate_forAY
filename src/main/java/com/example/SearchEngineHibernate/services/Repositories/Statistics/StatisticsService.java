package com.example.SearchEngineHibernate.services.Repositories.Statistics;

import com.example.SearchEngineHibernate.dto.statistics.StatisticsResponse;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

public interface StatisticsService {
    StatisticsResponse getStatistics() throws SQLException;
}
