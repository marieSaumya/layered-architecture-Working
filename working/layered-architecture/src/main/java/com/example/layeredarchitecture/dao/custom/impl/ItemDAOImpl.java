package com.example.layeredarchitecture.dao.custom.impl;

import com.example.layeredarchitecture.dao.SQLUtil;
import com.example.layeredarchitecture.dao.custom.ItemDao;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDao {
    @Override
  public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
      ResultSet rst = SQLUtil.execute("SELECT * FROM Item");
      ArrayList<ItemDTO> itemList = new ArrayList<>();
      while (rst.next()){
         itemList.add(new ItemDTO(rst.getString("code"),
                 rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand")));
      }
      return itemList;
  }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Item WHERE code=?",id);
    }
    @Override
    public boolean save(ItemDTO dto) throws SQLException, ClassNotFoundException {

       return SQLUtil.execute("INSERT INTO Item (code, description, unitPrice, qtyOnHand) VALUES (?,?,?,?)",dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }
   @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getCode());
    }
    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT code FROM Item WHERE code=?", id);
        return rst.next();
    }
    @Override
    public String getId() {
        try {
            ResultSet rst = SQLUtil.execute("SELECT code FROM Item ORDER BY code DESC LIMIT 1;");

            if (rst.next()) {
                String id = rst.getString("code");
                int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
                return String.format("I00-%03d", newItemId);
            } else {
                return "I00-001";
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }

    @Override
    public ItemDTO search(String newItemCode) throws SQLException, ClassNotFoundException {

        ResultSet rst = SQLUtil.execute("SELECT * FROM Item WHERE code=?",newItemCode + "");
        rst.next();
        ItemDTO item = new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        return  item;
    }

    @Override
    public boolean updateQuantity(List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
       int count = 0;
        for (OrderDetailDTO detail : orderDetails){
            ItemDTO item = findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
            SQLUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE code=?",item.getDescription(),
                    item.getUnitPrice(),item.getQtyOnHand(),item.getCode());

            count ++;
        }
        return count==orderDetails.size();
    }
    public ItemDTO findItem(String code) {
        try {

            ResultSet rst = SQLUtil.execute("SELECT * FROM Item WHERE code=?",code);
            rst.next();
            return new ItemDTO(code, rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
