package es.iessaladillo.pedrojoya.pr05.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.ui.main.MainActivityViewModel;

public class ListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Database database;

    public ListFragmentViewModelFactory(Database database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListFragmentViewModel(database);
    }
}
