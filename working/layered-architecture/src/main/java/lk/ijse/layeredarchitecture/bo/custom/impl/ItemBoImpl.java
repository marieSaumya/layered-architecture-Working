package lk.ijse.layeredarchitecture.bo.custom.impl;


import lk.ijse.layeredarchitecture.bo.ItemBo;
import lk.ijse.layeredarchitecture.dao.DaoFactory;
import lk.ijse.layeredarchitecture.dao.custom.ItemDao;
import lk.ijse.layeredarchitecture.dto.ItemDTO;
import lk.ijse.layeredarchitecture.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBo {
    private ItemDao itemDao = (ItemDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> loadAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDao.getAll();
        ArrayList<ItemDTO> dtos = new ArrayList<>();
        for (Item item : items){
            dtos.add(new ItemDTO(item.getCode(), item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
        }

        return dtos;
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDao.update(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDao.delete(id);
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return  itemDao.isExist(code);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return itemDao.getId();
    }
}
