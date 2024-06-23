package entities;
import javax.persistence.*;
import java.util.*;

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
    private Double domicilioLongitude;

    @Column
    private Double domicilioLatitude;

    //driver o service
    @Column
    public String userType;

    // tabla familia_conductores
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "familia_conductores",
            joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idFamilia")
    )
    private Set<Familia> familias = new HashSet<>();

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<Store> stores = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Route> routes = new HashSet<>();


    //constructores
    public User(String Email, String Username, String Name, String Surname, String Password, Double longitude, Double latitude, String userType) {
        this.email = Email;
        this.username = Username;
        this.password = Password;
        this.domicilioLongitude = longitude;
        this.domicilioLatitude = latitude;
        this.name = Name;
        this.surname = Surname;
        this.userType = userType;
    }


    public String getDniCara() {
        return dniCara;
    }

    public void setDniCara(String dniCara) {
        this.dniCara = dniCara;
    }
    @Lob
    @Column(name = "dni_cara", columnDefinition = "CLOB")
    public String dniCara;

    public String getDniContracara() {
        return dniContracara;
    }

    public void setDniContracara(String dniContracara) {
        this.dniContracara = dniContracara;
    }

    @Lob
    @Column(name = "dni_contracara", columnDefinition = "CLOB")
    public String dniContracara;

    @OneToMany(mappedBy = "userRegistered", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Registration> carsRegistered = new HashSet<>();


    public Set<Registration> getRegistration () {
        return carsRegistered;
    }
    public void addRegistration(Registration registration) {
        carsRegistered.add(registration);
    }


    public User() {

    }
    // Métodos de route
    public void addRoute(Route route) {
        this.routes.add(route);
        route.setUser(this);
    }
    public void removeRoute(Route route) {
        this.routes.remove(route);
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public Set<Familia> getFamilias() {
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

    public void setDomicilioLongitude(Double domicilioLongitude) {
        this.domicilioLongitude = domicilioLongitude;
    }
    public void setDomicilioLatitude(Double domicilioLatitude) {
        this.domicilioLatitude = domicilioLatitude;
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

    public void addStore(Store store) {
        stores.add(store);
    }

    public Set<Store> getStores() {
        return stores;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Double getDomicilioLongitude() {
        return domicilioLongitude;
    }
    
    public Double getDomicilioLatitude() {
        return domicilioLatitude;
    }
}