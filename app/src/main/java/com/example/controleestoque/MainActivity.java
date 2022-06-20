package com.example.controleestoque;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.controleestoque.adapter.AdapterProdutosList;
import com.example.controleestoque.models.Produto;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton btnProximo;

    ListView lvProdutos;

    Intent i;

    ArrayList<Produto> listaProdutos = new ArrayList <Produto> ();
    AdapterProdutosList adapter;

    //firebase

    FirebaseDatabase database;
    DatabaseReference myRef;

    //FirebaseStorage storage;
    //StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(this.getApplicationContext());

        database = FirebaseDatabase.getInstance(firebaseApp, "https://controleestoque-f7ac3-default-rtdb.firebaseio.com/");
        FirebaseStorage.getInstance(firebaseApp);

        myRef = database.getReference("produto");
        adapter = new AdapterProdutosList(getApplicationContext(), listaProdutos);

        setContentView(R.layout.activity_main);

        btnProximo = (ImageButton) findViewById(R.id.btnProximo);

        Picasso.with(this).load(R.drawable.plus).resize(50, 50).centerCrop().into(btnProximo);

        lvProdutos = (ListView) findViewById(R.id.lvProdutos);

        carregarTabela();

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MainActivity.this, NewProductActivity.class);

                finish();

                startActivity(i);
            }
        });

    }

    public void carregarTabela() {

        Query query = myRef.orderByChild("nome");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listaProdutos.clear();

                for (DataSnapshot dados : snapshot.getChildren()) {
                    Produto p = new Produto();
                    p.setNome(dados.child("nome").getValue().toString());
                    p.setPreco((Double) dados.child("preco").getValue());
                    p.setEstoqueAtual(Integer.parseInt(dados.child("estoqueAtual").getValue().toString()));
                    p.setEstoqueIdeal(Integer.parseInt(dados.child("estoqueIdeal").getValue().toString()));
                    p.setImagePath(dados.child("imagePath").getValue().toString());

                    listaProdutos.add(p);

                }

                lvProdutos.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}