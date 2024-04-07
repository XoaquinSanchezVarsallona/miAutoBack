package org.austral.ing.lab1;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserDriver {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.SEQUENCE)
    private long idDriver;

    @Column(nullable = false, unique = true)
    public String Username;

    @Column(nullable= false, unique = true)
    public String Email;

    @Column
    public String Name;

    @Column
    public String Surname;

    @Column
    public String Password;

    //entiendo q esto lo usariamos para en un futuro buscar mecanicos cerca del domicilio. habria q pensar si no conviene pedir provincia y ciudad, xq si nos pasan "tigre avenida bancalari 1200 barrio las aguas"
    //no nos va a servir tanto. estaría bueno simplificarlo capaz, tipo q sea [provincia][ciudad] o algo asi.
    @Column
    public String Domicilio;

    //dicen q hace falta? de ser asi, habría q pasarle al constructor la date del momento.
    //@Column
    //private LocalDateTime creationDate;

    //tabla familia_conductores
    @ManyToMany
    @JoinTable(
            name = "familia_conductores",
            joinColumns = @JoinColumn(name = "idDriver"),
            inverseJoinColumns = @JoinColumn(name = "idFamilia")
    )
    private List<Familia> familias = new ArrayList<>(); //cada conductor tiene un lista de familias



    //constructores
    public UserDriver(String Email, String Username, String Name, String Surname, String Password, String Domicilio) {
        this.Email = Email;
        this.Username = Username;
        this.Password = Password;
        this.Domicilio = Domicilio;
        this.Name = Name;
        this.Surname = Surname;
    }

    public UserDriver() {

    }

    //obtener las familias a las que pertenece el usuario
    public List<Familia> getFamilias() {
        return familias;
    }

    public String getPassword() {
        return this.Password;
    }
}