package com.example.ruangan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.sax.Element;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminLaporanActivity extends AppCompatActivity {
    Button pengguna, ruang, transaksi;
    List<Users> dataList;
    List<Ruangan> dataListe;
    ArrayList<ModelOrderAdmin> orderAdminArrayList;
    private int STORAGE_PERMISSION_CODE = 1;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_laporan);

        pengguna = findViewById(R.id.btn_laporanPengguna);
        ruang = findViewById(R.id.btn_laporanRuang);
        transaksi = findViewById(R.id.btn_laporanPermohonan);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.laporan);
        dataList = new ArrayList<>();
        dataListe = new ArrayList<>();
        orderAdminArrayList = new ArrayList<>();
        datashowPengguna();
        datashowRuangan();
        datashowTransaksi();
        bottomNavigationView.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
                case R.id.laporan:

                    return true;
                case R.id.pengaturan:
                    startActivity(new Intent(getApplicationContext(),AdminSettingActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
                    finish();
                    return true;
            }
            return false;
        });
        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(ContextCompat.checkSelfPermission(AdminLaporanActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) AdminLaporanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                    }else {

                    }
                    createPdfPermohonan();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
        ruang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(ContextCompat.checkSelfPermission(AdminLaporanActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) AdminLaporanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                    }else {

                    }
                    createPdfRuangan();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
        pengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(ContextCompat.checkSelfPermission(AdminLaporanActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) AdminLaporanActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                    }else {

                    }
                    createPdfPengguna();
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void datashowTransaksi() {
        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderAdminArrayList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ModelOrderAdmin modelOrderAdmin = ds.getValue(ModelOrderAdmin.class);
                    orderAdminArrayList.add(modelOrderAdmin);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void datashowRuangan(){
        databaseReference.child("ruangan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataListe.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){

                    Ruangan ruangan = itemSnapshot.getValue(Ruangan.class);
                    ruangan.setKey(itemSnapshot.getKey());
                    dataListe.add(ruangan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void datashowPengguna(){
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){

                    Users users = itemSnapshot.getValue(Users.class);
                    users.setKey(itemSnapshot.getKey());
                    dataList.add(users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createPdfRuangan() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"LaporanDataRuangan.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        float columnWidth1[] = {140,420};
        Table table1 = new Table(columnWidth1);

        Drawable d1 = getDrawable(R.drawable.logobaru);
        Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,stream1);
        byte[] bitmapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitmapData1);
        Image image1 = new Image(imageData1);
        image1.setWidth(200f);

        table1.addCell(new Cell(5,1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("POLITEKNIK NEGERI JAKARTA").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("JURUSAN TEKNIK SIPIL").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Jalan Prof. Dr. A. Siwabessy, Kampus UI, Depok 16425")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Telepon (021) 7270036, Hunting, Fax (021) 7270034").setFixedLeading(1.0f)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Laman : https://www.pnj.ac.id e-pos : humas@pnj.ac.id")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

//        table1.addCell(new Cell().add(new Paragraph("Laporan Data Pengguna")).setBorder(Border.NO_BORDER));

        float columnWidth2[] = {10,100,200,100,100};
        Table table2 = new Table(columnWidth2);

        table2.addCell(new Cell(1,5).add(new Paragraph("Laporan Data Ruangan").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell(1,5).add(new Paragraph("\n")).setBorder(Border.NO_BORDER));

        table2.addCell(new Cell().add(new Paragraph("No")));
        table2.addCell(new Cell().add(new Paragraph("Nama Ruangan")));
        table2.addCell(new Cell().add(new Paragraph("Deksripsi")));
        table2.addCell(new Cell().add(new Paragraph("Fasilitas")));
        table2.addCell(new Cell().add(new Paragraph("Kapasitas")));

        for(int i = 0; i < dataListe.size(); i++){
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
            table2.addCell(new Cell().add(new Paragraph(dataListe.get(i).getNama())));
            table2.addCell(new Cell().add(new Paragraph(dataListe.get(i).getDeksripsi())));
            table2.addCell(new Cell().add(new Paragraph(dataListe.get(i).getFasilitas())));
            table2.addCell(new Cell().add(new Paragraph(dataListe.get(i).getKapasitas())));
        }
//
//        float columnWidth3[] = {10,140,140,140};
//        Table table3 = new Table(columnWidth3);
        document.add(table1);
        document.add(new Paragraph("\n"));

        float three = 190f;
        float fullwidth[] = {three*3};
        Border gb = new SolidBorder(1f/2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);
        document.add(divider);
        document.add(new Paragraph("\n"));
        document.add(table2);
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
        String dat = s.format(d);
        document.add(new Paragraph("\n\nJakarta, "+dat).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("\n\n\n\nAlif Nurrizki Pangestu").setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Petugas").setTextAlignment(TextAlignment.RIGHT));

        document.close();
        Toast.makeText(this, "Laporan Data Ruangan Berhasil Dibuat", Toast.LENGTH_SHORT).show();
    }

    private void createPdfPermohonan() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"LaporanDataPermohonan.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        float columnWidth1[] = {140,420};
        Table table1 = new Table(columnWidth1);

        Drawable d1 = getDrawable(R.drawable.logobaru);
        Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,stream1);
        byte[] bitmapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitmapData1);
        Image image1 = new Image(imageData1);
        image1.setWidth(200f);

        table1.addCell(new Cell(5,1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("POLITEKNIK NEGERI JAKARTA").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("JURUSAN TEKNIK SIPIL").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Jalan Prof. Dr. A. Siwabessy, Kampus UI, Depok 16425")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Telepon (021) 7270036, Hunting, Fax (021) 7270034").setFixedLeading(1.0f)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Laman : https://www.pnj.ac.id e-pos : humas@pnj.ac.id")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

//        table1.addCell(new Cell().add(new Paragraph("Laporan Data Pengguna")).setBorder(Border.NO_BORDER));

        float columnWidth2[] = {10,100,100,100,100,100};
        Table table2 = new Table(columnWidth2);

        table2.addCell(new Cell(1,8).add(new Paragraph("Laporan Data Permohonan").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell(1,8).add(new Paragraph("\n")).setBorder(Border.NO_BORDER));

        table2.addCell(new Cell().add(new Paragraph("No")));
        table2.addCell(new Cell().add(new Paragraph("Nomor Telepon")));
        table2.addCell(new Cell().add(new Paragraph("Keterangan Acara")));
        table2.addCell(new Cell().add(new Paragraph("Jam Mulai")));
        table2.addCell(new Cell().add(new Paragraph("Jam Selesai")));
        table2.addCell(new Cell().add(new Paragraph("Status")));

        for(int i = 0; i < orderAdminArrayList.size(); i++){
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
            table2.addCell(new Cell().add(new Paragraph(orderAdminArrayList.get(i).getOrderPhone())));
            table2.addCell(new Cell().add(new Paragraph(orderAdminArrayList.get(i).getOrderDesc())));
            table2.addCell(new Cell().add(new Paragraph(orderAdminArrayList.get(i).getOrderStartTime())));
            table2.addCell(new Cell().add(new Paragraph(orderAdminArrayList.get(i).getOrderFinishTime())));
            table2.addCell(new Cell().add(new Paragraph(orderAdminArrayList.get(i).getOrderStatus())));
        }
//
//        float columnWidth3[] = {10,140,140,140};
//        Table table3 = new Table(columnWidth3);
        document.add(table1);
        document.add(new Paragraph("\n"));

        float three = 190f;
        float fullwidth[] = {three*3};
        Border gb = new SolidBorder(1f/2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);
        document.add(divider);
        document.add(new Paragraph("\n"));
        document.add(table2);
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd MMMM yyyy");
        String dat = s.format(d);

        document.add(new Paragraph("\nJakarta, "+dat).setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.BOTTOM));
        document.add(new Paragraph("\n\n\n\nAlif Nurrizki Pangestu").setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.BOTTOM));
        document.add(new Paragraph("Petugas").setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.BOTTOM));

        document.close();
        Toast.makeText(this, "Laporan Data Pengguna Berhasil Dibuat", Toast.LENGTH_SHORT).show();

    }
    private void createPdfPengguna() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"LaporanDataPengguna.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        float columnWidth1[] = {140,420};
        Table table1 = new Table(columnWidth1);

        Drawable d1 = getDrawable(R.drawable.logobaru);
        Bitmap bitmap1 = ((BitmapDrawable)d1).getBitmap();
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,stream1);
        byte[] bitmapData1 = stream1.toByteArray();

        ImageData imageData1 = ImageDataFactory.create(bitmapData1);
        Image image1 = new Image(imageData1);
        image1.setWidth(200f);

        table1.addCell(new Cell(5,1).add(image1).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(new Paragraph("POLITEKNIK NEGERI JAKARTA").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("JURUSAN TEKNIK SIPIL").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Jalan Prof. Dr. A. Siwabessy, Kampus UI, Depok 16425")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Telepon (021) 7270036, Hunting, Fax (021) 7270034").setFixedLeading(1.0f)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table1.addCell(new Cell().add(new Paragraph("Laman : https://www.pnj.ac.id e-pos : humas@pnj.ac.id")).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

//        table1.addCell(new Cell().add(new Paragraph("Laporan Data Pengguna")).setBorder(Border.NO_BORDER));

        float columnWidth2[] = {10,170,170,170};
        Table table2 = new Table(columnWidth2);

        table2.addCell(new Cell(1,4).add(new Paragraph("Laporan Data Pengguna").setFontSize(18f).setBold()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
        table2.addCell(new Cell(1,4).add(new Paragraph("\n")).setBorder(Border.NO_BORDER));

        table2.addCell(new Cell().add(new Paragraph("No")));
        table2.addCell(new Cell().add(new Paragraph("Nama")));
        table2.addCell(new Cell().add(new Paragraph("Email")));
        table2.addCell(new Cell().add(new Paragraph("Tipe User")));

        for(int i = 0; i < dataList.size(); i++){
            table2.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1))));
            table2.addCell(new Cell().add(new Paragraph(dataList.get(i).getName())));
            table2.addCell(new Cell().add(new Paragraph(dataList.get(i).getEmail())));
            table2.addCell(new Cell().add(new Paragraph(dataList.get(i).getUserType())));
        }
//
//        float columnWidth3[] = {10,140,140,140};
//        Table table3 = new Table(columnWidth3);
        document.add(table1);
        document.add(new Paragraph("\n"));

        float three = 190f;
        float fullwidth[] = {three*3};
        Border gb = new SolidBorder(1f/2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);
        document.add(divider);
        document.add(new Paragraph("\n"));
        document.add(table2);
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("EEEE dd MMMM yyyy");
        String dat = s.format(d);
        document.add(new Paragraph("\n\nJakarta, "+dat).setFontSize(16f).setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.BOTTOM));
        document.add(new Paragraph("\n\n\n\nAlif Nurrizki Pangestu").setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.BOTTOM));
        document.add(new Paragraph("Petugas").setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.BOTTOM));

        document.close();
        Toast.makeText(this, "Laporan Data Pengguna Berhasil Dibuat", Toast.LENGTH_SHORT).show();
    }
}