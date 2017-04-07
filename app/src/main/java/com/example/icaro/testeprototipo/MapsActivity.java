package com.example.icaro.testeprototipo;

import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.params.CoreProtocolPNames;

import com.firebase.client.Firebase;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback,
                                                        GoogleMap.OnMapClickListener{
    private GoogleMap mMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        String url = "http://192.168.0.102:3000/Data";
        System.out.println(url);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getJson(url);

        //LatLng cordenadas = new LatLng(-19.5515, -43.5616);
        //MarkerOptions marker = new MarkerOptions();
        //marker.position(cordenadas);
        //marker.title("Borracheiro");
        //mMap.addMarker(marker);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(bh));

    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(), "Coordenadas: " + latLng.toString(),
                Toast.LENGTH_SHORT).show();
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

                        LatLng cordenadas = new LatLng(lista[Integer.parseInt("lat")].getLat(), lista[Integer.parseInt("lng")].getLng());
                        MarkerOptions marker = new MarkerOptions();
                        marker.position(cordenadas);
                        marker.title(lista[Integer.parseInt("nome")].getNome());
                        mMap.addMarker(marker);
                    }
                    content.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
