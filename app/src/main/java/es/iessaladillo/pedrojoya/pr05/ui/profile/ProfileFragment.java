package es.iessaladillo.pedrojoya.pr05.ui.profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;
import es.iessaladillo.pedrojoya.pr05.databinding.FragmentProfileBinding;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

public class ProfileFragment extends Fragment {

    private static final String ARG_USER = "ARG_USER";
    private User toEditUser;
    private final Database database = Database.getInstance();

    private ProfileFragmentViewModel viewModel;
    private FragmentProfileBinding b;
    private MainActivityViewModel viewModelActivity;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        if(user != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(ARG_USER, user);
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Objects.requireNonNull(getView());
        if(getArguments() != null) {
            Objects.requireNonNull(getArguments());
            Objects.requireNonNull(getArguments().getParcelable(ARG_USER));
        }
        viewModel = ViewModelProviders.of(this).get(ProfileFragmentViewModel.class);
        viewModelActivity = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        viewModelActivity.listLoaded = true;
        setupViews();
        validateFields();
    }

    private void setupViews() {
        //If user clicks editButton we have an user and asign to variable toEditUser,
        //but user clicks addButton we create a new User for our variable toEditUser
        if(getArguments() != null) {
            toEditUser = getArguments().getParcelable(ARG_USER);
        } else {
            toEditUser = new User();
            toEditUser.setAvatar(database.getDefaultAvatar()); //Need setAvatar for another methods
        }

        //
        if(!viewModelActivity.avatarChanged && !viewModel.isHaGirado()) {
            viewModel.setAvatar(toEditUser.getAvatar());
        } else {
            viewModel.setAvatar(viewModelActivity.getAvatar());
        }

        setProfileAvatar();
        setUser();

        //Avatar image listener
        View.OnClickListener avatarListener = (View v) -> {
            viewModelActivity.profileLoaded = false;
            viewModelActivity.avatarLoaded = true;
            /*if(viewModelActivity.avatarChanged) {
                viewModelActivity.setAvatar(viewModelActivity.getAvatar());
            } else {
                viewModelActivity.setAvatar(toEditUser.getAvatar());
            }*/
            viewModelActivity.setAvatar(viewModel.getAvatar());

        };

        b.imgAvatar.setOnClickListener(avatarListener);
        b.lblAvatar.setOnClickListener(avatarListener);

        //Label is bold when editText has focus
        b.include.txtName.setOnFocusChangeListener((v, hasFocus) -> setBold(b.include.txtName, b.include.lblName));
        b.include.txtEmail.setOnFocusChangeListener((v, hasFocus) -> setBold(b.include.txtEmail, b.include.lblEmail));
        b.include.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> setBold(b.include.txtPhonenumber, b.include.lblPhonenumber));
        b.include.txtAddress.setOnFocusChangeListener((v, hasFocus) -> setBold(b.include.txtAddress, b.include.lblAddress));
        b.include.txtWeb.setOnFocusChangeListener((v, hasFocus) -> setBold(b.include.txtWeb, b.include.lblWeb));

        //SetTag
        b.include.imgPhonenumber.setTag(R.drawable.ic_call_24dp);
        b.include.imgEmail.setTag(R.drawable.ic_email_24dp);
        b.include.imgWeb.setTag(R.drawable.ic_web_24dp);
        b.include.imgAddress.setTag(R.drawable.ic_map_24dp);
        b.imgAvatar.setTag(database.getDefaultAvatar().getImageResId());
        b.lblAvatar.setTag(database.getDefaultAvatar().getName());

        //TextWatcher, check fields
        GestorTextWatcher gestorTextWatcher = new GestorTextWatcher();

        b.include.txtName.addTextChangedListener(gestorTextWatcher);
        b.include.txtEmail.addTextChangedListener(gestorTextWatcher);
        b.include.txtPhonenumber.addTextChangedListener(gestorTextWatcher);
        b.include.txtAddress.addTextChangedListener(gestorTextWatcher);
        b.include.txtWeb.addTextChangedListener(gestorTextWatcher);

        //Keuboard editorAction
        b.include.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return false;
        });

        //Icons listener, send implicit intent
        View.OnClickListener imgListener = this::sendIntent;

        b.include.imgEmail.setOnClickListener(imgListener);
        b.include.imgPhonenumber.setOnClickListener(imgListener);
        b.include.imgAddress.setOnClickListener(imgListener);
        b.include.imgWeb.setOnClickListener(imgListener);

    }

    private void setUser() {
        b.include.txtName.setText(toEditUser.getName());
        b.include.txtEmail.setText(toEditUser.getEmail());
        b.include.txtPhonenumber.setText(toEditUser.getPhonenumber());
        b.include.txtAddress.setText(toEditUser.getAddress());
        b.include.txtWeb.setText(toEditUser.getWeb());
        b.imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
    }

    private void validateFields() {
        b.include.lblName.setEnabled(viewModel.isStateName());
        b.include.lblEmail.setEnabled(viewModel.isStateEmail());
        b.include.lblPhonenumber.setEnabled(viewModel.isStatePhonenumber());
        b.include.lblAddress.setEnabled(viewModel.isStateAddress());
        b.include.lblWeb.setEnabled(viewModel.isStateWeb());

        b.include.imgEmail.setEnabled(viewModel.isStateImgEmail());
        b.include.imgPhonenumber.setEnabled(viewModel.isStateImgPhonenumber());
        b.include.imgAddress.setEnabled(viewModel.isStateImgAddress());
        b.include.imgWeb.setEnabled(viewModel.isStateImgWeb());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        if (!validateAll()) {
            Snackbar.make(b.clProfile, R.string.main_error_saving, Snackbar.LENGTH_LONG).show();
        } else {
            asignUser();
            if(getArguments() != null) {
                database.editUser(toEditUser);
            } else {
                database.addUser(toEditUser);
            }
            viewModelActivity.listLoaded = true;
            viewModelActivity.avatarChanged = true;
//            KeyboardUtils.hideSoftKeyboard(requireActivity());
            getFragmentManager().popBackStack();
        }
        KeyboardUtils.hideSoftKeyboard(requireActivity());
    }

    private void asignUser() {
        toEditUser.setName(b.include.txtName.getText().toString());
        toEditUser.setEmail(b.include.txtEmail.getText().toString());
        toEditUser.setPhonenumber(b.include.txtPhonenumber.getText().toString());
        toEditUser.setAddress(b.include.txtAddress.getText().toString());
        toEditUser.setWeb(b.include.txtWeb.getText().toString());
        toEditUser.setAvatar(viewModel.getAvatar());
    }

    private void setBold(EditText editText, TextView label) {
        if(editText.hasFocus()) {
            label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            label.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    private void setProfileAvatar() {
        b.imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        b.lblAvatar.setText(viewModel.getAvatar().getName());
        b.imgAvatar.setTag(viewModel.getAvatar().getImageResId());
    }

    //Implicit Intents
    private void sendIntent(View v) {
        if (v.getId() == b.include.imgEmail.getId()) {
            sendEmail();
        } else if (v.getId() == b.include.imgPhonenumber.getId()) {
            callPhone();
        } else if (v.getId() == b.include.imgAddress.getId()) {
            searchAddress();
        } else if (v.getId() == b.include.imgWeb.getId()) {
            viewWeb();
        }
    }

    private void sendEmail() {
        String email = b.include.txtEmail.getText().toString();
        Intent sendEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("mailto:%s", email)));
        startActivity(sendEmailIntent);
    }

    private void callPhone() {
        String phone = b.include.txtPhonenumber.getText().toString();
        Intent callPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", phone)));
        startActivity(callPhoneIntent);
    }

    private void searchAddress() {
        String address = b.include.txtAddress.getText().toString();
        Intent searchAddressIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", address)));
        startActivity(searchAddressIntent);
    }

    private void viewWeb() {
        String web = b.include.txtWeb.getText().toString();
        if (!web.startsWith("http://")) {
            web = String.format("http://%s", web);
        }
        Intent viewWebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
        startActivity(viewWebIntent);
    }

    private class GestorTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkCurrentView();
        }

        @Override
        public void afterTextChanged(Editable s) {
            checkCurrentView();
        }
    }

    //Validation to enable/disable icons/labels
    private void checkName() {
        enabledDisabledField(b.include.txtName, b.include.lblName, ValidationUtils.isValidName(b.include.txtName.getText().toString()));
    }

    private void checkEmail() {
        enabledDisabledFieldImg(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()));
    }

    private void checkPhonenumber() {
        enabledDisabledFieldImg(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber, ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString()));
    }

    private void checkAddress() {
        enabledDisabledFieldImg(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, ValidationUtils.isValidAddress(b.include.txtAddress.getText().toString()));
    }

    private void checkWeb() {
        enabledDisabledFieldImg(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()));
    }

    private void checkCurrentView() {
        if(getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getId() == b.include.txtName.getId()) {
                checkName();
            } else if (getActivity().getCurrentFocus().getId() == b.include.txtEmail.getId()) {
                checkEmail();
            } else if (getActivity().getCurrentFocus().getId() == b.include.txtPhonenumber.getId()) {
                checkPhonenumber();
            } else if (getActivity().getCurrentFocus().getId() == b.include.txtAddress.getId()) {
                checkAddress();
            } else if (getActivity().getCurrentFocus().getId() == b.include.txtWeb.getId()) {
                checkWeb();
            }
        }
    }

    private void checkAll() {
        checkName();
        checkEmail();
        checkPhonenumber();
        checkAddress();
        checkWeb();
    }

    private boolean validateAll() {
        checkAll();
        View[] array = new View[]{b.include.lblName, b.include.lblEmail, b.include.lblPhonenumber, b.include.lblAddress, b.include.lblWeb};
        for (View view: array) {
            if(!view.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void enabledDisabledFieldImg(EditText editText, ImageView imageView, TextView textView, boolean valid) {
        if(valid) {
            editText.setError(null);
        } else {
            editText.setError(getString(R.string.main_invalid_data));
        }
        imageView.setEnabled(valid);
        textView.setEnabled(valid);
        selectStateView(textView, valid);
    }

    //For name field, field has not icon
    private void enabledDisabledField(EditText editText, TextView textView, boolean valid) {
        if(valid) {
            editText.setError(null);
        } else {
            editText.setError(getString(R.string.main_invalid_data));
        }
        textView.setEnabled(valid);
        selectStateView(textView, valid);
    }

    //Save states
    private void selectStateView(View view, boolean state) {
        if(view == b.include.lblName) {
            viewModel.setStateName(state);
        } else if(view == b.include.lblEmail) {
            viewModel.setStateEmail(state);
            viewModel.setStateImgEmail(state);
        } else if(view == b.include.lblPhonenumber) {
            viewModel.setStatePhonenumber(state);
            viewModel.setStateImgPhonenumber(state);
        } else if(view == b.include.lblAddress) {
            viewModel.setStateAddress(state);
            viewModel.setStateImgAddress(state);
        } else if(view == b.include.lblWeb) {
            viewModel.setStateWeb(state);
            viewModel.setStateImgWeb(state);
        }
    }
}
