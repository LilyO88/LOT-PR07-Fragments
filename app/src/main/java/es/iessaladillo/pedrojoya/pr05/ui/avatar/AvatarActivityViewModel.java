package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class AvatarActivityViewModel extends ViewModel {

    private Avatar avatar;
    private boolean isNotFirstTime = false;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public boolean isNotFirstTime() {
        return isNotFirstTime;
    }

    public void setNotFirstTime(boolean notFirstTime) {
        isNotFirstTime = notFirstTime;
    }
}
