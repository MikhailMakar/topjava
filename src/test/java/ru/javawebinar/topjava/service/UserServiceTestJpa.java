package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"jpa","postgres"})
public class UserServiceTestJpa extends UserServiceTest {
}
