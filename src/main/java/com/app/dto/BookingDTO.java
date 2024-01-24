package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String cleaner;
    private String cleaningType; //normal cleaning, deep cleaning or kitchen cleaning
    private String serviceType; //one time, two weeks or monthly cleaning

    //applicable for one time cleaning
    private String cleaningDate;
    private String cleaningTime;
}
