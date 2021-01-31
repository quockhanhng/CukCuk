package vn.com.misa.cukcukstarterclone.ui.setup;

public class SetupPresenter implements SetupContract.Presenter {

    private SetupContract.View view;

    @Override
    public void attach(SetupContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        view = null;
    }
}
