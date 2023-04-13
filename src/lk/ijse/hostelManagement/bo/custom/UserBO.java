package lk.ijse.hostelManagement.bo.custom;

import lk.ijse.hostelManagement.bo.SuperBO;
import lk.ijse.hostelManagement.dto.LoginDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

     ArrayList<LoginDTO> getAllUsers() throws SQLException, ClassNotFoundException ;

    ArrayList<LoginDTO> searchAllUser(String id) throws SQLException, ClassNotFoundException;

    boolean seveUser(LoginDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateUser(LoginDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    boolean existUser(String id) throws SQLException, ClassNotFoundException;
}
