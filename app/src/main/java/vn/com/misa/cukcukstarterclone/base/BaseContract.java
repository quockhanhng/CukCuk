package vn.com.misa.cukcukstarterclone.base;

import androidx.annotation.StringRes;

/**
 * @created_by KhanhNQ on 07-Jan-21.
 */
public interface BaseContract {
    interface Presenter<T> {
        void attach(T view);

        void detach();
    }

    interface View {
        void showMessage(String msg);

        void showMessage(@StringRes int resId);
    }
}
