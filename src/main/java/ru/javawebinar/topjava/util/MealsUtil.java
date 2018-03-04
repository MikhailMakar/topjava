package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static void main(String[] args) {
    }
    public static List<MealWithExceed> getFilteredWithExceededByStream(List<Meal> mealList, int caloriesPerDay){

        Map<LocalDate, Integer> dayAndCalories = mealList.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        return mealList.stream()
                .map(Meal -> new MealWithExceed(Meal.getDateTime(), Meal.getDescription(), Meal.getCalories(), dayAndCalories.get(Meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}