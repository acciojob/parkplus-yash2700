package com.driver.controllers;
import com.driver.model.User;
import com.driver.repository.PaymentRepository;
import com.driver.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String password){
        userService.register(name,phoneNumber,password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updatePassword(@RequestParam Integer userId, @RequestParam String password){
        return new ResponseEntity<>(userService.updatePassword(userId,password), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam Integer userId){
    }
}
