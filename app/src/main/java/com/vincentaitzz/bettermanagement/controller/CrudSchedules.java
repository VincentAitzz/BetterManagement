package com.vincentaitzz.bettermanagement.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.model.Schedule;
import com.vincentaitzz.bettermanagement.model.User;

import java.util.ArrayList;

public class CrudSchedules extends AppCompatActivity {

    private Database db;
    private EditText horaInicioInput, horaTerminoInput, personaInput, fechaInput;
    private Spinner userSpinner;
    private ListView horarioListView;
    private Button btnRegister, btnUpdate, btnDelete;

    private ArrayList<Schedule> schedules;
    private ArrayAdapter<String> listAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crud_schedules);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new Database(this);
        horaInicioInput = findViewById(R.id.hora_inicio_input);
        horaTerminoInput = findViewById(R.id.hora_termino_input);
        personaInput = findViewById(R.id.persona_input);
        fechaInput = findViewById(R.id.fecha_input);
        userSpinner = findViewById(R.id.user_spinner);
        horarioListView = findViewById(R.id.horario_list_view);
        btnRegister = findViewById(R.id.register_button);

        schedules = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        horarioListView.setAdapter(listAdapter);

        loadUsers();

        btnRegister.setOnClickListener(v -> registerHorario());

        horarioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                loadSelectedHorario(position);
            }
        });

        btnUpdate.setOnClickListener(v -> updateHorario());
        btnDelete.setOnClickListener(v -> deleteHorario());

        loadHorarios();
    }

    private void loadUsers() {
        ArrayList<User> users = db.getAllUsers();
        ArrayList<String> userNames = new ArrayList<>();

        for (User user : users) {
            userNames.add(user.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
    }

    private void loadHorarios() {
        int usuarioId = userSpinner.getSelectedItemPosition() + 1; // Ajustar según el ID real
        schedules.clear();

        schedules.addAll(db.getHorariosPorUsuario(usuarioId));

        listAdapter.clear();

        for (Schedule s : schedules) {
            listAdapter.add("ID:" + s.getId() + " - " + s.getHoraInicio() + " a " + s.getHoraTermino());
        }

        listAdapter.notifyDataSetChanged();
    }

    private void loadSelectedHorario(int position) {
        if (position >= 0 && position < schedules.size()) {
            Schedule selectedHorario = schedules.get(position);
            horaInicioInput.setText(selectedHorario.getHoraInicio());
            horaTerminoInput.setText(selectedHorario.getHoraTermino());
            personaInput.setText(selectedHorario.getPersona());
            fechaInput.setText(selectedHorario.getFecha());

            // Hacer visibles los botones de actualizar y eliminar
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            // Guardar ID del horario seleccionado para actualizar o eliminar después
            btnUpdate.setTag(selectedHorario.getId());
            btnDelete.setTag(selectedHorario.getId());
        } else {
            Toast.makeText(this, "Selecciona un horario válido", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerHorario() {
        String horaInicio = horaInicioInput.getText().toString();
        String horaTermino = horaTerminoInput.getText().toString();
        String persona = personaInput.getText().toString();
        String fecha = fechaInput.getText().toString();

        int usuarioId = userSpinner.getSelectedItemPosition() + 1; // Ajustar según el ID real

        Schedule horario = new Schedule(0, horaInicio, horaTermino, persona, fecha, usuarioId);

        if (db.insertHorario(horario)) {
            Toast.makeText(this, "Horario registrado exitosamente", Toast.LENGTH_SHORT).show();
            clearFields();
            loadHorarios(); // Recargar horarios después de registrar uno nuevo
        } else {
            Toast.makeText(this, "Error al registrar horario", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateHorario() {
        if (btnUpdate.getTag() != null) {
            int id = (int) btnUpdate.getTag(); // Obtener ID del horario seleccionado

            String horaInicio = horaInicioInput.getText().toString();
            String horaTermino = horaTerminoInput.getText().toString();
            String persona = personaInput.getText().toString();
            String fecha = fechaInput.getText().toString();

            Schedule horarioActualizado = new Schedule(id, horaInicio, horaTermino, persona, fecha, id);

            db.updateHorario(horarioActualizado); // Actualizar en la base de datos

            Toast.makeText(this, "Horario actualizado exitosamente", Toast.LENGTH_SHORT).show();
            clearFields();
            loadHorarios(); // Recargar horarios después de actualizar uno existente
        } else {
            Toast.makeText(this, "Selecciona un horario para actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteHorario() {
        if (btnDelete.getTag() != null) {
            int id = (int) btnDelete.getTag(); // Obtener ID del horario seleccionado

            db.deleteHorario(id); // Eliminar de la base de datos

            Toast.makeText(this, "Horario eliminado exitosamente", Toast.LENGTH_SHORT).show();
            clearFields();
            loadHorarios(); // Recargar horarios después de eliminar uno existente
        } else {
            Toast.makeText(this, "Selecciona un horario para eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        horaInicioInput.setText("");
        horaTerminoInput.setText("");
        personaInput.setText("");
        fechaInput.setText("");

        btnUpdate.setVisibility(View.GONE); // Ocultar botón de actualización
        btnDelete.setVisibility(View.GONE); // Ocultar botón de eliminación
    }
}