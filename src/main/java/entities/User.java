package entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "familia_conductores",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idFamilia")
    )
    private List<Familia> familias = new ArrayList<>();


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

    public String getEmail() {
        return this.email;
    }

    public List<Integer> getFamiliasId() {
        List<Integer> result = new ArrayList<>();
        for (Familia familia : familias) {
            result.add(familia.getFamiliaId());
        }
        return result;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserID() {
        return String.valueOf(this.idUser);
    }

    public Map<Integer, String> getFamiliasMap() {
        HashMap<Integer, String> result = new HashMap<>();
        for (Familia familia : familias) {
            result.put(familia.getFamiliaId(), familia.getApellido());
        }
        return result;
    }
}