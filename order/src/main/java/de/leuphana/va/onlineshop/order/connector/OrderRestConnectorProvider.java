package de.leuphana.va.onlineshop.order.connector;

import de.leuphana.va.onlineshop.order.component.behaviour.OrderComponentService;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.component.structure.requests.OrderCreateRequest;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderGetResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderWriteResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrdersGetResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/order")
public class OrderRestConnectorProvider {

    private final OrderComponentService orderService;

    public OrderRestConnectorProvider(OrderComponentService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "{orderId}")
    public OrderGetResponse getOrder(@PathVariable("orderId") Integer orderId) {
        Orderr order = orderService.getOrder(orderId);
        return new OrderGetResponse(order);
    }

    @GetMapping(path = "all")
    public OrdersGetResponse getOrder() {
        Set<Orderr> orders = orderService.getAllOrders();
        return new OrdersGetResponse(orders);
    }

    @GetMapping(path = "customer/{customerId}")
    public OrdersGetResponse getOrdersForCustomer(@PathVariable("customerId") Integer customerId) {
        Set<Orderr> orders = orderService.getOrdersForCustomer(customerId);
        return new OrdersGetResponse(orders);
    }

    @PostMapping(path = "create")
    public OrderWriteResponse createOrder(@RequestBody OrderCreateRequest createOrderRequest) {
        boolean success = orderService.createOrder(createOrderRequest.order());
        return new OrderWriteResponse(success);
    }

    @DeleteMapping(path = "{orderId}")
    public OrderWriteResponse deleteOrder(@PathVariable("orderId") Integer orderId) {
        boolean success = orderService.deleteOrder(orderId);
        return new OrderWriteResponse(success);
    }

}
