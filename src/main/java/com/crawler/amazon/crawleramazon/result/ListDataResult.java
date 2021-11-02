package com.crawler.amazon.crawleramazon.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListDataResult extends BaseResult{
    private Object data;
    private int totalPage;
    private int totalItems;
}
