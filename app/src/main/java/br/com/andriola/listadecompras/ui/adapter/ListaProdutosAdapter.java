package br.com.andriola.listadecompras.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.andriola.listadecompras.R;
import br.com.andriola.listadecompras.model.Produto;

import static java.lang.String.format;

public class ListaProdutosAdapter extends BaseAdapter {
    private List<Produto> produtos = new ArrayList<>();
    private final Context context;
    private atualizaPrecoTotalListener listener;
    private OnItemClickListener ClickListener;

    public ListaProdutosAdapter(Context context, atualizaPrecoTotalListener atualizaPrecoTotalListener, OnItemClickListener clickListener) {
        this.context = context;
        this.listener = atualizaPrecoTotalListener;
        this.ClickListener = clickListener;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Produto getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.activity_lista_em_compras_item, parent, false);
        final Produto produtoDevolvido = produtos.get(position);

        configuraNomeProduto(viewCriada, produtoDevolvido);
        configuraItemClickListener(position, viewCriada, produtoDevolvido);
        configuraCampoPreco(viewCriada, produtoDevolvido);
        configuraItemCheckbox(viewCriada, produtoDevolvido);

        return viewCriada;
    }

    private void configuraItemCheckbox(View viewCriada, final Produto produtoDevolvido) {
        final CheckBox marcado = viewCriada.findViewById(R.id.checkbox_item);
        if (marcado != null) {
            marcado.setChecked(produtoDevolvido.isMarcado());
            marcado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    marcado.setChecked(isChecked);
                    Log.e("Checked", produtoDevolvido.toString() + ", " + Boolean.toString(isChecked));
                    produtoDevolvido.setMarcado(isChecked);
                    listener.checkBoxAtualizado(produtoDevolvido);
                }
            });
        }
    }


    private void configuraCampoPreco(View viewCriada, final Produto produtoDevolvido) {
        final EditText preco = viewCriada.findViewById(R.id.preco_Produto);
        preco.setText(format("%.2f", produtoDevolvido.getPreco()));
        preco.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                String s = v.getText().toString();
                listener.precoAtualizao(produtoDevolvido, s);
                return false;
            }
        });
    }

    private void configuraItemClickListener(final int position, View viewCriada, final Produto produtoDevolvido) {
        viewCriada.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                new MenuInflater(context).inflate(R.menu.activity_lista_compras_menu, menu);
                menu.findItem(R.id.activity_lista_compras_menu_context_remove).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ClickListener.onItemClickListener(position, produtoDevolvido);
                        return true;
                    }
                });
            }
        });
    }

    private void configuraNomeProduto(View viewCriada, Produto produtoDevolvido) {
        TextView nome = viewCriada.findViewById(R.id.Nome_Produto);
        nome.setText(produtoDevolvido.toString());
    }

    public void atualiza(List<Produto> Produtos) {
        this.produtos.clear();
        this.produtos.addAll(Produtos);
        notifyDataSetChanged();
    }

    public void remove(int posicao) {
        this.produtos.remove(posicao);
        atualiza(produtos);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int posicao, Produto produto);
    }

    public interface atualizaPrecoTotalListener {
        void precoAtualizao(Produto produto, String precoNovo);

        void checkBoxAtualizado(Produto produto);
    }

}
