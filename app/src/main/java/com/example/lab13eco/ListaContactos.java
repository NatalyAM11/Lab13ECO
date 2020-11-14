package com.example.lab13eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListaContactos extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase db;
    private ContactoAdapter adapter;
    private ListView listaContactos;
    private EditText nombreC, telefono;
    private Button bAgregar;
    private String idUser;

    private ValueEventListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);

        //Permiso de llamada
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.CALL_PHONE
        }, 1 );

        //database
        db= FirebaseDatabase.getInstance();

        listaContactos=findViewById(R.id.listaContactos);
        nombreC=findViewById(R.id.nombreC);
        telefono=findViewById(R.id.telefono);
        bAgregar=findViewById(R.id.bAgregar);


        //Recibimos el id del usuario que acaba de ingresar
        idUser= getIntent().getExtras().getString("idUser");

        //Adapter
        adapter= new ContactoAdapter();
        listaContactos.setAdapter(adapter);

        bAgregar.setOnClickListener(this);

        //Metodo para cargar los datos del database
        loadDatabase();
    }


    public void loadDatabase(){
        DatabaseReference ref= db.getReference().child("contactos");

        ref.orderByChild("idUser").equalTo(idUser).addValueEventListener(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {

                        adapter.clear();

                        for(DataSnapshot child: data.getChildren()){
                            Contacto contacto=child.getValue(Contacto.class);
                            adapter.addContacto(contacto);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bAgregar:
                String idC= db.getReference().child("contactos").push().getKey();
                DatabaseReference reference=db.getReference().child("contactos").child(idC);

                Contacto contacto= new Contacto(
                        nombreC.getText().toString(),
                        telefono.getText().toString(),
                        idUser,
                        idC
                );

                reference.setValue(contacto);

                break;
        }
    }
}