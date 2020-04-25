package br.com.andriola.listadecompras.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;

import br.com.andriola.listadecompras.R;
import br.com.andriola.listadecompras.dialog.FormularioDialog;
import br.com.andriola.listadecompras.model.Produto;
import br.com.andriola.listadecompras.repositorio.ProdutoRepository;
import br.com.andriola.listadecompras.ui.adapter.ListaProdutosAdapter;

import static java.lang.String.format;

public class EmComprasActivity extends AppCompatActivity {
    double PrecoTotal = 0;
    TextView precoTotalText;
    ListaProdutosAdapter adapter;
    ProdutoRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_em_compras);

        repository = new ProdutoRepository(this);

        configuraAdapter();
        configuraBuscaProduto();
        configuraFloatingActionButtom();
        atualizaListView();
    }

    private void configuraAdapter() {
        adapter = new ListaProdutosAdapter(this, new ListaProdutosAdapter.atualizaPrecoTotalListener() {
            @Override
            public void precoAtualizao(Produto produto, String precoNovo) {
                if(precoNovo.isEmpty())
                    precoNovo="0";
                produto.setPreco(Double.parseDouble(precoNovo));
                repository.editaProduto(produto, new ProdutoRepository.DadosCarregados() {
                    @Override
                    public void quandoSucesso(Object resultado) {
                        atualizaPreco();
                    }
                });
            }

            @Override
            public void checkBoxAtualizado(Produto produto) {
                repository.editaProduto(produto, new ProdutoRepository.DadosCarregados() {
                    @Override
                    public void quandoSucesso(Object resultado) {
                        atualizaPreco();
                    }

                });
            }
        }, new ListaProdutosAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int posicao, Produto produto) {
                Log.e(String.valueOf(posicao), produto.toString());
                Toast.makeText(EmComprasActivity.this,
                        "Você está removendo um Produto",
                        Toast.LENGTH_SHORT).show();
                repository.removeProduto(produto, new ProdutoRepository.DadosCarregados() {
                    @Override
                    public void quandoSucesso(Object resultado) {
                        atualizaPreco();
                    }
                });

            }
        });
    }

    private void configuraBuscaProduto() {
        repository.buscaProdutos(new ProdutoRepository.DadosCarregados() {
            @Override
            public void quandoSucesso(Object resultado) {
                adapter.atualiza((List<Produto>) resultado);
            }
        });
    }

    private void configuraFloatingActionButtom() {
        FloatingActionButton fab = findViewById(R.id.activity_lista_em_compras_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FormularioDialog(EmComprasActivity.this, new FormularioDialog.ConfirmacaoListener() {
                    @Override
                    public void concluído(Produto produto) {
                        repository.salvaProduto(produto, new ProdutoRepository.DadosCarregados() {
                            @Override
                            public void quandoSucesso(Object resultado) {
                                atualizaPreco();
                            }

                        });
                    }

                    @Override
                    public void cancelado() {

                    }
                }).mosta();

            }
        });
    }

    private void atualizaListView() {
        ListView listViewCompras = findViewById(R.id.activity_lista_em_compras_listview);
        listViewCompras.setAdapter(adapter);
        precoTotalText = findViewById(R.id.activity_lista_em_compras_preco_total);
        atualizaPreco();
    }

    public void atualizaPreco() {

        repository.buscaProdutos(new ProdutoRepository.DadosCarregados() {
            @Override
            public void quandoSucesso(Object resultado) {
                List<Produto> todos = (List<Produto>) resultado;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    todos.sort(new Comparator<Produto>() {
                        @Override
                        public int compare(Produto o1, Produto o2) {
                            if (o1.isMarcado() == o2.isMarcado()) {
                                return 0;
                            } else if (o1.isMarcado() == false) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    });
                }
                adapter.atualiza(todos);
                PrecoTotal = 0;
                for (Produto produto :
                        todos) {
                    if (produto.isMarcado()) {
                        PrecoTotal += produto.getPreco();
                        Log.e("Preço atualizado", "Produto atual: "+produto.toString()+". Preco total: " + PrecoTotal);
                    }
                }
                precoTotalText.setText(format("%.2f", PrecoTotal));
            }

        });

    }
}
