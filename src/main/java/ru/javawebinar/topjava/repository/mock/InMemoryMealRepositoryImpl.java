package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository("inMemoryMealRepositoryImpl")
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        meal.setUserId(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage

        Meal meal1 = repository.get(meal.getId());

        return meal1 != null && meal1.getUserId() == userId ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }


    @Override
    public boolean checkedUserId(int id, int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId;
    }

    @Override
    public boolean delete(int id, int userId) {
        return checkedUserId(id, userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return checkedUserId(id, userId) ? repository.get(id) : null;
    }

    @Override
    public Stream<Meal> getFilteredByUserId(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredByUserId(userId)
                .sorted(Comparator.comparing(Meal::getDateTime)
                        .reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getFilteredByUserId(userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime)
                        .thenComparing(Meal::getId))
                .collect(Collectors.toList());
    }
}

