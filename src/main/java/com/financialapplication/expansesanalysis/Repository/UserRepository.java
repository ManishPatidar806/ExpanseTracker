package com.financialapplication.expansesanalysis.Repository;


import com.financialapplication.expansesanalysis.Model.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByMobile(String mobile);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET created_at = NOW() WHERE created_at <= NOW() - INTERVAL 1 MONTH", nativeQuery = true)
    int  updateCreatedAt();
}
