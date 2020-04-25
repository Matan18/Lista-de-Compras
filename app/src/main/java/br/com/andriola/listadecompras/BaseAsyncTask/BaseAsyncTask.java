package br.com.andriola.listadecompras.BaseAsyncTask;

import android.os.AsyncTask;

public class BaseAsyncTask<T> extends AsyncTask<Void, Void, T> {

    private final ExecutaListener<T> executaListener;

    public BaseAsyncTask(ExecutaListener<T> executaListener, FinalizaListener<T> finalizaListener) {
        this.executaListener = executaListener;
        this.finalizaListener = finalizaListener;
    }

    private final FinalizaListener<T> finalizaListener;


    @Override
    protected T doInBackground(Void... voids) {
        return executaListener.quandoExecuta();
    }

    @Override
    protected void onPostExecute(T resultado) {
        super.onPostExecute(resultado);
        finalizaListener.quandoFinaliza(resultado);
    }

    public interface ExecutaListener<T>{
        T quandoExecuta();
    }
    public interface FinalizaListener<T>{
        void quandoFinaliza(T resultado);
    }
}
