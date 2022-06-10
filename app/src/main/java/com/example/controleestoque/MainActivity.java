package com.example.controleestoque;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    ImageView ibProduto;

    Button btnSelectImage;

    EditText etQuantity;
    Button btnPlusQuantity;
    Button btnMinusQuantity;

    EditText etIdealQuantity;
    Button btnPlusIdealQuantity;
    Button btnMinusIdealQuantity;

    int quantidade = 0;
    int quantidadeIdeal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ibProduto = (ImageView) findViewById(R.id.ibProduto);
        ibProduto.setImageResource(R.drawable.produto_icon);
        ibProduto.setLayoutParams(new LinearLayout.LayoutParams(500, 500));

        etQuantity = (EditText) findViewById(R.id.etQuantity);
        btnPlusQuantity = (Button) findViewById(R.id.btnPlusQuantity);
        btnMinusQuantity = (Button) findViewById(R.id.btnMinusQuantity);

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                /*try {

                    if(etQuantity.getText().toString().length() != 0) {
                        quantidade = Integer.parseInt(etQuantity.getText().toString());
                    } else {
                        quantidade = 0;
                    }

                } catch (Exception e) {

                }*/

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

                /*try {

                    if(etQuantity.getText().toString().length() != 0) {
                        quantidade = Integer.parseInt(etQuantity.getText().toString());
                    } else {
                        quantidade = 0;
                    }

                } catch (Exception e) {

                }*/

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

        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);

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

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());

                Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);

                ibProduto.setImageBitmap(bitmap1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

}