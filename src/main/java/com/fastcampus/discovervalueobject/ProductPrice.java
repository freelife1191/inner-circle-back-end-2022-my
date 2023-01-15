package com.fastcampus.discovervalueobject;

record ProductPrice(long originalPrice, long discountedPrice) {
    boolean isDiscounted() {
        return (discountedPrice > 0) && (discountedPrice < originalPrice);
    }

    long finalPrice() {
        return isDiscounted() ? discountedPrice : originalPrice;
    }

    int discountRate() {
        return (int) ((originalPrice - finalPrice()) * 100 / originalPrice);
    }
}