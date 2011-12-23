package com.fb.restbucks;

import com.fb.restbucks.domain.*;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.fb.restbucks.config.DispatcherConfig.messageConverters;
import static org.junit.Assert.assertEquals;

/**
 * @author oxladed
 */
public class OrderResourceIT {

    @Test
    public void postOrder() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(messageConverters());

        HttpEntity<Order> requestEntity = new HttpEntity<Order>(order(), headers());
        ResponseEntity<Resource> response = restTemplate.exchange("http://localhost:8080/restbucks/orders/", HttpMethod.POST, requestEntity, Resource.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private MultiValueMap<String, String> headers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private Order order() {
        Order order = new Order();
        order.setId("34390340");
        order.setStatus(OrderStatus.PENDING);
        Item item = new Item();
        item.setMilk(Milk.SKIM);
        item.setName("americano");
        item.setQuantity(1);
        item.setSize(Size.SMALL);
        order.setItems(Arrays.asList(item));
        return order;
    }
}
