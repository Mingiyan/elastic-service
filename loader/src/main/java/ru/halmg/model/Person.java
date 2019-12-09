package ru.halmg.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "person_tbl")
public class Person {

    @Id
    private String string;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "departmentId")
    private String departmentId;

    @Column(name = "positionId")
    private String positionId;
}
