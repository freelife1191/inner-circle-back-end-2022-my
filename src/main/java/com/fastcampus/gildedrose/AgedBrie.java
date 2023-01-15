package com.fastcampus.gildedrose;

public class AgedBrie extends Item {
    public AgedBrie(final int sellIn, final int quality) {
        super("Aged Brie", sellIn, quality);
    }

    @Override
    void doUpdateQuality() {
        if (quality < 50) {
            quality = quality + 1;
        }

        sellIn = sellIn - 1;

        if (sellIn < 0) {
            if (quality < 50) {
                quality = quality + 1;
            }
        }
    }
}
