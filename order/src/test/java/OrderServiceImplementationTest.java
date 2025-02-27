
import ch.qos.logback.classic.Logger;
import org.example.dto.OrderRequestDto;
import org.example.entity.Order;
import org.example.repository.OrderRepository;
import org.example.service.OrderServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplementationTest {

    @Mock
    private OrderRepository orderRepository = new OrderRepository();

    @InjectMocks
    private OrderServiceImplementation orderService;

    @Mock
    private Logger log;

    private OrderRequestDto orderRequestDto;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(1L);
        orderRequestDto.setQuantity(10);
        orderRequestDto.setProductId(100L);
        order = new Order();
        order.setId(1L);
        order.setQuantity(10);
        order.setProductId(100L);
    }

    @Test
    void allOrders_ordersFound_returnsOrderList() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Order> result = orderService.allOrders();

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
    }

    @Test
    void createOrder_validRequest_returnsCreatedOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(orderRequestDto);

        assertEquals(order, result);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_orderAlreadyExists_throwsAssertionError() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(1L);

        when(orderRepository.findById(1L)).thenReturn(new Order()); // Simulate existing order

        assertThrows(AssertionError.class, () -> orderService.createOrder(orderRequestDto));

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_saveThrowsException_throwsRuntimeExceptionAndLogsError() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(1L);

        RuntimeException exception = new RuntimeException("Save failed");

        when(orderRepository.findById(1L)).thenReturn(null);
        when(orderRepository.save(any(Order.class))).thenThrow(exception);

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> orderService.createOrder(orderRequestDto));
        assertEquals("Save failed", thrownException.getMessage());
    }
}