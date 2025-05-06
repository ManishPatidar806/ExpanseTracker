package com.financialapplication.expansesanalysis.Service;


import com.financialapplication.expansesanalysis.Exception.CommonException;
import com.financialapplication.expansesanalysis.Exception.UserAlreadyExistException;
import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Entity.Money;
import com.financialapplication.expansesanalysis.Model.Entity.User;
import com.financialapplication.expansesanalysis.Model.Request.RegisterRequest;
import com.financialapplication.expansesanalysis.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final ModelMapper mapper;

    public AuthServiceImpl(UserRepository userRepository,ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper =mapper;
    }

    public User signUpUser(RegisterRequest registerRequest) throws UserAlreadyExistException {
        Optional<User> user1 = userRepository.findByMobile(registerRequest.getMobileNo());
        if (user1.isPresent()) {
            throw new UserAlreadyExistException();
        }
       User user= mapper.map(registerRequest,User.class);
        Money money = new Money();
        money.setUser(user);
        user.setMoney(money);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }


    public User loginUser(String mobileNo, String password) throws CommonException, NotFoundException {
        Optional<User> user1 = userRepository.findByMobile(mobileNo);
        if (user1.isPresent()) {
            if (user1.get().getPassword().equals(password)) {
                return user1.get();
            } else {
                throw new CommonException("Invalid Password", 405);
            }
        } else {
            throw new NotFoundException();
        }
    }


    public User extraceUser(String mobileNo) throws NotFoundException {
        Optional<User> user = userRepository.findByMobile(mobileNo);
        user.orElseThrow(NotFoundException::new);
        return user.get();

    }


}