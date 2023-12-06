package controllApp.paquetes.DB;


import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO {

    private List<User> listUser;
    private List<Tareas> listTask;
    private List<Events> listEvents;
    private DatabaseReference dbReference;
    private boolean verificacion = false;
    private User usuarioConectado;

    public DAO(DatabaseReference dbReference){

        listUser = new ArrayList<User>();
        listTask = new ArrayList<Tareas>();
        listEvents = new ArrayList<Events>();
        this.dbReference = dbReference;
    }



    public void usuarioConectado(User usuario){


        retornarUsers(dbReference, new DataStatusManager.ReadUsers() {
            @Override
            public void onUsersLoaded(List<User> listaUsuarios) {
                for(User lista : listaUsuarios){
                    if(usuario.getUserName() == lista.getUserName() &&
                            usuario.getPassword() == lista.getPassword() &&
                            usuario.getNombre() == lista.getNombre()){

                        DAO.this.usuarioConectado = lista;
                    }
                }
            }

            @Override
            public void onUsersLoadFailed(String errorMessage) {

            }
        });
    }

    public User getUsuarioConectado(){
        return usuarioConectado;
    }


    public void retornarUsers(DatabaseReference dbReference, final DataStatusManager.ReadUsers readDataStatus ) {

        dbReference.child("Usuarios").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear();
                for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    User usuario = objSnapshot.getValue(User.class);
                    listUser.add(usuario);
                }
                readDataStatus.onUsersLoaded(listUser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                readDataStatus.onUsersLoadFailed(error.getMessage());
            }
        });
    }// method



    public void registrarUser(DatabaseReference dbReference,User user, final DataStatusManager.WriteUsers writeUserStatus) {

        DatabaseReference usersRef = dbReference.child("Usuarios");

        usersRef.orderByChild("id").equalTo(user.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                if(snapshot.exists()){
                    writeUserStatus.onUsersWriteFailure("El usuario ya está registrado");
                }else{
                    usersRef.child(user.getId()).setValue(user);
                    writeUserStatus.onUsersWriteSuccess();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }// method



    public void registrarTask(DatabaseReference dbReference, Tareas tarea, final DataStatusManager.WriteTasks writeTasks) {

        DatabaseReference usuarioRef = dbReference.child("Usuarios").child(usuarioConectado.getId());

        usuarioRef.child("tareas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTask.clear();
                // if theres existing task, add them to the list
                if (dataSnapshot.exists()) {
                    for (DataSnapshot tareaSnapshot : dataSnapshot.getChildren()) {
                        Tareas tareaExistente = tareaSnapshot.getValue(Tareas.class);
                        listTask.add(tareaExistente);
                    }
                }
                writeTasks.onTaskWriteSuccess();

                // add the new task to the list
                listTask.add(tarea);

                // update the list of tasks of the user
                usuarioRef.child("tareas").setValue(listTask);

                // aving the task in a individual "table"
                dbReference.child("Tareas").child(tarea.getId()).setValue(tarea);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }// method




    public void retornarTask(DatabaseReference dbReference, final DataStatusManager.GetTasks getTasks) {

        DatabaseReference usuarioRef = dbReference.child("Usuarios").child(usuarioConectado.getId());

        usuarioRef.child("tareas").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    listTask.clear();
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        Tareas tarea = objSnapshot.getValue(Tareas.class);
                        getTasks.onTasksGet(tarea);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }// method




    public void registrarEvent(DatabaseReference dbReference, Events evento, final DataStatusManager.WriteEvents writeEvents){

        DatabaseReference usuarioRef = dbReference.child("Usuarios").child(usuarioConectado.getId());

        usuarioRef.child("eventos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEvents = new ArrayList<>();

                // if theres existing task, add them to the list
                if (dataSnapshot.exists()) {
                    for (DataSnapshot tareaSnapshot : dataSnapshot.getChildren()) {
                        Events eventoExistente = tareaSnapshot.getValue(Events.class);
                        listEvents.add(eventoExistente);
                    }
                }
                // update the list of events of the user
                usuarioRef.child("eventos").setValue(listEvents);

                // aving the task in a individual "table"
                dbReference.child("Eventos").child(evento.getId()).setValue(evento);

                writeEvents.onEventsWriteSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public List<Events> retornarEvents(DatabaseReference dbReference, final DataStatusManager.GetEvents getEvents){

        DatabaseReference usuarioRef = dbReference.child("Usuarios").child(usuarioConectado.getId());

        usuarioRef.child("Eventos").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                        Events eventos = objSnapshot.getValue(Events.class);
                        getEvents.onEventsGet(eventos);
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return listEvents;
    }

}// class
