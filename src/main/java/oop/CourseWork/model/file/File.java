package oop.CourseWork.model.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.order.Order;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "file_id")
    private Long id;

    private String name;
    private String path;

    // N:1 with orders
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
