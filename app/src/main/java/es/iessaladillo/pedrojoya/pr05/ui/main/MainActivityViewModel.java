package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;

public class MainActivityViewModel extends ViewModel {

    private Avatar avatar;
    private TextView lblName;
    private TextView lblEmail;
    private TextView lblPhonenumber;
    private TextView lblAddress;
    private TextView lblWeb;
    private ImageView imgEmail;
    private ImageView imgPhonenumber;
    private ImageView imgAddress;
    private ImageView imgWeb;
    private boolean firstInstance = true;

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

    private Database database;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public TextView getLblName() {
        return lblName;
    }

    public void setLblName(TextView lblName) {
        this.lblName = lblName;
    }

    public TextView getLblEmail() {
        return lblEmail;
    }

    public void setLblEmail(TextView lblEmail) {
        this.lblEmail = lblEmail;
    }

    public TextView getLblPhonenumber() {
        return lblPhonenumber;
    }

    public void setLblPhonenumber(TextView lblPhonenumber) {
        this.lblPhonenumber = lblPhonenumber;
    }

    public TextView getLblAddress() {
        return lblAddress;
    }

    public void setLblAddress(TextView lblAddress) {
        this.lblAddress = lblAddress;
    }

    public TextView getLblWeb() {
        return lblWeb;
    }

    public void setLblWeb(TextView lblWeb) {
        this.lblWeb = lblWeb;
    }

    public ImageView getImgEmail() {
        return imgEmail;
    }

    public void setImgEmail(ImageView imgEmail) {
        this.imgEmail = imgEmail;
    }

    public ImageView getImgPhonenumber() {
        return imgPhonenumber;
    }

    public void setImgPhonenumber(ImageView imgPhonenumber) {
        this.imgPhonenumber = imgPhonenumber;
    }

    public ImageView getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(ImageView imgAddress) {
        this.imgAddress = imgAddress;
    }

    public ImageView getImgWeb() {
        return imgWeb;
    }

    public void setImgWeb(ImageView imgWeb) {
        this.imgWeb = imgWeb;
    }
}
