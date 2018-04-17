package ru.javawebinar.topjava.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.Util.orElse;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private final MealService service;

    @Autowired
    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping("/delete")
    public String deleteMeal (@RequestParam("id") int id) {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String createMeal (Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update")
    public String updateMeal (@RequestParam("id") int id, Model model) {
        int userId = AuthorizedUser.id();
        final Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping
    public String allMeal (Model model) {
        int userId = AuthorizedUser.id();
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @PostMapping("")
    public String setMeal(
            @RequestParam("id") Integer id,
            @RequestParam("dateTime") String dateTime,
            @RequestParam("calories") int calories,
            @RequestParam("description") String description
    ) {
        int userId = AuthorizedUser.id();
        Meal meal = new Meal(
                LocalDateTime.parse(dateTime),
                description,
                calories);

        if (id == null) {
            checkNew(meal);
            service.create(meal, userId);
        } else {
            assureIdConsistent(meal, id);
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filterMeal(
            @RequestParam("startDate") String startDateParam,
            @RequestParam("endDate") String endDateParam,
            @RequestParam("startTime") String startTimeParam,
            @RequestParam("endTime") String endTimeParam,
            Model model) {
        LocalDate startDate = parseLocalDate(startDateParam);
        LocalDate endDate = parseLocalDate(endDateParam);
        LocalTime startTime = parseLocalTime(startTimeParam);
        LocalTime endTime = parseLocalTime(endTimeParam);
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        List<Meal> mealsDateFiltered = service.getBetweenDates(
                orElse(startDate, DateTimeUtil.MIN_DATE), orElse(endDate, DateTimeUtil.MAX_DATE), userId);

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                orElse(startTime, LocalTime.MIN), orElse(endTime, LocalTime.MAX)
        );
    }
}
