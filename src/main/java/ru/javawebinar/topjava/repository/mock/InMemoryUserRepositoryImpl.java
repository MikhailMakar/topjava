package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private HashMap<Integer, User> userRepository = new HashMap<>();
    private AtomicInteger userCounter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);

        for (User user: userRepository.values()) {
            if (user.getId() == id) {
                userRepository.remove(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);

        if (user.isNew()) {
            user.setId(userCounter.incrementAndGet());
            userRepository.put(user.getId(), user);
            return user;
        }
        return userRepository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return userRepository.values().stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return userRepository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .map(user -> userRepository.get(user.getId()))
                .orElse(null);
    }
}
