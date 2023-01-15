package com.fastcampus.discovervalueobject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductPriceTest {

    @Test
    void isDiscounted() {
        assertAll(
                () -> assertIsDiscounted(1000L, 0L, false),
                () -> assertIsDiscounted(1000L, 900L, true),
                () -> assertIsDiscounted(1000L, 1000L, false),
                () -> assertIsDiscounted(1000L, 1100L, false)
        );
    }

    @Test
    void finalPrice() {
        assertAll(
                () -> assertFinalPrice(1000L, 0L, 1000L),
                () -> assertFinalPrice(1000L, 900L, 900L),
                () -> assertFinalPrice(1000L, 1000L, 1000L),
                () -> assertFinalPrice(1000L, 1100L, 1000L)
        );
    }

    @Test
    void discountRate() {
        assertAll(
                () -> assertDiscountRate(1000L, 0L, 0),
                () -> assertDiscountRate(1000L, 900L, 10),
                () -> assertDiscountRate(1000L, 1000L, 0),
                () -> assertDiscountRate(1000L, 1100L, 0)
        );
    }

    private void assertIsDiscounted(final long originalPrice, final long discountedPrice, final boolean expected) {
        assertThat(new ProductPrice(originalPrice, discountedPrice).isDiscounted()).isEqualTo(expected);
    }

    private void assertFinalPrice(final long originalPrice, final long discountedPrice, final long expected) {
        assertThat(new ProductPrice(originalPrice, discountedPrice).finalPrice()).isEqualTo(expected);
    }

    private void assertDiscountRate(final long originalPrice, final long discountedPrice, final int expected) {
        assertThat(new ProductPrice(originalPrice, discountedPrice).discountRate()).isEqualTo(expected);
    }

}