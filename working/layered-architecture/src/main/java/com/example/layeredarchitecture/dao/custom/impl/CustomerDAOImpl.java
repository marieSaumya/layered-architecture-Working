package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.CustomerDao;
import com.example.layeredarchitecture.model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDao
{
  @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        ArrayList<CustomerDTO> getAllCustomer = new ArrayList<>();
        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(rst.getString("id"), rst.getString("name"), rst.getString("address"));
            getAllCustomer.add(customerDTO);
        }
        return getAllCustomer;
    }
    @Override
    public boolean save(CustomerDTO dto) throws SQLException, ClassNotFoundException {

        String sql ="INSERT INTO Customer (id,name, address) VALUES (?,?,?)";
      return   SQLUtil.execute(sql,dto.getId(),dto.getName(),dto.getAddress());
    }
    @Override
    public boolean update(CustomerDTO dto) throws SQLException, ClassNotFoundException {

       return SQLUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?",dto.getId(),dto.getName(),dto.getAddress());
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
    public CustomerDTO search(String newValue) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE id=?",newValue+"");
        rst.next();
        CustomerDTO customerDTO = new CustomerDTO(newValue + "", rst.getString("name"), rst.getString("address"));
       return customerDTO;
    }
}
