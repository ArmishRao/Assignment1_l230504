package com.example.assignment1_l230504;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SnacksActivity extends AppCompatActivity {
    Button btnplus1, btnplus2, btnplus3, btnsub1, btnsub2, btnsub3, btnconfirm;
    TextView tvQuantity1, tvQuantity2, tvQuantity3;

    // hardcoding it for now
    final double POPCORN_PRICE = 8.99;
    final double NACHOS_PRICE = 7.99;
    final double SOFT_DRINK_PRICE = 5.99;

    int popcornQuantity = 0;
    int nachosQuantity = 0;
    int softDrinkQuantity = 0;

    ArrayList<Integer> seats;
    String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_snack);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        seats = getIntent().getIntegerArrayListExtra("SEATS");
        movieName = getIntent().getStringExtra("MOVIE_NAME");

        if (seats == null || seats.isEmpty()) {
            Toast.makeText(this, "No seats received!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (movieName == null || movieName.isEmpty()) {
            Toast.makeText(this, "No movie name received!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        initViews();
        setupClickListeners();
    }

    private void setupClickListeners() {
        btnplus1.setOnClickListener(v -> {
            popcornQuantity++;
            showQuantityUpdate("Popcorn", popcornQuantity);
            if (tvQuantity1 != null)
                tvQuantity1.setText(String.valueOf(popcornQuantity));
        });

        btnplus2.setOnClickListener(v -> {
            nachosQuantity++;
            showQuantityUpdate("nachos", nachosQuantity);
            if (tvQuantity2 != null)
                tvQuantity2.setText(String.valueOf(nachosQuantity));
        });

        btnplus3.setOnClickListener(v -> {
            softDrinkQuantity++;
            showQuantityUpdate("soft Drinks", softDrinkQuantity);
            if (tvQuantity3 != null)
                tvQuantity3.setText(String.valueOf(softDrinkQuantity));
        });

        btnsub1.setOnClickListener(v -> {
            if (popcornQuantity > 0)
                popcornQuantity--;
            showQuantityUpdate("popcorn", popcornQuantity);
            if (tvQuantity1 != null)
                tvQuantity1.setText(String.valueOf(popcornQuantity));
        });

        btnsub2.setOnClickListener(v -> {
            if (nachosQuantity > 0)
                nachosQuantity--;
            showQuantityUpdate("nachos", nachosQuantity);
            if (tvQuantity2 != null)
                tvQuantity2.setText(String.valueOf(nachosQuantity));
        });

        btnsub3.setOnClickListener(v -> {
            if (softDrinkQuantity > 0)
                softDrinkQuantity--;
            showQuantityUpdate("soft Drinks", softDrinkQuantity);
            if (tvQuantity3 != null)
                tvQuantity3.setText(String.valueOf(softDrinkQuantity));
        });

        btnconfirm.setOnClickListener(v -> {
            try {
                double totalSnackPrice = calculateTotalSnackPrice();

                ArrayList<SnackItem> selectedSnacks = new ArrayList<>();

                if (popcornQuantity > 0) {
                    selectedSnacks.add(new SnackItem("Popcorn", "Large/Buttered", POPCORN_PRICE, popcornQuantity));
                }
                if (nachosQuantity > 0) {
                    selectedSnacks.add(new SnackItem("Nachos", "with Cheese dip", NACHOS_PRICE, nachosQuantity));
                }
                if (softDrinkQuantity > 0) {
                    selectedSnacks.add(new SnackItem("Soft Drink", "Large/Any Flavor", SOFT_DRINK_PRICE, softDrinkQuantity));
                }

                android.util.Log.d("SnacksActivity", "Movie: " + movieName);
                android.util.Log.d("SnacksActivity", "Seats: " + seats.toString());
                android.util.Log.d("SnacksActivity", "Snacks Total: $" + totalSnackPrice);
                android.util.Log.d("SnacksActivity", "Selected Snacks: " + selectedSnacks.size());

                Intent intent = new Intent(SnacksActivity.this, TicketSummaryActivity.class);
                intent.putExtra("MOVIE_NAME", movieName);
                intent.putIntegerArrayListExtra("SEATS", seats);
                intent.putExtra("SNACKS_TOTAL", totalSnackPrice);
                intent.putExtra("TICKET_PRICE_PER_SEAT", 16); // $16 per seat as in image
                intent.putExtra("SCREEN_TYPE", "ScreenX • Dolby Atmos");
                intent.putExtra("THEATER", "Stars (90°Mall)");
                intent.putExtra("HALL", "1st");
                intent.putExtra("DATE", "13.04.2025");
                intent.putExtra("TIME", "22:15");
                intent.putExtra("SELECTED_SNACKS", selectedSnacks);

                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private double calculateTotalSnackPrice() {
        return (popcornQuantity * POPCORN_PRICE) +
                (nachosQuantity * NACHOS_PRICE) +
                (softDrinkQuantity * SOFT_DRINK_PRICE);
    }

    private void showQuantityUpdate(String itemName, int quantity) {
        String message = itemName + " quantity: " + quantity;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void initViews() {
        btnplus1 = findViewById(R.id.btnplus1);
        btnsub1 = findViewById(R.id.btnsub1);
        btnplus2 = findViewById(R.id.btnplus2);
        btnsub2 = findViewById(R.id.btnsub2);
        btnplus3 = findViewById(R.id.btnplus3);
        btnsub3 = findViewById(R.id.btnsub3);
        btnconfirm = findViewById(R.id.confirm);
        tvQuantity1 = findViewById(R.id.tv_quantity1);
        tvQuantity2 = findViewById(R.id.tv_quantity2);
        tvQuantity3 = findViewById(R.id.tv_quantity3);
    }
}