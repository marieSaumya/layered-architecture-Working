package lk.ijse.layeredarchitecture.bo.custom.impl;

import lk.ijse.layeredarchitecture.bo.PlaceOrderBo;
import lk.ijse.layeredarchitecture.dao.DaoFactory;
import lk.ijse.layeredarchitecture.dao.custom.CustomerDao;
import lk.ijse.layeredarchitecture.dao.custom.ItemDao;
import lk.ijse.layeredarchitecture.dao.custom.OrderDao;
import lk.ijse.layeredarchitecture.dao.custom.OrderDetailDao;
import lk.ijse.layeredarchitecture.db.DBConnection;
import lk.ijse.layeredarchitecture.dto.CustomerDTO;
import lk.ijse.layeredarchitecture.dto.ItemDTO;
import lk.ijse.layeredarchitecture.dto.OrderDetailDTO;
import lk.ijse.layeredarchitecture.entity.Customer;
import lk.ijse.layeredarchitecture.entity.Item;
import lk.ijse.layeredarchitecture.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBo {
    private CustomerDao customerDao = (CustomerDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataTypes.CUSTOMER);
    private ItemDao itemDao = (ItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataTypes.ITEM);
    private OrderDao orderDao = (OrderDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataTypes.ORDER);
    private OrderDetailDao orderDetailDao = (OrderDetailDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataTypes.ORDER_DETAIL);
    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails, String name) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = null;

            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);
            if (!orderDao.isExist(orderId)) {
                return false;
            }
            boolean order = orderDao.save(new Order(orderId,orderDate,customerId,name));
            if (!order) {

                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            boolean orderDetail = orderDetailDao.save(orderDetails);
            if (!orderDetail) {

                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            boolean item = itemDao.updateQuantity(orderDetails);
            if (!item) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDao.search(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.isExist(id);
    }

    @Override
    public ItemDTO searchItem(String newItemCode) throws SQLException, ClassNotFoundException {

       Item item = itemDao.search(newItemCode);
       return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.isExist(code);
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDao.getId();
    }

    @Override
    public ArrayList<CustomerDTO> loadAllCustomerIds() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> dtoArrayList = new ArrayList<>();
        ArrayList<Customer> customerArrayList =  customerDao.getAll();
        for (Customer customer : customerArrayList){
            dtoArrayList.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress()))  ;
        }
        return  dtoArrayList;
    }

    @Override
    public ArrayList<ItemDTO> loadAllItemCodes() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDao.getAll();
        ArrayList<ItemDTO> dtos = new ArrayList<>();
        for (Item item : items){
            dtos.add(new ItemDTO(item.getCode(), item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
        }

        return dtos;
    }

}
