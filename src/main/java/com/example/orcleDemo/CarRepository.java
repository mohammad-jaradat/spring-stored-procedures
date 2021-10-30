package com.example.orcleDemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {


    @Query(value = "SELECT GET_CARS_COUNT_FN(:year_in) FROM DUAL", nativeQuery = true)
    int getCarsCount(@Param("year_in") Integer year_in);


    @Procedure(procedureName = Car.NamedQuery_FINDCAR, outputParameterName = "car_name")
    String findCardWithinYear(@Param("year_in") String year_in);

}
