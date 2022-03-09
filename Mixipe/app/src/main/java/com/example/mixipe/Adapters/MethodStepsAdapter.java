package com.example.mixipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixipe.Models.Step;
import com.example.mixipe.R;

import java.util.List;

public class MethodStepsAdapter extends RecyclerView.Adapter<MethodStepsViewHolder> {

    Context context;
    List<Step> list;

    public MethodStepsAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MethodStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MethodStepsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_method_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MethodStepsViewHolder holder, int position) {

        // update both text views
        holder.Method_StepNo.setText(String.valueOf(list.get(position).number));
        holder.Method_StepText.setText(list.get(position).step);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MethodStepsViewHolder extends RecyclerView.ViewHolder {

    TextView Method_StepNo, Method_StepText;
    public MethodStepsViewHolder(@NonNull View itemView) {
        super(itemView);

        Method_StepNo = itemView.findViewById(R.id.Method_StepNo);
        Method_StepText = itemView.findViewById(R.id.Method_StepText);
    }
}
