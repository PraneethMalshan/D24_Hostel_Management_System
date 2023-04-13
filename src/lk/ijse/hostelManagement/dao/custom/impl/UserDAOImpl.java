package lk.ijse.hostelManagement.dao.custom.impl;

import lk.ijse.hostelManagement.dao.custom.UserDAO;
import lk.ijse.hostelManagement.entity.Loging;
import lk.ijse.hostelManagement.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jfree.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<Loging> getAllUsers() throws SQLException, ClassNotFoundException{
        ArrayList<Loging>login;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Loging");
        login = (ArrayList<Loging>) query.list();
        transaction.commit();
        session.close();
        return login;
    }

    @Override
    public ArrayList<Loging> getAll() throws SQLException, ClassNotFoundException{
        ArrayList<Loging> allUsers;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Loging ");
        allUsers = (ArrayList<Loging>) query.list();
        transaction.commit();
        session.close();
        return allUsers;
    }

    @Override
    public boolean save (Loging login) throws SQLException, ClassNotFoundException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(login);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(Loging login) throws SQLException, ClassNotFoundException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(login);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Loging login = session.get(Loging.class, id);
        session.delete(login);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Loging search(String id) throws SQLException, ClassNotFoundException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Loging login = session.get(Loging.class, id);
        transaction.commit();
        session.close();
        return login;
    }

    @Override
    public  boolean exist(String id) throws SQLException, ClassNotFoundException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT userID FROM Loging WHERE userID=:id");
        String id1 = (String) query.setParameter("id", id).uniqueResult();
        if (id1 != null){
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }
}
