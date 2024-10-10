package org.example.onlinestore.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.onlinestore.entity.Cart;
import org.example.onlinestore.model.UserPrinciple;
import org.example.onlinestore.repo.CartRepo;
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
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final PasswordValidator validator;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<User> showUsers(){
        log.info("Getting all users");
        return userRepo.findAll();
    }

    public Optional<User> showUserByID(User user){
        return userRepo.findById(user.getUserID());
    }

    public void createUser(User user){
        if(!validator.validatePassword(user.getPassword())){
            throw new IllegalArgumentException(validator.errorMessage);
        };
        if (userRepo.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepo.findUserByUsername(user.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cartRepo.save(cart);
        user.setCart(cart);
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

    public void remakeUser(User user) {
        User existingUser = userRepo.findById(user.getUserID()).get();

        if(!validator.validatePassword(user.getPassword())){
            throw new IllegalArgumentException(validator.errorMessage);
        }
        if (!user.getUsername().equals(existingUser.getUsername())){
            if (userRepo.findUserByUsername(user.getUsername()).isPresent()){
                throw new IllegalArgumentException("Username already exists");
            }
            existingUser.setUsername(user.getUsername());
        }
        if (!user.getEmail().equals(existingUser.getEmail())){
            if (userRepo.findByEmail(user.getEmail()).isPresent()){
                throw new IllegalArgumentException("Email already exists");
            }
            existingUser.setEmail(user.getEmail());
        }

        existingUser.setPassword(encoder.encode(user.getPassword()));
        existingUser.setRole(user.getRole());
        existingUser.setAddress(user.getAddress());
        existingUser.setPaymentMethod(user.getPaymentMethod());
        userRepo.save(existingUser);
    }

    public List<User> searchUsers(String query){
        return userRepo.findByUsernameContainingOrEmailContainingOrRoleContaining(query,query,query);
    }

    public void changeUserAddressOrPayment(User user){
        userRepo.save(user);
    }

    public void changeUserStatus(User user) {
        userRepo.save(user);
    }
}
