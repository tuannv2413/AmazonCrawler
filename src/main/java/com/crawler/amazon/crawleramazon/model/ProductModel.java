package com.crawler.amazon.crawleramazon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductModel {
    private Long id;

    private String asin;

    private String brand;

    private BigDecimal price;

    private String title;

    private Integer rating;

    private Integer ranking;

    private String img;
}
