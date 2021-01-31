package vn.com.misa.cukcukstarterclone.ui.order;

import vn.com.misa.cukcukstarterclone.base.BaseContract;
import vn.com.misa.cukcukstarterclone.ui.order.dto.OrderDto;

/**
 * @created_by KhanhNQ on 29-Jan-2021.
 */
public class OrderContract {
    interface View extends BaseContract.View {
        void addOrders(OrderDto item);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getAllOrder();
    }
}
