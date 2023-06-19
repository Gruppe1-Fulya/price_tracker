package com.price_tracker.server;

import com.price_tracker.server.controller.WatchlistController;
import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.repository.ProductRepo;
import com.price_tracker.server.repository.WatchlistRepo;
import com.price_tracker.server.service.WatchlistService;
import com.price_tracker.server.service.UserService;
import com.price_tracker.server.service.ProductService;
import com.price_tracker.server.entity.User;
import com.price_tracker.server.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WatchlistTest {

  private WatchlistService watchlistService;
  private WatchlistRepo watchlistRepo;
  private ProductRepo productRepo;
  private UserService userService;
  private ProductService productService;
  private WatchlistController watchlistController;

  @BeforeEach
  public void setup() {
    watchlistRepo = Mockito.mock(WatchlistRepo.class);
    productRepo = Mockito.mock(ProductRepo.class);
    watchlistRepo = Mockito.mock(WatchlistRepo.class);
    productService = Mockito.mock(ProductService.class);
    userService = Mockito.mock(UserService.class);

    watchlistService = new WatchlistService(watchlistRepo, productRepo, null);
    watchlistController = new WatchlistController(watchlistService, userService, productService);
  }

  @Test
  public void testDeleteWatchlist() {
    Watchlist watchlist = new Watchlist();
    watchlistService.deleteWatchlist(watchlist);

    Mockito.verify(watchlistRepo, Mockito.times(1)).delete(watchlist);
  }

  @Test
  public void testGetWatchlistsForUser() {
    int userId = 1;
    List<Watchlist> watchlists = new ArrayList<>();
    Mockito.when(watchlistRepo.findByUserId(userId)).thenReturn(watchlists);

    List<Watchlist> result = watchlistService.getWatchlistsForUser(userId);

    Assertions.assertEquals(watchlists, result);
  }

  @Test
  public void testFindById() {
    int id = 1;
    Watchlist watchlist = new Watchlist();
    Mockito.when(watchlistRepo.findById(id)).thenReturn(watchlist);

    Watchlist result = watchlistService.findById(id);

    Assertions.assertEquals(watchlist, result);
  }

  @Test
  public void testCreateWatchlist() throws IOException {
    // Test data
    String email = "bateman@gmail.com";
    String name = "Puma ESS Cap Siyah Şapka";
    String imageUrl = "https://cdn.dsmcdn.com/ty315/product/media/images/20220201/23/40833697/15920472/2/2_org_zoom.jpg";
    String productUrl = "https://www.trendyol.com/puma/ess-cap-siyah-sapka-p-3806547";

    // Mock dependencies
    User user = new User();
    Mockito.when(userService.findByEmail(email)).thenReturn(user);

    Product product = new Product(name, imageUrl, productUrl);
    Mockito.when(productService.findByURL(productUrl)).thenReturn(null);
    Mockito.when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(product);

    Watchlist expectedWatchlist = new Watchlist(user, product);
    Mockito.when(watchlistRepo.save(expectedWatchlist)).thenReturn(expectedWatchlist);

    // Perform the test
    Mockito.when(watchlistRepo.save(Mockito.any(Watchlist.class))).thenReturn(expectedWatchlist);
    ResponseEntity<Watchlist> response = watchlistController.createWatchlistForUser(email, name, productUrl, imageUrl);
    // Assertions
    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Assertions.assertEquals(expectedWatchlist, response.getBody());
  }
}
