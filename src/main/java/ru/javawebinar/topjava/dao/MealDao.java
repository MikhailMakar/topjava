package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {

    Meal create(Meal meal);
    void deleteMeal(int id);
    void updateMeal(Meal meal);
    Meal getMealById(int id);
    List<Meal> getMealList();

}
