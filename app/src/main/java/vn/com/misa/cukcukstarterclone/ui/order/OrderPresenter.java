package vn.com.misa.cukcukstarterclone.ui.order;

import java.util.List;

import vn.com.misa.cukcukstarterclone.data.IOnLoadedCallback;
import vn.com.misa.cukcukstarterclone.data.model.Cart;
import vn.com.misa.cukcukstarterclone.data.model.Order;
import vn.com.misa.cukcukstarterclone.data.repository.CartRepository;
import vn.com.misa.cukcukstarterclone.data.repository.OrderRepository;
import vn.com.misa.cukcukstarterclone.ui.order.dto.OrderDto;

/**
 * @created_by KhanhNQ on 29-Jan-2021.
 */
public class OrderPresenter implements OrderContract.Presenter {
    private OrderContract.View view;

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderPresenter(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void attach(OrderContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        this.view = null;
    }

    @Override
    public void getAllOrder() {
        orderRepository.getAllOrders(new IOnLoadedCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> data) {
                loadCartItems(data);
            }

            @Override
            public void onFailure(Exception e) {
                view.showMessage(e.getLocalizedMessage());
            }
        });
    }

    private void loadCartItems(List<Order> data) {
        for (Order order : data) {
            cartRepository.getCartById(order.getCartId(), new IOnLoadedCallback<Cart>() {
                @Override
                public void onSuccess(Cart data) {
                    view.addOrders(new OrderDto(data, order));
                }

                @Override
                public void onFailure(Exception e) {
                    view.showMessage(e.getMessage());
                }
            });
        }
    }
}
