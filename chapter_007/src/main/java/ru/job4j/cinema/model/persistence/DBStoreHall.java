package ru.job4j.cinema.model.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.job4j.cinema.model.CinemaSize;
import ru.job4j.cinema.model.Site;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DBStoreHall implements StoreCinema {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final StoreCinema INSTANCE = new DBStoreHall();
    private static final Logger logger = Logger.getLogger(DBStoreHall.class);

    public DBStoreHall() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost:5432/postgres");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("a");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        this.createCinemaTable();
        this.createAccountsTable();
    }

    public static StoreCinema getInstance() {
        return INSTANCE;
    }

    @Override
    public void createCinemaTable() {
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS cinema \n" +
                    "(id INT PRIMARY KEY ,\n" +
                    "row INT,\n" +
                    "place INT,\n" +
                    "isFree BOOLEAN,\n" +
                    "cost INT)";
            st.execute(query);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void createAccountsTable() {
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS accounts (\n" +
                    "   id SERIAL PRIMARY KEY,\n" +
                    "   name_person VARCHAR(50) NOT NULL,\n" +
                    "   phone VARCHAR(50) NOT NULL,\n" +
                    "   place_id  INTEGER REFERENCES cinema (id) NOT NULL\n" +
                    ")";
            st.execute(query);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public Collection<Site> getAll() {
        Collection<Site> result = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             Statement statement = connection.createStatement();
        ) {
            ResultSet rs = statement.executeQuery("SELECT * FROM cinema ORDER BY id");
            while (rs.next()) {
                Site newSite = new Site(
                        rs.getInt("id"),
                        rs.getInt("row"),
                        rs.getInt("place"),
                        rs.getBoolean("isFree"),
                        rs.getInt("cost"));
                result.add(newSite);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public CinemaSize getCinemaSize() {
        CinemaSize rsl = new CinemaSize(0, 0);
        int row = 0;
        int place = 0;
        try (Connection connection = SOURCE.getConnection();
             Statement st = connection.createStatement();
             Statement st2 = connection.createStatement()) {
            String querySizeRow = "SELECT max(row) from cinema";
            String querySizePlace = "SELECT max(place) from cinema";
            try (ResultSet rs = st.executeQuery(querySizeRow);
                 ResultSet rs2 = st2.executeQuery(querySizePlace)) {
                while (rs.next()) {
                    row = rs.getInt(1);
                }
                while (rs2.next()) {
                    place = rs2.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        rsl.setRow(row);
        rsl.setPlace(place);
        return rsl;
    }


    @Override
    public boolean reservePlace(int id, String username, String phone) {
        boolean result = false;
        String queryIsFree = "SELECT isFree FROM cinema WHERE id = ?";
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement ps = connection.prepareStatement(queryIsFree);
        ) {
            connection.setAutoCommit(false);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getBoolean("isFree");
            }
            if (result) {
                String queryBook = "UPDATE  cinema SET isfree=FALSE  WHERE id = ?";
                String queryAdd = "INSERT INTO accounts (name_person, phone, place_id) VALUES (?, ?, ?)";
                try (PreparedStatement ps1 = connection.prepareStatement(queryBook);
                     PreparedStatement ps2 = connection.prepareStatement(queryAdd)) {
                    ps1.setInt(1, id);
                    ps1.executeUpdate();
                    ps2.setString(1, username);
                    ps2.setString(2, phone);
                    ps2.setInt(3, id);
                    ps2.executeUpdate();
                    connection.commit();

                }
            }

            connection.setAutoCommit(true);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }


}
