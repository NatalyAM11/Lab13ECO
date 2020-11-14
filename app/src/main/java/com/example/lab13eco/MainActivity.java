package com.example.lab13eco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.ConstantCallSite;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase db;

    private EditText name;
    private Button bIngresar;
    private boolean yaEsta;
    private  Usuario u;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db= FirebaseDatabase.getInstance();

        name= findViewById(R.id.name);
        bIngresar=findViewById(R.id.bIngresar);
        yaEsta=false;

        bIngresar.setOnClickListener(this);


    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bIngresar:
                String id= db.getReference().child("users").push().getKey();
                DatabaseReference reference=db.getReference().child("users").child(id);

                Intent i = new Intent(this, ListaContactos.class);


               db.getReference().child("users").orderByChild("nombre").equalTo(name.getText().toString()).addListenerForSingleValueEvent(
                        new ValueEventListener() {

                            @Override
                            public void onDataChange( DataSnapshot data) {

                                    for (DataSnapshot child : data.getChildren()) {
                                        Usuario usuarioo = child.getValue(Usuario.class);
                                        u = usuarioo;

                                }
                            //Si el usuario no esta en database, lo agrega y obtenemos ese id nuevo
                                if(u==null) {
                                    Usuario usuario= new Usuario(
                                            name.getText().toString(),
                                            id
                                    );

                                    reference.setValue(usuario);

                                    idUser=id;
                                }


                            //si el usuario ya esta en database, obtengo su id para buscar sus contactos
                                if(u!=null){
                                    idUser=u.getId();
                                }

                                Log.e(">", idUser);

                             //Mandamos el id del usuario a la otra actividad
                                i.putExtra("idUser", idUser);
                                startActivity(i);

                            }

                            @Override
                            public void onCancelled( DatabaseError error) {

                            }
                        }
                );

                break;
        }
    }
}