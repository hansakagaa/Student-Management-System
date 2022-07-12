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
import lk.ijse.sms.model.Course;

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
    public JFXTextField StudentId;
    public JFXTextField StudentName;
    public JFXTextField StudentEmail;
    public JFXTextField StudentContact;
    public JFXTextField StuAddress;
    public JFXTextField StudentNIC;
    public JFXButton btnStudent;
    public JFXButton btnStudentDelete;
    public JFXTextField StudentIdSearch;
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

    public void initialize(){
        lordDateAndTime();
        lordCourseId();
        try {
            lordRegistrationId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void lordRegistrationId() throws SQLException, ClassNotFoundException {
        String id;
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(
                "SELECT registration_id FROM Registration ORDER BY registration_id DESC LIMIT 1"
        ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                id = "REG00-00"+tempId;
            }else if(tempId<99){
                id = "REG00-0"+tempId;
            }else{
                id = "REG00-"+tempId;
            }

        }else{
            id =  "REG00-001";
        }

        txtRegistration_Id.setText(id);
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

    }

    public void studentDeleteOnAction(ActionEvent actionEvent) {

    }

    public void studentSearchOnAction(ActionEvent actionEvent) {

    }

    public void registrationSaveOnAction(ActionEvent actionEvent) {

    }

    public void cancelOnAction(ActionEvent actionEvent) {

    }
}
