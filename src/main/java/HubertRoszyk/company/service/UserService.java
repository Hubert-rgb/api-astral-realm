package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.User;
import HubertRoszyk.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class UserService{
    @Autowired
    private UserRepository repository;

    public User saveUser(User user) {
        return repository.save(user);
    }
    public List<User> getUsersList() {
        return repository.findAll();
    }
    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void deleteUsers() {
        repository.deleteAll();
        System.out.println("All users deleted");
    }
}
