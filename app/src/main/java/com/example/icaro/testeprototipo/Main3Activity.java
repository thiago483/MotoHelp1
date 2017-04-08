package com.example.icaro.testeprototipo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    private void getJson(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object retorno = null;
                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet request = new HttpGet(url);
                    //request.setURI(new URI(url));
                    HttpResponse response = httpclient.execute(request);
                    InputStream content = response.getEntity().getContent();
                    Reader reader = new InputStreamReader(content);
                    Gson gson = new Gson();
                    Enderecos[] lista = gson.fromJson(reader, Enderecos[].class);
                    //retorno = gson.fromJson(reader, ArrayList<Enderecos>.class);
                    for(int i = 0; i<lista.length ; i++){
                        Enderecos end = new Enderecos();


                    }
                    content.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
