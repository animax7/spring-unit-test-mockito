package com.bharatpe.tests.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.bharatpe.tests.dao.UserDao;
import com.bharatpe.tests.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserDao userDao;

    @InjectMocks
    UserService userService;

    List<User> userList;

    @Before
    public void init(){
        userList = new ArrayList<>();
        when(userDao.findAllByEmployeeCode(any(String.class))).thenAnswer(new Answer<Iterable<User>>(){

            @Override
            public Iterable<User> answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                String employeeCode = arguments[0].toString();
                List<User> userWithEmployeeCode = new ArrayList<>();

                userList.forEach((user)-> {
                    if (user.getEmployeeCode().equals(employeeCode)){
                        userWithEmployeeCode.add(user);
                    }
                });
                return userWithEmployeeCode;
            }
        });

        when(userDao.save(any(User.class))).thenAnswer(new Answer<User>() {

            @Override
            public User answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                User user = (User) arguments[0];
                userList.add(user);
                return user;
            }
        });
    }

    @Test
    public void testCreateUser(){
        assertThat(userService.createUser("Animesh","Sharma","1"),is(notNullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void testUserCreationFailsForDuplicateEmployeeCode(){
        assertThat(userService.createUser("Animesh","Sharma","1"),is(notNullValue()));
        assertThat(userService.createUser("Animesh","Sharma","1"),is(notNullValue()));
    }
}
