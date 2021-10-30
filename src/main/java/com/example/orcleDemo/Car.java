package com.example.orcleDemo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = Car.NamedQuery_FINDCAR,
                procedureName = Car.NamedQuery_FINDCAR,
                parameters = {
                        @StoredProcedureParameter(type = String.class,name = "year_in", mode = ParameterMode.IN),
                        @StoredProcedureParameter(type = String.class, name = "car_name",mode = ParameterMode.OUT)
                }
        )})

public class Car implements Serializable {
    public static final String NamedQuery_FINDCAR = "FIND_CAR_WITHIN_YEAR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String model;

    @Column
    private String year;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Car(long id, String model, String year) {
        this.id = id;
        this.model = model;
        this.year = year;
    }
}
