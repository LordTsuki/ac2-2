package com.example.ac2_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunoList;

    public AlunoAdapter(List<Aluno> alunoList) {
        this.alunoList = alunoList;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_aluno, parent, false); // Assuming 'item_aluno' is your layout file
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunoList.get(position);
        holder.textViewRa.setText(aluno.getRa());
        holder.textViewName.setText(aluno.getName());
        holder.textViewZip.setText(aluno.getZip());
        holder.textViewStreet.setText(aluno.getStreet());
        holder.textViewComplement.setText(aluno.getComplement());
        holder.textViewNeighborhood.setText(aluno.getNeighborhood());
        holder.textViewCity.setText(aluno.getCity());
        holder.textViewUf.setText(aluno.getUf());
    }

    @Override
    public int getItemCount() {
        return alunoList.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRa;
        public TextView textViewName;
        public TextView textViewZip;
        public TextView textViewStreet;
        public TextView textViewComplement;
        public TextView textViewNeighborhood;
        public TextView textViewCity;
        public TextView textViewUf;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRa = itemView.findViewById(R.id.textViewRa);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewZip = itemView.findViewById(R.id.textViewZip);
            textViewStreet = itemView.findViewById(R.id.textViewStreet);
            textViewComplement = itemView.findViewById(R.id.textViewComplement);
            textViewNeighborhood = itemView.findViewById(R.id.textViewNeighborhood);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewUf = itemView.findViewById(R.id.textViewUf);
        }
    }
}