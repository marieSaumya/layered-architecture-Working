package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ItemDao {
     ArrayList<ItemDTO> loadItem() throws SQLException, ClassNotFoundException ;

     boolean deleteItem(String code) throws SQLException, ClassNotFoundException ;

     boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;

     boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException ;
     boolean existItem(String code) throws SQLException, ClassNotFoundException ;
     String generateNewId() ;

     ItemDTO searchItem(String newItemCode) throws SQLException,ClassNotFoundException;

     boolean updateQuantity(List<OrderDetailDTO> orderDetails) throws SQLException,ClassNotFoundException;
}
