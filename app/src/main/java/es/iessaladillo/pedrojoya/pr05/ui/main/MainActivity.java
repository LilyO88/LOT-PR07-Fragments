package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarFragment;
import es.iessaladillo.pedrojoya.pr05.ui.list.ListFragment;
import es.iessaladillo.pedrojoya.pr05.ui.profile.ProfileFragment;


public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModelActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModelActivity = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        if(getSupportFragmentManager().findFragmentByTag(ListFragment.class.getSimpleName()) == null) {
            loadInitialFragment();
        }
        observeUser();
        observeAvatar();
    }

    private void observeUser() {
        viewModelActivity.getUserLiveData().observe(this, user -> {
            if(getSupportFragmentManager().findFragmentByTag(ListFragment.class.getSimpleName()) != null) {
                loadProfileFragment(user);
            }
        });
    }

    private void observeAvatar() {
        viewModelActivity.getAvatarLiveData().observe(this, avatar -> {
            if(getSupportFragmentManager().findFragmentByTag(ListFragment.class.getSimpleName()) != null) {
                loadAvatarFragment(avatar);
            }
        });
    }

    //Load fragments for first time
    private void loadInitialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain, new ListFragment(), ListFragment.class.getSimpleName()).commit();
    }

    private void loadProfileFragment(User user){
        if(!viewModelActivity.listLoaded &&getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName()) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flMain, ProfileFragment.newInstance(user), ProfileFragment.class.getSimpleName())
                    .addToBackStack(ProfileFragment.class.getSimpleName()).commit();

        }

    }

    private void loadAvatarFragment(Avatar avatar){
        if(!viewModelActivity.profileLoaded && getSupportFragmentManager().findFragmentByTag(AvatarFragment.class.getSimpleName()) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flMain, AvatarFragment.newInstance(avatar), AvatarFragment.class.getSimpleName())
                    .addToBackStack(AvatarFragment.class.getSimpleName()).commit();
        }

    }
}