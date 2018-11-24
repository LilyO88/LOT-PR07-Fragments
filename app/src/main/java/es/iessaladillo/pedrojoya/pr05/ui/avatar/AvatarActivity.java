package es.iessaladillo.pedrojoya.pr05.ui.avatar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.databinding.ActivityAvatarBinding;
import es.iessaladillo.pedrojoya.pr05.utils.ResourcesUtils;

public class AvatarActivity extends AppCompatActivity {

    @VisibleForTesting
    public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

    private AvatarActivityViewModel viewModel;

    /*private ImageView imgAvatar1;
    private ImageView imgAvatar2;
    private ImageView imgAvatar3;
    private ImageView imgAvatar4;
    private ImageView imgAvatar5;
    private ImageView imgAvatar6;
    private TextView lblAvatar1;
    private TextView lblAvatar2;
    private TextView lblAvatar3;
    private TextView lblAvatar4;
    private TextView lblAvatar5;
    private TextView lblAvatar6;*/
    private final Database database = Database.getInstance();
    private ActivityAvatarBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_avatar);
        viewModel = ViewModelProviders.of(this).get(AvatarActivityViewModel.class);
        obtainData(getIntent());
        initViews();
        setTransparent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSelect) {
            buildResult();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initViews() {
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
    }

    private void selectImageView(View imageView) {
        imageView.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
    }

    private void deselectImageView() {
        b.imgAvatar1.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar2.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar3.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar4.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar5.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
        b.imgAvatar6.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));
    }

    public static void startForResult(Activity activity, int requestCode, Avatar avatar) {
        Intent intent = new Intent(activity, AvatarActivity.class);
        intent.putExtra(EXTRA_AVATAR, avatar);
        activity.startActivityForResult(intent, requestCode);
    }

    private void obtainData(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_AVATAR) && viewModel.getAvatar() == null) { //OJO
            viewModel.setAvatar(intent.getParcelableExtra(EXTRA_AVATAR));
        }
    }

    private void buildResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_AVATAR, viewModel.getAvatar());
        this.setResult(RESULT_OK, intent);
    }

}
