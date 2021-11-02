package com.crawler.amazon.crawleramazon.service.impl;


import com.crawler.amazon.crawleramazon.dto.ProductDTO;
import com.crawler.amazon.crawleramazon.entity.Product;
import com.crawler.amazon.crawleramazon.repository.ProductsRepository;
import com.crawler.amazon.crawleramazon.result.BaseResult;
import com.crawler.amazon.crawleramazon.result.ListDataResult;
import com.crawler.amazon.crawleramazon.service.CrawlDataProductService;
import com.crawler.amazon.crawleramazon.service.CrawlLinkDetailProductService;
import com.crawler.amazon.crawleramazon.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(CrawlLinkDetailProductService.class);

    private CrawlDataProductService crawlDataProductService;
    private CrawlLinkDetailProductService crawlLinkDetailProductService;
    private ProductsRepository productsRepository;

    @Autowired
    public ProductServiceImpl(CrawlDataProductService crawlDataProductService, CrawlLinkDetailProductService crawlLinkDetailProductService, ProductsRepository productsRepository) {
        this.crawlDataProductService = crawlDataProductService;
        this.crawlLinkDetailProductService = crawlLinkDetailProductService;
        this.productsRepository = productsRepository;
    }

    /**
     * Service Insert Product use BatchInsert
     *
     * @param urls
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<BaseResult> insertProduct(List<String> urls) {
        try {
            logger.info("Start Process Insert");
            List<String> urlsProduct = crawlLinkDetailProductService.crawlLinkDetail(urls);
            List<Product> productModels = crawlDataProductService.crawlDetailProduct(urlsProduct);
            productsRepository.saveAll(productModels);
            BaseResult result = BaseResult.builder()
                    .message("Insert SuccessFully")
                    .build();
            logger.info("End Process Insert");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error Insert");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(BaseResult.builder().message("Insert Fall").build());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ListDataResult> getProducts(Integer page, Integer limit, Integer sort) {
        ListDataResult listDataResult = new ListDataResult();
        List<ProductDTO> productDTOS = new ArrayList<>();
        Pageable pageable;
        if (sort == 0) {
            pageable = PageRequest.of(page, limit);
        } else {
            pageable = PageRequest.of(page, limit, Sort.by("ranking").ascending());
        }
        Page<Product> products = productsRepository.findAll(pageable);
        for (Product product : products) {
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .asin(product.getAsin())
                    .brand(product.getBrand())
                    .price(product.getPrice())
                    .title(product.getTitle())
                    .img(product.getImg())
                    .ranking(product.getRanking())
                    .rating(product.getRating())
                    .build();
            productDTOS.add(productDTO);
        }
        listDataResult.setMessage("List Products");
        listDataResult.setData(productDTOS);
        listDataResult.setTotalPage(products.getTotalPages());
        listDataResult.setTotalItems((int) products.getTotalElements());
        return ResponseEntity.ok(listDataResult);
    }
}
