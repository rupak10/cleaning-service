package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String id;
    private String cleaner;
    //normal cleaning, deep cleaning or kitchen cleaning
    private String cleaningType;
    //one time, two weeks or monthly cleaning
    //private String serviceType;
    private String cleaningDate;
    private String hour;
    private String totalPrice;

    private String status;
}
