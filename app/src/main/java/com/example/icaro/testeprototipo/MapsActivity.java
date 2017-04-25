package com.example.icaro.testeprototipo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback,
                                                        GoogleMap.OnMapClickListener{
    private GoogleMap mMap;
    private Enderecos[] lista;


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
        //String url = "http://192.168.43.224:3000/Data";
        String url = "http://192.168.43.195:8080/lista/home/jsonContatos";
        getJson(url);
        //System.out.println(url);

        //LatLng cordenadas = new LatLng(-19.5515, -43.5616);
        //MarkerOptions marker = new MarkerOptions();
        //marker.position(cordenadas);
        //marker.title("Borracheiro");String url = "http://192.168.43.195:8080/lista/home/jsonContatos";
        getJson(url);
        //mMap.addMarker(marker);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(bh));
        
        //Teste de commit

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
                    lista = gson.fromJson(reader, Enderecos[].class);
                    //List<Enderecos> lista = (List<Enderecos>) gson.fromJson(reader, Enderecos.class);
                    //retorno = gson.fromJson(reader, ArrayList<Enderecos>.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            marcarMapa(lista);
                        }
                    });
                    content.close();


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private  void marcarMapa(Enderecos[] lista){

        for (Enderecos endereco : lista) {
            LatLng cordenadas = new LatLng(endereco.getLat(), endereco.getLng());
            MarkerOptions marker = new MarkerOptions();
            marker.position(cordenadas);
            marker.title(endereco.getNome());
            mMap.addMarker(marker);
        }
    }
}
