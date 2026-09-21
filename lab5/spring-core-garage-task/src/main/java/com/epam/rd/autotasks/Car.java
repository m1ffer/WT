package com.epam.rd.autotasks;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class Car {
    private String model;
    private String year;
    private Owner owner;
}
