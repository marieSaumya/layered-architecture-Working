package lk.ijse.layeredarchitecture.dao.custom.impl;

import lk.ijse.layeredarchitecture.dao.SQLUtil;
import lk.ijse.layeredarchitecture.dao.custom.CustomerDao;
import lk.ijse.layeredarchitecture.entity.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDao
{
  @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> getAllCustomer = new ArrayList<>();
        while (rst.next()) {
            Customer entity = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"));
            getAllCustomer.add(entity);
        }
        return getAllCustomer;
    }
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {

        String sql ="INSERT INTO Customer (id,name, address) VALUES (?,?,?)";
      return   SQLUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress());
    }
    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

       return SQLUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?",entity.getId(),entity.getName(),entity.getAddress());
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("DELETE FROM Customer WHERE id=?",id);
    }
    @Override
    public String getId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");

        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }
    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT id FROM Customer WHERE id=?",id);
        return rst.next();
    }

    @Override
    public Customer search(String newValue) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE id=?",newValue+"");
        rst.next();
        Customer entity = new Customer(newValue + "", rst.getString("name"), rst.getString("address"));
       return entity;
    }
}
