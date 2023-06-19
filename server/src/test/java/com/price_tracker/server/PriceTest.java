package com.price_tracker.server;

import com.price_tracker.server.controller.PriceController;
import com.price_tracker.server.entity.Price;
import com.price_tracker.server.service.PriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class PriceTest {

  @Mock
  private PriceService priceService;

  private PriceController priceController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    priceController = new PriceController(priceService);
  }

  @Test
  public void testListPrices() {
    // Mock data
    int productId = 1;
    Price price1 = new Price();
    Price price2 = new Price();
    List<Price> prices = Arrays.asList(price1, price2);

    // Mock service method
    when(priceService.getPricesForLast7Days(productId)).thenReturn(prices);

    // Perform GET request
    ResponseEntity<List<Price>> response = priceController.listPrices(productId);

    // Verify service method was called
    verify(priceService, times(1)).getPricesForLast7Days(productId);

    // Perform assertions
    Assertions.assertEquals(HttpStatus.FOUND, response.getStatusCode());
    Assertions.assertEquals(prices, response.getBody());
  }

  @Test
  public void testUpdatePrice() throws IOException {
    // Mock data
    int productId = 1;

    // Perform GET request
    ResponseEntity<?> response = priceController.updatePrice(productId);

    // Verify service method was called
    verify(priceService, times(1)).checkIndividualPrice(productId);

    // Perform assertions
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}