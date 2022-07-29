package lk.ijse.sms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : hansakagaa
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private String subject_id;
    private String subject_name;
    private double credit;
    private String teacher_id;
}
