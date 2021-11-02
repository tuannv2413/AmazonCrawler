package com.crawler.amazon.crawleramazon.service;

import com.crawler.amazon.crawleramazon.entity.Product;

import java.util.List;

public interface CrawlDataProductService {
    List<Product> crawlDetailProduct(List<String> urls);
}
