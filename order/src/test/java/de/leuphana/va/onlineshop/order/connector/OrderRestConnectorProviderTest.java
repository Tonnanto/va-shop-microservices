package de.leuphana.va.onlineshop.order.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.leuphana.va.onlineshop.order.component.OrderApplication;
import de.leuphana.va.onlineshop.order.component.structure.OrderPosition;
import de.leuphana.va.onlineshop.order.component.structure.Orderr;
import de.leuphana.va.onlineshop.order.component.structure.requests.OrderCreateRequest;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrderWriteResponse;
import de.leuphana.va.onlineshop.order.component.structure.responses.OrdersGetResponse;
import de.leuphana.va.onlineshop.order.configuration.OrderTestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = OrderApplication.class)
@Import(OrderTestConfiguration.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class OrderRestConnectorProviderTest {

    public static Set<Integer> orderIds;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Order(0)
    @Test
    void canOrderBeCreated() throws Exception {
        OrderPosition orderPosition1 = new OrderPosition();
        orderPosition1.setArticleId(1);
        orderPosition1.setArticleQuantity(3);

        OrderPosition orderPosition2 = new OrderPosition();
        orderPosition2.setArticleId(1);
        orderPosition2.setArticleQuantity(2);

        OrderPosition orderPosition3 = new OrderPosition();
        orderPosition3.setArticleId(3);
        orderPosition3.setArticleQuantity(2);

        Orderr newOrder = new Orderr();
        newOrder.setCustomerId(1234);
        newOrder.addOrderPosition(orderPosition1);
        newOrder.addOrderPosition(orderPosition2);
        newOrder.addOrderPosition(orderPosition3);

        OrderCreateRequest requestBody = new OrderCreateRequest(newOrder);

        String requestJson = mapToJson(requestBody);

        String uri = "/api/v1/order/create";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson)
                        .characterEncoding("utf-8"))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        OrderWriteResponse orderWriteResponse = mapFromJson(content, OrderWriteResponse.class);

        boolean success = orderWriteResponse.success();

        Assertions.assertTrue(success);
    }

    @Test
    @Order(1)
    void canOrdersForCustomerBeGotten() throws Exception {

        String uri = "/api/v1/order/customer/1234";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        OrdersGetResponse ordersGetResponse = mapFromJson(content, OrdersGetResponse.class);
        Set<Orderr> orders = ordersGetResponse.orders();

        Assertions.assertNotNull(orders);
        Assertions.assertTrue(orders.size() > 0);

        orderIds = orders.stream().map(Orderr::getOrderId).collect(Collectors.toSet());
    }

    @Test
    @Order(2)
    void canOrderBeRemoved() throws Exception {
        for (int orderId : orderIds) {

            String uri = "/api/v1/order/" + orderId;
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            int status = mvcResult.getResponse().getStatus();
            Assertions.assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            OrderWriteResponse orderWriteResponse = mapFromJson(content, OrderWriteResponse.class);
            boolean success = orderWriteResponse.success();

            Assertions.assertTrue(success);
        }
    }
}
