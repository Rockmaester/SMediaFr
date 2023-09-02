package com.lrb.smediafr.communication;

import com.lrb.smediafr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers(){
        String url = "http://localhost:3333/api/all/";
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> allUsers = responseEntity.getBody();
        return allUsers;
    }

    public void saveOrUpdate(User user){
        String url = "http://localhost:3333/api/all/";
        long id = user.getId();
        if(id == 0){
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, user, String.class);
        } else {
            restTemplate.put(url, user);
        }
    }

    public User getUserByName(String name){
        String url = "http://localhost:3333/api/byname/";
        User user = restTemplate.getForObject(url + "/" + name, User.class);
        return user;
    }



}
