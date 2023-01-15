package com.fastcampus.gildedrose;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;

class GildedRoseTest {

    @Test
    void updateQuality() {
        CombinationApprovals.verifyAllCombinations(
                this::doUpdateQuality,
                new String[]{"foo", "Aged Brie", "Backstage passes to a TAFKAL80ETC concert", "Sulfuras, Hand of Ragnaros", "Backstage passes to a TAFKAL80ETC concert"},
                new Integer[]{-1, 0, 1, 6, 11},
                new Integer[]{0, 1, 49, 50});
    }

    private String doUpdateQuality(final String name, final int sellIn, final int quality) {
        Item[] items = new Item[]{Item.createItem(name, sellIn, quality)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items[0].toString();
    }
}