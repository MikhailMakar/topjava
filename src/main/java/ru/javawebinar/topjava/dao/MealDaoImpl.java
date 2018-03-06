package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao {

    private static AtomicInteger countMeals = new AtomicInteger(1);
    ConcurrentMap<Integer, Meal> mealConcurrentMap = new ConcurrentHashMap<>();

    {
        List<Meal> mealsDao = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        for (Meal m: mealsDao) {
            mealConcurrentMap.put(countMeals.get(), m);
            m.setId(countMeals.get());
            countMeals.getAndIncrement();
        }
    }

    @Override
    public void create(Meal meal) {
        mealConcurrentMap.put(mealConcurrentMap.size()+1, meal);
        meal.setId(mealConcurrentMap.size()+1);
    }

    @Override
    public void delete(int id) {
        for (int i: mealConcurrentMap.keySet()) {
            if(id != 0 && id == i){
                mealConcurrentMap.remove(id);
            }
        }
    }

    @Override
    public void update(Meal meal) {
        mealConcurrentMap.put(meal.getId(), meal);
    }

    @Override
    public Meal getById(int id) {
        Meal meal = null;

        for (int i: mealConcurrentMap.keySet()) {
            if (id != 0 && id == i){
                meal = mealConcurrentMap.get(id);
            }
        }

        return meal;
    }

    @Override
    public List<Meal> getList() {
        List<Meal> resultList = new ArrayList<>();
        for (Meal meal: mealConcurrentMap.values()) {
            resultList.add(new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId()));
        }
        return resultList;
    }
}
