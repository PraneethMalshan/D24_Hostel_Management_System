package lk.ijse.hostelManagement.dao;

import lk.ijse.hostelManagement.dao.custom.impl.ReserveDAOImpl;
import lk.ijse.hostelManagement.dao.custom.impl.RoomDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory (){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;

    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types){
            case RESERVE:
                return new ReserveDAOImpl();

            case ROOM:
                return new RoomDAOImpl();

            default:
                return null;
        }
    }

    public enum DAOTypes{
         ROOM, RESERVE
    }
}
