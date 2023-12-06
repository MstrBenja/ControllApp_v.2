package controllApp.paquetes.DB;

import com.google.firebase.annotations.concurrent.Background;

public class Events {

    private String id;
    private String texto;
    private String background;

    public Events(String id, String texto, String background){
        this.id = id;
        this.texto = texto;
        this.background = background;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
