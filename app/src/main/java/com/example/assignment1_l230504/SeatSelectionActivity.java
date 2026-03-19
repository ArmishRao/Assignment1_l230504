package com.example.assignment1_l230504;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SeatSelectionActivity extends AppCompatActivity {

    GridLayout gridLeft, gridRight;
    Button btnBookSeats, btnProceedSnacks;
    TextView tvMovieName, tvTotal;

    ArrayList<Integer> selectedSeats = new ArrayList<>();
    ArrayList<Integer> bookedSeats = new ArrayList<>();
    String movie;

    int pricePerSeat = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        gridLeft = findViewById(R.id.gridLeft);
        gridRight = findViewById(R.id.gridRight);
        btnBookSeats = findViewById(R.id.btnBookSeats);
        btnProceedSnacks = findViewById(R.id.btnProceedSnacks);
        tvMovieName = findViewById(R.id.tvMovieName);
        tvTotal = findViewById(R.id.tvTotal);

        movie = getIntent().getStringExtra("MOVIE_NAME");
        tvMovieName.setText(movie);
        bookedSeats.add(2);
        bookedSeats.add(5);
        bookedSeats.add(9);
        bookedSeats.add(14);

        createSeats(gridLeft, 16, 0);
        createSeats(gridRight, 16, 100);

        btnBookSeats.setOnClickListener(v -> goToSummary());
        btnProceedSnacks.setOnClickListener(v -> goToSnacks());

        updateTotal();
    }

    private void createSeats(GridLayout grid, int count, int offset) {
        for (int i = 0; i < count; i++) {
            Button seat = new Button(this);
            int seatId = i + offset;
            seat.setTag(seatId);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 90;
            params.height = 90;
            params.setMargins(10,10,10,10);
            seat.setLayoutParams(params);

            seat.setText("");

            if (bookedSeats.contains(seatId)) {
                seat.setBackgroundColor(Color.RED);
                seat.setEnabled(false);
            } else {
                seat.setBackgroundColor(Color.DKGRAY);
                seat.setOnClickListener(this::toggleSeat);
            }

            grid.addView(seat);
        }
    }

    private void toggleSeat(View view) {
        Button seat = (Button) view;
        int id = (int) seat.getTag();

        if (selectedSeats.contains(id)) {
            selectedSeats.remove(Integer.valueOf(id));
            seat.setBackgroundColor(Color.DKGRAY);
        } else {
            selectedSeats.add(id);
            seat.setBackgroundColor(Color.GREEN);
        }

        btnProceedSnacks.setEnabled(!selectedSeats.isEmpty());
        updateTotal();
    }

    private void updateTotal() {
        int count = selectedSeats.size();
        int total = count * pricePerSeat;
        tvTotal.setText("Selected: " + count + " | Total: Rs " + total);
    }

    private void goToSummary() {
        bookedSeats.addAll(selectedSeats);

        Intent i = new Intent(this, TicketSummaryActivity.class);
        i.putIntegerArrayListExtra("SEATS", selectedSeats);
        i.putExtra("TOTAL", selectedSeats.size() * pricePerSeat);
        startActivity(i);
    }

    private void goToSnacks() {
        bookedSeats.addAll(selectedSeats);

        Intent i = new Intent(this, SnacksActivity.class);
        i.putIntegerArrayListExtra("SEATS", selectedSeats);
        i.putExtra("MOVIE_NAME",movie);
        startActivity(i);
    }
}
