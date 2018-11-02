package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.ui.avatar.AvatarActivity;
import es.iessaladillo.pedrojoya.pr05.utils.KeyboardUtils;
import es.iessaladillo.pedrojoya.pr05.utils.ValidationUtils;

@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    private final Database database = Database.getInstance();
    private ImageView imgAvatar;
    private TextView lblAvatar;
    private EditText txtName;
    private TextView lblName;
    private EditText txtEmail;
    private TextView lblEmail;
    private EditText txtPhonenumber;
    private TextView lblPhonenumber;
    private EditText txtAddress;
    private TextView lblAddress;
    private EditText txtWeb;
    private TextView lblWeb;
    private ImageView imgPhonenumber;
    private ImageView imgEmail;
    private ImageView imgWeb;
    private ImageView imgAddress;
    private ConstraintLayout constraitLayout;

    private boolean[] fields;

    private static final int RC_AVATAR = 1;
    private static final String IDENTIFIER = "IDENTIFIER";

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        initViews();
        if (savedInstanceState != null){
            this.fields = savedInstanceState.getBooleanArray(IDENTIFIER);
        }
        validateFields(fields);
    }

    private void validateFields(boolean[] fields) {

        lblName.setEnabled(fields[0]);
        lblEmail.setEnabled(fields[1]);
        lblPhonenumber.setEnabled(fields[2]);
        lblAddress.setEnabled(fields[3]);
        lblWeb.setEnabled(fields[4]);

        imgEmail.setEnabled(fields[1]);
        imgPhonenumber.setEnabled(fields[2]);
        imgAddress.setEnabled(fields[3]);
        imgWeb.setEnabled(fields[4]);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBooleanArray(IDENTIFIER, fields);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
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
            Snackbar.make(constraitLayout, R.string.main_error_saving, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(constraitLayout, R.string.main_saved_succesfully, Snackbar.LENGTH_LONG).show();
        }
        KeyboardUtils.hideSoftKeyboard(this);
    }

    private void initViews() {
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar);
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        lblPhonenumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        imgPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        constraitLayout = ActivityCompat.requireViewById(this, R.id.clRoot);

        viewModel.setDefaultAvatar();
        setProfileAvatar();

        View.OnClickListener avatarListener = v -> AvatarActivity.startForResult(MainActivity.this, RC_AVATAR, viewModel.getAvatar());

        imgAvatar.setOnClickListener(avatarListener);
        lblAvatar.setOnClickListener(avatarListener);
        txtName.setOnFocusChangeListener((v, hasFocus) -> setBold(txtName, lblName));
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> setBold(txtEmail, lblEmail));
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> setBold(txtPhonenumber, lblPhonenumber));
        txtAddress.setOnFocusChangeListener((v, hasFocus) -> setBold(txtAddress, lblAddress));
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> setBold(txtWeb, lblWeb));

        imgPhonenumber.setTag(R.drawable.ic_call_24dp);
        imgEmail.setTag(R.drawable.ic_email_24dp);
        imgWeb.setTag(R.drawable.ic_web_24dp);
        imgAddress.setTag(R.drawable.ic_map_24dp);
        imgAvatar.setTag(database.getDefaultAvatar().getImageResId());
        lblAvatar.setTag(database.getDefaultAvatar().getName());

        GestorTextWatcher gestorTextWatcher = new GestorTextWatcher();

        txtName.addTextChangedListener(gestorTextWatcher);
        txtEmail.addTextChangedListener(gestorTextWatcher);
        txtPhonenumber.addTextChangedListener(gestorTextWatcher);
        txtAddress.addTextChangedListener(gestorTextWatcher);
        txtWeb.addTextChangedListener(gestorTextWatcher);

        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return false;
        });

        View.OnClickListener imgListener = this::sendIntent;

        imgEmail.setOnClickListener(imgListener);
        imgPhonenumber.setOnClickListener(imgListener);
        imgAddress.setOnClickListener(imgListener);
        imgWeb.setOnClickListener(imgListener);

        fields = new boolean[5];
        for (int i=0; i < 5; i++){
            fields[i] = true;
        }
    }

    private void sendIntent(View v) {
        if (v.getId() == imgEmail.getId()) {
            sendEmail();
        } else if (v.getId() == imgPhonenumber.getId()) {
            callPhone();
        } else if (v.getId() == imgAddress.getId()) {
            searchAddress();
        } else if (v.getId() == imgWeb.getId()) {
            viewWeb();
        }
    }

    private void setBold(EditText editText, TextView label) {
        if(editText.hasFocus()) {
            label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            label.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_AVATAR) {
            obtainResponseData(data);
        }
    }

    private void obtainResponseData(Intent intent) {
        if (intent != null && intent.hasExtra(AvatarActivity.EXTRA_AVATAR)) {
            viewModel.setAvatar(intent.getParcelableExtra(AvatarActivity.EXTRA_AVATAR));
        }
        setProfileAvatar();
    }

    private void setProfileAvatar() {
        imgAvatar.setImageResource(viewModel.getAvatar().getImageResId());
        lblAvatar.setText(viewModel.getAvatar().getName());
        imgAvatar.setTag(viewModel.getAvatar().getImageResId());
    }

    //Implicit Intents
    private void sendEmail() {
        String email = txtEmail.getText().toString();
        Intent sendEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(String.format("mailto:%s", email)));
        startActivity(sendEmailIntent);
    }

    private void callPhone() {
        String phone = txtPhonenumber.getText().toString();
        Intent callPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", phone)));
        startActivity(callPhoneIntent);
    }

    private void searchAddress() {
        String address = txtAddress.getText().toString();
        Intent searchAddressIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s", address)));
        startActivity(searchAddressIntent);
    }

    private void viewWeb() {
        String web = txtWeb.getText().toString();
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
        if(!isValidName(txtName.getText().toString())) {
            disabledField(txtName, lblName);
            fields[0] = false;
        } else {
            enabledField(txtName, lblName);
            fields[0] = true;
        }
    }

    private void checkEmail() {
        if (!ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
            disabledFieldImg(txtEmail, imgEmail, lblEmail);
            fields[1] = false;
        } else {
            enabledFieldImg(txtEmail, imgEmail, lblEmail);
            fields[1] = true;
        }
    }

    private void checkPhonenumber() {
        if (!ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) {
            disabledFieldImg(txtPhonenumber, imgPhonenumber, lblPhonenumber);
            fields[2] = false;
        } else {
            enabledFieldImg(txtPhonenumber, imgPhonenumber, lblPhonenumber);
            fields[2] = true;
        }
    }

    private void checkAddress() {
        if (!isValidAddress(txtAddress.getText().toString())) {
            disabledFieldImg(txtAddress, imgAddress, lblAddress);
            fields[3] = false;
        } else {
            enabledFieldImg(txtAddress, imgAddress, lblAddress);
            fields[3] = true;
        }
    }

    private void checkWeb() {
        if (!ValidationUtils.isValidUrl(txtWeb.getText().toString())
                || TextUtils.isEmpty(txtWeb.getText().toString())) {
            disabledFieldImg(txtWeb, imgWeb, lblWeb);
            fields[4] = false;
        } else {
            enabledFieldImg(txtWeb, imgWeb, lblWeb);
            fields[0] = true;
        }
    }

    private void checkCurrentView() {
        if(getCurrentFocus() != null) {
            if (getCurrentFocus().getId() == txtName.getId()) {
                checkName();
            } else if (getCurrentFocus().getId() == txtEmail.getId()) {
                checkEmail();
            } else if (getCurrentFocus().getId() == txtPhonenumber.getId()) {
                checkPhonenumber();
            } else if (getCurrentFocus().getId() == txtAddress.getId()) {
                checkAddress();
            } else if (getCurrentFocus().getId() == txtWeb.getId()) {
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
        View[] array = new View[]{lblName, lblEmail, lblPhonenumber, lblAddress, lblWeb};
        for (View view: array) {
            if(!view.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidName(String name) {
        return !TextUtils.isEmpty(name);
    }

    private boolean isValidAddress(String address) {
        return !TextUtils.isEmpty(address);
    }

    private void disabledFieldImg(EditText editText, ImageView imageView, TextView textView) {
        editText.setError(getString(R.string.main_invalid_data));
        imageView.setEnabled(false);
        textView.setEnabled(false);
    }

    private void enabledFieldImg(EditText editText, ImageView imageView, TextView textView) {
        editText.setError(null);
        imageView.setEnabled(true);
        textView.setEnabled(true);
    }

    private void disabledField(EditText editText, TextView textView) {
        editText.setError(getString(R.string.main_invalid_data));
        textView.setEnabled(false);
    }

    private void enabledField(EditText editText, TextView textView) {
        editText.setError(null);
        textView.setEnabled(true);
    }


}
