package com.price_tracker.server;

import com.price_tracker.server.entity.Price;
import com.price_tracker.server.repository.PriceRepo;
import com.price_tracker.server.service.PriceService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PriceTest {

  @Autowired
  private PriceRepo priceRepo;

  @Autowired
  private PriceService priceService;

  @Test
  public void testGetPricesForLast7Days() {
    // Retrieve prices for the last 7 days
    List<Price> prices = priceService.getPricesForLast7Days(1);

    // Perform assertions
    Assertions.assertEquals(1, prices.size());
  }

  @Test
  public void testCheckIndividualPrice() {
    // Check the individual price
    Assertions.assertDoesNotThrow(() -> priceService.checkIndividualPrice(1));
  }

  @Test
  public void testFindByProductIdAndDateBetweenOrderByDateDesc() {
    // Find prices by product ID and date range
    LocalDate startDate = LocalDate.now().minusDays(7);
    LocalDate endDate = LocalDate.now();
    List<Price> prices = priceRepo.findByProductIdAndDateBetweenOrderByDateDesc(1, startDate, endDate);

    // Perform assertions
    Assertions.assertEquals(1, prices.size());
  }

  @Test
  public void testFindTopByProductIdOrderByDateDesc() {
    // Find the latest price by product ID
    Price price = priceRepo.findTopByProductIdOrderByDateDesc(1);

    // Perform assertions
    Assertions.assertNotNull(price);
  }
}
