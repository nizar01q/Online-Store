package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "USERS")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "USERID")
    private int userID;

    @Column(name = "USERNAME",unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(name = "USERSTATUS")
    private String userStatus;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @OneToOne
    @JoinColumn(name = "CART_CARTID")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(int userID) {
        this.userID = userID;
    }

    public User(String username, String password, String role, String email, String userStatus) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.userStatus = userStatus;
    }

    public User(int userID, String username, String password, String role, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public User(String username, String password, String role, Cart cart) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.cart = cart;
    }

    public User(int userID, String username, String password, String role, String email, Cart cart) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.cart = cart;
    }
}
