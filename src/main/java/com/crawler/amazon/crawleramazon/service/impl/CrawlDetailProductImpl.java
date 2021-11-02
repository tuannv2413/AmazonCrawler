package com.crawler.amazon.crawleramazon.service.impl;

import com.crawler.amazon.crawleramazon.common.Common;
import com.crawler.amazon.crawleramazon.entity.Product;
import com.crawler.amazon.crawleramazon.service.CrawlDataProductService;
import com.crawler.amazon.crawleramazon.service.CrawlLinkDetailProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CrawlDetailProductImpl implements CrawlDataProductService {
    private static final Logger logger = LoggerFactory.getLogger(CrawlLinkDetailProductService.class);

    /**
     * Crawl Data of Product Detail
     * @param urls
     * @return
     */
    @Override
    public List<Product> crawlDetailProduct(List<String> urls) {
        logger.info("Start Crawl Detail Product");
        HashMap<String, String> cookies = Common.getCookies();
        List<Product> listProducts = new ArrayList<>();
        int index = 0;
        try {
            for (String url : urls) {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 Edg/94.0.992.47")
                        .cookies(cookies)
                        .timeout(5000)
                        .get();
                String image, brand, title, asin;
                Integer ranking = 0;
                BigDecimal rating, price;
                try {
                    image = doc.getElementById("imgTagWrapperId").getElementsByTag("img").attr("src");
                } catch (Exception e) {
                    image = "";
                }
                try {
                    String textBrand = doc.getElementById("bylineInfo").html();
                    brand = textBrand.substring(7,textBrand.length());
                } catch (Exception e) {
                    brand = "";
                }
                try {
                    title = doc.getElementById("productTitle").html();
                } catch (Exception e) {
                    title = "";
                }
                try {
                    String textRating = doc.getElementsByClass("a-popover-trigger a-declarative").get(0).getElementsByClass("a-icon-alt").html();
                    Double convertRatingToDouble = Double.valueOf(textRating);
                    rating = BigDecimal.valueOf(convertRatingToDouble);
                } catch (Exception e) {
                    rating = new BigDecimal("0.0");
                }
                try {
                    String textPrice = doc.getElementsByClass("a-price a-text-price a-size-medium apexPriceToPay").get(0).getElementsByClass("a-offscreen").html();
                    Double convertPriceToDouble = Double.valueOf(textPrice.substring(1,6));
                    price = BigDecimal.valueOf(convertPriceToDouble);
                } catch (Exception e) {
                    price = new BigDecimal("0.0");
                }
                try {
                    asin = doc.getElementsByClass("a-unordered-list a-nostyle a-vertical a-spacing-none detail-bullet-list").get(0).getElementsByTag("li").get(4).getElementsByTag("span").get(2).html();
                } catch (Exception e) {
                    asin = "";
                }
                try {
                    String textRanking = doc.getElementsByClass("a-unordered-list a-nostyle a-vertical a-spacing-none detail-bullet-list").get(1).getElementsByTag("span").get(0).text();
                    int indexStart = textRanking.indexOf("#");
                    int indexEnd = textRanking.indexOf("in");
                    ranking = Integer.valueOf(textRanking.substring(indexStart+1, indexEnd-1).replace(",",""));
                } catch (Exception e) {
                    ranking = 0;
                }
                Product product = Product.builder()
                        .asin(asin)
                        .brand(brand)
                        .price(price)
                        .title(title)
                        .rating(rating)
                        .ranking(ranking)
                        .img(image)
                        .build();
                listProducts.add(product);
                index++;
            }
            logger.info("Total Product Detail: "+index);
            logger.info("End Crawl Product Detail");
            return listProducts;
        } catch (Exception e) {
            logger.error("Error Crawl Product Detail");
            return listProducts;
        }
    }

}
