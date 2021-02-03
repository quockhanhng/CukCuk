package vn.com.misa.cukcukstarterclone.data.repository;

import java.util.List;

import vn.com.misa.cukcukstarterclone.data.IOnLoadedCallback;
import vn.com.misa.cukcukstarterclone.data.model.OverallReport;
import vn.com.misa.cukcukstarterclone.data.source.IReportDataSource;

/**
 * - Mục đích Class: Repository điều hướng xử lí dữ liệu liên quan đến {@link OverallReport}
 *
 * @created_by KhanhNQ on 01-Feb-2021.
 */
public class ReportRepository implements IReportRepository {

    private final IReportDataSource.Local localDataSource;

    private ReportRepository(IReportDataSource.Local localDataSource) {
        this.localDataSource = localDataSource;
    }

    private static ReportRepository instance;

    public static ReportRepository getInstance(IReportDataSource.Local localDataSource) {
        if (instance == null) {
            instance = new ReportRepository(localDataSource);
        }
        return instance;
    }

    /**
     * - Mục đích method: Lấy thống kê tổng doanh số
     *
     * @param date     ngày muốn lấy dữ liệu
     * @param callback Callback xử lý sau khi lấy được thông tin
     * @created_by KhanhNQ on 01-Feb-21
     */
    @Override
    public void getTotalMoneyReport(String date, IOnLoadedCallback<List<OverallReport>> callback) {
        localDataSource.getTotalMoneyReport(date, callback);
    }

    /**
     * - Mục đích method: Lấy thống kê doanh số tiền mặt
     *
     * @param date     ngày muốn lấy dữ liệu
     * @param callback Call back xử lý dữ liệu sau khi lấy được thông tin
     * @created_by KhanhNQ on 01-Feb-21
     */
    @Override
    public void getCashReport(String date, IOnLoadedCallback<List<OverallReport>> callback) {
        localDataSource.getCashReport(date, callback);
    }
}
