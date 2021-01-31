package vn.com.misa.cukcukstarterclone.ui.report;

/**
 * @created_by KhanhNQ on 31-Jan-2021.
 */
public class ReportPresenter implements ReportContract.Presenter {

    private ReportContract.View view;

    @Override
    public void attach(ReportContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }
}
