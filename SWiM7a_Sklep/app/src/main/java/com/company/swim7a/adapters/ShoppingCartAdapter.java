package com.company.swim7a.adapters;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.company.swim7a.MainActivity;
import com.company.swim7a.R;
import com.company.swim7a.entities.Product;
import com.company.swim7a.services.ShoppingCartService;
import com.company.swim7a.utils.Pair;

public class ShoppingCartAdapter extends BaseAdapter {
    private ShoppingCartService shoppingCartService;
    private LayoutInflater inflater;
    private int layoutResource;

    public ShoppingCartAdapter(ShoppingCartService shoppingCartService, LayoutInflater inflater, @LayoutRes int layoutResource) {
        this.shoppingCartService = shoppingCartService;
        this.inflater = inflater;
        this.layoutResource = layoutResource;
    }

    @Override
    public int getCount() {
        return shoppingCartService.getShoppingCartItems().size();
    }

    @Override
    public Object getItem(int i) {
        return shoppingCartService.getShoppingCartItems().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(layoutResource, null);

        TextView title = view.findViewById(R.id.titleTextView);
        TextView quantity = view.findViewById(R.id.quantityTextView);
        TextView price = view.findViewById(R.id.priceTextView);
        ImageButton addButton = view.findViewById(R.id.addButton);
        ImageButton subtractButton = view.findViewById(R.id.subtractButton);

        Pair<Product, Integer> product = shoppingCartService.getShoppingCartItems().get(i);
        price.setText(String.format("%.2f zl", product.getKey().getPrice()));
        title.setText(product.getKey().getTitle());
        quantity.setText(String.valueOf(product.getValue()));

        View finalView = view;
        addButton.setOnClickListener(v -> changeQuantityListener(product, finalView, true));
        subtractButton.setOnClickListener(v -> changeQuantityListener(product, finalView, false));
        subtractButton.setOnLongClickListener(v -> {
            shoppingCartService.removeProduct(product);
            MainActivity.shoppingCartAdapter.notifyDataSetChanged();
            MainActivity.totalValue.setText(
                    String.format(finalView.getContext().getResources().getString(R.string.total_price),
                            shoppingCartService.getTotalValue()));
            if (shoppingCartService.getShoppingCartItems().size() == 0)
                MainActivity.purchaseButton.setEnabled(false);
            return true;
        });

        return view;
    }

    private void changeQuantityListener(Pair<Product, Integer> product, View finalView, boolean b) {
        boolean emptyCart = shoppingCartService.changeQuantityByOne(product.getKey(), b);
        notifyDataSetChanged();

        MainActivity.totalValue.setText(
                String.format(finalView.getContext().getResources().getString(R.string.total_price),
                        shoppingCartService.getTotalValue()));
        if (emptyCart)
            MainActivity.purchaseButton.setEnabled(false);
    }
}
