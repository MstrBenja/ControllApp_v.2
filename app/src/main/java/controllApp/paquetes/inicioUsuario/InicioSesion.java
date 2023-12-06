package controllApp.paquetes.inicioUsuario;

import static controllApp.paquetes.DB.BD.getDatabaseInstance;
import static controllApp.paquetes.DB.BD.inicializarFirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.DataStatusManager;
import controllApp.paquetes.DB.Events;
import controllApp.paquetes.DB.Tareas;
import controllApp.paquetes.DB.User;
import controllApp.paquetes.MQTT.mqttHandler;
import controllApp.paquetes.R;
import controllApp.paquetes.menu.Menu;

public class InicioSesion extends AppCompatActivity {

    // Layout
    private Button login, register;
    private EditText usuarioUI, contrasenha;
    private Switch modo;
    private TextView titulo, comentario;
    private ConstraintLayout disenho;



    // info in the code
    private List<User> listUser;

    public static List<Tareas> listaTareas;
    public static List<Events> listaEventos;

    // DB
    private DatabaseReference dbReference;
    public DAO dao;
    private boolean verif;


    // MQTT ======================================================================
    private static final String BROKER_URL = "tcp://androidteststiqq.cloud.shiftr.io:1883";
    private static final String CLIENT_ID = "ControllApp";
    private mqttHandler mqttHandler;
    // MQTT ======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);


        listaTareas = new ArrayList<Tareas>();
        listaEventos = new ArrayList<Events>();

        // MQTT ====================================================================
        try{
            mqttHandler = new mqttHandler();
            mqttHandler.connect(BROKER_URL,CLIENT_ID, this);

            subscribeToTopic("Tema1");
            publishMessage("Tema1", "a");
        }catch (Exception E){
            String mensaje = E.getMessage().toString();
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
        // MQTT ====================================================================

        // buttons
        login = (Button) findViewById(R.id.loginUI);
        register = (Button) findViewById(R.id.registerUI);

        // text, passwords
        usuarioUI = (EditText) findViewById(R.id.userUI);
        contrasenha = (EditText) findViewById(R.id.passwordUI);

        // other
        modo = (Switch) findViewById(R.id.modeUI);
        titulo = (TextView) findViewById(R.id.titleUI);
        comentario = (TextView) findViewById(R.id.commentUI);
        disenho = (ConstraintLayout) findViewById(R.id.disenho);


        // DB
        inicializarFirebase(this);
        dbReference = getDatabaseInstance(this);
        dao = new DAO(getDatabaseInstance(this));

        // register onclick function
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irRegistro(view);
            }
        });

        // login onclick function
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser(view);
            }
        });

        // night/day mode onlick function
        modo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarModo(view, InicioSesion.this);
            }
        });

    }//onCreate


    @Override
    protected void onDestroy() {
        mqttHandler.disconnect();
        super.onDestroy();
    }

    private void irRegistro(View v){
        Intent siguiente = new Intent(this, Registro.class);
        startActivity(siguiente);
        finish();
    }//method

    private void verifyUser(View v){

        String userName = usuarioUI.getText().toString();
        String psw = contrasenha.getText().toString();


        if(userName.isEmpty() || psw.isEmpty()){
            Toast.makeText(this, "No debe estar nada vacio", Toast.LENGTH_SHORT).show();
        }else{

            User usuario = new User(userName, psw);

            boolean verif = estaRegistrado(usuario);

            if(verif){
                dao.usuarioConectado(usuario);
                irMenu(v);
            }else{

                Toast.makeText(this, "No existe este usuario", Toast.LENGTH_SHORT).show();
                usuarioUI.setText("");
                contrasenha.setText("");
            }
        }
    }//method

    public boolean estaRegistrado(User usuario){

        verif = false;

        dao.retornarUsers(dbReference, new DataStatusManager.ReadUsers() {
            @Override
            public void onUsersLoaded(List<User> listaUsuarios) {
                if(listaUsuarios == null){
                    verif = false;
                }else{
                    for(User list : listaUsuarios){
                        if(usuario.getUserName() == list.getUserName() &&
                                usuario.getPassword() == list.getPassword()){
                            verif = true;
                            return;
                        }
                    }
                }
            }

            @Override
            public void onUsersLoadFailed(String errorMessage) {
                Toast.makeText(InicioSesion.this, "Error al cargar la lista de Usuarios", Toast.LENGTH_SHORT).show();
            }
        });
        return verif;
    }

    private void cambiarModo(View v, Activity context){

        if(modo.isChecked()){
            disenho.setBackgroundColor(getResources().getColor(R.color.black));
            titulo.setTextColor(getResources().getColor(R.color.white));
            comentario.setTextColor(getResources().getColor(R.color.white));
            usuarioUI.setTextColor(getResources().getColor(R.color.white));
            usuarioUI.setHintTextColor(context.getResources().getColor(R.color.white));
            contrasenha.setTextColor(getResources().getColor(R.color.white));
            contrasenha.setHintTextColor(context.getResources().getColor(R.color.white));
            modo.setTextColor(getResources().getColor(R.color.white));
        }else{
            disenho.setBackgroundColor(getResources().getColor(R.color.white));
            titulo.setTextColor(getResources().getColor(R.color.black));
            comentario.setTextColor(getResources().getColor(R.color.black));
            usuarioUI.setTextColor(getResources().getColor(R.color.black));
            usuarioUI.setHintTextColor(context.getResources().getColor(R.color.black));
            contrasenha.setTextColor(getResources().getColor(R.color.black));
            contrasenha.setHintTextColor(context.getResources().getColor(R.color.black));
            modo.setTextColor(getResources().getColor(R.color.black));
        }
    }//method

    public void irMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    // MQTT ======================================================================
    private void publishMessage(String topic, String message){
        Toast.makeText(this, "Publishing message: " + message, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic,message, this);
    }
    private void subscribeToTopic(String topic){
        Toast.makeText(this, "Subscribing to topic "+ topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);
    }
    // MQTT ====================================================================

}//class