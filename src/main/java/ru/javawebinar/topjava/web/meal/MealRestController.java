package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController{
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal){
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id){
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id){
        return service.get(id, AuthorizedUser.id());
    }

    public void update(Meal meal){
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllListFiltered(String startTime, String endTime, String startDate, String endDate){
        LocalTime sTime = LocalTime.parse(startTime);
        LocalTime eTime = LocalTime.parse(endTime);
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay(), sDate.atTime(sTime.getHour(), sTime.getMinute()).toLocalTime(), eDate.atTime(eTime.getHour(), eTime.getMinute()).toLocalTime());
    }

    public List<Meal> getAll(){
        return service.getAll(AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllWithExceed(){
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }
}