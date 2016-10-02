package app.com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;
import static android.R.attr.order;


//This app displays an order form to order coffee.
public class MainActivity extends AppCompatActivity {

    private int quantity = 1;
    private static final int COFFEE_PRICE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //This method is called when the increment button is clicked
    public void incrementQuantity(View view){
        if(quantity == 100) {
            //Show an error message as a toast (Length_short makes the duration short)
            Toast.makeText(this, "You cannot have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);

    }


    //This method is called when the decrement button is clicked
    public void decrementQuantity(View view){
        if(quantity == 1) {
            //Show an error message as a toast (Length_short makes the duration short)
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }


//    //This method displays the given text on the screen.
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


    //This method displays the given quantity value on the screen.
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    //computes the total price of the order
    public int computePrice(boolean hasWhippedCream ,boolean hasChocolate) {
        int price = COFFEE_PRICE;
        if(hasWhippedCream) {
            price += 1;
        }
        if(hasChocolate) {
            price += 2;
        }
        price *= quantity;
        return price;
    }


    //Creates a summary message for order
    private String createOrderSummary(String name, int totalPrice, boolean addWhippedCream, boolean addChocolate) {
        String message = "Name: " + name;
        if(addWhippedCream){
            message += "\nAdd whipped cream ";
        }
        if(addChocolate){
            message += "\nAdd Chocolate ";
        }
        message += "\nQuantity: " + quantity +
                   "\nTotal: $" + totalPrice +
                   "\nThank you!";

        return message;
    }


    //This method is called when the order button is clicked.
    public void submitOrder(View view) {
        //Get user name
        EditText userName = (EditText) findViewById(R.id.name_field);
        String name = userName.getText().toString();

        //Does the user want whipped cream
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = checkBox.isChecked();

        //Does the user want chocolate
        checkBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkBox.isChecked();

        //get order total price
        int totalPrice = computePrice(hasWhippedCream, hasChocolate);

        //get email body text
        String emailBody = createOrderSummary(name, totalPrice, hasWhippedCream, hasChocolate);

        //send to email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}