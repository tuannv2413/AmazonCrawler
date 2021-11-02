package com.crawler.amazon.crawleramazon.service.impl;

import com.crawler.amazon.crawleramazon.common.Common;
import com.crawler.amazon.crawleramazon.service.CrawlLinkDetailProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CrawlLinkDetailProductServiceImpl implements CrawlLinkDetailProductService {
    private static final Logger logger = LoggerFactory.getLogger(CrawlLinkDetailProductService.class);

    /**
     * Crawl url for Product
     * @param listUrl
     * @return
     */
    @Override
    public List<String> crawlLinkDetail(List<String> listUrl) {
        logger.info("Start Crawl Url Product Detail");
        try {
            HashMap<String, String> cookies = Common.getCookies();
            List<String> urls = new ArrayList<>();
            for (String url : listUrl) {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 Edg/94.0.992.47")
                        .cookies(cookies)
                        .timeout(5000)
                        .get();
                /**
                 * Get List url for href in <a class="a-link-normal s-no-outline"></a>
                 *
                 */
                Elements classProducts = doc.getElementsByClass("a-link-normal s-no-outline");
                for (Element classProduct : classProducts) {
                    urls.add("https://www.amazon.com/" + classProduct.attr("href").toString());
                }
            }
            logger.info("Total Url Product Detail: " + urls.size());
            logger.info("End Crawl Url Product Detail");
            return urls;
        } catch (Exception e) {
            logger.error("Error Crawl Url Product Detail");
            return null;
        }
    }
}
