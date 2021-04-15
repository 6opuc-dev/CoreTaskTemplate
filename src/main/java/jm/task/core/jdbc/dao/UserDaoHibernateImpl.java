package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` SMALLINT(20) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            session.delete(session.load(User.class, id));
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            userList = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for (User user : userList) {
            System.out.println(user);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
