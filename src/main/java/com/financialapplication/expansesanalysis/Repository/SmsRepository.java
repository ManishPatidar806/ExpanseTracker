package com.financialapplication.expansesanalysis.Repository;

import com.financialapplication.expansesanalysis.Model.Entity.Sms;
import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Long> {

    @Query("SELECT MAX(sms.dateTime) FROM Sms sms")
    LocalDateTime findLatestDateTime();

    @Query("SELECT sms FROM Sms sms WHERE sms.user.mobile = :mobileNo AND sms.category IS NULL AND sms.dateTime >= :createdAt")
    List<Sms> getAllSmsOfCategoryNull(@Param("mobileNo") String mobileNo, @Param("createdAt") LocalDateTime createdAt);

    @Query("SELECT sms FROM Sms sms WHERE sms.user.mobile = :mobileNo AND sms.category = :category AND sms.dateTime >= :dateTime")
    List<Sms> getAllSmsByCategory(@Param("mobileNo") String mobileNo, @Param("category") CategoryType category, @Param("dateTime") LocalDateTime dateTime);
}
