package lk.ijse.sms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author : hansakagaa
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intake {
    private String intake_id;
    private LocalDate date;
    private String intakeCol;
    private String description;
    private String course_id;
}
