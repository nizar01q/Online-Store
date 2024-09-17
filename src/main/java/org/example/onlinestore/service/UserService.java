package org.example.onlinestore.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.onlinestore.model.UserPrinciple;
import org.example.onlinestore.repo.UserRepo;
import org.example.onlinestore.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordValidator validator;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<User> showUsers(){
        return userRepo.findAll();
    }

    public Optional<User> showUserByID(User user){
        return userRepo.findById(user.getUserID());
    }

    public void createUser(User user){
        if(!validator.validatePassword(user.getPassword())){
            throw new IllegalArgumentException(validator.errorMessage);
        };
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void deleteUser(User user){
        userRepo.deleteById(user.getUserID());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrinciple(user);
    }
}
