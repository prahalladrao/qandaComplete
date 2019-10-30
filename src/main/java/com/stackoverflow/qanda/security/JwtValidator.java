package com.stackoverflow.qanda.security;

import com.stackoverflow.qanda.exception.UnauthorizedException;
import com.stackoverflow.qanda.model.JwtAuthenticationToken;
import com.stackoverflow.qanda.model.JwtUser;
import com.stackoverflow.qanda.model.User;
import com.stackoverflow.qanda.repository.UserCrudRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class JwtValidator {


    private String secret = "$#12&T86";
    @Autowired
    private JwtUser jwtUser;
    @Autowired
    private UserCrudRepo userCrudRepo;
    @Autowired
    RestTemplate restTemplate;

    public JwtUser validate(String token) {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser.setUserName(body.getSubject());
            System.out.println(jwtUser.getUserName());
        String url = "http://localhost:9002/signup/user";
        //String requestJson = "{\"queriedQuestion\":\"Is there pain in your hand?\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(Long.parseLong((String)body.get("userId")),headers);

        if(!restTemplate.postForObject(url, entity, Boolean.class))
            throw new RuntimeException();

            //jwtUser.setId(Long.parseLong((String) body.get("userId")));
           // jwtUser.setRole((String) body.get("role"));

            return jwtUser;
    }
}
