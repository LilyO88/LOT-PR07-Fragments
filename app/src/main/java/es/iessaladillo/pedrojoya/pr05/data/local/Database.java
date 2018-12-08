package es.iessaladillo.pedrojoya.pr05.data.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;


// DO NOT TOUCH

public class Database {

    private static Database instance;

    private final ArrayList<Avatar> avatars = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private long count;
    private long countUser;
    private final Random random = new Random(1);

    private Database() {
        insertAvatar(new Avatar(R.drawable.cat1, "Tom"));
        insertAvatar(new Avatar(R.drawable.cat2, "Luna"));
        insertAvatar(new Avatar(R.drawable.cat3, "Simba"));
        insertAvatar(new Avatar(R.drawable.cat4, "Kitty"));
        insertAvatar(new Avatar(R.drawable.cat5, "Felix"));
        insertAvatar(new Avatar(R.drawable.cat6, "Nina"));

        insertUser(new User("Baldo", "baldo@mero.com", "666666666", "Avenida Baldomero", "www.marca.com", queryAvatar(1)));
        insertUser(new User("Germán Ginés", "german@mero.com", "776666666", "Avenida Germán Ginés", "www.marca.com", queryAvatar(2)));
        insertUser(new User("Dolores Fuertes de Barriga", "dolores@fuertes.com", "886666666", "Avenida Dolores", "www.marca.com", queryAvatar(3)));

        updateUsersLiveData();
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    @VisibleForTesting()
    public void insertAvatar(Avatar avatar) {
        long id = ++count;
        avatar.setId(id);
        avatars.add(avatar);
    }

    private void insertUser(User user) {
        long id = ++countUser;
        user.setId(id);
        users.add(user);
    }

    public Avatar getRandomAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(random.nextInt(avatars.size()));
    }

    public Avatar getDefaultAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(0);
    }

    public List<Avatar> queryAvatars() {
        return new ArrayList<>(avatars);
    }

    public Avatar queryAvatar(long id) {
        for (Avatar avatar: avatars) {
            if (avatar.getId() == id) {
                return avatar;
            }
        }
        return null;
    }

    @VisibleForTesting
    public void setAvatars(List<Avatar> list) {
        count = 0;
        avatars.clear();
        avatars.addAll(list);
    }

    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    private void updateUsersLiveData() {
        usersLiveData.setValue(new ArrayList<>(users));
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void deleteUser(User user) {
        users.remove(user);
        updateUsersLiveData();
    }

    public void addUser(User user) {
        insertUser(user);
        updateUsersLiveData();
    }

    public void editUser(User newUser){
        for(int i = 0; i < users.size() ; i++) {
            if(users.get(i).getId() == newUser.getId()) {
                users.set(i, newUser);
                i = users.size();
            }
        }
        updateUsersLiveData();
    }

}