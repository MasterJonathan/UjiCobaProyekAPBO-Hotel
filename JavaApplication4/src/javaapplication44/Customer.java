/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication44;

/**
 *
 * @author Kevin Jonathan
 */
public class Customer {
    int id = 1;
    String nama;

    public Customer(String nama) {
        this.id = id;
        this.nama = nama;
        
        id += 1;
    }

    public int getId() {
        return id;
    }
    
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
    
}
