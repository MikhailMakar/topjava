package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    Optional<Meal> findById(Integer id);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> findAllByUserId(@Param("userId") int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime BETWEEN  ?2 AND ?3 ORDER BY m.dateTime DESC")
    List<Meal> findAllByUserIdAndGetBetween(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select m from Meal m inner join fetch m.user where m.id=:id and m.user.id=:userId")
    Optional<Meal> getMealOfUser(@Param("id") int id, @Param("userId") int userId);
}
