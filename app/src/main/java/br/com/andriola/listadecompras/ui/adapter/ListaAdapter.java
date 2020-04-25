package br.com.andriola.listadecompras.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

@SuppressWarnings("unchecked")
public class ListaAdapter<Produto> extends ArrayAdapter {

    public ListaAdapter(@NonNull Context context, int resource, @NonNull List<Produto> objects) {
        super(context, resource, objects);
    }

    public void atualiza(List<Produto> produtos){
        super.clear();
        super.addAll(produtos);
        super.notifyDataSetChanged();
    }
}
