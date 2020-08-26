package com.example.rest.mysql.repository;

import com.example.rest.mysql.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}