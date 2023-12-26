package lk.ijse.layeredarchitecture.dao.custom.impl;

import lk.ijse.layeredarchitecture.dao.SQLUtil;
import lk.ijse.layeredarchitecture.dao.custom.QueryDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDaoImpl implements QueryDao {

    @Override
    public void orderOrderDetails() {
        String sql = "SELECT * FROM orders o LEFT JOIN orderDetails od ON o.oid=od.oid";
        try {
            ResultSet rst = SQLUtil.execute(sql);
            while (rst.next()){
                //process
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
