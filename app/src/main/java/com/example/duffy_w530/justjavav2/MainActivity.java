package com.example.duffy_w530.justjavav2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final double PRICEPERCUP = 5.00;
    private static final double PRICEFORWHIPPEDCREAM = 0.75;
    private static final double PRICEFORCHOCOLATE = 1.50;
    private static final int MAXCUPSODERABLE = 10;

    /**
     * Handles updating order based on adjust passed in (can be + or -)
     */
    private void adjustQuantity(View view, int adjustment) {
        TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
        int theQty = Integer.parseInt(quantityView.getText().toString());
        theQty += adjustment;
        updateOrder(theQty);
        updateButtons();
    }

    /**
     * Called when checkbox for one of the condiments is checked, just calls the updateOrder method
     *
     * @param view
     */
    public void condimentChecked(View view) {
        updateOrder();
    }

    private void createOrderSummary() {
        EditText nameEditText = (EditText) findViewById(R.id.name);
        TextView thanksTextView = (TextView) findViewById(R.id.thank_you_msg);

        String theName = nameEditText.getText().toString();

        String outMsg = getString(R.string.hi) + theName +
                "\n" + getString(R.string.your_order) +
                "\n" + getString(R.string.condiments);

        int condiments = 0;

        if (isWhippedCreamChecked()) {
            outMsg += getString(R.string.whipped_cream);
            Log.v("MainActivity", "whipped cream checkbox is checked");
            condiments++;
        }
        if (isChocolateChecked()) {
            outMsg += (condiments > 0 ? ", " : "") + getString(R.string.chocolate) +
                    " (yum)";
            Log.v("MainActivity", "chocolate checkbox is checked");
            condiments++;
        }
        if (condiments == 0) outMsg += "(none - boring)";
        outMsg += "\n" + getString(R.string.good_day);

        thanksTextView.setText(getString(R.string.thanks) + theName + "!");

        // Launxample launching the map app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject) +
                nameEditText.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, outMsg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Method for when user wants to decrement quantity
     */
    public void decrement(View view) {
        adjustQuantity(view, -1);

    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Display the price on the screen
     */
    private void displayPrice(double number) {

        String thanksMsg = "";
        if (number < 0.01) {
            thanksMsg = "Please order";
        } else if (number > 50.0) {
            thanksMsg = "You know we don't take credit cards, right?";
        } else {
            thanksMsg = "Thank you!!";
        }

        TextView thanksTextView = (TextView) findViewById(R.id.thank_you_msg);
        thanksTextView.setText(thanksMsg);

        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("Price: " + NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * Method for when user wants to increment quantity
     */
    public void increment(View view) {
        adjustQuantity(view, 1);
    }

    /**
     * @return indicator on whether the chocolate checkbox is checked
     */
    private boolean isChocolateChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkBoxChocolate);
        return cb.isChecked();
    }

    /**
     * @return indicator if the whipped cream indicator is checked
     */
    private boolean isWhippedCreamChecked() {
        CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
        return checkBoxWhippedCream.isChecked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateButtons();
    }

    /**
     * Method called when user wants to submit the order
     */
    public void submitOrder(View view) {
        createOrderSummary();
    }

    private void updateButtons() {
        TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
        int theQty = Integer.parseInt(quantityView.getText().toString());

        if (theQty == 0) {
            Button butt = (Button) findViewById(R.id.minusButton);
            butt.setEnabled(false);
            butt = (Button) findViewById(R.id.orderButton);
            butt.setEnabled(false);
        } else {
            Button butt = (Button) findViewById(R.id.minusButton);
            butt.setEnabled(true);
            butt = (Button) findViewById(R.id.orderButton);
            butt.setEnabled(true);
        }

        Button butt = (Button) findViewById(R.id.plusButton);
        if (theQty >= MAXCUPSODERABLE) {
            butt.setEnabled(false);
        } else {
            butt.setEnabled(true);
        }

    }

    /**
     * Update order when we aren't passed the number of cups... we'll read if from screen
     */
    private void updateOrder() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        int theNum = Integer.parseInt(quantityTextView.getText().toString());
        updateOrder(theNum);
    }

    /**
     * Update order based on the number of cups passed in, this is a wrapper
     * for the methods to display the number of cups and the price
     */
    private void updateOrder(int numCups) {
        boolean wantWhippedCream = isWhippedCreamChecked();
        boolean wantChocolate = isChocolateChecked();

        double extras = (wantWhippedCream ? PRICEFORWHIPPEDCREAM : 0.00) +
                (wantChocolate ? PRICEFORCHOCOLATE : 0.00);

        display(numCups);
        displayPrice(numCups * (PRICEPERCUP + extras));
    }
}
