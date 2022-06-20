package com.example.controleestoque.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.controleestoque.models.Produto;
import com.example.controleestoque.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProdutosList extends BaseAdapter {

    private Context context;
    private ArrayList <Produto> listaProdutos;

    public AdapterProdutosList(Context context, ArrayList<Produto> listaProdutos) {

        this.context = context;
        this.listaProdutos = listaProdutos;

    }

    @Override
    public int getCount() {

        return this.listaProdutos.size();

    }

    @Override
    public Object getItem(int position) {

        return this.listaProdutos.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(this.context, R.layout.layout_produto, null);

        v.setBackgroundColor(Color.parseColor("#d3d3d3"));

        ImageView ivProduto = (ImageView) v.findViewById(R.id.ivProduto);

        TextView tvNomeProduto = (TextView) v.findViewById(R.id.txt_nome_produto);
        TextView tvPrecoProduto = (TextView) v.findViewById(R.id.txt_preco_produto);
        TextView tvEstoqueAtual = (TextView) v.findViewById(R.id.tvEstoqueAtual);
        TextView tvEstoqueIdeal = (TextView) v.findViewById(R.id.tvEstoqueIdeal);

        if(listaProdutos.get(position).getImagePath() != null) {
            Picasso.with(v.getContext()).load(Uri.parse(listaProdutos.get(position).getImagePath())).into(ivProduto);
        }

        String preco = "R$" + listaProdutos.get(position).getPreco();

        tvNomeProduto.setText(listaProdutos.get(position).getNome());
        tvPrecoProduto.setText(preco);
        tvEstoqueAtual.setText(listaProdutos.get(position).getEstoqueAtual()+"");
        tvEstoqueIdeal.setText(listaProdutos.get(position).getEstoqueIdeal()+"");

        if(listaProdutos.get(position).getEstoqueAtual() >= listaProdutos.get(position).estoqueIdeal) {
            tvEstoqueAtual.setTextColor(Color.parseColor("#008000"));
        } else {
            tvEstoqueAtual.setTextColor(Color.parseColor("#FF0000"));
        }

        return v;

    }
}