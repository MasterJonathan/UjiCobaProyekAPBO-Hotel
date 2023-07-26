/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication44;
import java.util.ArrayList;

/**
 *
 * @author Kevin Jonathan
 */
public class Kamar {
    static int jumlah = 1;
    String id;
    String tipe;
    
    int jumlahOrang;
    int harga;
    int status; //0 : KOSONG, 1 : TERISI
    
    ArrayList<AddOn> AddOnTerpasang = new ArrayList<AddOn>();
    
    public Kamar(String nama, String tipe, int harga) {
        this.id = nama;
        this.status = 0;
        this.jumlahOrang = 0;
        
        
        this.tipe = tipe;
        this.harga = harga;
        
        jumlah += 1;
    }
    
    public Kamar(String nama, String tipe, int harga, int jumlahOrang, ArrayList<AddOn>pasangAddOn) {
        this.id = nama;
        this.status = 0;
        
        this.tipe = tipe;
        this.harga = harga;
        this.jumlahOrang = jumlahOrang;
        this.AddOnTerpasang = pasangAddOn;
        
        jumlah += 1;
    }
    
    

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlahOrang() {
        return jumlahOrang;
    }

    public void setJumlahOrang(int jumlahOrang) {
        this.jumlahOrang = jumlahOrang;
    }

    public ArrayList<AddOn> getAddOnTerpasang() {
        return AddOnTerpasang;
    }

    public void setAddOnTerpasang(ArrayList<AddOn> AddOnTerpasang) {
        this.AddOnTerpasang = AddOnTerpasang;
    }
    
    public String toString() {
        return this.id + " - " + this.tipe + " - " + this.harga;
    }
    
    
    
}
