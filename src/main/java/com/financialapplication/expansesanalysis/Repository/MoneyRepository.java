package com.financialapplication.expansesanalysis.Repository;


import com.financialapplication.expansesanalysis.Model.Dto.ExpenseSummary;
import com.financialapplication.expansesanalysis.Model.Entity.Money;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoneyRepository extends JpaRepository<Money ,Long> {
    @Query("SELECT money from Money money Where money.user.id=:userId")
    public Optional<Money> findByUserId(@Param("userId") Long userId);

    @Query("SELECT money FROM Money money WHERE money.user.mobile=:mobileNo")
    Optional<Money> findAmountByMoblieNo(@Param("mobileNo") String moblieNo);


    @Modifying
    @Transactional
    @Query(value = "UPDATE money SET credit_amount = 0,debited_amount=0 WHERE user_id IN (SELECT id FROM user WHERE created_at <= NOW() - INTERVAL 1 MONTH)", nativeQuery = true)
    int  updateCreditAndDebited();



    @Query(value = "SELECT m.user_id AS userId, SUM(m.debited_amount) AS totalExpense " +
            "FROM money m " +
            "JOIN user u ON m.user_id = u.id " +
            "WHERE u.created_at <= NOW() - INTERVAL 1 MONTH " +
            "GROUP BY m.user_id", nativeQuery = true)
    List<ExpenseSummary> findPreviousData();

}
