package es.iessaladillo.pedrojoya.pr05.ui.profile;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class ProfileFragmentViewModel extends ViewModel {

    private Avatar avatar;

    private User user;

    private TextView lblName;
    private boolean stateName = true;

    private TextView lblEmail;
    private ImageView imgEmail;
    private boolean stateEmail = true;
    private boolean stateImgEmail = true;

    private TextView lblPhonenumber;
    private ImageView imgPhonenumber;
    private boolean statePhonenumber = true;
    private boolean stateImgPhonenumber = true;

    private TextView lblAddress;
    private ImageView imgAddress;
    private boolean stateAddress = true;
    private boolean stateImgAddress = true;

    private TextView lblWeb;
    private ImageView imgWeb;
    private boolean stateWeb = true;
    private boolean stateImgWeb = true;

    private Database database;

    private boolean firstInstance = true;

    private boolean haGirado = false;

    public boolean isHaGirado() {
        return haGirado;
    }

    public void setHaGirado(boolean haGirado) {
        this.haGirado = haGirado;
    }

    public Database getDatabase() {
        if(database == null){
            database = Database.getInstance();
        }
        return database;
    }

    public void setDefaultAvatar(){
        if(firstInstance){
            setAvatar(getDatabase().getDefaultAvatar());
            firstInstance = false;
        }
    }

    //Avatar
    public Avatar getAvatar() {
        return avatar;
    }
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    //Name
    public TextView getLblName() {
        return lblName;
    }
    public void setLblName(TextView lblName) {
        this.lblName = lblName;
    }

    public void setStateName(boolean stateName) {
        this.stateName = stateName;
    }
    public boolean isStateName() {
        return stateName;
    }


    //Email
    public TextView getLblEmail() {
        return lblEmail;
    }
    public void setLblEmail(TextView lblEmail) {
        this.lblEmail = lblEmail;
    }

    public ImageView getImgEmail() {
        return imgEmail;
    }
    public void setImgEmail(ImageView imgEmail) {
        this.imgEmail = imgEmail;
    }

    public boolean isStateEmail() {
        return stateEmail;
    }
    public void setStateEmail(boolean stateEmail) {
        this.stateEmail = stateEmail;
    }

    public boolean isStateImgEmail() {
        return stateImgEmail;
    }
    public void setStateImgEmail(boolean stateImgEmail) {
        this.stateImgEmail = stateImgEmail;
    }


    //Phonenumber
    public TextView getLblPhonenumber() {
        return lblPhonenumber;
    }
    public void setLblPhonenumber(TextView lblPhonenumber) {
        this.lblPhonenumber = lblPhonenumber;
    }

    public ImageView getImgPhonenumber() {
        return imgPhonenumber;
    }
    public void setImgPhonenumber(ImageView imgPhonenumber) {
        this.imgPhonenumber = imgPhonenumber;
    }

    public boolean isStatePhonenumber() {
        return statePhonenumber;
    }
    public void setStatePhonenumber(boolean statePhonenumber) {
        this.statePhonenumber = statePhonenumber;
    }

    public boolean isStateImgPhonenumber() {
        return stateImgPhonenumber;
    }
    public void setStateImgPhonenumber(boolean stateImgPhonenumber) {
        this.stateImgPhonenumber = stateImgPhonenumber;
    }


    //Address
    public TextView getLblAddress() {
        return lblAddress;
    }
    public void setLblAddress(TextView lblAddress) {
        this.lblAddress = lblAddress;
    }

    public ImageView getImgAddress() {
        return imgAddress;
    }
    public void setImgAddress(ImageView imgAddress) {
        this.imgAddress = imgAddress;
    }

    public void setStateAddress(boolean stateAddress) {
        this.stateAddress = stateAddress;
    }
    public boolean isStateAddress() {
        return stateAddress;
    }

    public boolean isStateImgAddress() {
        return stateImgAddress;
    }
    public void setStateImgAddress(boolean stateImgAddress) {
        this.stateImgAddress = stateImgAddress;
    }


    //Web
    public TextView getLblWeb() {
        return lblWeb;
    }
    public void setLblWeb(TextView lblWeb) {
        this.lblWeb = lblWeb;
    }

    public ImageView getImgWeb() {
        return imgWeb;
    }
    public void setImgWeb(ImageView imgWeb) {
        this.imgWeb = imgWeb;
    }

    public void setStateWeb(boolean stateWeb) {
        this.stateWeb = stateWeb;
    }
    public boolean isStateWeb() {
        return stateWeb;
    }

    public boolean isStateImgWeb() {
        return stateImgWeb;
    }
    public void setStateImgWeb(boolean stateImgWeb) {
        this.stateImgWeb = stateImgWeb;
    }

    //User
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
