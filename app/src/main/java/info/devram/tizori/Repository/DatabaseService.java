package info.devram.tizori.Repository;

import java.util.List;


interface DatabaseService<T> {

    public Boolean addData(T obj);

    public List<T> getAll();

    public T getOne(int id);

    public Boolean onUpdate(T obj);

    public void onDelete(T obj);

    public int getCount();
}
