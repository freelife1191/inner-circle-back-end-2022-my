package com.fastcampus.gildedrose;

public class BackstagePass extends Item {
    public BackstagePass(final int sellIn, final int quality) {
        super("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    }

    @Override
    void doUpdateQuality() {
        if (quality < 50) {
            quality = quality + 1;

            if (sellIn < 11) {
                if (quality < 50) {
                    quality = quality + 1;
                }
            }

            if (sellIn < 6) {
                if (quality < 50) {
                    quality = quality + 1;
                }
            }
        }

        sellIn = sellIn - 1;

        if (sellIn < 0) {
            quality = 0;
        }
    }
}
