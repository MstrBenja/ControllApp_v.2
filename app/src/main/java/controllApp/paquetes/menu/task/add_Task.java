package controllApp.paquetes.menu.task;


import static controllApp.paquetes.DB.BD.getDatabaseInstance;
import static controllApp.paquetes.inicioUsuario.InicioSesion.listaTareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import controllApp.paquetes.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.UUID;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.DataStatusManager;
import controllApp.paquetes.DB.Tareas;

public class add_Task extends AppCompatActivity {

    private EditText titulo;
    private TextInputEditText info;
    private Button agregar;
    private DatabaseReference dbReference;

    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbReference = getDatabaseInstance(this);
        dao = new DAO(getDatabaseInstance(this));

        titulo = (EditText) findViewById(R.id.tituloName);
        info = (TextInputEditText) findViewById(R.id.informacion);
        agregar = (Button) findViewById(R.id.agregarTareaUI);

        guardarTarea();

    }

    public void guardarTarea(){

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(titulo.equals("") || info.equals("")){
                    Toast.makeText(add_Task.this, "Nada debe quedar en blanco!!", Toast.LENGTH_SHORT).show();
                }else{
                    String title = titulo.getText().toString();
                    String information = info.getText().toString();

                    try {
                        Tareas tarea = new Tareas(UUID.randomUUID().toString(),title, information, true);
                        dao.registrarTask(dbReference, tarea, new DataStatusManager.WriteTasks() {
                            @Override
                            public void onTaskWriteSuccess() {
                                Toast.makeText(add_Task.this, "Se ha guardado tu tarea!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onTaskWriteFailure(String errorMessage) {
                                Toast.makeText(add_Task.this, "No se ha podido guardar tu tare!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        listaTareas.add(tarea);
                    }catch (Exception E){
                        Toast.makeText(add_Task.this, E.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(add_Task.this, Tasks.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}