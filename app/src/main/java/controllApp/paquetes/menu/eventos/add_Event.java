package controllApp.paquetes.menu.eventos;


import static controllApp.paquetes.DB.BD.getDatabaseInstance;
import static controllApp.paquetes.inicioUsuario.InicioSesion.listaEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import controllApp.paquetes.R;
import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.DataStatusManager;
import controllApp.paquetes.DB.Events;

public class add_Event extends AppCompatActivity {

    private EditText nombreEvento;
    private Button agregar;
    private Spinner colores;
    private DatabaseReference dbReference;
    private DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        dbReference = getDatabaseInstance(this);
        dao = new DAO(getDatabaseInstance(this));

        nombreEvento = (EditText) findViewById(R.id.eventName);
        agregar = (Button) findViewById(R.id.agregar);
        colores = (Spinner) findViewById(R.id.colores);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combo_colores , androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        colores.setAdapter(adapter);

        guardarEvento();

    }

    public void guardarEvento(){

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombreEvento.equals("") || colores.getSelectedItemPosition() == 0){
                    Toast.makeText(add_Event.this, "Nada debe quedar en blanco!!", Toast.LENGTH_SHORT).show();
                }else{
                    String nombre = nombreEvento.getText().toString();
                    String color = colores.getSelectedItem().toString();

                    try {
                        Events evento = new Events(UUID.randomUUID().toString(), nombre, color);
                        dao.registrarEvent(dbReference, evento, new DataStatusManager.WriteEvents() {
                            @Override
                            public void onEventsWriteSuccess() {
                                Toast.makeText(add_Event.this, "Se ha guardado el evento!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEventsWriteFailure(String errorMessage) {
                                Toast.makeText(add_Event.this, "No se ha podido guardar el evento!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        listaEventos.add(evento);
                    }catch (Exception E){
                        Toast.makeText(add_Event.this, E.toString(), Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(add_Event.this, EventsMain.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}