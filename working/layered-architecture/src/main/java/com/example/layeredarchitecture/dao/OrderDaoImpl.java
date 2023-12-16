package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

 private  OrderDetailDao orderDetailDao = new OrderDetailDaoImpl();
 private  ItemDao itemDao = new ItemDAOImpl();
    @Override
    public boolean checkOrderExist(String orderId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("SELECT oid FROM `Orders` WHERE oid=?");
        stm.setString(1, orderId);
        /*if order id already exist*/
        if (stm.executeQuery().next()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        connection = DBConnection.getDbConnection().getConnection();
        connection.setAutoCommit(false);
        boolean orderSaved = insertOrder(orderDTO);
        if (orderSaved) {
            boolean orderDetailSaved = orderDetailDao.insertOrderDetail(orderDetails,orderDTO.getOrderId());
         if (orderDetailSaved){
             boolean updateItem = itemDao.updateQuantity(orderDetails);
             if (updateItem){
                 connection.commit();
                 connection.setAutoCommit(true);
                 return true;
             }
             else {
                 connection.rollback();
                 connection.setAutoCommit(true);
                 return false;
             }
         }
        }else {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        return true;
    }

    @Override
    public String getOrderId() throws SQLException, ClassNotFoundException {

            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

            return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }


    private boolean insertOrder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)");
        stm.setString(1, orderDTO.getOrderId());
        System.out.println(orderDTO.getOrderDate());
        stm.setDate(2, Date.valueOf(orderDTO.getOrderDate()));
        stm.setString(3, orderDTO.getCustomerId());
        return stm.executeUpdate() > 0;
    }
}

