package com.epam.demo.dto;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EmployeeDto implements Serializable {
    String name;
    String password;

    String email;
    LocalDate birthday;
    String gender;
    String status;
}
