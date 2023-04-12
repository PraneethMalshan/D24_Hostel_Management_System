package lk.ijse.hostelManagement.view.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginTM {
    private String userID;
    private String name;
    private String address;
    private String contact_no;
    private String password;
    private String gender;
}
