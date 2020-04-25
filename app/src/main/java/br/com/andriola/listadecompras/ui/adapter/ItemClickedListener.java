package br.com.andriola.listadecompras.ui.adapter;

import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;

import br.com.andriola.listadecompras.model.Produto;

public interface ItemClickedListener {
    void onItemClick(ContextMenu menu, View view, AdapterView.AdapterContextMenuInfo menuInfo);
}
