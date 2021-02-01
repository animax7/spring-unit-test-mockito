package com.bharatpe.tests.dao;

import com.bharatpe.tests.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User,Long> {

    public Iterable<User> findAllByEmployeeCode(String employeeCode);
}
