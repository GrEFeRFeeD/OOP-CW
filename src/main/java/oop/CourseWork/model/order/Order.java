package oop.CourseWork.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.file.File;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import oop.CourseWork.model.receiving.Receiving;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long id;

    @Temporal(value = TemporalType.DATE)
    private Date date;

    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<File> files;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<Receiving> receivings;

    @OneToMany(mappedBy = "orderObj")
    @JsonIgnore
    private Set<OrderProduct> orderBody;

    public Order() {
        this.files = new HashSet<>();
        this.receivings = new HashSet<>();
        this.orderBody = new HashSet<>();
    }

    public Order(Long id, Date date, Provider provider, Employee employee) {
        this.id = id;
        this.date = date;
        this.provider = provider;
        this.employee = employee;
        this.files = new HashSet<>();
        this.receivings = new HashSet<>();
        this.orderBody = new HashSet<>();
    }

    public void addFile(File file) { files.add(file); }
    public void addReceiving(Receiving receiving) { receivings.add(receiving); }
    public void addOrderBody(OrderProduct orderProduct) {
        orderBody.add(orderProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", provider=" + provider.getName() +
                ", employee=" + employee.getFirstName() +
                ", files=" + files +
                ", receivings=" + receivings +
                ", orderBody=" + orderBody +
                '}';
    }
}
