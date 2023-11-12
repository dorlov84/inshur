package com.inshur.shared.datalayer;

import java.util.List;

public interface DataRepository<T> {

    void save(T item);
    List<T> findAll();

}
