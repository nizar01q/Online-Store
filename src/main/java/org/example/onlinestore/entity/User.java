package org.example.onlinestore.entity;

import jakarta.persistence.*;
import lombok.*;


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

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
    private String role;



    @OneToOne
    @JoinColumn(name = "CART_CARTID")
    private Cart cart;

    public User(int userID) {
        this.userID = userID;
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password, String role, Cart cart) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.cart = cart;
    }
}
