package es.iessaladillo.pedrojoya.pr05.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentListBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;

public class ListFragment extends Fragment {

    private FragmentListBinding b;
    private ListFragmentAdapter listAdapter;
    private final Database database = Database.getInstance();
    private ListFragmentViewModel viewModel;
    private MainActivityViewModel viewModelActivity;


    //System calls this constructor, make newInstance
    public ListFragment() {}

    /*public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }*/

    //Inflate layout
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getView());
        viewModel = ViewModelProviders.of(this, new ListFragmentViewModelFactory(database)).get(ListFragmentViewModel.class);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        setupViews();
        observeUsers();
    }

    private void observeUsers() {
        viewModel.getUsers().observe(this, users -> {
            listAdapter.submitList(users);
            b.mainLblEmptyView.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        listAdapter = new ListFragmentAdapter(position -> editUser(listAdapter.getItem(position)), position -> viewModel.deleteUser(listAdapter.getItem(position)));
        //Security measure: fixed size
        b.mainLstUsers.setHasFixedSize(true);
        b.mainLstUsers.setLayoutManager(new GridLayoutManager(requireContext(), getResources().getInteger(R.integer.main_lstStudents_columns)));

        b.mainLstUsers.setAdapter(listAdapter);

        //Add listener
        View.OnClickListener listener = v -> addUser();

        b.mainFab.setOnClickListener(listener);
        b.mainLblEmptyView.setOnClickListener(listener);
    }

    private void editUser(User user) {
        viewModelActivity.listLoaded = false;
        viewModelActivity.avatarChanged = false;
//        viewModelActivity.avatarLoaded = false;
        viewModelActivity.setUser(user);
    }

    private void addUser() {
        viewModelActivity.listLoaded = false;
        viewModelActivity.avatarChanged = false;
//        viewModelActivity.avatarLoaded = false;
        viewModelActivity.setUser(null);
    }

}