package controllApp.paquetes.inicioUsuario;


import static controllApp.paquetes.DB.BD.getDatabaseInstance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import controllApp.paquetes.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.UUID;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.DataStatusManager;
import controllApp.paquetes.DB.User;

public class Registro extends AppCompatActivity {

    // Buttons
    private Button goBack, register;

    // Texts
    private TextView titulo, generoText;
    private EditText name, email, age, userName, password;

    // other
    private Spinner gender;
    private ScrollView diseno;
    private boolean respuesta;
    public DatabaseReference dbReference;
    private DAO dao;
    private boolean verif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        // Texts
        name = (EditText) findViewById(R.id.nombreUI);
        email = (EditText) findViewById(R.id.emailUI);
        age = (EditText) findViewById(R.id.edadUI);
        gender = (Spinner) findViewById(R.id.generoUI);
        userName = (EditText) findViewById(R.id.userNameUI);
        password = (EditText) findViewById(R.id.contrasenhaUI);

        // buttons
        register = (Button) findViewById(R.id.registrarseUI);
        goBack = (Button) findViewById(R.id.goBackUI);

        // Spinner
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combo_genero , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        dbReference = getDatabaseInstance(this);
        dao = new DAO(getDatabaseInstance(this));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar(view);
            }
        });// onclick back to sing up

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar(view);
            }
        });// onclick registrar


    }// oncreate

    private void registrar(View view){

        int posicion = gender.getSelectedItemPosition();
        String correo = email.getText().toString();
        String nombre = name.getText().toString();
        String username = userName.getText().toString();
        String contra = password.getText().toString();

        if(correo.isEmpty() ||
                nombre.isEmpty() ||
                Integer.parseInt(age.getText().toString()) < 0 ||
                posicion == 0 ||
                username.isEmpty() || contra.isEmpty()){

            Toast.makeText(this, "Todos los campos deben estar rellenados", Toast.LENGTH_SHORT).show();

        }else{

            User usuario = new User();
            usuario.setId(UUID.randomUUID().toString());
            usuario.setNombre(name.getText().toString());
            usuario.setCorreo(email.getText().toString());
            usuario.setEdad(Integer.parseInt(age.getText().toString()));
            usuario.setGender(gender.getSelectedItem().toString());
            usuario.setUserName(userName.getText().toString());
            usuario.setPassword(password.getText().toString());

            verif = true;

            try {
                verif = isRegistered(usuario);
            }catch (Exception E){
                Toast.makeText(this, E.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

            if(!verif){

                try {
                    dao.registrarUser(dbReference, usuario, new DataStatusManager.WriteUsers() {
                        @Override
                        public void onUsersWriteSuccess() {
                            AlertDialog.Builder mensaje = new AlertDialog.Builder(Registro.this);
                            mensaje.setCancelable(true);
                            mensaje.setTitle("Felicidades "+ usuario.getNombre());
                            mensaje.setMessage("Has sido registrado satisfactoriamente!");
                            mensaje.show();
                        }

                        @Override
                        public void onUsersWriteFailure(String errorMessage) {
                            Toast.makeText(Registro.this, "No se ha podido guardar tu usuario...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception E){
                    Toast.makeText(this, E.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        regresar(new View(view.getContext()));
                    }
                },5000);

            }else{

                Toast.makeText(this, "Ya estás registrado", Toast.LENGTH_SHORT).show();

            }// else
        }// else
    }// method


    public boolean isRegistered(User usuario){

        verif = false;

        dao.retornarUsers(dbReference, new DataStatusManager.ReadUsers() {
            @Override
            public void onUsersLoaded(List<User> listaUsuarios) {
                if (listaUsuarios == null){
                    verif = false;
                }else{
                    for(User list : listaUsuarios){
                        if(usuario.getUserName() == list.getUserName() &&
                                usuario.getPassword() == list.getPassword() &&
                                usuario.getNombre() == list.getNombre()){

                            verif = true;
                        }
                    }
                }
            }

            @Override
            public void onUsersLoadFailed(String errorMessage) {

            }
        });

        return verif;

    }

    private void regresar(View view){
        Intent regresar = new Intent(this, InicioSesion.class);
        startActivity(regresar);
        finish();
    }// method

}// class