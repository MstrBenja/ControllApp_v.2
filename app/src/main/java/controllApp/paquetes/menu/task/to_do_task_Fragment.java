package controllApp.paquetes.menu.task;

import static controllApp.paquetes.DB.BD.getDatabaseInstance;
import static controllApp.paquetes.inicioUsuario.InicioSesion.listaTareas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import controllApp.paquetes.R;
import com.google.firebase.database.DatabaseReference;

import controllApp.paquetes.DB.DAO;
import controllApp.paquetes.DB.Tareas;

public class to_do_task_Fragment extends Fragment {
    private Button agregarTarea;
    private LinearLayout ly, box;
    private View estructura_tarea;
    private String titulo;
    private String informacion;
    private TextView tituloTarea, infoTarea;
    private CheckBox checked;
    private DAO dao;
    private DatabaseReference dbReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_to_do_task_, container, false);

        // assignation
        ly = (LinearLayout) rootView.findViewById(R.id.taskBox);
        agregarTarea = (Button) rootView.findViewById(R.id.agregarTarea);

        dbReference = getDatabaseInstance(rootView.getContext());
        dao = new DAO(getDatabaseInstance(rootView.getContext()));

        // open other tab
        irAgregarInfo(rootView);

        // method to put all the info all ready created
        try {
            infoGuardada(ly, rootView);
        }catch (Exception E) {
            String mensaje = E.getMessage().toString();
            Toast.makeText(rootView.getContext(), mensaje, Toast.LENGTH_SHORT).show();
        }

        return rootView;

    }


    // this method is created to insert all the info allready created en the bbdd
    public void infoGuardada(LinearLayout taskBox, View rootView){

        if (listaTareas.isEmpty()) {
            Toast.makeText(rootView.getContext(), "No hay nada", Toast.LENGTH_SHORT).show();
        }else{
/*
            try{
                listaTareas = dao.retornarTareas();
            }catch(Exception E){
                Toast.makeText(rootView.getContext(), E.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }*/

            if(!listaTareas.isEmpty()){

                for(Tareas lista : listaTareas){

                    estructura_tarea = getLayoutInflater().inflate(R.layout.estructure_task, null);

                    tituloTarea = (TextView) estructura_tarea.findViewById(R.id.tituloTarea);
                    infoTarea = (TextView) estructura_tarea.findViewById(R.id.informacionTarea);
                    checked = (CheckBox) estructura_tarea.findViewById(R.id.confirmacion);

                    informacion = lista.getInfo();
                    titulo = lista.getTitulo();

                    tituloTarea.setText(titulo);
                    infoTarea.setText(informacion);


                    boolean activo =  lista.estaActivo();

                    if(estructura_tarea.getParent() != null) {
                        ((ViewGroup) estructura_tarea.getParent()).removeView(estructura_tarea);
                    }

                    if(activo){
                        taskBox.addView(estructura_tarea);
                        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                tareaFinalizada(estructura_tarea, taskBox);
                            }
                        });

                    }
                }
            }
        }
    }


    // this method is for open other activity
    public void irAgregarInfo(View view){

        agregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(to_do_task_Fragment.super.getContext(), add_Task.class);
                startActivity(intent);
            }
        });
    }


    public void tareaFinalizada(View estructura_tarea, LinearLayout taskBox){

        CheckBox checked = (CheckBox) estructura_tarea.findViewById(R.id.confirmacion);

        for(Tareas lista : listaTareas){
            if(checked.isChecked()) {
                if (titulo.equals(lista.getTitulo())) {
                    lista.setActivo(false);
                    estructura_tarea.setVisibility(View.GONE);
                }
            }
        }
    }

}