package kitchenpos.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsAllByOrderTableIdInAndOrderStatus(List<Long> orderTableIds, OrderStatus orderStatus);
}
