package com.price_tracker.server;

import com.price_tracker.server.entity.Watchlist;
import com.price_tracker.server.repository.WatchlistRepo;
import com.price_tracker.server.service.WatchlistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class WatchlistTest {

  private WatchlistService watchlistService;
  private WatchlistRepo watchlistRepo;

  @BeforeEach
  public void setup() {
    watchlistRepo = Mockito.mock(WatchlistRepo.class);
    watchlistService = new WatchlistService(watchlistRepo, null, null);
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
}
