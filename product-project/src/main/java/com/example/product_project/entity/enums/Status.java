package com.example.product_project.entity.enums;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
//@RequiredArgsConstructor
//@NoArgsConstructor
@Getter
public enum Status {
    SELLABLE, UNFULFILLABLE, INBOUND

//    @Enumerated(EnumType.STRING)
//    private Status status;
}
