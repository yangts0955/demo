package com.epam.demo.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeVO {
    String name;
    String email;
    LocalDate birthday;
    String gender;
    String status;
}
