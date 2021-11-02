package com.crawler.amazon.crawleramazon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    private Long id;

    private String asin;

    private String brand;

    private BigDecimal price;

    private String title;

    private BigDecimal rating;

    private Integer ranking;

    private String img;
}
