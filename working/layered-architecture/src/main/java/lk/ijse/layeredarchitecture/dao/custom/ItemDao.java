package lk.ijse.layeredarchitecture.dao.custom;

import lk.ijse.layeredarchitecture.dao.CrudDao;
import lk.ijse.layeredarchitecture.dto.OrderDetailDTO;
import lk.ijse.layeredarchitecture.entity.Item;

import java.sql.*;
import java.util.List;

public interface ItemDao extends CrudDao<Item> {


     boolean updateQuantity(List<OrderDetailDTO> orderDetails) throws SQLException,ClassNotFoundException;
}
