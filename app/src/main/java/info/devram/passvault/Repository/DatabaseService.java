package info.devram.passvault.Repository;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import info.devram.passvault.controller.DatabaseHandler;

interface DatabaseService<T> {

    public void addData(T obj);

    public MutableLiveData<List<T>> getAll();

    public MutableLiveData<T> getOne();

    public int onUpdate(T obj);

    public void onDelete(T obj);

    public int getCount();
}
