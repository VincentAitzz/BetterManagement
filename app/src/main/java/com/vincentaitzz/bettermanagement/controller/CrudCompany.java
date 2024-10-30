package com.vincentaitzz.bettermanagement.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vincentaitzz.bettermanagement.R;

import java.util.ArrayList;

public class CrudCompany extends AppCompatActivity {
    private Database database;

    private EditText ingresarCompania, editarCompania;
    private Button btnAgregar, btnEditar,btnEliminar;
    private ListView companyList;
    private ArrayList<String> company;
    private ArrayAdapter<String> adapter;
    private String selectCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.crud_company);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = new Database(this);
        ingresarCompania = findViewById(R.id.editTextTask);
        editarCompania = findViewById(R.id.editTextEditTask);
        btnAgregar = findViewById(R.id.buttonAdd);
        btnEditar = findViewById(R.id.buttonEdit);
        btnEliminar = findViewById(R.id.buttonDelete);
        companyList = findViewById(R.id.listViewTasks);

        company = database.getAllCompany();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,company);

        companyList.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String company = ingresarCompania.getText().toString();

                if(!company.isEmpty()){
                    database.insertCompany(company);
                    actualizarListadoCompanias();
                    ingresarCompania.setText("");
                }
            }
        });

        companyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectCompany = company.get(position);
                editarCompania.setText(selectCompany);
                editarCompania.setVisibility(View.VISIBLE);
                btnEditar.setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.VISIBLE);
                btnAgregar.setVisibility(View.INVISIBLE);
                ingresarCompania.setVisibility(View.INVISIBLE);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCompany = editarCompania.getText().toString();
                if(!company.isEmpty()){
                    database.updateCompany(selectCompany,newCompany);
                    actualizarListadoCompanias();
                    cleanData();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.deleteCompany(selectCompany);
                actualizarListadoCompanias();
                cleanData();

            }
        });
    }

    public void actualizarListadoCompanias(){
        company.clear();
        company.addAll(database.getAllCompany());
        adapter.notifyDataSetChanged();
    }
    public void cleanData(){
        editarCompania.setText("");
        btnAgregar.setVisibility(View.VISIBLE);
        ingresarCompania.setVisibility(View.VISIBLE);
        btnEditar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);
        editarCompania.setVisibility(View.GONE);
    }
}