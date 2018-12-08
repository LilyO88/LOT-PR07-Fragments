package es.iessaladillo.pedrojoya.pr05.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<Avatar> avatarLiveData = new MutableLiveData<>();
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    public boolean avatarChanged = false;
    public boolean listLoaded = true;
    public boolean profileLoaded = true;
    public boolean avatarLoaded = false;


    //Avatar
    public LiveData<Avatar> getAvatarLiveData() {
        return avatarLiveData;
    }

    public Avatar getAvatar() {
        return avatarLiveData.getValue();
    }

    public void setAvatar(Avatar avatar) {
        avatarLiveData.setValue(avatar);
    }



   //User
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public User getUser() {
        return userLiveData.getValue();
    }

    public void setUser(User newUser) {
        userLiveData.postValue(newUser);
     }


}
