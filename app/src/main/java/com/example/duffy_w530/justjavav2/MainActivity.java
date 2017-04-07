package com.example.duffy_w530.justjavav2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /**
   * Method called when user wants to submit the order
   */
  public void submitOrder(View view) {
    int numberOfCups = 2;
    updateOrder(numberOfCups);
  }

  /**
   * Method for when user wants to decrement quantity
   */
  public void decrement(View view) {
    adjustQuantity(view, -1);
  }

  /**
   * Method for when user wants to increment quantity
   */
  public void increment(View view) {
    adjustQuantity(view, 1);
  }

  /**
   * Update order based on the number of cups passed in, this is a wrapper
   * for the methods to display the number of cups and the price
   */
  private void updateOrder(int numCups) {
    display(numCups);
    displayPrice(numCups * 5);
  }

  /**
   * Handles updating order based on adjust passed in (can be + or -)
   */
  private void adjustQuantity(View view, int adjustment) {
    TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
    int theQty = Integer.parseInt(quantityView.getText().toString());
    theQty += adjustment;
    updateOrder(theQty);
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
  private void displayPrice(int number) {

    String thanksMsg = "";
    if (number == 0) {
      thanksMsg = "Please order";
    }
    else
      if (number > 50) {
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
}
