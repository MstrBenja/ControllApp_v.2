package controllApp.paquetes.DB;

import java.util.List;

public class User {

    private String id;
    private String correo;
    private String nombre;
    private int edad;
    private String gender;
    private String userName;
    private String password;
    private List<Tareas> tareas;
    private List<Events> eventos;

    public User (String id, String gmail, String name, int age, String gender, String user, String psw){
        this.id = id;
        this.correo = gmail;
        this.nombre = name;
        this.edad = age;
        this.gender = gender;
        this.userName = user;
        this.password = psw;
    }

    public User(String name, String psw){
        this.userName = name;
        this.password = psw;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Tareas> getTareas() {
        return tareas;
    }

    public void setTareas(Tareas tareas) {
        this.tareas.add(tareas);
    }

    public List<Events> getEventos() {
        return eventos;
    }

    public void setEventos(Events eventos) {
        this.eventos.add(eventos);
    }
}
