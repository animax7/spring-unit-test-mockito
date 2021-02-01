package com.bharatpe.tests.services;

import com.bharatpe.tests.dao.UserDao;
import com.bharatpe.tests.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public boolean userWithEmployeeCodeExists(String employeeCode){
        Iterable<User> userIterable = userDao.findAllByEmployeeCode(employeeCode);
        return userIterable.iterator().hasNext();
    }

    public User createUser(String firstName,String lastName,String employeeCode){

        if (userWithEmployeeCodeExists(employeeCode)){
            throw new RuntimeException("Employee with code already exists");
        }

        User obj = new User();
        obj.setFirstName(firstName);
        obj.setLastName(lastName);
        obj.setEmployeeCode(employeeCode);
        return userDao.save(obj);
    }
}
