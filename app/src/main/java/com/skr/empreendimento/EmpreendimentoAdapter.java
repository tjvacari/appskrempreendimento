package com.skr.empreendimento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skr.empreendimento.dialog.ImageDialog;
import com.skr.empreendimento.to.EmpreentimentoTO;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmpreendimentoAdapter extends RecyclerView.Adapter {

    private List<EmpreentimentoTO> empreendimentList;
    private EmpreendimentoActivity mActivity;

    public EmpreendimentoAdapter(List<EmpreentimentoTO> empreendimentList, EmpreendimentoActivity mActivity) {
        this.empreendimentList = empreendimentList;
        this.mActivity = mActivity;
    }

    public void update(List<EmpreentimentoTO> empreendimentList) {
        this.empreendimentList = empreendimentList;
        notifyDataSetChanged();
    }

    public void increment(List<EmpreentimentoTO> empreendimentoList) {
        this.empreendimentList.addAll(empreendimentoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.item_empreendimento, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;

        configureActions(holder);

        EmpreentimentoTO empreentimentoTO = empreendimentList.get(i);

        holder.nome.setText(empreentimentoTO.getNome());
        holder.descricao.setText(empreentimentoTO.getDescricao());

        Picasso.get().load(empreentimentoTO.getUrlImagem()).placeholder(R.mipmap.load_shimmey)
                .into(holder.imagem);
    }

    private void configureActions(ViewHolder holder) {
        holder.imagem.setOnClickListener((View v) -> {
            new ImageDialog(mActivity, (ImageView) v).show();
        });
    }

    @Override
    public int getItemCount() {
        return empreendimentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nome;
        final TextView descricao;
        final ImageView imagem;

        public ViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.card_nome_id);
            descricao = (TextView) view.findViewById(R.id.card_descricao_id);
            imagem = (ImageView) view.findViewById(R.id.imageView_id);
        }
    }
}
