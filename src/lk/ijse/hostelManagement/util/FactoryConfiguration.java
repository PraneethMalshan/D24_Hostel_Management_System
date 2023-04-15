package lk.ijse.hostelManagement.util;

import lk.ijse.hostelManagement.entity.Loging;
import lk.ijse.hostelManagement.entity.Reservation;
import lk.ijse.hostelManagement.entity.Room;
import lk.ijse.hostelManagement.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

import static java.lang.System.getProperties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration(){
//        StandardServiceRegistry buildRegistry = new StandardServiceRegistryBuilder()
//                .loadProperties("Property_File.properties")
//                .loadProperties("hibernate.properties")
//                .configure().build();
//         sessionFactory = new MetadataSources(buildRegistry)
//                 .getMetadataBuilder().build()
//                 .getSessionFactoryBuilder().build();

         sessionFactory = new Configuration()
                .mergeProperties(getProperties())
                .addAnnotatedClass(Loging.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

    }

    public static Properties getProperties(){
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader
            .getSystemClassLoader()
            .getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            System.out.println("Property file not found!");
            e.printStackTrace();
        }
        return properties;
    }


    public static FactoryConfiguration getInstance(){
        return factoryConfiguration==null ? factoryConfiguration=new FactoryConfiguration()
                :factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
