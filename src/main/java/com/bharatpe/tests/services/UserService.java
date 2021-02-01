package com.bharatpe.tests.services;

import com.bharatpe.tests.dao.UserDao;
import com.bharatpe.tests.models.Manga;
import com.bharatpe.tests.models.MangaResult;
import com.bharatpe.tests.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final RestTemplate restTemplate;
    private static final String MANGA_SEARCH_URL="http://api.jikan.moe/search/manga/";

    public UserService(UserDao userDao, RestTemplate restTemplate){
        this.userDao = userDao;
        this.restTemplate = restTemplate;
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

    public List<Manga> getMangasByTitle(String title) {
        return restTemplate.getForEntity(MANGA_SEARCH_URL+title, MangaResult.class).getBody().getResult();
    }
}
