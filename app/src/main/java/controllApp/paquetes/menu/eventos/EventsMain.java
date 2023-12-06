package controllApp.paquetes.menu.eventos;

import static controllApp.paquetes.DB.BD.getDatabaseInstance;
import static controllApp.paquetes.inicioUsuario.InicioSesion.listaEventos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import controllApp.paquetes.R;
import com.google.firebase.database.DatabaseReference;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.DataStatusManager;
import controllApp.paquetes.DB.Events;
import controllApp.paquetes.menu.Menu;

public class EventsMain extends AppCompatActivity {

    //private Chronometer chronometer;
    private LinearLayout primerLayout, segundoLayout, tercerLayout, cuartoLayout, progresoEvento, eventsBox;
    private TableLayout tabla;
    private TableRow primeraFila, segundaFila, terceraFila, cuartaFila;
    private Button agregar;
    private DAO dao;
    private DatabaseReference dbReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Linear Layouts
        primerLayout = (LinearLayout) findViewById(R.id.firstParent);
        segundoLayout = (LinearLayout) findViewById(R.id.secondParent);
        tercerLayout = (LinearLayout) findViewById(R.id.thirdParent);
        cuartoLayout = (LinearLayout) findViewById(R.id.fourthParent);
        progresoEvento = (LinearLayout) findViewById(R.id.showProgressUI);

        // Table Rows
        primeraFila = (TableRow) findViewById(R.id.firstTableRow);
        segundaFila = (TableRow) findViewById(R.id.secondTableRow);
        terceraFila = (TableRow) findViewById(R.id.thirdTableRow);
        cuartaFila = (TableRow) findViewById(R.id.fourthTableRow);
        tabla = (TableLayout) findViewById(R.id.tabla);

        dao = new DAO(getDatabaseInstance(this));
        dbReference = getDatabaseInstance(this);

        // Button
        agregar = (Button) findViewById(R.id.agregar);

        // open activity to add event.
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEvento(v);
            }
        });

        try {
            eventoGuardado();
        }catch (Exception E){
            Toast.makeText(this, E.toString(), Toast.LENGTH_SHORT).show();
        }


    }// onCreate


    public void eventoGuardado(){

        if(listaEventos.isEmpty()){
            Toast.makeText(this, "No hay nada", Toast.LENGTH_SHORT).show();
        }else{

            try{
                dao.retornarEvents(dbReference, new DataStatusManager.GetEvents() {
                    @Override
                    public void onEventsGet(Events events) {
                        listaEventos.add(events);
                    }

                    @Override
                    public void onEventsGetFailed(String errorMessage) {
                        Toast.makeText(EventsMain.this, "Ha ocurrido un error al cargar los eventos", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception E){
                Toast.makeText(this, E.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(listaEventos.isEmpty()){
                Toast.makeText(this, "no hay nada", Toast.LENGTH_SHORT).show();
            }else{
                for (Events lista: listaEventos) {

                    GradientDrawable fondoBoton = new GradientDrawable();
                    fondoBoton.setShape(R.drawable.event_design);

                    View estructura_evento = getLayoutInflater().inflate(R.layout.estructure_event, null);

                    Button evento = (Button) estructura_evento.findViewById(R.id.evento);

                    String texto = lista.getTexto().toString();
                    String color = "";
                    String colorTexto = "";


                    int colorId = verificarColor(lista.getBackground(), evento);

                    evento.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, colorId)));
                    evento.setText(texto);

                    int fila = verificarFila();


                    switch (fila) {
                        case 1:
                            primeraFila.addView(evento);
                            evento.setOnClickListener(new View.OnClickListener() {
                                boolean botonPresionado = false;
                                @Override
                                public void onClick(View v) {
                                    if(botonPresionado){
                                        botonPresionado = false;
                                        eventoPresionado(texto, botonPresionado);
                                    }else{
                                        botonPresionado = true;
                                        eventoPresionado(texto, botonPresionado);
                                    }
                                }
                            });
                            break; case 2: segundaFila.addView(evento);
                            evento.setOnClickListener(new View.OnClickListener() {
                                boolean botonPresionado = false;
                                @Override
                                public void onClick(View v) {
                                    if(botonPresionado){
                                        botonPresionado = false;
                                        eventoPresionado(texto, botonPresionado);
                                    }else{
                                        botonPresionado = true;
                                        eventoPresionado(texto, botonPresionado);
                                    }
                                }
                            });
                            break; case 3: terceraFila.addView(evento);
                            evento.setOnClickListener(new View.OnClickListener() {
                                boolean botonPresionado = false;
                                @Override
                                public void onClick(View v) {
                                    if(botonPresionado){
                                        botonPresionado = false;
                                        eventoPresionado(texto, botonPresionado);
                                    }else{
                                        botonPresionado = true;
                                        eventoPresionado(texto, botonPresionado);
                                    }
                                }
                            });
                            break; case 4: cuartaFila.addView(evento);
                            evento.setOnClickListener(new View.OnClickListener() {
                                boolean botonPresionado = false;
                                @Override
                                public void onClick(View v) {
                                    if(botonPresionado){
                                        botonPresionado = false;
                                        eventoPresionado(texto, botonPresionado);
                                    }else{
                                        botonPresionado = true;
                                        eventoPresionado(texto, botonPresionado);
                                    }
                                }
                            });
                            break;
                    }
                }
            }
        }

    }

    public int verificarColor(String color, Button boton){

        int colorId = 0;

        switch (color) {
            case "Rojo":
                colorId = R.color.red;
                break;

            case "Amarillo":
                colorId = R.color.yellow;
                break;

            case "Verde":
                colorId = R.color.green;
                break;

            case "Negro":
                colorId = R.color.black;
                break;

            case "Azul":
                colorId = R.color.blue;
                break;

            case "Morado":
                colorId = R.color.purple;
                break;

            case "Naranjo":
                colorId = R.color.orange;
                break;

            case "Rosado":
                colorId = R.color.pink;
                break;

            case "Celeste":
                colorId = R.color.sky;
                break;
        }

        return colorId;
    }

    public int verificarFila(){
        if (primeraFila.getChildCount() <= 4){
           return 1;
        }else if (segundaFila.getChildCount() <= 4){
            return 2;
        }else if (terceraFila.getChildCount() <= 4){
            return 3;
        }else {
            return 4;
        }
    }

    public void eventoPresionado(String tituloEvento, boolean botonPresionado) {
        eventsBox = (LinearLayout) findViewById(R.id.eventsBox);

        View evento_activo = getLayoutInflater().inflate(R.layout.estructure_active_event, null);
        TextView title = evento_activo.findViewById(R.id.tituloEvents);
        TextView information = evento_activo.findViewById(R.id.infoEvents);

        if(botonPresionado){
            title.setText(tituloEvento);
            information.setText("Acá debería ir un cronometro de monitorización de datos");

            eventsBox.addView(evento_activo);
        }else{
            if (eventsBox.getChildCount() > 0) {
                eventsBox.removeViewAt(0);
            }

        }
    }

    public void aMenuFromEvents(View v){
        Intent menu = new Intent(this, Menu.class);
        startActivity(menu);
    }// method

    public void agregarEvento(View view){

        Intent intent = new Intent(this, add_Event.class);
        startActivity(intent);
        finish();
    }

}// class