package controllApp.paquetes.DB;

import android.widget.CheckBox;

public class Tareas {

    private String id;
    private String titulo;
    private String info;
    private boolean activo;

    public Tareas (String id, String title, String info, boolean active){
        this.id = id;
        this.titulo = title;
        this.info = info;
        this.activo = active;
    }

    public boolean estaActivo(){
        return activo;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
