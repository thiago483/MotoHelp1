package com.example.icaro.testeprototipo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by icaro on 06/04/2017.
 */

public class Enderecos {
    @SerializedName("Nome")
    private String nome;
    private double lat;
    private double lng;
    private String gayfagh1n1er;

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public double getLat(){
        return lat;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLng(){
        return lng;
    }
    public void setLng(double lng){
        this.lng = lng;
    }
}
