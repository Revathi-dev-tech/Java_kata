package com.gildedrose;

import java.util.Arrays;

class GildedRose {

    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String CONJURED = "Conjured Mana Cake";
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items)
            .forEach(item -> {
                // Handle "Sulfuras" (never changes)
                switch (item.name) {
                    case SULFURAS_HAND_OF_RAGNAROS:
                        break;
                    // Handle "Aged Brie"
                    case AGED_BRIE:
                        if (item.quality < 50) {
                            item.quality++;
                        }
                        break;
                    // Handle "Backstage passes"
                    case BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT:
                        handleBackstagePasses(item);
                        break;
                    // Handle "Conjured" items
                    case CONJURED: {
                        int degradeAmount = (item.sellIn <= 0) ? 4 : 2; // Degrades by 4 if SellIn is passed, else by 2
                        item.quality -= degradeAmount;
                        break;
                    }
                    // Handle default items
                    default: {
                        int degradeAmount = (item.sellIn <= 0) ? 2 : 1; // Degrades by 2 if SellIn is passed, else by 1
                        item.quality -= degradeAmount;
                        break;
                    }
                }

                if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                    item.quality = adjustQuality(item.quality);
                    item.sellIn--;
                }
            });
    }

    private int adjustQuality(int quality) {
        return Math.max(0, Math.min(50, quality));
    }

    private static void handleBackstagePasses(Item item) {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else {
            int qualityIncrease = 1;
            if (item.sellIn <= 5) {
                qualityIncrease = 3;
            } else if (item.sellIn <= 10) {
                qualityIncrease = 2;
            }
            item.quality += qualityIncrease;
        }
    }
}
