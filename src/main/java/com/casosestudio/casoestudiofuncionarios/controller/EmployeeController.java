package com.casosestudio.casoestudiofuncionarios.controller;

import com.casosestudio.casoestudiofuncionarios.data.EmployeeDao;
import com.casosestudio.casoestudiofuncionarios.domain.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeController {

    private EmployeeDao employeeDao;

    public EmployeeController() {
        employeeDao = new EmployeeDao();
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDao.getAllEmployees();
    }

    public Employee getEmployeeById(String id) throws SQLException {
        return employeeDao.getEmployeeById(id);
    }

    public void addEmployee(Employee employee) throws SQLException {
        employeeDao.createEmployee(employee);
    }

    public int updateEmployee(Employee employee, String id) throws SQLException {
        return employeeDao.updateEmployee(employee, id);
    }

    public int deleteEmployee(String id) throws SQLException {
        return employeeDao.deleteEmployee(id);
    }
}
