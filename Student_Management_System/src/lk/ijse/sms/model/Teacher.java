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
public class Teacher {
    private String teacher_id;
    private String name;
    private String nic;
    private String contact;
    private String address;
}
