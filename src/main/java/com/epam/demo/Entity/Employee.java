package com.epam.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert
@Table(indexes = {@Index(columnList = "name, gender, status")}, uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String password;

    String email;
    LocalDate birthday;
    String gender;
    String status;

}
