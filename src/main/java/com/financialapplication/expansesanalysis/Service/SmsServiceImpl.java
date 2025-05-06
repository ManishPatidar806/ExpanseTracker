package com.financialapplication.expansesanalysis.Service;


import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Entity.Category;
import com.financialapplication.expansesanalysis.Model.Entity.Money;
import com.financialapplication.expansesanalysis.Model.Entity.Sms;
import com.financialapplication.expansesanalysis.Model.Entity.User;
import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import com.financialapplication.expansesanalysis.Model.Enum.MoneyType;
import com.financialapplication.expansesanalysis.Model.Request.SmsSavedRequest;
import com.financialapplication.expansesanalysis.Model.Request.SmsUpdateRequest;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.SmsResponse;
import com.financialapplication.expansesanalysis.Repository.CategoryRepository;
import com.financialapplication.expansesanalysis.Repository.MoneyRepository;
import com.financialapplication.expansesanalysis.Repository.SmsRepository;
import com.financialapplication.expansesanalysis.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SmsServiceImpl implements SmsService {

    private final ModelMapper mapper;

    private final UserRepository userRepository;


    private final MoneyRepository moneyRepository;

    private final SmsRepository smsRepository;

    private final CategoryRepository categoryRepository;


    public SmsServiceImpl(ModelMapper mapper, UserRepository userRepository, MoneyRepository moneyRepository, SmsRepository smsRepository, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.moneyRepository = moneyRepository;
        this.smsRepository = smsRepository;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<CommonResponse> savedSms(SmsSavedRequest smsRequest, String mobileNo) throws NotFoundException {
        if (smsRequest.getSms().isEmpty()) {
            return new ResponseEntity<>(new CommonResponse("NO New Message Is Found", true), HttpStatus.NO_CONTENT);
        }

        User user = userRepository.findByMobile(mobileNo)
                .orElseThrow(NotFoundException::new);

        LocalDateTime smsTime = smsRepository.findLatestDateTime();
        LocalDateTime oneMonthBeforeUserCreated = user.getCreatedAt().minusMonths(1);

        List<Sms> filteredSmsList = smsRequest.getSms().stream()
                .map(smsDto -> mapper.map(smsDto, Sms.class))
                .filter(sms -> sms.getDateTime().isAfter(smsTime) &&
                        sms.getDateTime().isAfter(oneMonthBeforeUserCreated))
                .peek(sms -> sms.setUser(user))
                .toList();

        if (filteredSmsList.isEmpty()) {
            return new ResponseEntity<>(new CommonResponse("NO New Message to Save", true), HttpStatus.NO_CONTENT);
        }

        // Partition into credited and debited in a single pass
        Map<Boolean, List<Sms>> partitioned = filteredSmsList.stream()
                .collect(Collectors.partitioningBy(sms -> sms.getMoneyType() == MoneyType.CREDITED));

        double credited = partitioned.get(true).stream().mapToDouble(Sms::getAmount).sum();
        double debited = partitioned.get(false).stream().mapToDouble(Sms::getAmount).sum();

        // Calculate expenses after user creation and after last saved sms
        double expenses = filteredSmsList.stream()
                .filter(sms -> sms.getDateTime().isAfter(user.getCreatedAt()) &&
                        sms.getDateTime().isAfter(smsTime))
                .mapToDouble(Sms::getAmount)
                .sum();

        // Retrieve or create Money object
        Money money = moneyRepository.findByUserId(user.getId()).orElseGet(() -> {
            Money m = new Money();
            m.setUser(user);
            return m;
        });

        // Update money fields
        money.setCreditAmount(money.getCreditAmount() + credited);
        money.setDebitedAmount(money.getDebitedAmount() + debited);
        money.setMonthlyLimit(money.getMonthlyLimit() - expenses);  // Consider revising this logic if needed

        // Save entities
        smsRepository.saveAll(filteredSmsList);
        moneyRepository.save(money);

        return new ResponseEntity<>(new CommonResponse("Sms Saved Successfully", true), HttpStatus.OK);
    }


    /*
     * Get all Sms of Category null
     * */
    @Override
    public ResponseEntity<SmsResponse> getAllSms(String moblieNo) throws NotFoundException {
        User user = userRepository.findByMobile(moblieNo).orElseThrow(NotFoundException::new);
        List<Sms> smsList = smsRepository.getAllSmsOfCategoryNull(moblieNo, user.getCreatedAt());
        SmsResponse response = new SmsResponse();
        response.setMessage("Sms Found Successfully");
        response.setStatus(true);
        response.setSmsList(smsList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Get sms acc to category
     * */

    @Override
    public ResponseEntity<SmsResponse> getSmsByCategory(String mobileNo, CategoryType category) throws NotFoundException {
        User user = userRepository.findByMobile(mobileNo).orElseThrow(NotFoundException::new);
        List<Sms> smsList = smsRepository.getAllSmsByCategory(mobileNo, category, user.getCreatedAt());
        SmsResponse response = new SmsResponse();
        response.setMessage("Sms found Successfully");
        response.setSmsList(smsList);
        response.setStatus(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> updateCategory(String mobileNo, SmsUpdateRequest smsRequest) throws NotFoundException {
        User user = userRepository.findByMobile(mobileNo).orElseThrow(NotFoundException::new);
        List<Sms> filteredSms = smsRequest.getSms().stream()
                .map(smsDto->mapper.map(smsDto,Sms.class))
                .filter(sms -> sms.getCategory() != null)
                .peek(sms -> sms.setUser(user))
                .toList();
        Category category = categoryRepository.findCategoryByUserId(user.getId()).orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setUser(user);
                    return newCategory;
                }
        );

        for (Sms sm : filteredSms) {
            switch (sm.getCategory()) {
                case GROCERIES -> category.setGroceries(category.getGroceries() + sm.getAmount());
                case MEDICAL -> category.setMedical(category.getMedical() + sm.getAmount());
                case DOMESTIC -> category.setDomestic(category.getDomestic() + sm.getAmount());
                case SHOPPING -> category.setShopping(category.getShopping() + sm.getAmount());
                case BILLS -> category.setBills(category.getBills() + sm.getAmount());
                case ENTERTAINMENT -> category.setEntertainment(category.getEntertainment() + sm.getAmount());
                case TRAVELLING -> category.setTravelling(category.getTravelling() + sm.getAmount());
                case FUELING -> category.setFueling(category.getFueling() + sm.getAmount());
                case EDUCATIONAL -> category.setEducational(category.getEducational() + sm.getAmount());
                case OTHERS -> category.setOthers(category.getOthers() + sm.getAmount());
            }
        }
        smsRepository.saveAll(filteredSms);
        categoryRepository.save(category);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage("Category Updated Successfully");
        commonResponse.setStatus(true);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }


}

