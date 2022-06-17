package oop.CourseWork.model.order_product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "orders_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @EmbeddedId
    private OrderProductKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order orderObj;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product productObj;

    @Column(name = "cur_balance")
    private int balance;

    @Column(name = "cur_order")
    private int order;

    @Column(name = "cur_return")
    private int retrn;

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", orderObj=" + orderObj.getId() +
                ", productObj=" + productObj.getName() +
                ", balance=" + balance +
                ", order=" + order +
                ", retrn=" + retrn +
                '}';
    }
}
