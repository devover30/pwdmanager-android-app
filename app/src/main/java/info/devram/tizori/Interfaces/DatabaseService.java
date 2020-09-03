package info.devram.tizori.Interfaces;

import java.util.List;


public interface DatabaseService<T> {

    public Boolean addData(T obj);

    public List<T> getAll();

    public T getOne(int id);

    public Boolean onUpdate(T obj);

    public void onDelete(T obj);

    public int getCount();
}
