package br.com.andriola.listadecompras.repositorio;

import android.content.Context;

import java.util.List;

import br.com.andriola.listadecompras.BaseAsyncTask.BaseAsyncTask;
import br.com.andriola.listadecompras.database.ComprasDatabase;
import br.com.andriola.listadecompras.database.dao.ProdutoDao;
import br.com.andriola.listadecompras.model.Produto;

public class ProdutoRepository {
    private final ProdutoDao Dao;

    public ProdutoRepository(Context context) {
        ComprasDatabase db = ComprasDatabase.getInstance(context);
        Dao = db.getRoomProdutoDAO();
    }

    public void buscaProdutos(final DadosCarregados listener) {
        new BaseAsyncTask<>(new BaseAsyncTask.ExecutaListener<List<Produto>>() {
            @Override
            public List<Produto> quandoExecuta() {
                return Dao.todos();
            }
        }, new BaseAsyncTask.FinalizaListener<List<Produto>>() {
            @Override
            public void quandoFinaliza(List<Produto> resultado) {
                listener.quandoSucesso(resultado);
            }
        }).execute();
    }

    public void salvaProduto(final Produto produto, final DadosCarregados listener){
        new BaseAsyncTask<>(new BaseAsyncTask.ExecutaListener<Produto>() {
            @Override
            public Produto quandoExecuta() {
                Dao.salva(produto);
                return produto;
            }
        }, new BaseAsyncTask.FinalizaListener<Produto>() {
            @Override
            public void quandoFinaliza(Produto resultado) {
                listener.quandoSucesso(resultado);
            }
        }).execute();
    }
    public void removeProduto(final Produto produto, final DadosCarregados listener){
        new BaseAsyncTask<>(new BaseAsyncTask.ExecutaListener<Void>() {
            @Override
            public Void quandoExecuta() {
                Dao.remove(produto);
                return null;
            }
        }, new BaseAsyncTask.FinalizaListener<Void>() {
            @Override
            public void quandoFinaliza(Void resultado) {
                listener.quandoSucesso(resultado);
            }
        }).execute();
    }

    public void editaProduto(final Produto produto, final DadosCarregados listener){
        new BaseAsyncTask<>(new BaseAsyncTask.ExecutaListener<Produto>() {
            @Override
            public Produto quandoExecuta() {
                Dao.Edita(produto);
                return produto;
            }
        }, new BaseAsyncTask.FinalizaListener<Produto>() {
            @Override
            public void quandoFinaliza(Produto resultado) {
                listener.quandoSucesso(resultado);
            }
        }).execute();
    }

    public interface DadosCarregados<T>{
        void quandoSucesso(T resultado);
    }
}
