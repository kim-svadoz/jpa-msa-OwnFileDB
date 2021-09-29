package sunghyun.server.fileDBWork.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sunghyun.server.fileDBWork.domain.Order;
import sunghyun.server.fileDBWork.domain.dto.OrderItemRequestDto;
import sunghyun.server.fileDBWork.domain.dto.OrderRequestDto;
import sunghyun.server.fileDBWork.repository.OrderRepository;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ModelMapper modelMapper;

    @Test
    public void 주문_생성_테스트() throws Exception {
        //given
        List<OrderItemRequestDto> orderItemList = new ArrayList<>();

        OrderItemRequestDto orderItemRequestDto = new OrderItemRequestDto();
        orderItemRequestDto.setId((long) 1);
        orderItemRequestDto.setProdcutId((long) 2);

        orderItemList.add(orderItemRequestDto);

        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setId((long) 1);
        orderRequestDto.setOrderItemList(orderItemList);

        //when
        Order order = modelMapper.map(orderRequestDto, Order.class);
        orderRepository.save(order);

        //then
        assertEquals((long) 1, (long) order.getId());
    }
}