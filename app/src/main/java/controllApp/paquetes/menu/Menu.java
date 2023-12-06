package controllApp.paquetes.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import controllApp.paquetes.R;
import controllApp.paquetes.menu.eventos.EventsMain;
import controllApp.paquetes.menu.info.Information;
import controllApp.paquetes.menu.task.Tasks;


public class Menu extends AppCompatActivity {

    private Button info, eventos, tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        info = (Button) findViewById(R.id.infoButton);
        eventos = (Button) findViewById(R.id.eventosButton);
        tareas = (Button) findViewById(R.id.tareasButton);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(Menu.this, Information.class);
                startActivity(info);
            }
        });

        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent events = new Intent(Menu.this, EventsMain.class);
                startActivity(events);
            }
        });

        tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tasks = new Intent(Menu.this, Tasks.class);
                startActivity(tasks);
            }
        });

    }// oncreate

    public void irEventos(View v){
        Intent events = new Intent(Menu.this, EventsMain.class);
        startActivity(events);
    }// method

    public void irInfo(View view){
        Intent info = new Intent(Menu.this, Information.class);
        startActivity(info);
    }// method

    public void irTareas(View view){
        Intent tasks = new Intent(this, Tasks.class);
        startActivity(tasks);
    }// method

}// class