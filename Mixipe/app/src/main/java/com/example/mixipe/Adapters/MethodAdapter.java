package com.example.mixipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixipe.Models.Method;
import com.example.mixipe.R;

import java.util.List;

public class MethodAdapter extends RecyclerView.Adapter<MethodViewHolder> {

    Context context;
    List<Method> list;

    public MethodAdapter(Context context, List<Method> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MethodViewHolder(LayoutInflater.from(context).inflate(R.layout.list_method, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MethodViewHolder holder, int position) {

        // update method_steps recyclerview
        holder.Method_Steps.setHasFixedSize(true);
        holder.Method_Steps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        MethodStepsAdapter methodStepsAdapter =  new MethodStepsAdapter(context, list.get(position).steps);
        holder.Method_Steps.setAdapter(methodStepsAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MethodViewHolder extends RecyclerView.ViewHolder {

    RecyclerView Method_Steps;

    public MethodViewHolder(@NonNull View itemView) {
        super(itemView);
        Method_Steps = itemView.findViewById(R.id.Method_Steps);

    }
}
