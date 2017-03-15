package pl.tsieciechowicz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tsieciechowicz.db.model.User;
import pl.tsieciechowicz.db.repository.UserRepository;

import java.util.List;

/**
 * Created by tsieciechowicz on 11.03.2017.
 */

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/")
    public @ResponseBody
    List<User> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public @ResponseBody
    User findById(@PathVariable String id){
        return userRepository.findOne(id);
    }

    @PostMapping("/users/save")
    public @ResponseBody
    User save(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/users/update")
    public @ResponseBody
    User update(@RequestBody User user){
        return userRepository.save(user);
    }

    @DeleteMapping("/users/delete/{id}")
    public void delete(@PathVariable String id){
        userRepository.delete(id);
    }
}
