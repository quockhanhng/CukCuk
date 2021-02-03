package vn.com.misa.cukcukstarterclone.data.source.local.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcukstarterclone.data.model.Cart;
import vn.com.misa.cukcukstarterclone.data.model.Order;
import vn.com.misa.cukcukstarterclone.data.model.OverallReport;
import vn.com.misa.cukcukstarterclone.data.source.local.database.AppDatabase;

/**
 * - Mục đích Class:
 *
 * @created_by KhanhNQ on 01-Feb-2021.
 */
public class ReportDao implements IReportDao {

    private final SQLiteDatabase database;

    private ReportDao(AppDatabase appDatabase) {
        this.database = appDatabase.getWritableDatabase();
    }

    private static ReportDao instance;

    public static ReportDao getInstance(AppDatabase appDatabase) {
        if (instance == null) {
            instance = new ReportDao(appDatabase);
        }
        return instance;
    }

    @Override
    public List<OverallReport> getOverallReport(String date) {
        String from = date.split("to")[0];
        String to = date.split("to")[1];
        String query = "SELECT SUM(c." + Cart.TOTAL_AMOUNT + ") as " + OverallReport.AMOUNT +
                ", strftime('%Y-%m-%d', o." + Order.CREATED_AT + ") as " + OverallReport.DAY +
                " FROM " + Cart.TABLE_NAME + " c," + Order.TABLE_NAME + " o" +
                " WHERE c." + Cart.ID + " = o." + Order.CART_ID +
                " and o." + Order.CREATED_AT + " >= '" + from +
                "' and o." + Order.CREATED_AT + " <= '" + to + " 23:59:59'" +
                " GROUP BY " + OverallReport.DAY +
                " ORDER BY o." + Order.CREATED_AT + " ASC";
        Cursor cursor = database.rawQuery(query, null);
        List<OverallReport> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(new OverallReport(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }

    @Override
    public List<OverallReport> getOverallCashReport(String date) {
        String from = date.split("to")[0];
        String to = date.split("to")[1];
        String query = "SELECT SUM(c." + Cart.TOTAL_AMOUNT + ") as " + OverallReport.AMOUNT +
                ", strftime('%d-%m-%Y', o." + Order.CREATED_AT + ") as " + OverallReport.DAY +
                " FROM " + Cart.TABLE_NAME + " c," + Order.TABLE_NAME + " o" +
                " WHERE c." + Cart.ID + " = o." + Order.CART_ID +
                " and o." + Order.TYPE + " = 0" +
                " and o." + Order.CREATED_AT + " >= '" + from +
                "' and o." + Order.CREATED_AT + " <= '" + to + " 23:59:59'" +
                " GROUP BY " + OverallReport.DAY +
                " ORDER BY o." + Order.CREATED_AT + " ASC";
        Cursor cursor = database.rawQuery(query, null);
        List<OverallReport> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                list.add(new OverallReport(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return list;
    }
}
