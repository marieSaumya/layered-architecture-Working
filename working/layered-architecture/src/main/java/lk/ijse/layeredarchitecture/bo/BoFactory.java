package lk.ijse.layeredarchitecture.bo;

import lk.ijse.layeredarchitecture.bo.custom.impl.CustomerBoImpl;
import lk.ijse.layeredarchitecture.bo.custom.impl.ItemBoImpl;
import lk.ijse.layeredarchitecture.bo.custom.impl.PlaceOrderBoImpl;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getBoFactory(){
        return (boFactory == null) ? boFactory=new BoFactory():boFactory;
    }

    public enum DataType{
        CUSTOMER_BO,ITEM_BO,PLACE_ORDER_BO
    }
    public SuperBo getBo (DataType type){
        switch (type){
            case CUSTOMER_BO: return new CustomerBoImpl();
            case ITEM_BO:return new ItemBoImpl();
            case PLACE_ORDER_BO:return new PlaceOrderBoImpl();
            default:return null;
        }
    }
}
