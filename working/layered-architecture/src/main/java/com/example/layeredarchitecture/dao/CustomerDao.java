package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDao {
     ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException ;

     void saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    void updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException ;
     boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

     String getId() throws SQLException, ClassNotFoundException ;

    boolean isCustomerExit(String id) throws SQLException, ClassNotFoundException ;
}
