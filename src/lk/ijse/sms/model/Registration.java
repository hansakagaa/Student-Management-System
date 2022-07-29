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
public class Registration {
    private String registration_id;
    private LocalDate reg_date;
    private String student_id;
    private String intake_id;
}
