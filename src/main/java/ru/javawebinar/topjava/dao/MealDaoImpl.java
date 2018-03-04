package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao {

    List<Meal> mealsDao = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    @Override
    public Meal create(Meal meal) {
        return null;
    }

    @Override
    public void deleteMeal(int id) {

    }

    @Override
    public void updateMeal(Meal meal) {

    }

    @Override
    public Meal getMealById(int id) {
        return null;
    }

    @Override
    public List<Meal> getMealList() {
        return mealsDao.stream()
                .map(meal -> new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories()))
                .collect(Collectors.toList());
    }
}
