package com.ttchoa22ite.population.DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ttchoa22ite.population.models.Resident;
import com.ttchoa22ite.population.utils.ConnectionDB;

public class ResidentDAO {
    private Connection conn;

    public ResidentDAO() {
        conn = ConnectionDB.DAO();
    }

    public Resident searchResident(String residentName) throws SQLException {
        String selectStmt = "SELECT * FROM resident WHERE name = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(selectStmt);
            pstmt.setString(1, residentName);
            rs = pstmt.executeQuery();

            return getResidentFromResultSet(rs);
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed: " + e);
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    private Resident getResidentFromResultSet(ResultSet resultSet) throws SQLException {
        Resident resident = null;

        if (resultSet.next()) {
            resident = new Resident();
            resident.setId(resultSet.getInt("id"));
            resident.setSshk(resultSet.getString("sshk"));
            resident.setCccd(resultSet.getString("cccd"));
            resident.setName(resultSet.getString("name"));
            resident.setNOphone(resultSet.getString("NOphone"));
            resident.setAddress(resultSet.getString("address"));
            resident.setJob(resultSet.getString("job"));
        }

        return resident;
    }

    public ObservableList<Resident> getAllResidents() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();

        try {
            pstmt = conn.prepareStatement("SELECT * FROM resident");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Resident resident = new Resident();
                resident.setId(rs.getInt("id"));
                resident.setSshk(rs.getString("sshk"));
                resident.setCccd(rs.getString("cccd"));
                resident.setName(rs.getString("name"));
                resident.setNOphone(rs.getString("NOphone"));
                resident.setAddress(rs.getString("address"));
                resident.setJob(rs.getString("job"));
                resident.setSex(rs.getString("sex"));
                resident.setBirt(rs.getString("birt"));
                residentsList.add(resident);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }

        return residentsList;
    }

    public void updateResident(String sshk, String name, String sex, String cccd, String NOphone, String address, String job) throws SQLException {
        PreparedStatement pstmt = null;
        String updateStmt = "UPDATE resident SET name = ?, sex = ?, cccd = ?, NOphone = ?, address = ?, job = ? WHERE sshk = ?";

        try {
            pstmt = conn.prepareStatement(updateStmt);
            pstmt.setString(1, name);
            pstmt.setString(2, sex);
            pstmt.setString(3, cccd);
            pstmt.setString(4, NOphone);
            pstmt.setString(5, address);
            pstmt.setString(6, job);
            pstmt.setString(7, sshk);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurred while UPDATE Operation: " + e);
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void deleteResident(String sshk) throws SQLException {
        PreparedStatement pstmt = null;
        String deleteStmt = "DELETE FROM resident WHERE sshk = ?";

        try {
            pstmt = conn.prepareStatement(deleteStmt);
            pstmt.setString(1, sshk);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurred while DELETE Operation: " + e);
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void insertResident(String name, String cccd, String sshk, String NOphone, String address, String job, String sex, String birt) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ObservableList<Resident> residentsList = FXCollections.observableArrayList();
int id=0;
        try {
            pstmt = conn.prepareStatement("SELECT MAX(id) FROM resident");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1))+1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insertStmt = "INSERT INTO resident (name, sex, cccd, NOphone, address, job, sshk, birt,id) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(insertStmt);
            pstmt.setString(1, name);
            pstmt.setString(2, sex);
            pstmt.setString(3, cccd);
            pstmt.setString(4, NOphone);
            pstmt.setString(5, address);
            pstmt.setString(6, job);
            pstmt.setString(7, sshk);
            pstmt.setString(8, birt);
            pstmt.setString(9,""+id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurred while INSERT Operation: " + e);
            throw e;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}

