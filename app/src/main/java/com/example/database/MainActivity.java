package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button btnCrear;
    private Button btnVeure;
    private Button btnEliminar;
    private EditText editTitol;
    private EditText editComent;
    private EditText txtTitol;
    private EditText txtComent;

    private Spinner spinComentaris;
    private ArrayAdapter spinnerAdapter;

    private ArrayList<Comentario>lista;
    private Comentario c;

    private DataBaseAssistant db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitol=(EditText) findViewById(R.id.editTitol);
        editComent =(EditText)findViewById(R.id.editComent);
        txtTitol=(EditText) findViewById(R.id.txtTitol);
        txtComent =(EditText)findViewById(R.id.txtComent);

        txtTitol.setEnabled(false);
        txtComent.setEnabled(false);

        btnCrear=(Button)findViewById(R.id.btnCrear);
        btnVeure=(Button)findViewById(R.id.btnVeure);
        btnEliminar=(Button)findViewById(R.id.btnEliminar);

        btnCrear.setOnClickListener(this);
        btnVeure.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        db=new DataBaseAssistant(this);

        spinComentaris=(Spinner) findViewById(R.id.spinComentaris);
        lista=db.getComments();

        spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lista);
        spinComentaris.setAdapter(spinnerAdapter);
        spinComentaris.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnCrear:
                db.insertar(editTitol.getText().toString(), editComent.getText().toString());
                lista=db.getComments();
                spinnerAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lista);
                spinComentaris.setAdapter(spinnerAdapter);
                editTitol.setText("");
                editComent.setText("");

                break;
            case R.id.btnVeure:
                if(c!=null) {
                    txtTitol.setText(c.getNombre());
                    txtComent.setText(c.getComentario());
                }
                break;
            case R.id.btnEliminar:
                if(c!=null) {
                    db.borrar(c.getId());
                    lista = db.getComments();
                    spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lista);
                    spinComentaris.setAdapter(spinnerAdapter);
                    txtTitol.setText("");
                    txtComent.setText("");
                    c=null;
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        if (adapterView.getId() == R.id.spinComentaris) {
            if (lista.size() > 0) {
                c = lista.get(i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}