package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    boolean checkedUserId(int id, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    Stream<Meal> getFilteredByUserId(int userId);

    List<Meal> getAll(int userId);

    List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate);
}
