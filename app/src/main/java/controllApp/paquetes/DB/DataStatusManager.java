package controllApp.paquetes.DB;

import java.util.List;

public class DataStatusManager {
    //interfaces para usuarios
    public interface ReadUsers {
        void onUsersLoaded(List<User> listaUsuarios);

        void onUsersLoadFailed(String errorMessage);
    }

    public interface WriteUsers {
        void onUsersWriteSuccess();

        void onUsersWriteFailure(String errorMessage);
    }


    public interface ReadTasks {
        void onTaskLoaded(List<Tareas> listaTareas);

        void onTaskLoadFailed(String errorMessage);
    }

    public interface WriteTasks {
        void onTaskWriteSuccess();

        void onTaskWriteFailure(String errorMessage);
    }

    public interface GetTasks {
        void onTasksGet(Tareas tareas);

        void onTasksGetFailed(String errorMessage);
    }

    public interface ReadEvents {
        void onEventsLoaded(List<Events> listaEventos);

        void onEventsLoadFailed(String errorMessage);
    }

    public interface WriteEvents {
        void onEventsWriteSuccess();

        void onEventsWriteFailure(String errorMessage);
    }

    public interface GetEvents {
        void onEventsGet(Events events);

        void onEventsGetFailed(String errorMessage);
    }

}