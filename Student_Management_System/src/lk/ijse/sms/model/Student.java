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
public class Student {
    private String student_id;
    private String student_name;
    private String email;
    private String contact;
    private String address;
    private String nic;
}
