package com.epam.demo.Entity;

import com.epam.demo.Entity.enumeration.GenderEnum;
import com.epam.demo.Entity.enumeration.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Table(indexes = {@Index(columnList = "name, gender, status")})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "name needed")
    String name;
    @NotBlank(message = "password needed")
    String password;
    @NotBlank(message = "email needed")
    @Email(message = "email format error")
    String email; //校验
    @NotNull(message = "birthday needed")
    LocalDate birthday;
    @ColumnDefault("0")
    @NotBlank(message = "gender needed")
    String gender;
    @ColumnDefault("0")
    String status;

//    public String getGender(){
//        return GenderEnum.toType(this.gender);
//    }
//
//    public String getStatus(){
//        return StatusEnum.toType(this.status);
//    }


}
