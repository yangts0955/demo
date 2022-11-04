package com.epam.demo.vo;

import com.epam.demo.Entity.Employee;
import com.epam.demo.Entity.enumeration.GenderEnum;
import com.epam.demo.Entity.enumeration.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeVO {
    Long id;
    String name;
    String email;
    LocalDate birthday;
    String gender;
    String status;

    public String getGender(){
        return GenderEnum.toType(this.gender);
    }

    public String getStatus(){
        return StatusEnum.toType(this.status).toString();
    }
}
