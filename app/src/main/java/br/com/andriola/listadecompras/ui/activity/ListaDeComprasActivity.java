package br.com.andriola.listadecompras.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.andriola.listadecompras.R;
import br.com.andriola.listadecompras.model.Produto;
import br.com.andriola.listadecompras.repositorio.ProdutoRepository;
import br.com.andriola.listadecompras.ui.adapter.ListaAdapter;


@SuppressWarnings("unchecked")
public class ListaDeComprasActivity extends AppCompatActivity {
    private ListaAdapter adapter;
    private ProdutoRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_compras);

        final ListView listViewCompras =  findViewById(R.id.activity_lista_compras_listview);
        registerForContextMenu(listViewCompras);

        repository = new ProdutoRepository(this);

        configuraBuscaProdutos(listViewCompras);
        configuraBotaoComprar();
        configuraFloatingActionButtom();
    }

    private void configuraBuscaProdutos(final ListView listViewCompras) {
        repository.buscaProdutos(new ProdutoRepository.DadosCarregados() {
            @Override
            public void quandoSucesso(Object resultado) {
                adapter = new ListaAdapter(ListaDeComprasActivity.this,
                        android.R.layout.simple_list_item_1, (List<Produto>) resultado);
                listViewCompras.setAdapter(adapter);
            }

        });
    }

    private void configuraBotaoComprar() {
        Button Comecar = findViewById(R.id.button);
        Comecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListaDeComprasActivity.this,
                        "Começando Compras!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ListaDeComprasActivity.this, EmComprasActivity.class));
            }
        });
    }

    private void configuraFloatingActionButtom() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView novoProduto = findViewById(R.id.activity_lista_compras_novo_produto);
                String nome = novoProduto.getText().toString();
                repository.salvaProduto(new Produto(nome), new ProdutoRepository.DadosCarregados<Produto>() {
                    @Override
                    public void quandoSucesso(Produto resultado) {
                        adapter.add(resultado);
                    }
                });
                novoProduto.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        repository.buscaProdutos(new ProdutoRepository.DadosCarregados() {
            @Override
            public void quandoSucesso(Object resultado) {
                adapter.atualiza((List<Produto>) resultado);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_compras_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_compras_menu_context_remove) {
            Toast.makeText(ListaDeComprasActivity.this,
                    "Você está removendo um Produto",
                    Toast.LENGTH_SHORT).show();
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final Produto produtoARemover = (Produto) adapter.getItem(menuInfo.position);

            repository.removeProduto(produtoARemover, new ProdutoRepository.DadosCarregados() {
                @Override
                public void quandoSucesso(Object resultado) {
                    adapter.remove(produtoARemover);
                }
            });
        }
        return super.onContextItemSelected(item);
    }
}