package lk.ijse.sms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
