package com.example.controleestoque;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.controleestoque.models.Produto;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

public class NewProductActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    //componentes

    ImageView ivProduto;
    Button btnSelectImage;
    EditText etNome;
    EditText etPreco;
    EditText etQuantity;
    Button btnPlusQuantity;
    Button btnMinusQuantity;
    EditText etIdealQuantity;
    Button btnPlusIdealQuantity;
    Button btnMinusIdealQuantity;
    Button btnCadastrarProduto;

    //

    Produto p;
    String imageUri = null;

    //

    String id;

    //

    Intent i;

    //dados

    int quantidade = 0;
    int quantidadeIdeal = 0;

    //

    OnSuccessListener onSuccessListener;
    OnFailureListener onFailureListener;

    //firebase
    DatabaseReference myRef;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproduct);

        i = new Intent(this, MainActivity.class);

        //

        onSuccessListener = new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(NewProductActivity.this, "Funcionou", Toast.LENGTH_SHORT).show();
            }
        };

        onFailureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewProductActivity.this, "Falhou", Toast.LENGTH_SHORT).show();
            }
        };

        //FirebaseApp.getInstance();

        ivProduto = findViewById(R.id.ivProduto);
        ivProduto.setImageResource(R.drawable.produto_icon);
        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);

        etNome = (EditText) findViewById(R.id.etNome);
        etPreco = (EditText) findViewById(R.id.etPreco);
        etQuantity = (EditText) findViewById(R.id.etQuantity);
        btnPlusQuantity = (Button) findViewById(R.id.btnPlusQuantity);
        btnMinusQuantity = (Button) findViewById(R.id.btnMinusQuantity);

        /////////////////////////////////////

        FirebaseApp firebaseApp = FirebaseApp.getInstance();

        myRef = FirebaseDatabase.getInstance(firebaseApp).getReference("produto");

        storageReference = FirebaseStorage.getInstance(firebaseApp).getReference();

        /////////////////////////////////////

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if(etQuantity.getText().toString().length() != 0) {
                        quantidade = Integer.parseInt(etQuantity.getText().toString());
                    } else {
                        quantidade = 0;
                    }

                } catch (Exception e) {

                }

            }
        });

        btnPlusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidade = quantidade + 1;
                etQuantity.setText("" + quantidade);
            }
        });

        btnMinusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantidade > 0) {
                    quantidade--;
                    etQuantity.setText("" + quantidade);
                }
            }
        });

        /////////////////////////////////////

        etIdealQuantity = (EditText) findViewById(R.id.etIdealQuantity);
        btnPlusIdealQuantity = (Button) findViewById(R.id.btnPlusIdealQuantity);
        btnMinusIdealQuantity = (Button) findViewById(R.id.btnMinusIdealQuantity);

        etIdealQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {

                    if(etIdealQuantity.getText().toString().length() != 0) {
                        quantidadeIdeal = Integer.parseInt(etIdealQuantity.getText().toString());
                    } else {
                        quantidadeIdeal = 0;
                    }

                } catch (Exception e) {

                }

            }
        });

        btnPlusIdealQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeIdeal = quantidadeIdeal + 1;
                etIdealQuantity.setText("" + quantidadeIdeal);
            }
        });

        btnMinusIdealQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantidadeIdeal > 0) {
                    quantidadeIdeal--;
                    etIdealQuantity.setText("" + quantidadeIdeal);
                }
            }
        });

        /////////////////////////////////////

        btnSelectImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);

            }

        });

        btnCadastrarProduto = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = myRef.push().getKey().toString();

                GetImageUri getImageUri = new GetImageUri();
                getImageUri.execute(10);

                /*try {
                    addObserver((IObserver) MainActivity.class.());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }*/

                i = new Intent(NewProductActivity.this, MainActivity.class);

                startActivity(i);

                //uploadFoto();
                //uploadDados();

            }
        });

    }

    public void uploadDados() {

        while(imageUri == null) {
            //WAITING FOR THE IMAGE URI
        }

        p = new Produto();
        p.setId(id);
        p.setNome(etNome.getText().toString());
        p.setPreco(Double.parseDouble(etPreco.getText().toString()));
        p.setEstoqueAtual(quantidade);
        p.setEstoqueIdeal(quantidadeIdeal);
        p.setImagePath(imageUri);

        myRef.child(id).setValue(p);

    }



    public void uploadFoto() {
        ivProduto.setDrawingCacheEnabled(true);
        ivProduto.buildDrawingCache();

        Bitmap bitmap1 = ((BitmapDrawable) ivProduto.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imagem = byteArrayOutputStream.toByteArray();

        StorageReference imgReference = storageReference.child(id+".jpg");

        UploadTask uploadTask = imgReference.putBytes(imagem);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        imageUri = uri.toString();

                        Toast.makeText(NewProductActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        //uploadTask.addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    private class GetImageUri extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {

            uploadFoto();

            while(imageUri == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "finished";
        }

        @Override
        protected void onPostExecute(String s) {
            uploadDados();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri imageUri = data.getData();

            Picasso.with(NewProductActivity.this).load(imageUri).into(ivProduto);
        }
    }

}
