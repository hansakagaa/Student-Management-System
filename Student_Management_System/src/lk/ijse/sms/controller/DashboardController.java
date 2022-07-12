package lk.ijse.sms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.sms.db.DBConnection;
import lk.ijse.sms.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author : hansakagaa
 **/
public class DashboardController {
    public AnchorPane root;
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtStudentEmail;
    public JFXTextField txtStudentContact;
    public JFXTextField txtStuAddress;
    public JFXTextField txtStudentNIC;
    public JFXButton btnStudent;
    public JFXButton btnStudentDelete;
    public JFXTextField txtStudentIdSearch;
    public JFXButton btnStuSearch;
    public JFXTextField txtRegistration_Id;
    public Label lblDateTime;
    public JFXTextField txtIntakeId;
    public JFXDatePicker startDate;
    public JFXTextField txtIntakeCol;
    public JFXTextField txtDescription;
    public JFXComboBox<String> cmbCourseId;
    public JFXTextField txtPaymentId;
    public JFXDatePicker paymentDate;
    public JFXTextField txtCost;
    public JFXButton btnCancel;
    public JFXButton btnSave;

    public void initialize() {
        lordDateAndTime();
        lordCourseId();
        try {
            lordRegistrationId();
            lordStudentId();
            lordIntakeId();
            lordPaymentId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        txtStudentId.setFocusTraversable(false);


    }

    private void lordRegistrationId() throws SQLException, ClassNotFoundException {
        String id;
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT registration_id FROM Registration ORDER BY registration_id DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                id = "REG00-00" + tempId;
            } else if (tempId < 99) {
                id = "REG00-0" + tempId;
            } else {
                id = "REG00-" + tempId;
            }

        } else {
            id = "REG00-001";
        }

        txtRegistration_Id.setText(id);
    }

    private void lordIntakeId() throws SQLException, ClassNotFoundException {
        String id;
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT intake_id FROM Intake ORDER BY intake_id DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                id = "INT00-00" + tempId;
            } else if (tempId < 99) {
                id = "INT00-0" + tempId;
            } else {
                id = "INT00-" + tempId;
            }

        } else {
            id = "INT00-001";
        }

        txtIntakeId.setText(id);
    }

    private void lordStudentId() throws SQLException, ClassNotFoundException {
        String id;
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT student_id FROM Student ORDER BY student_id DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                id = "S00-00" + tempId;
            } else if (tempId < 99) {
                id = "S00-0" + tempId;
            } else {
                id = "S00-" + tempId;
            }

        } else {
            id = "S00-001";
        }
        txtStudentId.setText(id);
    }

    private void lordPaymentId() throws SQLException, ClassNotFoundException {
        String id;
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId < 9) {
                id = "P00-00" + tempId;
            } else if (tempId < 99) {
                id = "P00-0" + tempId;
            } else {
                id = "P00-" + tempId;
            }

        } else {
            id = "P00-001";
        }
        txtPaymentId.setText(id);
    }

    private void lordCourseId() {
        try {
            ArrayList<Course> dtoS = getAllCourseId();
            for (Course dto : dtoS) {
                cmbCourseId.getItems().add(dto.getCourse_id());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Course ids").show();
        }
    }

    private ArrayList<Course> getAllCourseId() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM Course");
        ResultSet rst = stm.executeQuery();
        ArrayList<Course> courses = new ArrayList<>();
        while (rst.next()) {
            courses.add(new Course(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return courses;
    }

    private void lordDateAndTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy : MM : dd     HH : mm : ss");
            lblDateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void studentSaveOrUpdateOnAction(ActionEvent actionEvent) {
        String id = txtStudentId.getText();
        String name = txtStudentName.getText();
        String email = txtStudentEmail.getText();
        String contact = txtStudentContact.getText();
        String address = txtStuAddress.getText();
        String nic = txtStudentNIC.getText();
        if (btnSave.getText().equalsIgnoreCase("Save")) {
            /*Save Student*/
            try {
                if (existStudent(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }

                boolean student = saveStudent(new Student(id, name, email, contact, address, nic));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the Student " + e.getMessage()).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {
            /*Update student*/
            try {
                if (!existStudent(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + id).show();
                }

                Boolean student = UpdateStudent(new Student(id, name, email, contact, address, nic));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the student " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean saveStudent(Student std) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Student VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1, std.getStudent_id());
        stm.setObject(2, std.getStudent_name());
        stm.setObject(3, std.getEmail());
        stm.setObject(4, std.getContact());
        stm.setObject(5, std.getAddress());
        stm.setObject(6, std.getNic());

        return stm.executeUpdate() > 0;
    }

    private boolean UpdateStudent(Student std) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "UPDATE Student SET student_name=?, email=?, contact=?, address=?, nic=? WHERE student_id=?"
        );
        stm.setObject(1, std.getStudent_id());
        stm.setObject(2, std.getStudent_name());
        stm.setObject(3, std.getEmail());
        stm.setObject(4, std.getContact());
        stm.setObject(5, std.getAddress());
        stm.setObject(6, std.getNic());

        return stm.executeUpdate() > 0;
    }

    private boolean existStudent(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("");
        pStm.setObject(1, id);

        return pStm.executeQuery().next();
    }

    public void studentDeleteOnAction(ActionEvent actionEvent) {
        String id = txtStudentId.getText();
        try {
            if (!existStudent(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + id).show();
            }
            boolean student = deleteStudent(id);

            clear(txtStudentId, txtStudentIdSearch, txtStudentName, txtStudentEmail, txtStudentContact, txtStuAddress, txtStudentNIC);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the student " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("DELETE FROM Student WHERE student_id=?");
        pStm.setObject(1, id);

        return pStm.executeUpdate() > 0;
    }

    public void studentSearchOnAction(ActionEvent actionEvent) {
        String id = txtStudentIdSearch.getText();
        try {
            Student std = searchStudent(id);
            txtStudentId.setText(std.getStudent_id());
            txtStudentName.setText(std.getStudent_name());
            txtStudentEmail.setText(std.getEmail());
            txtStudentContact.setText(std.getContact());
            txtStuAddress.setText(std.getAddress());
            txtStudentNIC.setText(std.getNic());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Student searchStudent(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pStm = connection.prepareStatement("SELECT * FROM Student WHERE student_id=?");
        pStm.setObject(1, id);
        ResultSet rst = pStm.executeQuery();
        if (rst.next()){
            return new Student(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
        }
        return null;
    }

    public void registrationSaveOnAction(ActionEvent actionEvent) {
        try {
            boolean intake = saveIntake(new Intake());
            boolean payment = savePayment(new Payment());
            boolean registration = saveRegistration(new Registration());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save" + e.getMessage()).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean saveRegistration(Registration rsg) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Registraction VALUES(?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1, rsg.getRegistration_id());
        stm.setObject(2, rsg.getReg_date());
        stm.setObject(3, rsg.getStudent_id());
        stm.setObject(4, rsg.getIntake_id());

        return stm.executeUpdate() > 0;
    }

    private boolean savePayment(Payment pay) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Intake VALUES(?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1, pay.getPayment_id());
        stm.setObject(2, pay.getDate());
        stm.setObject(3, pay.getCost());
        stm.setObject(4, pay.getRegistration_id());

        return stm.executeUpdate() > 0;
    }

    private boolean saveIntake(Intake intake) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Intake VALUES(?,?,?,?,?)";
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(query);
        stm.setObject(1, intake.getIntake_id());
        stm.setObject(2, intake.getDate());
        stm.setObject(3, intake.getIntakeCol());
        stm.setObject(4, intake.getDescription());
        stm.setObject(5, intake.getCourse_id());

        return stm.executeUpdate() > 0;
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        cmbCourseId.getSelectionModel().clearSelection();
        clear(txtStuAddress, txtStudentName, txtStudentEmail, txtStudentContact, txtStudentNIC, txtIntakeCol, txtDescription, txtCost);
    }

    private void clear(JFXTextField... field) {
        for (JFXTextField textField : field) {
            textField.clear();
        }
    }
}