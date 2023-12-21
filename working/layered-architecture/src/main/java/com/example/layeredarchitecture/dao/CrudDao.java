package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao<T> {
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;

    boolean save(T dto) throws SQLException, ClassNotFoundException;

    boolean update(T dto) throws SQLException, ClassNotFoundException ;
    boolean delete(String id) throws SQLException, ClassNotFoundException;

    String getId() throws SQLException, ClassNotFoundException ;

    boolean isExist(String id) throws SQLException, ClassNotFoundException ;

    T search(String newValue) throws  SQLException,ClassNotFoundException;
}
