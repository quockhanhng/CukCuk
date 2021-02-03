package vn.com.misa.cukcukstarterclone.data.repository;

import java.util.List;

import vn.com.misa.cukcukstarterclone.data.IOnLoadedCallback;
import vn.com.misa.cukcukstarterclone.data.model.OverallReport;

/**
 * - Mục đích Class:
 *
 * @created_by KhanhNQ on 01-Feb-2021.
 */
public interface IReportRepository {
    void getTotalMoneyReport(String date, IOnLoadedCallback<List<OverallReport>> callback);

    void getCashReport(String date, IOnLoadedCallback<List<OverallReport>> callback);
}
