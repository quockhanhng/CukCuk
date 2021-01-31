package vn.com.misa.cukcukstarterclone.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;

import vn.com.misa.cukcukstarterclone.R;

/**
 * @created_by KhanhNQ on 07-Jan-21.
 */
abstract public class BaseActivity<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends AppCompatActivity implements BaseContract.View {

    protected abstract @LayoutRes
    int getLayout();

    protected @StyleRes
    int getStyle() {
        return R.style.Theme_MISACukcukStarterClone;
    }

    protected abstract void bindViews();

    protected abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getStyle());
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        bindViews();
        initData();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }
}
