package com.company.swim7a;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.swim7a.adapters.ShoppingCartAdapter;
import com.company.swim7a.repositories.ProductRepository;
import com.company.swim7a.services.ShoppingCartService;

public class MainActivity extends AppCompatActivity {
    private static ShoppingCartService shoppingCartService;

    static {
        shoppingCartService = new ShoppingCartService(new ProductRepository());
    }

    public static ShoppingCartAdapter shoppingCartAdapter;
    public static TextView totalValue;
    public static Button purchaseButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12) {
            if (resultCode == RESULT_OK) {
                boolean succeeded = shoppingCartService.addProduct(data.getData().toString());
                if (succeeded) {
                    Toast.makeText(this, "Produkt został dodany do koszyka", Toast.LENGTH_SHORT).show();
                    totalValue.setText(String.format(getResources().getString(R.string.total_price), shoppingCartService.getTotalValue()));
                    purchaseButton.setEnabled(true);
                    shoppingCartAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(this, "Nie znaleziono produktu", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 15) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Dokonano zakupu", Toast.LENGTH_SHORT).show();
                shoppingCartService.emptyShoppingCart();
                shoppingCartAdapter.notifyDataSetChanged();
                totalValue.setText(String.format(getResources().getString(R.string.total_price), 0F));
            } else {
                Toast.makeText(this, "Płatność anulowano", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(String.valueOf("iStore"));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 123);

        ListView shoppingCartList = findViewById(R.id.shoppingCartListView);
        shoppingCartAdapter = new ShoppingCartAdapter(shoppingCartService, getLayoutInflater(), R.layout.list_item);
        shoppingCartList.setAdapter(shoppingCartAdapter);

        totalValue = findViewById(R.id.totalValueTextView);

        purchaseButton = findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra("totalValue", shoppingCartService.getTotalValue());
            startActivityForResult(intent, 15);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivityForResult(new Intent(this, ScannerActivity.class), 12));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//
}
