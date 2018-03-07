package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    void create(Meal meal);
    void remove(int id);
    void update(Meal meal);
    Meal get(int id);
    List<Meal> getList();
}
