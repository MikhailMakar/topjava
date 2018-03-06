package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {

    public static void main(String[] args) {
    }

    public static List<MealWithExceed> getFilteredWithExceededByStream(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay){

        Map<LocalDate, Integer> dayAndCalories = mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return mealList.stream()
                .filter(Meal -> TimeUtil.isBetween(Meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(Meal -> new MealWithExceed(Meal.getDateTime(), Meal.getDescription(), Meal.getCalories(), dayAndCalories.get(Meal.getDate()) > caloriesPerDay, Meal.getId()))
                .collect(Collectors.toList());
    }
}