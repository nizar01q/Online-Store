package org.example.onlinestore.repo;


import org.example.onlinestore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email) ;

    Optional<User> findUserByUsername(String username);

    User findByUsername(String username);

    List<User> findByUsernameContainingOrEmailContainingOrRoleContaining(String username,String email, String role);

    Optional<Object> findByAddress(String email);
}
