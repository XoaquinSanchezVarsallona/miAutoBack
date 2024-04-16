package org.austral.ing.lab1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private long idUser;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable= false, unique = true)
    public String email;

    @Column
    public String name;

    @Column
    public String surname;

    @Column
    public String password;

    @Column
    public String domicilio;

    //driver o service
    @Column
    public String userType;

    //entiendo q esto lo usariamos para en un futuro buscar mecanicos cerca del domicilio. habria q pensar si no conviene pedir provincia y ciudad, xq si nos pasan "tigre avenida bancalari 1200 barrio las aguas"
    //no nos va a servir tanto. estaría bueno simplificarlo capaz, tipo q sea [provincia][ciudad] o algo asi.

    //dicen q hace falta? de ser asi, habría q pasarle al constructor la date del momento.
    //@Column
    //private LocalDateTime creationDate;

    //tabla familia_conductores
    @ManyToMany
    @JoinTable(
            name = "familia_conductores",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idFamilia")
    )
    private List<Familia> familias = new ArrayList<>(); //cada conductor tiene un lista de familias



    //constructores
    public User(String Email, String Username, String Name, String Surname, String Password, String Domicilio, String userType) {
        this.email = Email;
        this.username = Username;
        this.password = Password;
        this.domicilio = Domicilio;
        this.name = Name;
        this.surname = Surname;
        this.userType = userType;
    }

    public User() {

    }

    //obtener las familias a las que pertenece el usuario
    public List<Familia> getFamilias() {
        return familias;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserType() {
        return this.userType;
    }

    public void addFamily(Familia familia) {
        this.familias.add(familia);
    }
    public void removeFamilia(Familia familia) {
        this.familias.remove(familia);
    }
    public String getUsername() {
        return this.username;
    }

}