package com.financialapplication.expansesanalysis.Repository;

import com.financialapplication.expansesanalysis.Model.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {

    @Query("SELECT category FROM Category category WHERE category.user.id=:userId")
    Optional<Category> findCategoryByUserId(@Param("userId") Long userId);


    @Query("SELECT category FROM Category category WHERE category.user.mobile=:mobileNo")
    Optional<Category> findCategoryByMoblieNo(@Param("moblieNo") String moblieNo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM category WHERE user_id IN (SELECT id FROM user WHERE created_at <= NOW() - INTERVAL 1 MONTH)", nativeQuery = true)
    void deletePreviousCategoryData();


}
