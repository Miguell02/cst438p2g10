package org.example.dailytier.repository;

import org.example.dailytier.model.TierList;
import org.example.dailytier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TierListRepository extends JpaRepository<TierList, Long> {
    List<TierList> findByUser(User user);

    @Query("SELECT t FROM TierList t WHERE t.user.id != :userId")
    List<TierList> findAllExceptUser(@Param("userId") Long userId); // Fetch all except the current user
}
