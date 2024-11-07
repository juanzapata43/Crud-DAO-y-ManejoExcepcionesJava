package com.casosestudio.casoestudiofuncionarios.presentacion;

import com.casosestudio.casoestudiofuncionarios.controller.EmployeeController;
import com.casosestudio.casoestudiofuncionarios.domain.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.SQLException;
import java.util.List;

public class EmployeeView {

    @FXML
    private TextField txtTipoIdentificacion, txtNumeroIdentificacion, txtNombres, txtApellidos, txtEstadoCivil, txtSexo, txtDireccion, txtTelefono, txtFechaNacimiento;

    @FXML
    private TableView<Employee> tablaEmpleados;

    @FXML
    private TableColumn<Employee, Integer> idColumn;

    @FXML
    private TableColumn<Employee, String> idTypeColumn, idNumberColumn, firstNameColumn, lastNameColumn, phoneColumn;

    private ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idTypeColumn.setCellValueFactory(new PropertyValueFactory<>("identificationType"));
        idNumberColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        getAllEmployees();
    }


    @FXML
    private void addEmployee() {
        try {
            Employee employee = createEmployee();
            getController().addEmployee(employee);
            getAllEmployees();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void updateEmployee() {
        try {
            Employee employee = createEmployee();
            int result = getController().updateEmployee(employee, employee.getIdentificationNumber());
            if (result == 1) {
                showAlert(Alert.AlertType.INFORMATION, "Funcionario actualizado correctamente.");
                getAllEmployees();
            } else {
                showAlert(Alert.AlertType.ERROR, "No se encontró un funcionario con ese número de identificación.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void removeEmployee() {
        try {
            int rowsDeleted = getController().deleteEmployee(txtNumeroIdentificacion.getText());
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Funcionario eliminado correctamente.");
                getAllEmployees();
            } else {
                showAlert(Alert.AlertType.ERROR, "No se encontró un funcionario con ese número de identificación.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void findEmployee() {
        try {
            Employee employee = getController().getEmployeeById(txtNumeroIdentificacion.getText());
            if (employee != null) {
                txtTipoIdentificacion.setText(employee.getIdentificationType());
                txtNombres.setText(employee.getName());
                txtApellidos.setText(employee.getLastName());
                txtEstadoCivil.setText(employee.getMaritalStatus());
                txtSexo.setText(employee.getGender());
                txtDireccion.setText(employee.getAddress());
                txtTelefono.setText(employee.getPhone());
                txtFechaNacimiento.setText(employee.getBirthDate().toString());
                showAlert(Alert.AlertType.INFORMATION, "Funcionario encontrado y cargado.");
            } else {
                showAlert(Alert.AlertType.ERROR, "No se encontró un funcionario con ese número de identificación.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void getAllEmployees() {
        try {
            List<Employee> employees = getController().getAllEmployees();
            ObservableList<Employee> observableFuncionarios = FXCollections.observableArrayList(employees);
            tablaEmpleados.setItems(observableFuncionarios);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setIdentificationType(txtTipoIdentificacion.getText());
        employee.setIdentificationNumber(txtNumeroIdentificacion.getText());
        employee.setName(txtNombres.getText());
        employee.setLastName(txtApellidos.getText());
        employee.setMaritalStatus(txtEstadoCivil.getText());
        employee.setGender(txtSexo.getText());
        employee.setAddress(txtDireccion.getText());
        employee.setPhone(txtTelefono.getText());
        employee.setBirthDate(java.sql.Date.valueOf(txtFechaNacimiento.getText()));
        return employee;
    }

    public EmployeeController getController() {
        return new EmployeeController();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearFields() {
        txtTipoIdentificacion.clear();
        txtNumeroIdentificacion.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEstadoCivil.clear();
        txtSexo.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtFechaNacimiento.clear();
    }

}