package com.crawler.amazon.crawleramazon.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "product")
public class Product implements Serializable {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", allocationSize = 1)
    @Column(name = "id")
    @Id
    private Long id;

    @Column(name = "asin")
    private String asin;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "img")
    private String img;
}
