package com.example.keycloakuserstore.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.sql.Date;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(name="getUserByUsername", query="select u from User u where u.username = :username"),
        @NamedQuery(name="getUserByEmail", query="select u from User u where u.email = :email "),
        @NamedQuery(name="getUserCount", query="select count(id) from User u "),
        @NamedQuery(name="getAllUsers", query="select u from User u "),
        @NamedQuery(name="searchForUser", query="select u from User u where " +
                "( lower(u.username) like :search or u.email like :search ) order by u.username"),
})
@Entity
@Table(name = "USER", schema="USR")
@Data
@Accessors(chain = true)
public class User {
    // @Id
    // @GeneratedValue
    @Id
    @Column(unique = true, length = 15, name = "Id")
    private String id;

    @Column(length = 60, name = "Name")
    private String firstName;

    @Column(length = 110, name = "Password")
    private String password;

    @Column(length = 11, name = "UserName")
    private String username;

    @Column(length = 100, name = "Email")
    private String email;

}
