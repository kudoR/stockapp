package myapp;

import myapp.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/user")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/user/{userId}")
    public User getUserById(@PathVariable(value = "userId") Long userId) {
        return (User) userRepository.findOne(userId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/userByName/{username}")
    public User getUserByName(@PathVariable(value = "username") String username) {
        return (User) userRepository.findByUsername(username);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/saveUser")
    public void saveUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email
    ) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        userRepository.save(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User registerNewUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
