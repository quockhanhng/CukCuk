package vn.com.misa.cukcukstarterclone.ui.order;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import vn.com.misa.cukcukstarterclone.R;
import vn.com.misa.cukcukstarterclone.base.BaseActivity;
import vn.com.misa.cukcukstarterclone.data.repository.CartRepository;
import vn.com.misa.cukcukstarterclone.data.repository.OrderRepository;
import vn.com.misa.cukcukstarterclone.di.Injector;
import vn.com.misa.cukcukstarterclone.ui.order.adapter.BillAdapter;
import vn.com.misa.cukcukstarterclone.ui.order.dto.OrderDto;
import vn.com.misa.cukcukstarterclone.ui.order.orderdetails.OrderDetailsFragment;
import vn.com.misa.cukcukstarterclone.utils.Utils;

public class OrderActivity extends BaseActivity<OrderContract.View, OrderPresenter>
        implements OrderContract.View {

    private OrderPresenter mPresenter;

    private final BillAdapter billAdapter = new BillAdapter();

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void bindViews() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(v -> {
                finish();
            });

            RecyclerView rcvBill = findViewById(R.id.rcvBill);

            billAdapter.setItemClickListener((item, position) -> showBillDetails(item));
            rcvBill.setAdapter(billAdapter);
        } catch (Exception e) {
            Utils.handleException(e);
        }

        initPresenter();
    }

    private void showBillDetails(OrderDto orderDto) {
        OrderDetailsFragment orderDetailsFragment = OrderDetailsFragment.newInstance(orderDto);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.root, orderDetailsFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initPresenter() {
        try {
            CartRepository cartRepository = Injector.getCartRepository(this);
            OrderRepository orderRepository = Injector.getOrderRepository(this);

            mPresenter = new OrderPresenter(cartRepository, orderRepository);
            mPresenter.attach(this);
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    @Override
    protected void initData() {
        mPresenter.getAllOrder();
    }

    @Override
    public void addOrders(OrderDto item) {
        billAdapter.insertItem(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detach();
    }
}
