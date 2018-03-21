package com.ejemplo.files001;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText textBox;
    Button botonSave,botonLoad;
    boolean sdDisponible=false;
    boolean sdEscritura=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textBox = (EditText) findViewById(R.id.editText);
        botonSave=(Button) findViewById(R.id.btnSave);
        botonLoad=(Button) findViewById(R.id.btnLoad);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }

        String estado = Environment.getExternalStorageState();
        if(estado.equals(Environment.MEDIA_MOUNTED)){
            sdDisponible=true;
            sdEscritura=true;
        }else if(estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            sdDisponible=true;
            sdEscritura=false;
        }else{
            sdDisponible=false;
            sdEscritura=false;
        }

        botonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }
                String str = textBox.getText().toString();
                if(sdDisponible&&sdEscritura){
                    try{
                        File ruta=Environment.getExternalStorageDirectory();
                        File f=new File(ruta.getAbsolutePath(),"fichero.txt");
                        OutputStreamWriter fout=new OutputStreamWriter(new FileOutputStream(f));
                        fout.write(str);
                        fout.close();
                        Toast.makeText(MainActivity.this,"Guardado Exitoso", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.e("Ficheros","Error al guardar texto en la tarjeta SD");
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Tarjeta SD no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }

                if(sdDisponible){
                    try{
                        File ruta=Environment.getExternalStorageDirectory();
                        File f=new File(ruta.getAbsolutePath(),"fichero.txt");
                        BufferedReader fin=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                        String texto=fin.readLine();
                        textBox.setText(texto);
                        fin.close();
                    }catch(Exception e){
                        Log.e("Ficheros","Error al leer fichero desde la tarjeta SD");
                    }
                }
            }
        });
    }

}


