package com.example.layeredarchitecture.dao.custom;

import com.example.layeredarchitecture.dao.CrudDao;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ItemDao extends CrudDao<ItemDTO> {


     boolean updateQuantity(List<OrderDetailDTO> orderDetails) throws SQLException,ClassNotFoundException;
}
