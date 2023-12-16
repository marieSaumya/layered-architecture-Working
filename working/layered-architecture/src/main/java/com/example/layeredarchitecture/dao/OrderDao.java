package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean checkOrderExist(String orderId) throws SQLException,ClassNotFoundException;

    boolean saveOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws SQLException,ClassNotFoundException;

    String getOrderId() throws SQLException,ClassNotFoundException;
}
