package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(null, "user1", "email1", "password1", Role.ROLE_USER),
            new User(null, "user2", "email2", "password2", Role.ROLE_USER)
    );

}
