package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDaoImpl mealDaoImpl = new MealDaoImpl();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static String INSERT_OR_EDIT = "/editmeal.jsp";
    private static String LIST_USER = "/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("formatter", formatter);
        String forward="";
        String action = request.getParameter("action");
//        List<MealWithExceed> mealsWithExceeded = MealsUtil.getFilteredWithExceededByStream(mealDaoImpl.getList(), LocalTime.MIN, LocalTime.MAX, 2000);

        if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("id"));
            mealDaoImpl.delete(mealId);
            forward = LIST_USER;
            request.setAttribute("meals", MealsUtil.getFilteredWithExceededByStream(mealDaoImpl.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDaoImpl.getById(mealId);
            request.setAttribute("mealInstance", meal);
        } else if (action.equalsIgnoreCase("listmeals")){
            forward = LIST_USER;
            request.setAttribute("meals", MealsUtil.getFilteredWithExceededByStream(mealDaoImpl.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else {
            forward = INSERT_OR_EDIT;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        meal.setDateTime(localDateTime);
        meal.setDescription(request.getParameter("description"));
        int calories = Integer.parseInt(request.getParameter("calories"));
        meal.setCalories(calories);

        String mealId = request.getParameter("id");
        if(mealId == null || mealId.isEmpty())
        {
            mealDaoImpl.create(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(mealId));
            mealDaoImpl.update(meal);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("meals", MealsUtil.getFilteredWithExceededByStream(mealDaoImpl.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
        dispatcher.forward(request, response);
    }
}
