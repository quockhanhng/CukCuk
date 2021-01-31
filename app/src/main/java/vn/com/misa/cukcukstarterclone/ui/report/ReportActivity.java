package vn.com.misa.cukcukstarterclone.ui.report;

import android.widget.TextView;

import vn.com.misa.cukcukstarterclone.R;
import vn.com.misa.cukcukstarterclone.base.BaseActivity;
import vn.com.misa.cukcukstarterclone.utils.Utils;

public class ReportActivity extends BaseActivity<ReportContract.View, ReportPresenter>
        implements ReportContract.View {

    private TextView tvNoData;

    private ReportPresenter mPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_report;
    }

    @Override
    protected void bindViews() {
        try {

            tvNoData = findViewById(R.id.tvNoData);
        } catch (Exception e) {
            Utils.handleException(e);
        }

        initPresenter();
    }

    private void initPresenter() {
        try {
            mPresenter.attach(this);
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    @Override
    protected void initData() {
        mPresenter.detach();
    }
}
