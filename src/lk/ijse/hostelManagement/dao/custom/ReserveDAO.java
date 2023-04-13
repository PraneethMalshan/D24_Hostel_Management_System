package lk.ijse.hostelManagement.dao.custom;

import lk.ijse.hostelManagement.dao.CrudDAO;
import lk.ijse.hostelManagement.entity.Reservation;

import java.sql.SQLException;

public interface ReserveDAO extends CrudDAO<Reservation, String> {

    boolean existStudent(String id)throws SQLException, ClassNotFoundException;

    String generateNewId()throws SQLException, ClassNotFoundException;
}
