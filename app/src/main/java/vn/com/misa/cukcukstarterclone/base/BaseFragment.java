package vn.com.misa.cukcukstarterclone.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @created_by KhanhNQ on 07-Jan-21.
 */
public abstract class BaseFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends Fragment implements BaseContract.View {

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void bindViews(View view);

    protected abstract void initData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);
        initData();
    }

    @Override
    public void showMessage(String msg) {
        if (null != getContext()) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(int resId) {
        if (null != getContext()) {
            Toast.makeText(getContext(), getResources().getString(resId), Toast.LENGTH_SHORT).show();
        }
    }
}
