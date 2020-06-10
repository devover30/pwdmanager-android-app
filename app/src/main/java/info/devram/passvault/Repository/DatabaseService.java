package info.devram.passvault.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;


interface DatabaseService<T> {

    public void addData(T obj);

    public List<T> getAll();

    public T getOne();

    public int onUpdate(T obj);

    public void onDelete(T obj);

    public int getCount();
}
