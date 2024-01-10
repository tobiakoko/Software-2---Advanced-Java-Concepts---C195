package database;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentQuery {

    public static ObservableList<Appointment> getAppointmentList() {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.CONTACT_ID ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                int contactId = rs.getInt("Contact_ID");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment c = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public static void updateAppointment(int appointmentId, String title, String description, String location, String type,
                                         LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_Id = ?, User_ID = ?, Contact_ID  = ? WHERE Appointment_ID = ?";
            PreparedStatement updateAppointment = JDBC.connection.prepareStatement(sql);

            updateAppointment.setString(1, title);
            updateAppointment.setString(2, description);
            updateAppointment.setString(3, location);
            updateAppointment.setString(4, type);
            updateAppointment.setTimestamp(5, Timestamp.valueOf(start));
            updateAppointment.setTimestamp(6, Timestamp.valueOf(end));
            updateAppointment.setInt(7, customerId);
            updateAppointment.setInt(8, userId);
            updateAppointment.setInt(9, contactId);
            updateAppointment.setInt(10, appointmentId);

            updateAppointment.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAppointment(String title, String description, String location, String type,
                                      LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){
        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, USER_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8,userId);
            ps.setInt(9, contactId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> getApptByWeek(){
        ObservableList<Appointment> allWeek = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment byWeek = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                allWeek.add(byWeek);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allWeek;
    }

    public static void deleteAppointment(int appointmentId){
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement delAppointment = JDBC.connection.prepareStatement(sql);
            delAppointment.setInt(1, appointmentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> getMonthlyAppointment(){
        ObservableList<Appointment> allMonth = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE MONTH(START) = MONTH(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment byMonth = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                allMonth.add(byMonth);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMonth;
    }

    public static ObservableList<Appointment> getUserAppointment(int userID){
        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE User_ID = ' " + userID + " ' ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment results = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                userAppointments.add(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAppointments;
    }

    public static ObservableList<Appointment> getContactAppointment( int contactID) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments WHERE User_ID = ' " + contactID + " ' ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment results = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                contactAppointments.add(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactAppointments;
    }

    public static ObservableList<Appointment> AppointmentType(){
        ObservableList<Appointment> appointmentListType = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Type, Count(*) AS NUM FROM appointments GROUP BY Type";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Type");
                int typeTotal = rs.getInt("NUM");
                Appointment results = new Appointment(type, typeTotal);
                appointmentListType.add(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentListType;
    }

    public static ObservableList<Appointment> getAppointmentTypeMonth(){
        ObservableList<Appointment> appointmentTypeMonthTotal = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT(MONTHNAME(Start)) AS Month, Count(*) AS NUM FROM appointments GROUP BY Month";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("Month");
                int typeTotal = rs.getInt("NUM");
                Appointment results = new Appointment(type, typeTotal);
                appointmentTypeMonthTotal.add(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentTypeMonthTotal;
    }
}
