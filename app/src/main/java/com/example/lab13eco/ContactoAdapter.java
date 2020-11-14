package com.example.lab13eco;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactoAdapter extends BaseAdapter {

    private ArrayList<Contacto> contactos;

    public ContactoAdapter(){
        contactos= new ArrayList<>();
    }

    public void addContacto(Contacto contacto){
        contactos.add(contacto);
        notifyDataSetChanged();
    }

    public void clear(){
        contactos.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View renglon, ViewGroup lista) {

        LayoutInflater inflater= LayoutInflater.from(lista.getContext());
        View renglonView= inflater.inflate(R.layout.row,null);

        Contacto contacto= contactos.get(pos);

        TextView nombreContact= renglonView.findViewById(R.id.nombreContact);
        TextView telefonoContact=renglonView.findViewById(R.id.telefonoContact);
        Button bLlamar= renglonView.findViewById(R.id.bLlamar);
        Button bBorrar= renglonView.findViewById(R.id.bBorrar);

        nombreContact.setText(contacto.getNombre());
        telefonoContact.setText(contacto.getTelefono());


        //eliminar
        bBorrar.setOnClickListener(
                (v)->{
                    String idC=contacto.getIdC();
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("contactos").child(idC);
                    ref.setValue(null);

                }
        );

        //llamada
        bLlamar.setOnClickListener(

                (v)->{
                    String tel="tel:"+contacto.getTelefono();
                    Intent i= new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(tel));
                    lista.getContext().startActivity(i);

                }
        );

        return renglonView;
    }
}
