package com.financialapplication.expansesanalysis.Service;

import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Dto.ExpenseSummary;
import com.financialapplication.expansesanalysis.Model.Entity.ExpenseHistory;
import com.financialapplication.expansesanalysis.Model.Entity.User;
import com.financialapplication.expansesanalysis.Repository.ExpenseHistoryRepository;
import com.financialapplication.expansesanalysis.Repository.MoneyRepository;
import com.financialapplication.expansesanalysis.Repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpansesHistoryService {
    private final UserRepository userRepository;

    private final MoneyRepository moneyRepository;

    private final ExpenseHistoryRepository historyRepository;

    public ExpansesHistoryService(UserRepository userRepository, MoneyRepository moneyRepository, ExpenseHistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.moneyRepository = moneyRepository;
        this.historyRepository = historyRepository;
    }

    @SneakyThrows
    public int storeHistoryAndRemoveMoney() {
        List<ExpenseSummary> summaries = moneyRepository.findPreviousData();

        List<ExpenseHistory> entities = new ArrayList<>();
        for (ExpenseSummary expenseSummary : summaries) {
            ExpenseHistory eh = new ExpenseHistory();
            eh.setExpenseAmount(expenseSummary.getTotalExpense());
            eh.setRecordedAt(LocalDateTime.now());
            User user = userRepository.findById(expenseSummary.getUserId()).orElseThrow(NotFoundException::new);
            eh.setUser(user);
            entities.add(eh);
        }
        historyRepository.saveAll(entities);
        return moneyRepository.updateCreditAndDebited();
    }


}
