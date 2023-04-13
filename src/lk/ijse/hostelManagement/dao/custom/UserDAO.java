package lk.ijse.hostelManagement.dao.custom;

import lk.ijse.hostelManagement.dao.CrudDAO;
import lk.ijse.hostelManagement.entity.Loging;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<Loging, String> {

     ArrayList<Loging> getAllUsers() throws SQLException, ClassNotFoundException;

}
