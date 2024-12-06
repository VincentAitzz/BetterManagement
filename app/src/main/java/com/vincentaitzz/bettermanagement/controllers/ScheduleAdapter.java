package com.vincentaitzz.bettermanagement.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Firebase;
import com.vincentaitzz.bettermanagement.models.Schedule;
import com.vincentaitzz.bettermanagement.R;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List<Schedule> scheduleList;
    private FirebaseManager auth;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_card, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

        auth = new FirebaseManager();

        Schedule schedule = scheduleList.get(position);

        holder.titleTextView.setText(schedule.getNAME());
        holder.descriptionTextView.setText(schedule.getDESCRIPTION());
        holder.dateTextView.setText(schedule.getDATE());
        holder.timeTextView.setText(schedule.getSTART() + " - " + schedule.getFINISH());

        holder.itemView.setOnClickListener(v -> {
            showOptionsDialog(schedule);
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        TextView timeTextView;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.schedule_title);
            descriptionTextView = itemView.findViewById(R.id.schedule_description);
            dateTextView = itemView.findViewById(R.id.schedule_date);
            timeTextView = itemView.findViewById(R.id.schedule_time);
        }
    }

    private void showDeleteConfirmationDialog(Schedule schedule, String userID) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar Eliminación")
                .setMessage("¿Seguro que desea eliminar esta actividad?")
                .setPositiveButton("OK", (dialog, which) -> {
                    schedule.deleteSchedule(userID);
                    scheduleList.remove(schedule);
                    notifyDataSetChanged();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showOptionsDialog(Schedule schedule) {
        String[] options = {"Editar", "Eliminar"};

        new AlertDialog.Builder(context)
                .setTitle("Seleccione una opción")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openEditActivity(schedule);
                    } else if (which == 1) {
                        showDeleteConfirmationDialog(schedule,auth.getCurrentUser().getUid());
                    }
                })
                .show();
    }
    private void openEditActivity(Schedule schedule) {
        Intent intent = new Intent(context, EditSchedule.class);
        intent.putExtra("SCHEDULE_ID", schedule.getID());
        intent.putExtra("SCHEDULE_NAME", schedule.getNAME());
        intent.putExtra("SCHEDULE_DATE", schedule.getDATE());
        intent.putExtra("SCHEDULE_START", schedule.getSTART());
        intent.putExtra("SCHEDULE_FINISH", schedule.getFINISH());
        intent.putExtra("SCHEDULE_DESCRIPTION", schedule.getDESCRIPTION());
        context.startActivity(intent);
    }
}