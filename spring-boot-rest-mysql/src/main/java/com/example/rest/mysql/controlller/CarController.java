package com.example.rest.mysql.controlller;

import com.example.rest.mysql.entity.Car;
import com.example.rest.mysql.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService service;

    // POST calls
    @PostMapping("/addCar")
    public Car addCar(@RequestBody Car car) {
        return service.saveCar(car);
    }

    @PostMapping("/addCars")
    public List<Car> addCars(@RequestBody List<Car> cars) {
        return service.saveCars(cars);
    }

    // GET calls
    @GetMapping("/cars")
    public List<Car> getAllproducts() {
        return service.getCars();
    }

    @GetMapping("/carById/{id}")
    public Car findCarById(@PathVariable int id) {
        return service.findCarById(id);
    }

    // path format like: http://localhost:xxxx/carWhere?price=..&gl=..
    @GetMapping("/carWhere")
    public List<Car> getCarsWherePriceIsHigher(@RequestParam double price, @RequestParam char gl) {
        return service.getCarsWherePriceIsHigher(price, gl);
    }

    @GetMapping("/cars/range")
    public List<Car> getCarsInRange(@RequestParam double from, @RequestParam double to) {
        return service.getCarsInRange(from, to);
    }

    @GetMapping("/cars/br")
    public List<Car> getCarsInRangeAndBrand(@RequestParam double from, @RequestParam double to,
                                            @RequestParam String brand) {
        return service.getCarsInRangeAndBrand(from, to, brand);
    }

    // PUT calls
    @PutMapping("/update")
    public Car updateCar(@RequestBody Car car) {
        return service.updateCar(car);
    }

    // DELETE calls
    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable int id) {
        return service.deleteCar(id);
    }
}