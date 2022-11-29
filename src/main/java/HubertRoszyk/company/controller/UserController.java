package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.service.UserService;
import HubertRoszyk.company.wrongDataException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @PostMapping("/user-controller/users")
    public User loginUser(@RequestBody JSONObject jsonInput) {
        String displayName = (String) jsonInput.get("displayName");
        String firebaseUId = (String) jsonInput.get("firebaseUId");

        User currentUser;

        List<User> users = userService.getUsersList();

        for (User user : users) {
            if(user.getDisplayName().equals(displayName) && user.getFirebaseUId().equals(firebaseUId)) {
                currentUser = user;
                return currentUser;
            } else if (user.getDisplayName().equals(displayName)) {
                throw new wrongDataException();
            }
        }

        currentUser = createUser(displayName, firebaseUId);
        return currentUser;
    }
    @Autowired
    private UserService userService;
    public User createUser(String name, String password) {
        User user = new User(name, password);

        userService.saveUser(user);
        return user;
    }
}
