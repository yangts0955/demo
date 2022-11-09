package com.epam.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Where(clause = "status == '1'")
public class Role {

    @Id
    Long id;

    String name;

    String status;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Resource> resources;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Employee> employees;
}
