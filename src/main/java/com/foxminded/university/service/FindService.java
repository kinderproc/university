package com.foxminded.university.service;

import java.util.List;

public interface FindService<T> {
    public List<T> findAll();

    public T findById(int id);
}
