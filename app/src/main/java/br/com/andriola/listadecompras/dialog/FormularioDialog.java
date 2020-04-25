package br.com.andriola.listadecompras.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import br.com.andriola.listadecompras.R;
import br.com.andriola.listadecompras.model.Produto;


public class FormularioDialog {
    final String titulo = "Novo Produto";
    private Produto produto;
    private ConfirmacaoListener listener;
    private Context context;

    public FormularioDialog(Context context, ConfirmacaoListener listener) {
        this.listener = listener;
        this.context = context;
    }

    public void mosta() {
        @SuppressLint("InflatePrams") final View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.formulario_novo_produto, null);
        new AlertDialog.Builder(context).
                setView(viewCriada).
                setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText campoNome = viewCriada.findViewById(R.id.formulario_nome_produto);
                        EditText campoPreco = viewCriada.findViewById(R.id.formulario_preco_produto);
                        CheckBox isMarcado = viewCriada.findViewById(R.id.formulario_checkbox_no_carrinho);
                        criaProduto(campoNome, campoPreco, isMarcado);
                    }
                }).setNegativeButton("Cancelar", null).show();
    }

    private void criaProduto(EditText campoNome, EditText campoPreco, CheckBox isMarcado) {
        String nome =campoNome.getText().toString();
        double preco=0;
        if (!campoPreco.getText().toString().isEmpty()){
            preco = Double.parseDouble(campoPreco.getText().toString());
        }
        Boolean marcado = isMarcado.isChecked();
        Produto produto = new Produto(nome);
        produto.setMarcado(marcado);
        produto.setPreco(preco);
        listener.concluído(produto);
    }


    public interface ConfirmacaoListener {
        void concluído(Produto produto);

        void cancelado();
    }
}
