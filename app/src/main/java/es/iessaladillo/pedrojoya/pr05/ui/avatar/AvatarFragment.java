package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentAvatarBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarFragment extends Fragment {

    private static final String ARG_AVATAR = "ARG_AVATAR";

    private final Database database = Database.getInstance();

    private MainActivityViewModel viewModelActivity;
    private AvatarFragmentViewModel viewModel;
    private FragmentAvatarBinding b;

    public static AvatarFragment newInstance(Avatar avatar) {
        AvatarFragment fragment = new AvatarFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_AVATAR, avatar);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_avatar, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(getView());
        Objects.requireNonNull(getArguments());
        Objects.requireNonNull(getArguments().getParcelable(ARG_AVATAR));
        viewModel = ViewModelProviders.of(this).get(AvatarFragmentViewModel.class);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        initViews();
        setTransparent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_avatar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            viewModelActivity.setAvatar(viewModel.getAvatar());
            viewModelActivity.avatarChanged = true; //indica que el avatar se ha cambiado
            viewModelActivity.profileLoaded = true; //Para evitar que cargue de nuevo el fragmento profile sin pedirselo
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        //Si ha cambiado avatar y lo ha guardado y no ha elegido
        if(viewModelActivity.avatarChanged && !viewModel.isSeleccionado()) {
            viewModel.setAvatar(viewModelActivity.getAvatar());
        }
        //Si ha cambiado avatar y no lo ha guardado y ha elegido otro
        //Si no ha cambiado el avatar y ha elegido otro
        //Si ha cambiado avatar y lo ha guardado y ha elegido otro
        else if((viewModelActivity.avatarChanged && viewModel.isSeleccionado())
                || (!viewModelActivity.avatarChanged && viewModel.isSeleccionado())
                || (viewModelActivity.avatarChanged && viewModel.isSeleccionado())) {
            viewModel.setAvatar(viewModel.getAvatar());
        }
        //Si ha cambiado avatar y no lo ha guardado y no ha elegido otro
        //Si no ha cambiado el avatar y no ha elegido otro
        else if((!viewModelActivity.avatarChanged && !viewModel.isSeleccionado())
                || (!viewModelActivity.avatarChanged && !viewModel.isSeleccionado())) {
            viewModel.setAvatar(getArguments().getParcelable(ARG_AVATAR));
        }

        /*onClickListener*/
        View.OnClickListener clickListener = v -> {
            setAvatar(v);
            setTransparent();
        };

        b.imgAvatar1.setOnClickListener(clickListener);
        b.imgAvatar2.setOnClickListener(clickListener);
        b.imgAvatar3.setOnClickListener(clickListener);
        b.imgAvatar4.setOnClickListener(clickListener);
        b.imgAvatar5.setOnClickListener(clickListener);
        b.imgAvatar6.setOnClickListener(clickListener);

        b.lblAvatar1.setOnClickListener(clickListener);
        b.lblAvatar2.setOnClickListener(clickListener);
        b.lblAvatar3.setOnClickListener(clickListener);
        b.lblAvatar4.setOnClickListener(clickListener);
        b.lblAvatar5.setOnClickListener(clickListener);
        b.lblAvatar6.setOnClickListener(clickListener);
    }

    private void setTransparent() {
        deselectImageView();
        if (viewModel.getAvatar().getImageResId() == database.queryAvatar(1).getImageResId()) {
            selectImageView(b.imgAvatar1);
        } else if (viewModel.getAvatar().getImageResId() == database.queryAvatar(2).getImageResId()) {
            selectImageView(b.imgAvatar2);
        } else if (viewModel.getAvatar().getImageResId() == database.queryAvatar(3).getImageResId()) {
            selectImageView(b.imgAvatar3);
        } else if (viewModel.getAvatar().getImageResId() == database.queryAvatar(4).getImageResId()) {
            selectImageView(b.imgAvatar4);
        } else if (viewModel.getAvatar().getImageResId() == database.queryAvatar(5).getImageResId()) {
            selectImageView(b.imgAvatar5);
        } else if (viewModel.getAvatar().getImageResId() == database.queryAvatar(6).getImageResId()) {
            selectImageView(b.imgAvatar6);
        }
    }


    private void setAvatar(View v) {
        Database database = Database.getInstance();
        long num = 0;

        switch (v.getId()) {
            case R.id.imgAvatar1:
                num = 1;
                break;
            case R.id.imgAvatar2:
                num = 2;
                break;
            case R.id.imgAvatar3:
                num = 3;
                break;
            case R.id.imgAvatar4:
                num = 4;
                break;
            case R.id.imgAvatar5:
                num = 5;
                break;
            case R.id.imgAvatar6:
                num = 6;
                break;
            case R.id.lblAvatar1:
                num = 1;
                break;
            case R.id.lblAvatar2:
                num = 2;
                break;
            case R.id.lblAvatar3:
                num = 3;
                break;
            case R.id.lblAvatar4:
                num = 4;
                break;
            case R.id.lblAvatar5:
                num = 5;
                break;
            case R.id.lblAvatar6:
                num = 6;
                break;
        }
        viewModel.setAvatar(database.queryAvatar(num));
        viewModel.setSeleccionado(true);
    }

    private void selectImageView(View imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_selected_image_alpha));
    }

    private void deselectImageView() {
        b.imgAvatar1.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar2.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar3.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar4.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar5.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar6.setAlpha(ResourcesUtils.getFloat(getActivity(), R.dimen.avatar_not_selected_image_alpha));
    }

}
