package br.com.andriola.listadecompras.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.andriola.listadecompras.model.Produto;

@Dao
public interface ProdutoDao {
    @Insert
    void salva(Produto produto);

    @Query("SELECT * FROM Produto")
    List<Produto> todos();

    @Delete
    void remove(Produto produto);

    @Update
    void Edita(Produto produto);
}
