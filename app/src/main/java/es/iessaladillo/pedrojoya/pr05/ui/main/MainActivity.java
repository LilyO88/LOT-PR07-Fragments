package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.ActivityMainBinding;
import es.iessaladillo.pedrojoya.pr05.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private MainActivityAdapter listAdapter;
    private ActivityMainBinding b;

    private final int RC_EDIT = 2;
    private final int RC_ADD = 3;

    private final Database database = Database.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, new MainActivityViewModelFactory(database)).get(MainActivityViewModel.class);
        initViews();
        observeUsers();
    }

    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.mainLblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void initViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(position -> editUser(listAdapter.getItem(position)), position -> viewModel.deleteUser(listAdapter.getItem(position)));
        //Security measure: fixed size
        b.mainLstUsers.setHasFixedSize(true);
        b.mainLstUsers.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.main_lstStudents_columns)));

        b.mainLstUsers.setAdapter(listAdapter);

        View.OnClickListener listener = v -> addUser();

        b.mainFab.setOnClickListener(listener);
        b.mainLblEmptyView.setOnClickListener(listener);
    }

    private void addUser() {
        ProfileActivity.startForResult(MainActivity.this, RC_ADD, null);
    }

    private void editUser(User user) {
        ProfileActivity.startForResult(MainActivity.this, RC_EDIT, user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && (requestCode == RC_EDIT || requestCode == RC_ADD)) {
            obtainResponseData(data, requestCode);
        }
    }

    private void obtainResponseData(Intent intent, int requestCode) {
        User user;
        if(intent != null && intent.hasExtra(ProfileActivity.EXTRA_USER)) {
            user = intent.getParcelableExtra(ProfileActivity.EXTRA_USER);
            if(requestCode == RC_EDIT) {
                viewModel.editUser(user);
            } else if(requestCode == RC_ADD) {
                viewModel.addUser(user);
            }
        }
    }
}
