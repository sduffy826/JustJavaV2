package com.example.duffy_w530.justjavav2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.CheckBox;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

  private static final double PRICEPERCUP = 5.00;
  private static final double PRICEFORWHIPPEDCREAM = 0.75;

  /**
   * Handles updating order based on adjust passed in (can be + or -)
   */
  private void adjustQuantity(View view, int adjustment) {
    TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
    int theQty = Integer.parseInt(quantityView.getText().toString());
    theQty += adjustment;
    updateOrder(theQty);
  }

  public void checkedWhippedCream(View view) {
    updateOrder();
  }

  private void createOrderSummary() {
    TextView thanksTextView = (TextView) findViewById(R.id.thank_you_msg);
    thanksTextView.setText("Thank you for your order, have a GREAT day!");
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
    if (number <  0.01) {
      thanksMsg = "Please order";
    }
    else
      if (number > 50.0) {
        thanksMsg = "You know we don't take credit cards, right?";
      }
      else {
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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * Method called when user wants to submit the order
   */
  public void submitOrder(View view) {
    createOrderSummary();
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
    CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
    boolean wantWhippedCream = checkBoxWhippedCream.isChecked();

    display(numCups);
    displayPrice(numCups * (PRICEPERCUP + (wantWhippedCream ? PRICEFORWHIPPEDCREAM : 0.00)));
  }
}
