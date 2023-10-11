package com.example.ruangan;

public class Ruangan {
    private String nama;
    private String deksripsi;
    private String kapasitas;
    private String idruangan;

    public String getIdruangan() {
        return idruangan;
    }

    public void setIdruangan(String idruangan) {
        this.idruangan = idruangan;
    }

    public String getKey() {
        return key;
    }

    public Ruangan(String idruangan) {
        this.idruangan = idruangan;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public Ruangan(String nama, String deksripsi, String kapasitas, String fasilitas, String img) {
        this.nama = nama;
        this.deksripsi = deksripsi;
        this.kapasitas = kapasitas;
        this.fasilitas = fasilitas;
        this.img = img;
    }

    public String getNama() {
        return nama;
    }

    public String getDeksripsi() {
        return deksripsi;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public String getImg() {
        return img;
    }

    private String fasilitas;
    private String img;

    public Ruangan(){

    }
}
