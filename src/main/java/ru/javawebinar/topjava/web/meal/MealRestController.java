package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
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

    public List<MealWithExceed> getAllListFiltered(String startDate, String endDate, String startTime, String endTime){
        LocalDate sDate = DateTimeUtil.parseDate(startDate);
        LocalDate eDate = DateTimeUtil.parseDate(endDate);
        LocalTime sTime = DateTimeUtil.parseTime(startTime);
        LocalTime eTime = DateTimeUtil.parseTime(endTime);

        return MealsUtil.getFilteredWithExceeded(service.getFilteredByDate(AuthorizedUser.id(), sDate == null ? LocalDate.MIN : sDate, eDate == null ? LocalDate.MAX : eDate),
                AuthorizedUser.getCaloriesPerDay(),
                sTime == null ? LocalTime.MIN : sTime,
                eTime == null ? LocalTime.MAX : eTime
        );
    }

    public List<Meal> getAll(){
        return service.getAll(AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllWithExceed(){
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }
}