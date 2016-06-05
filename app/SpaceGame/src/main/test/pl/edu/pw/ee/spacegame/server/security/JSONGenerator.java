package pl.edu.pw.ee.spacegame.server.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.edu.pw.ee.spacegame.server.controller.signup.SignUpData;

/**
 * Created by Micha³ on 2016-06-05.
 */
public class JSONGenerator {

    Object objectToParse;

    @Before
    public void setUp() {
        objectToParse = getSignUpData();
    }

    @Test
    public void parse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(objectToParse));
        Assert.assertTrue(true);
    }

    private SignUpData getSignUpData() {
        SignUpData signUpData = new SignUpData();
        signUpData.setNickname("nickname");
        signUpData.setRawPassword("rawPassword");
        signUpData.setCoordinateX(0);
        signUpData.setCoordinateY(1);
        signUpData.setEmail("sampleEmail@gmail.com");
        return signUpData;
    }

}
