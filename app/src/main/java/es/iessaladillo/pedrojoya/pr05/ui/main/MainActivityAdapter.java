package es.iessaladillo.pedrojoya.pr05.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr05.R;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityAdapter extends ListAdapter<User, MainActivityAdapter.ViewHolder> {

    private final OnEditListener onEditListener;
    private final OnDeleteListener onDeleteListener;

    public MainActivityAdapter(OnEditListener onEditListener, OnDeleteListener onDeleteListener) {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName())
                        && TextUtils.equals(oldItem.getEmail(), newItem.getEmail())
                        && TextUtils.equals(oldItem.getPhonenumber(), newItem.getPhonenumber())
                        && oldItem.getAvatar().getImageResId() == newItem.getAvatar().getImageResId();
            }
        });
        this.onEditListener = onEditListener;
        this.onDeleteListener = onDeleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false), onEditListener, onDeleteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final Button btnEdit;
        private final Button btnDelete;
        private final TextView lblName;
        private final TextView lblEmail;
        private final TextView lblPhone;
        private final ImageView imgAvatar;

        public ViewHolder(@NonNull View itemView, OnEditListener onEditListener, OnDeleteListener onDeleteListener) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.card_lblName);
            lblEmail = ViewCompat.requireViewById(itemView, R.id.card_lblEmail);
            lblPhone = ViewCompat.requireViewById(itemView, R.id.card_lblPhonenumber);
            btnEdit = ViewCompat.requireViewById(itemView, R.id.card_btnEdit);
            btnDelete = ViewCompat.requireViewById(itemView, R.id.card_btnDelete);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.card_imageView);

            if(onEditListener != null) {
                btnEdit.setOnClickListener(v1 -> onEditListener.onEdit(getAdapterPosition()));
            }
            if(onDeleteListener != null) {
                btnDelete.setOnClickListener(v12 -> onDeleteListener.onDelete(getAdapterPosition()));
            }
        }
        //TODO: bind()
        void bind(User user) {
            lblName.setText(user.getName());
            lblEmail.setText(user.getEmail());
            lblPhone.setText(user.getPhonenumber());
            imgAvatar.setImageResource(user.getAvatar().getImageResId());
        }
    }
}
