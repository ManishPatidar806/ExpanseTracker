package com.financialapplication.expansesanalysis.Helper;


import com.financialapplication.expansesanalysis.Repository.CategoryRepository;
import com.financialapplication.expansesanalysis.Repository.UserRepository;
import com.financialapplication.expansesanalysis.Service.ExpansesHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Schedular {

    private final UserRepository userRepository;


    private final CategoryRepository categoryRepository;

    private final ExpansesHistoryService expansesHistoryService;

    public Schedular(UserRepository userRepository, CategoryRepository categoryRepository, ExpansesHistoryService expansesHistoryService) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.expansesHistoryService = expansesHistoryService;
    }

    @Scheduled(fixedRate = 86400000) // Every 24 hours
    public void updateDataAccToSchedular() {

        categoryRepository.deletePreviousCategoryData();
        log.info("One Month Category id Deleted!");

        expansesHistoryService.storeHistoryAndRemoveMoney();
        log.info("Money Reset Successfully !");


        int updatedCount = userRepository.updateCreatedAt();
        log.info("Updated " + updatedCount + " users' createdAt field.");

    }
}





