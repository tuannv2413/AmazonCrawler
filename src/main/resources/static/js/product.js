let page = 0;
let limit = 12;
let sort = 0;

init(page,limit,sort);

function init(page, limit, sort) {
    $.ajax({
        type: "GET",
        url: `/api/crawler?page=${page}&limit=${limit}&sort=${sort}`,
        contentType: 'application/json; charset=utf-8',
        dataTypes: 'json',
        success: (function (result) {
            let attribute = ``;
            for (const r of result.data) {
                let convert_price_to_string = r.price +"";
                let index = convert_price_to_string.indexOf(".")
                let price = convert_price_to_string.substring(0,index);
                let price_decimal = convert_price_to_string.substring(index+1, r.price.length);
                attribute += htmlProduct(r.img, r.brand, r.title, price, price_decimal, r.rating, r.asin, r.ranking);
            }
            $("#products").html(attribute)
            // pagination(page,result.totalPage);
            servicePagination(page,result.totalPage);
        }),
        error: (function (error) {
            console.log(error);
        })
    })
}

function htmlProduct(image, brand, title, price, price_decimal, rating, asin, ranking) {
    let product = `
    <div class="col-3 d-frame-product">
        <div class="d-product">
            <div class="d-image">
                <img class="s-image" src="${image}"/>
            </div>
            <div class="d-content">
                <div class="d-brand-title">
                    <div class="d-brand">
                        <h5 class="h-brand">
                            <span class="s-brand">${brand}</span>
                        </h5>
                    </div>
                    <div class="d-title">
                        <h5 class="h-title">
                            <span class="s-title">
                                ${title}
                            </span>
                        </h5>
                    </div>
                </div>
                <div class="d-rating-price">
                    <div class="d-rating" style="--rating: ${rating};"
                         aria-label="Rating of this product is 2 out of 5."></div>
                    <div class="d-price">
                        <span aria-hidden="true">
                            <span class="s-price-symbol">$</span>
                            <span class="s-price-whole">${price}</span>
                            <span class="s-price-fraction">${price_decimal}</span></span>
                        </span>
                    </div>
                    <div class="d-asin-ranking">
                        <div class="d-ranking">
                            <span style="font-weight: 600;margin-right: 10px;">Ranking: </span>
                            <span> ${ranking}</span>
                        </div>
                        <div class="d-asin">
                            <span style="font-weight: 600;margin-right: 10px;">Asin: </span>
                            <span> ${asin}</span>
                        </div>
                    </div>
                </div>
            </div>
         </div>
    </div>
    `;
    return product;
}

function sortRanking() {
    if ($("#ranking").is(":checked")) {
        init(page,limit,1);
    }else {
        init(page,limit,0);
    }
}

function clearSort() {
    $("#ranking").prop('checked', false);
    init(page,limit,0);
}

function servicePagination(currentPage, totalPage) {
    page = currentPage;
    let start = currentPage;
    let checkPageStart = 0;
    let checkPageEnd = 0;
    if (currentPage === 0){
        checkPageStart = 1;
    }
    if (currentPage === totalPage -1){
        checkPageEnd = 2;
    }
    if (totalPage > 10 && currentPage + 1 <= totalPage - 10) {
        if (currentPage == 0) {
            start = currentPage + 1;
        }
        pagination(start,totalPage,currentPage, 1, checkPageStart, checkPageEnd);
    } else if (totalPage > 10 && currentPage + 1 > totalPage - 10) {
        pagination(totalPage-10,totalPage,currentPage, 2, checkPageStart, checkPageEnd);
    } else if (totalPage < 10) {
        pagination(1,totalPage,currentPage, 3, checkPageStart, checkPageEnd);
    }
}

function getPagination(start, currentPage,end) {
    let attribute = ``;
    let total = end < (start + 10) ? end : start + 10;
    page = currentPage;
    for (let j = start - 1; j < total; j++) {
        if (j === currentPage) {
            attribute += `<li><a id="active${j}" onclick="init(${j},limit,sort)" class="active">${j + 1}</a></li>`;
        } else {
            attribute += `<li><a id="active${j}" onclick="init(${j},limit,sort)">${j + 1}</a></li>`;
        }
    }
    return attribute;
}

function checkPageStart(pageStart) {
    let attribute = ``;

    if (pageStart == 1) {
        attribute += `<li><a id="previous" onclick="init(0,limit,sort)"><<</a></li>`
    }
    if (pageStart == 0) {
        attribute += `<li><a id="previous" onclick="init(page-1,limit,sort)"><<</a></li>`
    }
    return attribute;
}

function checkPageEnd(pageEnd, end) {
    let attribute = ``;

    if (pageEnd == 2) {
        attribute += `<li><a id="next" onclick="init(${end-1},limit,sort)">>></a></li>`
    }
    if (pageEnd == 0) {
        attribute += `<li><a id="next" onclick="init(page+1,limit,sort)">>></a></li>`
    }
    return attribute;
}

function pagination(start, end, currentPage, state, checkPageStart, checkPageEnd) {
    let attribute = this.checkPageStart(checkPageStart)

    if (state === 1) {
        attribute += getPagination(start,currentPage,end);
        attribute += `<li><a>...</a></li>
                        <li><a id="next" onclick="init(${end - 1},limit,sort)">${end}</a></li>`;
    }
    if (state === 2) {
        attribute += `<li><a id="next" onclick="init(0,limit,sort)">1</a></li>
                        <li><a>...</a></li>`;
        attribute += getPagination(start,currentPage,end);
    }
    if (state === 3) {
        attribute += getPagination(start,currentPage,end);
    }

    attribute += this.checkPageEnd(checkPageEnd, end);

    $("#pagination").html(attribute);
}