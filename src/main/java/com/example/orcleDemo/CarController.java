package com.example.orcleDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarRepository carRepository;

    @Autowired
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    @RequestMapping("v1")
    public int getCars(){
        return carRepository.getCarsCount(1);
    }

    @GetMapping
    @RequestMapping("v2")
    public String getAllCars(){
        /*
        This is the normal solution

        StoredProcedureQuery query = this.entityManager.createNamedStoredProcedureQuery("FIND_CAR_WITHIN_YEAR");
        query.setParameter("year_in", "1920");
        query.execute();
        String carName= (String)query.getOutputParameterValue("car_name");
            */

        /* the below solution is the optimzed one using spring-jpa */
        String carName=carRepository.findCardWithinYear("1920");
        return "The car is: "+carName;
    }

    @Autowired
    EntityManager entityManager;
    @GetMapping
    @RequestMapping("v3")
    public List<Car> getAllCars3(){
        StoredProcedureQuery proc = entityManager.createStoredProcedureQuery("FIND_CARS_AFTER_YEAR");
        proc.registerStoredProcedureParameter("MY_CURSER", Car.class, ParameterMode.REF_CURSOR);
        proc.execute();
        List<Object[]> objectList = proc.getResultList();
        List<Car> cars=new ArrayList<>();

        for (Object[] object : objectList) {
            cars.add(new Car(Long.parseLong(String.valueOf(object[0])),String.valueOf(object[1]), String.valueOf(object[2])));
        }
        return cars;
    }
}

/*
    create or replace FUNCTION GET_CARS_COUNT_FN
        (
                year_in IN Integer
        )
return Integer
        AS
        mycount INTEGER;
        BEGIN
        SELECT count(*)
        into mycount
        FROM car ;
        return mycount;
        END GET_CARS_COUNT_FN;
        */