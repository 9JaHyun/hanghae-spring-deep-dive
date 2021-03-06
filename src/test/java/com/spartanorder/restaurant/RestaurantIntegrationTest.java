package com.spartanorder.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;



@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RestaurantIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private ObjectMapper mapper = new ObjectMapper();

    private final List<RestaurantDto> registeredRestaurants = new ArrayList<>();

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @AfterAll
    public void tearDown() {
        restTemplate.getForObject("/restaurant/deleteAll",
              Void.class);
    }


    @Rollback
    @Nested
    @DisplayName("????????? 3??? ?????? ??? ??????")
    @Order(1)
    class RegisterRestaurants {
        @Test
        @Order(1)
        @DisplayName("?????????1 ??????")
        void test1() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .name("???????????? ?????????")
                  .minOrderPrice(1_000)
                  .deliveryFee(0)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<RestaurantDto> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  RestaurantDto.class);

            // then
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            RestaurantDto restaurantResponse = response.getBody();
            assertNotNull(restaurantResponse);
            assertTrue(restaurantResponse.id > 0);
            assertEquals(restaurantRequest.name, restaurantResponse.name);
            assertEquals(restaurantRequest.minOrderPrice, restaurantResponse.minOrderPrice);
            assertEquals(restaurantRequest.deliveryFee, restaurantResponse.deliveryFee);

            // ????????? ?????? ?????? ???, registeredRestaurants ??? ??????
            registeredRestaurants.add(restaurantResponse);
        }

        @Test
        @Order(2)
        @DisplayName("?????????2 ??????")
        void test2() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(100_000)
                  .deliveryFee(10_000)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<RestaurantDto> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  RestaurantDto.class);

            // then
            assertEquals(HttpStatus.CREATED, response.getStatusCode());

            RestaurantDto restaurantResponse = response.getBody();
            assertNotNull(restaurantResponse);
            assertTrue(restaurantResponse.id > 0);
            assertEquals(restaurantRequest.name, restaurantResponse.name);
            assertEquals(restaurantRequest.minOrderPrice, restaurantResponse.minOrderPrice);
            assertEquals(restaurantRequest.deliveryFee, restaurantResponse.deliveryFee);

            // ????????? ?????? ?????? ???, registeredRestaurants ??? ??????
            registeredRestaurants.add(restaurantResponse);
        }

        @Test
        @Order(3)
        @DisplayName("?????????3 ??????")
        void test3() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(100000)
                  .deliveryFee(10000)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<RestaurantDto> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  RestaurantDto.class);

            // then
            assertEquals(HttpStatus.CREATED, response.getStatusCode());

            RestaurantDto restaurantResponse = response.getBody();
            assertNotNull(restaurantResponse);
            assertTrue(restaurantResponse.id > 0);
            assertEquals(restaurantRequest.name, restaurantResponse.name);
            assertEquals(restaurantRequest.minOrderPrice, restaurantResponse.minOrderPrice);
            assertEquals(restaurantRequest.deliveryFee, restaurantResponse.deliveryFee);

            // ????????? ?????? ?????? ???, registeredRestaurants ??? ??????
            registeredRestaurants.add(restaurantResponse);
        }

        @Test
        @Order(4)
        @DisplayName("????????? ?????? ????????? ??????")
        void test4() {
            // when
            ResponseEntity<RestaurantDto[]> response = restTemplate.getForEntity(
                  "/restaurants",
                  RestaurantDto[].class
            );

            System.out.println(Arrays.toString(response.getBody()));

            // then
            assertEquals(HttpStatus.OK, response.getStatusCode());
            RestaurantDto[] responseRestaurants = response.getBody();
            assertNotNull(responseRestaurants);
            assertEquals(registeredRestaurants.size(), responseRestaurants.length);
            for (RestaurantDto responseRestaurant : responseRestaurants) {
                RestaurantDto registerRestaurant = registeredRestaurants.stream()
                      .filter(restaurant -> responseRestaurant.getId().equals(restaurant.getId()))
                      .findAny()
                      .orElse(null);

                assertNotNull(registerRestaurant);
                assertEquals(registerRestaurant.getName(), responseRestaurant.getName());
                assertEquals(registerRestaurant.getDeliveryFee(), responseRestaurant.getDeliveryFee());
                assertEquals(registerRestaurant.getMinOrderPrice(), responseRestaurant.getMinOrderPrice());
            }
        }
    }

    @Rollback
    @Order(2)
    @Nested
    @DisplayName("???????????? ??????")
    class MinOrderPrice {
        @Test
        @DisplayName("1,000??? ?????? ??????")
        void test1() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(500)
                  .deliveryFee(1000)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  String.class);

            // then
            assertTrue(
                  response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        @Test
        @DisplayName("100,000??? ?????? ??????")
        void test2() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(100100)
                  .deliveryFee(1000)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  String.class);

            // then
            assertTrue(
                  response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        @Test
        @DisplayName("100??? ????????? ?????? ??? ??? ??????")
        void test3() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(2220)
                  .deliveryFee(1000)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  String.class);

            // then
            assertTrue(
                  response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Rollback
    @Order(3)
    @Nested
    @DisplayName("?????? ?????????")
    class DeliveryFee {
        @Test
        @DisplayName("0??? ?????? ??????")
        void test1() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(5000)
                  .deliveryFee(-500)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  String.class);

            // then
            assertTrue(
                  response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        @Test
        @DisplayName("10,000??? ?????? ??????")
        void test2() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(5000)
                  .deliveryFee(20000)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  String.class);

            // then
            assertTrue(
                  response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        @Test
        @DisplayName("500??? ????????? ?????? ??? ??? ??????")
        void test3() throws JsonProcessingException {
            // given
            RestaurantDto restaurantRequest = RestaurantDto.builder()
                  .id(null)
                  .name("???????????? ?????????")
                  .minOrderPrice(5000)
                  .deliveryFee(2200)
                  .build();

            String requestBody = mapper.writeValueAsString(restaurantRequest);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = restTemplate.postForEntity(
                  "/restaurant/register",
                  request,
                  String.class);

            // then
            assertTrue(
                  response.getStatusCode() == HttpStatus.BAD_REQUEST
                        || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @ToString
    @Getter
    @Setter
    @Builder
    static class RestaurantDto {
        private Long id;
        private String name;
        private int minOrderPrice;
        private int deliveryFee;
    }
}