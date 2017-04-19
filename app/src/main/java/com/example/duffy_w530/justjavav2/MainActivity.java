package com.example.duffy_w530.justjavav2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

  private static final double PRICEPERCUP = 5.00;
  private static final double PRICEFORWHIPPEDCREAM = 0.75;
  private static final double PRICEFORCHOCOLATE = 1.50;

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
   * Called when checkbox for one of the condiments is checked, just calls the updateOrder method
   * @param view
   */
  public void condimentChecked(View view) {
    updateOrder();
  }

  private void createOrderSummary() {
    EditText nameEditText = (EditText) findViewById(R.id.name);
    TextView thanksTextView = (TextView) findViewById(R.id.thank_you_msg);

    String outMsg = "Hi " + nameEditText.getText().toString() +
                     "\nHere's your order: \nCondiments: ";

    int condiments = 0;

    if ( isWhippedCreamChecked() ) {
      outMsg += "Whipped Cream";
      Log.v("MainActivity","whipped cream checkbox is checked");
      condiments++;
    }
    if ( isChocolateChecked() ) {
      outMsg += (condiments > 0 ? ", " : "" ) + "Chocolate (yum)";
      Log.v("MainActivity","chocolate checkbox is checked");
      condiments++;
    }
    if (condiments == 0) outMsg += "(none - boring)";
    outMsg += "\nHave a great day!";

    thanksTextView.setText(outMsg);
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
   * @return indicator if the whipped cream indicator is checked
   */
  private boolean isWhippedCreamChecked() {
    CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.checkBoxWhippedCream);
    return checkBoxWhippedCream.isChecked();
  }

  /**
   * @return indicator on whether the chocolate checkbox is checked
   */
  private boolean isChocolateChecked() {
    CheckBox cb = (CheckBox) findViewById(R.id.checkBoxChocolate);
    return cb.isChecked();
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
    boolean wantWhippedCream = isWhippedCreamChecked();
    boolean wantChocolate = isChocolateChecked();

    double extras = (wantWhippedCream ? PRICEFORWHIPPEDCREAM : 0.00) +
                    (wantChocolate ? PRICEFORCHOCOLATE : 0.00);

    display(numCups);
    displayPrice(numCups * (PRICEPERCUP + extras));
  }
}
