package com.capstone_breaking.newtral.repository;

import com.capstone_breaking.newtral.domain.Category;
import com.capstone_breaking.newtral.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    List<UserCategory> findUserCategoryByUserId(Long userId);

    boolean findByCategoryId(Long categoryId);
}

//힘들때는 당근을 흔들어보아요...