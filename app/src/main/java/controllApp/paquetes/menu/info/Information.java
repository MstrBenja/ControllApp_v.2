package controllApp.paquetes.menu.info;


import static controllApp.paquetes.DB.BD.getDatabaseInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import controllApp.paquetes.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.Events;
import controllApp.paquetes.DB.Tareas;
import controllApp.paquetes.DB.User;
import controllApp.paquetes.menu.Menu;

public class Information extends AppCompatActivity {

    private TextView infoNombre, infoApp;
    private DAO dao;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        infoNombre = (TextView) findViewById(R.id.infoNombreUsuario);
        infoApp = (TextView) findViewById(R.id.infoApp);

        dbReference = getDatabaseInstance(this);
        dao = new DAO(dbReference);

    }// onCreate

    public void agregarInformación(){

        User usuario = dao.getUsuarioConectado();

        infoNombre.setText("Información de "+usuario.getUserName()+"\n"+
                "Mejor conocido como: "+usuario.getNombre()+"\n" +
                "Genero: "+usuario.getGender()+"\n" +
                "Edad: "+usuario.getEdad()+"\n" +
                "Correo: "+usuario.getCorreo()+"\n" +
                "(Tu ID es este:): "+usuario.getId());

        List<Tareas> listaTareas = usuario.getTareas();
        List<Events> listaEventos = usuario.getEventos();

        int i = 0;
        int complete = 0;
        int incomplete = 0;
        int eventos = 0;
        for(Tareas lista: listaTareas){
            i++;
            if(lista.estaActivo()){
                incomplete++;
            }else{
                complete++;
            }
        }

        for(Events lista: listaEventos){
            eventos++;
        }

        infoApp.setText("Cantidad de tareas agregadas: "+i+"\n" +
                "Tareas completadas :"+complete+"\n" +
                "Tareas incompletas: "+incomplete+"\n"+"\n" +
                "Eventos creados :"+eventos);
    }

    public void aMenuFromInfo(View v){
        Intent menu = new Intent(this, Menu.class);
        startActivity(menu);
    }// method
}// class