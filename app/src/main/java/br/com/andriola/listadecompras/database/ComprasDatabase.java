package br.com.andriola.listadecompras.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.andriola.listadecompras.database.dao.ProdutoDao;
import br.com.andriola.listadecompras.model.Produto;


@Database(entities = {Produto.class}, version = 1, exportSchema = false)
public abstract class ComprasDatabase extends RoomDatabase {
    public abstract ProdutoDao getRoomProdutoDAO();

    private static final String NOME_BANCO_DE_DADOS = "compras.db";

    public static ComprasDatabase getInstance(Context context){
        return Room.databaseBuilder(context, ComprasDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();
    }
}
