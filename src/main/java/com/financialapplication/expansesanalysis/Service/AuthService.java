package com.financialapplication.expansesanalysis.Service;


import com.financialapplication.expansesanalysis.Exception.CommonException;
import com.financialapplication.expansesanalysis.Exception.UserAlreadyExistException;
import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Entity.User;
import com.financialapplication.expansesanalysis.Model.Request.RegisterRequest;
import org.springframework.stereotype.Service;


@Service
public interface AuthService {

    public User signUpUser(RegisterRequest registerRequest) throws NotFoundException, UserAlreadyExistException;

    public User loginUser(String mobile, String password) throws CommonException, NotFoundException;

    public User extraceUser(String mobileNO) throws NotFoundException;


}
