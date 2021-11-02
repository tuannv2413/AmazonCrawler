package com.crawler.amazon.crawleramazon.controller;

import com.crawler.amazon.crawleramazon.result.BaseResult;
import com.crawler.amazon.crawleramazon.result.ListDataResult;
import com.crawler.amazon.crawleramazon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/crawler")
public class CrawlToolController {

    private ProductService productService;

    @Autowired
    public CrawlToolController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<ListDataResult> getProducts(@RequestParam("page") Optional<Integer> page,
                                                      @RequestParam("limit") Optional<Integer> limit,
                                                      @RequestParam("sort") Optional<Integer> sort) {
        return productService.getProducts(page.orElse(0), limit.orElse(48), sort.orElse(0));
    }

    @PostMapping()
    public ResponseEntity<BaseResult> insertUrl(@RequestBody List<String> urls) {
        return productService.insertProduct(urls);
    }
}
