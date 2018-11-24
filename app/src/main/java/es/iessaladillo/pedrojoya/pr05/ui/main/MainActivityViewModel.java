package es.iessaladillo.pedrojoya.pr05.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityViewModel extends ViewModel {

    private final Database database;

    private LiveData<List<User>> users;

    public MainActivityViewModel(Database database) {
        this.database = database;
    }

    public LiveData<List<User>> getUsers() {
        if(users == null) {
            users = database.getUsers();
        }
        return users;
    }

    public void deleteUser(User user) {
        database.deleteUser(user);
    }

    public void editUser(User user) {
        database.editUser(user);
    }

    public void addUser(User user) {
        database.addUser(user);
    }
}
