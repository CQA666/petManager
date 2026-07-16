package com.pet.petmanager.VO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsVO {
    private Long animalCount;
    private Long availableCount;
    private Long userCount;
    private Long newUserCount;
    private Long adoptCount;
    private Long monthAdoptCount;
    private Long orderCount;
    private BigDecimal totalAmount;
    private Long fosterCount;
    private Long currentFosterCount;
}