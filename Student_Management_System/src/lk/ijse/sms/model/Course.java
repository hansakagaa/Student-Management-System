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
public class Course {
    private String course_id;
    private String course_name;
    private double cost;
    private String duration;
    private String subject_id;
}
