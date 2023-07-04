package com.example.demo.service;
import com.example.demo.entity.Stock;

import java.util.List;

public interface StockService {
    Stock createStock(Stock stock);
    List<Stock> getStocks();
    Stock getStockById(Integer id);
    Stock updateStock(Integer id, Stock stock);
    void deleteStock(Integer id);
}