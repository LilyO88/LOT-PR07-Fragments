package es.iessaladillo.pedrojoya.pr05.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private long id;
    private String name;
    private String email;
    private String phonenumber;
    private String address;
    private String web;
    private Avatar avatar;

    public User(String name, String email, String phonenumber, String address, String web, Avatar avatar) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.web = web;
        this.avatar = avatar;
    }

    public User() {}

    protected User(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.email = in.readString();
        this.phonenumber = in.readString();
        this.address = in.readString();
        this.web = in.readString();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phonenumber);
        dest.writeString(this.address);
        dest.writeString(this.web);
        dest.writeParcelable(this.avatar, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }



    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
