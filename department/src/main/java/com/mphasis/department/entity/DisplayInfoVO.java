package com.mphasis.department.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayInfoVO {

    private String displayMessage;
    private Long displayNumber;
    private Date displayDate;
    private double displayAmount;
    private LocalDateTime displayLocalDateTime;
    private String formattedMessage;
    private String formattedNumber;
    private String formattedDate;
    private String formattedCurrency;

}
