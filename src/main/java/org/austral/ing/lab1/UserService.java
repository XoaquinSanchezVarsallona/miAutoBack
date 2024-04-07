package org.austral.ing.lab1;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class UserService {
    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private long idServicio;

    @Column(nullable = false, unique = true)
    private String serviceUsername;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    //esta la dejamos?
    //@Column
    //private LocalDateTime creationDate;

    public UserService(String serviceUsername, String email, String password){
        this.serviceUsername = serviceUsername;
        this.email = email;
        this.password = password;
    }

    public UserService(){}
}