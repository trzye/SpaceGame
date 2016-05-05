package pl.edu.pw.ee.spacegame.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.dao.HelloWorldDao;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {

    @Autowired
    private HelloWorldDao helloWorldDao;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> helloWorld() {
        return new ResponseEntity<>(this.helloWorldDao.findOne("test"), HttpStatus.OK);
    }

}
