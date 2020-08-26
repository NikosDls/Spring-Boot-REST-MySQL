package com.example.rest.mysql.service;

import com.example.rest.mysql.entity.Car;
import com.example.rest.mysql.repository.CarRepository;
import com.example.rest.mysql.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    // save a single car
    public Car saveCar(Car car) {
        return repository.save(car);
    }

    // save multiple cars
    public List<Car> saveCars(List<Car> cars) {
        return repository.saveAll(cars);
    }

    // get a single car by id
    public Car findCarById(int id) {
        return repository.findById(id).orElse(null);
    }

    /*
    // get cars by brand
    public List<Car> getCar(String brand) {
        return repository.findByName(brand);
    }
    */

    // get all cars
    public List<Car> getCars() {
        return repository.findAll();
    }

    // get all car where price is higher than ..
    public List<Car> getCarsWherePriceIsHigher(double price, char gl) {
        // open the session
        Session session = HibernateUtil.getSessionFactory().openSession();

        // query with criteria builder
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Car> cr = cb.createQuery(Car.class);
        Root<Car> root = cr.from(Car.class);

        // greater than
        if (gl == 'g') {
            cr.select(root).where(cb.gt(root.get("price"), price));
        } else {
            cr.select(root).where(cb.lt(root.get("price"), price));
        }

        Query query = session.createQuery(cr);
        List<Car> results = query.getResultList();

        // close the session
        session.close();

        return results;
    }

    public List<Car> getCarsInRange(double from, double to) {
        // open the session
        Session session = HibernateUtil.getSessionFactory().openSession();

        // query with criteria builder
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Car> cr = cb.createQuery(Car.class);
        Root<Car> root = cr.from(Car.class);

        cr.select(root).where(cb.between(root.get("price"), from, to));

        Query query = session.createQuery(cr);
        List<Car> results = query.getResultList();

        // close the session
        session.close();

        return results;
    }

    public List<Car> getCarsInRangeAndBrand(double from, double to, String brand) {
        // open the session
        Session session = HibernateUtil.getSessionFactory().openSession();

        // query with criteria builder
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Car> cr = cb.createQuery(Car.class);
        Root<Car> root = cr.from(Car.class);

        // creating complex query
        // get all cars between in price range and where the name is like
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.between(root.get("price"), from, to);
        predicates[1] = cb.equal(root.get("brand"), brand);
        cr.select(root).where(predicates);

        Query query = session.createQuery(cr);
        List<Car> results = query.getResultList();

        // close the session
        session.close();

        return results;
    }

    // delete car by id
    public String deleteCar(int id) {
        repository.deleteById(id);

        return "\nCar with id " + id + " deleted!\n";
    }

    // update car
    public Car updateCar(Car car) {
        Car carBeforeUpdate = repository.findById(car.getId()).orElse(null);
        carBeforeUpdate.setBrand(car.getBrand());
        carBeforeUpdate.setModel(car.getModel());
        carBeforeUpdate.setPrice(car.getPrice());

        return repository.save(car);
    }
}