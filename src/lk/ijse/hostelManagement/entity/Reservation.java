package lk.ijse.hostelManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Reservation {

    @Id
    @Column(columnDefinition = "VARCHAR(200)")
    private String res_id;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_Id", referencedColumnName = "Student_Id")
    private Student student_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private Room room_id;
    private String key_money;
    private Double advance;
    private String status;
}
