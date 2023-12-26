package lk.ijse.layeredarchitecture.dao;

import com.example.layeredarchitecture.dao.custom.impl.*;
import lk.ijse.layeredarchitecture.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory ;
    private DaoFactory(){

    }
    public static DaoFactory getDaoFactory(){

        return( daoFactory ==null) ?daoFactory=new DaoFactory():daoFactory;
    }
    public enum DataTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL,QUERY
    }
    public SuperDao getDao(DataTypes dataTypes){
        switch (dataTypes){
            case CUSTOMER: return new CustomerDAOImpl();

            case ITEM:return new ItemDAOImpl();

            case ORDER:return new OrderDaoImpl();

            case ORDER_DETAIL:return new OrderDetailDaoImpl();

            case QUERY: return new QueryDaoImpl();

            default:return null;
        }

    }

}
