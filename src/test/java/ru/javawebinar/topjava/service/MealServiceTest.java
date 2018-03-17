package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
//import static ru.javawebinar.topjava.MealTestData.assertMatch;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;


    @Test
    public void create() {
        Meal meal = new Meal(null, LocalDateTime.of(2016, Month.MAY, 11, 10, 30), "descpirtion", 1000);
        Meal create = service.create(meal, USER_ID);
        assertMatch(service.getAll(USER_ID), create, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Lunch");
        updated.setCalories(1000);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotYours() {
        service.update(MEAL2, ADMIN_ID);
    }

    @Test
    public void get() {
        Meal got = new Meal(MEAL1);
        service.get(MEAL1.getId(), USER_ID);
        assertMatch(got, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotYours() {
        service.get(MEAL1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID),MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotYours() {
        service.delete(MEAL1.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

}