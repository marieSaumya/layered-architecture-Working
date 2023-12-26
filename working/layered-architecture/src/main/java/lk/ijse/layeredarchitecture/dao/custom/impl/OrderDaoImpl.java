package lk.ijse.layeredarchitecture.dao.custom.impl;

import lk.ijse.layeredarchitecture.dao.SQLUtil;
import lk.ijse.layeredarchitecture.dao.custom.ItemDao;
import lk.ijse.layeredarchitecture.dao.custom.OrderDao;
import lk.ijse.layeredarchitecture.dao.custom.OrderDetailDao;
import lk.ijse.layeredarchitecture.entity.Order;

import java.sql.*;
import java.util.ArrayList;

public class OrderDaoImpl implements OrderDao {

 private OrderDetailDao orderDetailDao = new OrderDetailDaoImpl();
 private ItemDao itemDao = new ItemDAOImpl();
    @Override
    public boolean isExist(String orderId) throws SQLException, ClassNotFoundException {

        /*if order id already exist*/
        ResultSet rst = SQLUtil.execute("SELECT oid FROM `Orders` WHERE oid=?",orderId);
        if (rst.next()) {
            return false;
        }
        return true;
    }

    @Override
    public Order search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public ArrayList getAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO `Orders` (oid, date, customerID) VALUES (?,?,?)",
                entity.getOrderId(),entity.getOrderDate(),entity.getCustomerId());
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getId() throws SQLException, ClassNotFoundException {

            ResultSet rst = SQLUtil.execute("SELECT oid FROM `Orders` ORDER BY oid DESC LIMIT 1;");

            return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("oid").replace("OID-", "")) + 1)) : "OID-001";
    }



}

