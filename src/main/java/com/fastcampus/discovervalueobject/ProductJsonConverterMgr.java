package com.fastcampus.discovervalueobject;

import lombok.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import java.util.HashMap;

public class ProductJsonConverterMgr {
    private static final String _PRODUCT_DETAIL_URL_ = "https://wwww.show.com/product?prd=";
    private static final int ORDER_IMG_W = 1200;
    private static final int ORDER_IMG_H = 900;
    private static final int THUMB_IMG_W = 400;
    private static final int THUMB_IMG_H = 300;

    public JSONObject getJsonObject(HashMap<String, String> prd, String type, Cookie[] cookieArr) {
        JSONObject data = new JSONObject();

        data.put("nckNm", prd.get("nckNm"));
        data.put("prdNo", prd.get("prdNo"));
        data.put("prdDtlUrl", _PRODUCT_DETAIL_URL_ + prd.get("prdNo"));

        String img = "";
        String minorYn = getMinorYn(cookieArr);
        String directYn = getDirectYn(cookieArr);
        if (prd.get("prdImg") != null) {
            if ("MYORDERLIST".equals(type)) {
                img = ImgUtil.getImageUrl(prd, ORDER_IMG_W, ORDER_IMG_H, minorYn);
            } else {
                img = ImgUtil.getImageUrl(prd, THUMB_IMG_W, THUMB_IMG_H, minorYn);
            }

        }
        data.put("img", ShopStringUtil.removeDomainProtocal(img));

        data.put("icon", DisplayUtil.getIcons(prd));
        data.put("optPrcText", DisplayUtil.getOptionPriceText(prd.get("optYn")));
        data.put("delInfo", setDelInfo(prd));

        final long originalPrice = CUtil.convertToLong(prd.get("selPrc"));
        final long discountedPrice = CUtil.convertToLong(prd.get("finalDscPrc"));
        final ProductPrice productPrice = new ProductPrice(originalPrice, discountedPrice);
        final long finalPrice = productPrice.finalPrice();
        if (productPrice.isDiscounted()) {
            data.put("finalPrc", CUtil.getCommaString(finalPrice));
            data.put("selPrc", CUtil.getCommaString(originalPrice));
        } else {
            data.put("finalPrc", CUtil.getCommaString(finalPrice));
        }


        // 미성년자 구매가능 여부
        String minorSelCnYn = prd.get("minorSelCnYn");
        if (!"N".equals(minorSelCnYn)) {
            minorSelCnYn = "Y";
        }
        data.put("minorSelCnYn", minorSelCnYn);

        // 할인율
        final int discountRate = productPrice.discountRate();
        if (discountRate >= 1) {
            data.put("discountRate", discountRate + "");
        } else {
            data.put("discountRate", "");
        }

        int nReviewCount = getReviewCount(prd);
        if (nReviewCount > 0) {
            data.put("reviewCount", CUtil.getCommaString(nReviewCount > 999999 ? 999999 : nReviewCount));
        }


        int buyCnt = (int) CUtil.convertToLong(prd.get("buyCnt"));
        String extraText = getExtraText(prd);
        String buySatisfy = prd.get("stsf");
        String buySatisfyGrd = getBuyStisfyGrd(buySatisfy, nReviewCount);
        if (!"".equals(buySatisfyGrd)) {
            data.put("buySatisfy", buySatisfy);
            data.put("buySatisfyGrd", buySatisfyGrd);
        }

        if ("MY_ORDER_PRD".equals(extraText)) {
            data.put("myOrderTxt", buyCnt + "건 구매");
        }

        data.put("logBody", LogData.builder()
                .contentNo(CUtil.convertToLong(prd.get("prdNo")))
                .contentName(prd.get("prdNm"))
                .contentType("PROD")
                .productPrice(finalPrice + "")
                .lastDiscountPrice(finalPrice + "")
                .directYn(directYn)
                .build().toJsonStrng());
        return data;
    }

    private String getDirectYn(final Cookie[] cookieArr) {
        throw new UnsupportedOperationException("unimplemented");
    }

    private String getMinorYn(final Cookie[] cookieArr) {
        throw new UnsupportedOperationException("unimplemented");
    }

    private String getExtraText(final HashMap prd) {
        throw new UnsupportedOperationException("unimplemented");
    }

    private String getBuyStisfyGrd(final String buySatisfy, final int nReviewCount) {
        throw new UnsupportedOperationException("unimplemented");
    }

    private int getReviewCount(final HashMap prd) {
        throw new UnsupportedOperationException("unimplemented");
    }

    private JSONObject setDelInfo(final HashMap prd) {
        throw new UnsupportedOperationException("unimplemented");
    }

    private static class ImgUtil {
        static String getImageUrl(final HashMap prd, final int orderImgW, final int orderImgH, final String minorYn) {
            throw new UnsupportedOperationException("unimplemented");
        }
    }

    private static class ShopStringUtil {
        static String removeDomainProtocal(final String img) {
            throw new UnsupportedOperationException("unimplemented");
        }
    }

    public static class DisplayUtil {
        static JSONArray getIcons(final HashMap prd) {
            throw new UnsupportedOperationException("unimplemented");
        }

        public static Object getOptionPriceText(final String optYn) {
            throw new UnsupportedOperationException("unimplemented");
        }

        public static int getDiscountRate(final long selPrc, final long finalDscPrc) {
            return (int) ((selPrc - finalDscPrc) * 100 / selPrc);
        }
    }

    private static class CUtil {
        public static long convertToLong(final String selPrc) {
            throw new UnsupportedOperationException("unimplemented");
        }

        public static String getCommaString(final long finalPrc) {
            throw new UnsupportedOperationException("unimplemented");
        }
    }

    @Builder
    private static class LogData {
        private long contentNo;
        private String contentName;
        private String contentType;
        private String productPrice;
        private String lastDiscountPrice;
        private String directYn;

        String toJsonStrng() {
            throw new UnsupportedOperationException("unimplemented");
        }
    }
}