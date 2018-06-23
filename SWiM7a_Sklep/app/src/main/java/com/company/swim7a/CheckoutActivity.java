package com.company.swim7a;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        float value = intent.getFloatExtra("totalValue", 0);

        TextView text = findViewById(R.id.paymentTextView);
        text.setText(String.format("Do zapłaty: %.2f zł", value));

        Button abortPayment = findViewById(R.id.abortPaymentButton);
        abortPayment.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        RadioGroup radioGroup = findViewById(R.id.paymentMethodsRadioGroup);

        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(v -> {
            if (radioGroup.getCheckedRadioButtonId() != -1) {
                Intent openWebsite = new Intent(Intent.ACTION_VIEW);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.ipkoRadioButton:
                        openWebsite.setData(Uri.parse("http://www.ipko.pl/"));
                        break;
                    case R.id.blikRadioButton:
                        openWebsite.setData(Uri.parse("http://www.blikmobile.pl/"));
                        break;
                    case R.id.ingRadioButton:
                        openWebsite.setData(Uri.parse("http://www.ingbank.pl/"));
                        break;
                }
                startActivity(openWebsite);
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Proszę wybrać metodę płatności", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
