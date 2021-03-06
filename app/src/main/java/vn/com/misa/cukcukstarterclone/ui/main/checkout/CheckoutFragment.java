package vn.com.misa.cukcukstarterclone.ui.main.checkout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.DecimalFormat;

import vn.com.misa.cukcukstarterclone.R;
import vn.com.misa.cukcukstarterclone.base.BaseFragment;
import vn.com.misa.cukcukstarterclone.data.model.Cart;
import vn.com.misa.cukcukstarterclone.data.model.Cart.CartStatus;
import vn.com.misa.cukcukstarterclone.data.model.Order;
import vn.com.misa.cukcukstarterclone.data.model.Order.PaymentType;
import vn.com.misa.cukcukstarterclone.data.repository.CartRepository;
import vn.com.misa.cukcukstarterclone.data.repository.OrderRepository;
import vn.com.misa.cukcukstarterclone.di.Injector;
import vn.com.misa.cukcukstarterclone.utils.Utils;

/**
 * @created_by KhanhNQ on 28-Jan-21
 */
public class CheckoutFragment extends BaseFragment<CheckoutContract.View, CheckoutPresenter>
        implements CheckoutContract.View {

    public static final String TAG = "CheckoutFragment";

    public static final String KEY_ADD_ORDER_SUCCESS = "KEY_ADD_ORDER_SUCCESS";

    private static final String ARG_CART = "arg_cart";

    private Cart cart;

    private TextView tvTotalMoney;
    private EditText edtCustomPay;
    private LinearLayout llChanges;
    private TextView tvChanges;

    private float totalMoney = 0;
    private Order.PaymentType paymentType = PaymentType.CASH;

    private CheckoutPresenter mPresenter;

    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public CheckoutFragment() {
    }

    public static CheckoutFragment newInstance(Cart cart) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CART, cart);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cart = getArguments().getParcelable(ARG_CART);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_checkout;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void bindViews(View view) {
        try {
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_back);
            toolbar.setNavigationOnClickListener(v -> popFragment());

            MaterialButtonToggleGroup toggleGroupPayment = view.findViewById(R.id.toggleGroupPayment);
            tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
            edtCustomPay = view.findViewById(R.id.edtCustomerPay);
            llChanges = view.findViewById(R.id.llChanges);
            tvChanges = view.findViewById(R.id.tvChanges);
            TextView btnFinish = view.findViewById(R.id.btnFinish);

            toggleGroupPayment.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (isChecked) {
                    edtCustomPay.setText(decimalFormat.format(totalMoney));
                    switch (checkedId) {
                        case R.id.btnCash:
                            paymentType = PaymentType.CASH;
                            llChanges.setVisibility(View.VISIBLE);
                            edtCustomPay.setEnabled(true);
                            break;
                        case R.id.btnAtmCard:
                            paymentType = PaymentType.CARD;
                            llChanges.setVisibility(View.GONE);
                            edtCustomPay.setEnabled(false);
                            break;
                    }
                }
            });

            edtCustomPay.setOnClickListener(v -> edtCustomPay.selectAll());

            edtCustomPay.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 0) {
                        edtCustomPay.setText("0");
                        edtCustomPay.requestFocus();
                        edtCustomPay.selectAll();
                    } else {
                        float pay = Float.parseFloat(edtCustomPay.getText().toString());
                        float changes = pay - totalMoney;
                        changes = Math.max(changes, 0);
                        tvChanges.setText(decimalFormat.format(changes));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            btnFinish.setOnClickListener(v -> validate());
        } catch (Exception e) {
            Utils.handleException(e);
        }

        initPresenter();
    }

    private void popFragment() {
        try {
            getParentFragmentManager().popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    /**
     * - Mục đích method: Kiểm tra số tiền khách trả có lớn hơn số tiền của Cart không
     *
     * @created_by KhanhNQ on 28-Jan-21
     */
    private void validate() {
        try {
            float pay = Float.parseFloat(edtCustomPay.getText().toString().replace(".", ""));
            float changes = pay - totalMoney;
            if (changes >= 0) {
                cart.setStatus(CartStatus.PAID);
                mPresenter.addNewOrder(cart, new Order(paymentType, pay, cart.getId()));
            } else {
                showMessage(String.valueOf(pay));
                showError();
            }
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    private void showError() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

            final View customLayout = getLayoutInflater().inflate(R.layout.dialog_not_enough_money, null);
            TextView tvOK = customLayout.findViewById(R.id.tvOK);

            builder.setView(customLayout);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            tvOK.setOnClickListener(view -> dialog.dismiss());

            dialog.show();
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    private void initPresenter() {
        try {
            CartRepository cartRepository = Injector.getCartRepository(getContext());
            OrderRepository orderRepository = Injector.getOrderRepository(getContext());
            mPresenter = new CheckoutPresenter(cartRepository, orderRepository);

            mPresenter.attach(this);
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    @Override
    protected void initData() {
        try {
            totalMoney = cart.getTotalAmount();
            tvTotalMoney.setText(decimalFormat.format(totalMoney));
            edtCustomPay.setText(decimalFormat.format(totalMoney));
        } catch (Exception e) {
            Utils.handleException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.detach();
    }

    @Override
    public void addOrderSuccess() {
        getParentFragmentManager().setFragmentResult(KEY_ADD_ORDER_SUCCESS, new Bundle());
        popFragment();
    }
}
