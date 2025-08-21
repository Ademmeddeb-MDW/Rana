package com.example.Lumiere.dto;

import lombok.Data;

@Data
public class DashboardStatsDto {
    private long totalUsers;
    private long totalDeliveryPersons;
    private long totalInventories;
    private long totalArticles;
    private long activeUsers;
    private long activeDeliveryPersons;
    private long activeInventories;
    private long activeArticles;
}