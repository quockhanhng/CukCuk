package vn.com.misa.cukcukstarterclone.data.source.local.dao;

import java.util.List;

import vn.com.misa.cukcukstarterclone.data.model.OverallReport;

/**
 * @created_by KhanhNQ on 01-Feb-2021.
 */
public interface IReportDao {
    List<OverallReport> getOverallReport(String date);

    List<OverallReport> getOverallCashReport(String date);
}