package org.example.repository.book;

import java.util.*;

//sa putem folosi atat pentru carti, cat si pt user etc
public class Cache<T>
{
    public List<T> storage;
    public List<T> load()
    {
        return storage;
    }

    public void save (List<T> storage){
        //trimit param o lista, nu un element.
        this.storage = storage;
    }
    public boolean hasResult(){
        return storage != null;
    }
    public void invalidateCache(){
        storage = null;
    }

}