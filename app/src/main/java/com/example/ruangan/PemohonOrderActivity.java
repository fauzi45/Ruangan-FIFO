package com.example.ruangan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class PemohonOrderActivity extends AppCompatActivity {
    EditText orderDate, orderStartTime, orderFinishTime, orderPhone, orderDesc;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    private String ruanganId,title;
    Button btnOrder;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemohon_order);
        orderDate = findViewById(R.id.orderDate);
        orderStartTime = findViewById(R.id.orderStartTime);
        orderFinishTime = findViewById(R.id.orderFinishTime);
        orderPhone = findViewById(R.id.orderPhone);
        orderDesc = findViewById(R.id.orderDesc);
        btnOrder = findViewById(R.id.btnOrder);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Menunggu");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();

        ruanganId = getIntent().getStringExtra("idRuangan");
        title = getIntent().getStringExtra("Title");
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
        orderStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartTime();
            }
        });
        orderFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinishTime();
            }
        });
        orderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

//
    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                orderDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showStartTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minute = minute;
                orderStartTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }

    private void showFinishTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int fminute) {
                hour = hourOfDay;
                minute = fminute;
                orderFinishTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }

    private void submitOrder(){
        progressDialog.setMessage("Mohon Menunggu...");
        progressDialog.show();
        String phone = orderPhone.getText().toString();
        String desc = orderDesc.getText().toString();
        String date = orderDate.getText().toString().trim();
        String starTime = orderStartTime.getText().toString();
        String finishTime = orderFinishTime.getText().toString();
        String timestamp = ""+System.currentTimeMillis();

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("orderId",""+timestamp);
        hashMap.put("orderStatus","Menunggu Persetujuan");
        hashMap.put("orderPhone",""+phone);
        hashMap.put("orderDesc",""+desc);
        hashMap.put("orderDate",""+date);
        hashMap.put("orderStartTime",""+starTime);
        hashMap.put("orderFinishTime",""+finishTime);
        hashMap.put("orderUserId",""+firebaseAuth.getUid());
        hashMap.put("ruanganId",""+ruanganId);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        ref.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(PemohonOrderActivity.this, "Permohonan berhasil diajukan, silahkan tunggu persetujuan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PemohonOrderActivity.this, PemohonRuanganActivity.class));
                        Intent inten = new Intent(PemohonOrderActivity.this, PemohonOrderDetail.class);
                        inten.putExtra("orderId",timestamp);
                        inten.putExtra("ruanganId",ruanganId);
                        inten.putExtra("orderUserId",firebaseAuth.getUid());
                        startActivity(inten);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(PemohonOrderActivity.this, "Permohonan gagal diajukan", Toast.LENGTH_SHORT).show();

                    }
                });


    }
}