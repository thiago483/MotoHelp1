package com.example.icaro.testeprototipo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    Firebase objetoRef;
    TextView textView;
    Button botao1;
    Button botao2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Firebase.setAndroidContext(this);

        botao1 = (Button) findViewById(R.id.botao1);
        botao2 = (Button) findViewById(R.id.botao2);
        textView = (TextView) findViewById(R.id.textView);

        objetoRef = new Firebase("https://helpmoto-29499.firebaseio.com/endereco");

        objetoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> mapa = dataSnapshot.getValue(Map.class);

                String nome = mapa.get("nome");
                String lag = mapa.get("lag");
                String lng = mapa.get("lng");
                textView.setText(nome + "" + lag + "" + lng);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objetoRef.setValue("Azul");
            }
        });

        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objetoRef.setValue("Vermelho");
            }
        });
    }
}
