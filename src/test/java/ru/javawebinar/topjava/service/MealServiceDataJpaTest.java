package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(profiles = {"datajpa","postgres"})
public class MealServiceDataJpaTest extends MealServiceTest {

    @Test
    public void getWithUser() throws Exception {
        Meal meal = service.getMealOfUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(meal, ADMIN_MEAL1);
    }
}
