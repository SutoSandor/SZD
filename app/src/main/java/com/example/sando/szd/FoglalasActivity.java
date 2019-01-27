package com.example.sando.szd;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class FoglalasActivity extends AppCompatActivity {

    private NumberPicker np;
    private TimePicker tp;
    private int hanyfo;
    private LinearLayout ido_layout;
    private TextView datum_szoveg,ido_szoveg;
    private Button foglalas_button,datum_beallit,ido_beallit;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foglalas);
        final Calendar mcurrentTime = Calendar.getInstance();
        hanyfo = 1;
        init();
        foglalas_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hanyfo = np.getValue();
            }
        });
        datum_beallit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final DatePickerDialog datum = new DatePickerDialog(FoglalasActivity.this);
                datum.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DatePicker picker = datum.getDatePicker();
                        int nap = picker.getDayOfMonth();
                        int honap = picker.getMonth() + 1;
                        int ev = picker.getYear();
                        datum_szoveg.setText("Dátum: " + ev + ". " + honap + ". "+ nap+".");
                    }
                });
                datum.show();
            }
        });
        ido_beallit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FoglalasActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ido_szoveg.setText("Időpont: " + selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    public void init(){
        np = findViewById(R.id.numberpicker);
        np.setMaxValue(20);
        np.setMinValue(1);
        foglalas_button = findViewById(R.id.foglalas_button);
        datum_beallit = findViewById(R.id.datum_beallit);
        datum_szoveg = findViewById(R.id.datum_text);
        ido_szoveg = findViewById(R.id.ido_text);
        ido_beallit = findViewById(R.id.ido_button);
        ido_layout = findViewById(R.id.ido_layout);
    }
}
