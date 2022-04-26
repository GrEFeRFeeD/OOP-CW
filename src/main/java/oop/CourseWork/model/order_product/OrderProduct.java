package oop.CourseWork.model.order_product;

import lombok.Data;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.product.Product;

import javax.persistence.*;

@Entity
@Data
public class OrderProduct {

    @EmbeddedId
    private OrderProductKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "prev_balance")
    private int prevBalance;

    @Column(name = "prev_order")
    private int prevOrder;

    @Column(name = "prev_return")
    private int prevReturn;

    @Column(name = "balance")
    private int curBalance;

    @Column(name = "order")
    private int curOrder;

    @Column(name = "return")
    private int curReturn;
}
