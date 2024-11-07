package com.casosestudio.casoestudiofuncionarios.data;

import com.casosestudio.casoestudiofuncionarios.domain.Employee;
import com.casosestudio.casoestudiofuncionarios.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private static final String GET_EMPLOYEES = "SELECT * FROM Employee";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM Employee WHERE id_number = ?";
    private static final String CREATE_EMPLOYEE = "INSERT INTO Employee (id_type, id_number, first_name, last_name, marital_status, gender, address, phone, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_EMPLOYEE = "DELETE FROM Employee WHERE id_number = ?";
    private static final String UPDATE_EMPLOYEE = "UPDATE Employee SET id_type = ?, first_name = ?, last_name = ?, marital_status = ?, gender = ?, address = ?, phone = ?, birth_date = ? WHERE id_number = ?";


    public List<Employee> getAllEmployees() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Employee> employees = new ArrayList<>();
        try {
            con = ConnectionUtil.getConnection();
            preparedStatement = con.prepareStatement(GET_EMPLOYEES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                addEmployeeInfo(employee, resultSet);
                employees.add(employee);
            }
            return employees;
        } finally {
            if (con != null) {
                con.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public Employee getEmployeeById(String id) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Employee employee = null;
        try {
            con = ConnectionUtil.getConnection();
            preparedStatement = con.prepareStatement(GET_EMPLOYEE_BY_ID);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee();
                addEmployeeInfo(employee, resultSet);
            }
            return employee;
        } finally {
            if (con != null) {
                con.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public void createEmployee(Employee employee) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionUtil.getConnection();
            preparedStatement = con.prepareStatement(CREATE_EMPLOYEE);
            preparedStatement.setString(1, employee.getIdentificationType());
            preparedStatement.setString(2, employee.getIdentificationNumber());
            preparedStatement.setString(3, employee.getName());
            preparedStatement.setString(4, employee.getLastName());
            preparedStatement.setString(5, employee.getMaritalStatus());
            preparedStatement.setString(6, employee.getGender());
            preparedStatement.setString(7, employee.getAddress());
            preparedStatement.setString(8, employee.getPhone());
            preparedStatement.setDate(9, (Date) employee.getBirthDate());
            preparedStatement.executeUpdate();
        } finally {
            if (con != null) {
                con.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public int updateEmployee(Employee employee, String identificationNumber) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionUtil.getConnection();
            preparedStatement = con.prepareStatement(UPDATE_EMPLOYEE);
            preparedStatement.setString(1, employee.getIdentificationType());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getMaritalStatus());
            preparedStatement.setString(5, employee.getGender());
            preparedStatement.setString(6, employee.getAddress());
            preparedStatement.setString(7, employee.getPhone());
            preparedStatement.setDate(8, (Date) employee.getBirthDate());
            preparedStatement.setString(9, identificationNumber);
            return preparedStatement.executeUpdate();
        } finally {
            if (con != null) {
                con.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public int deleteEmployee(String id) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionUtil.getConnection();
            preparedStatement = con.prepareStatement(DELETE_EMPLOYEE);
            preparedStatement.setString(1, id);
            return preparedStatement.executeUpdate();
        } finally {
            if (con != null) {
                con.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    private void addEmployeeInfo(Employee employee, ResultSet resultSet) throws SQLException {
        employee.setId(resultSet.getString("id"));
        employee.setIdentificationNumber(resultSet.getString("id_number"));
        employee.setIdentificationType(resultSet.getString("id_type"));
        employee.setName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setAddress(resultSet.getString("address"));
        employee.setPhone(resultSet.getString("phone"));
        employee.setGender(resultSet.getString("gender"));
        employee.setMaritalStatus(resultSet.getString("marital_status"));
        employee.setBirthDate(resultSet.getDate("birth_date"));
    }

}
