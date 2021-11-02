package com.crawler.amazon.crawleramazon.service;

import com.crawler.amazon.crawleramazon.result.BaseResult;
import com.crawler.amazon.crawleramazon.result.ListDataResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {
    ResponseEntity<BaseResult> insertProduct(List<String> urls);
    ResponseEntity<ListDataResult> getProducts(Integer page, Integer limit, Integer sort);
}
