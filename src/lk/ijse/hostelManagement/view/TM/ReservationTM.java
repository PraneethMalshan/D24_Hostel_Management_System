package lk.ijse.hostelManagement.view.TM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTM {
    private String res_id;
    private LocalDate date;
    private String student_id;
    private String room_id;
    private String key_Money;
    private Double advance;
    private String status;
}
