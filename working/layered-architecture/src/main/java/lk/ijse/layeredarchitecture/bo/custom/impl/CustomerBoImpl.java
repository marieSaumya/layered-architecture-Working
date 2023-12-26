package lk.ijse.layeredarchitecture.bo.custom.impl;

import lk.ijse.layeredarchitecture.bo.CustomerBo;
import lk.ijse.layeredarchitecture.dao.DaoFactory;
import lk.ijse.layeredarchitecture.dao.custom.CustomerDao;
import lk.ijse.layeredarchitecture.dto.CustomerDTO;
import lk.ijse.layeredarchitecture.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBo {
   private CustomerDao customerDAO = (CustomerDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> loadAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> dtoArrayList = new ArrayList<>();
        ArrayList<Customer> customerArrayList =  customerDAO.getAll();
        for (Customer customer : customerArrayList){
          dtoArrayList.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress()))  ;
        }
        return  dtoArrayList;
    }

    @Override
    public void saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.isExist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
       return customerDAO.delete(id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return customerDAO.getId();
    }
}
