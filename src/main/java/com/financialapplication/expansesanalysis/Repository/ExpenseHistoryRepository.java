package com.financialapplication.expansesanalysis.Repository;

import com.financialapplication.expansesanalysis.Model.Entity.ExpenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseHistoryRepository extends JpaRepository<ExpenseHistory,Long> {
}
