package com.sny.ratingapp.service;

import com.sny.ratingapp.beans.Car;
import com.sny.ratingapp.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class CarService implements IDao<Car> {
    private List<Car> cars;
    private static CarService instance;
    private CarService() {
        this.cars = new ArrayList<Car>();
    }
    public static CarService getInstance() {
        if(instance == null)
            instance = new CarService();
        return instance;
    }
    @Override
    public boolean create(Car o) {
        return cars.add(o);
    }
    @Override
    public boolean update(Car o) {
        for(Car car : cars){
            if(car.getId() == o.getId()){
                car.setImg(o.getImg());
                car.setMarque(o.getMarque());
                car.setModel(o.getModel());
                car.setStars(o.getStars());
            }
        }
        return true;
    }
    @Override
    public boolean delete(Car o) {
        return cars.remove(o);
    }
    @Override
    public Car findById(int id) {
        for(Car c : cars){
            if(c.getId() == id)
                return c;
        }
        return null;
    }
    @Override
    public List<Car> findAll() {
        return cars;
    }

}
