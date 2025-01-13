package com.gildedrose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    private GildedRose gildedRose;
    private Item[] items;

    @BeforeEach
    void setUp() {
        // Initialize the GildedRose instance and items array before each test
        items = new Item[] {
            new Item("Aged Brie", 10, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Sulfuras, Hand of Ragnaros", 10, 80),
            new Item("Conjured Mana Cake", 5, 20),
            new Item("Elixir of the Mongoose", 5, 10),
        };
        gildedRose = new GildedRose(items);
    }

    @Test
    void testAgedBrieQualityIncreases() {
        // Test Aged Brie - quality should increase over time, max out at 50
        gildedRose.updateQuality();
        assertEquals(21, items[0].quality);
        assertEquals(9, items[0].sellIn);
    }

    @Test
    void testBackstagePassesQualityIncreases() {
        // Test Backstage Passes - quality should increase based on sellIn value
        gildedRose.updateQuality();
        assertEquals(21, items[1].quality);
        assertEquals(14, items[1].sellIn);

        // Simulate sellIn <= 10
        items[1].sellIn = 9;
        gildedRose.updateQuality();
        assertEquals(23, items[1].quality);
        assertEquals(8, items[1].sellIn);

        // Simulate sellIn <= 5
        items[1].sellIn = 4;
        gildedRose.updateQuality();
        assertEquals(26, items[1].quality);
        assertEquals(3, items[1].sellIn);

        // Simulate sellIn <= 0
        items[1].sellIn = 0;
        gildedRose.updateQuality();
        assertEquals(0, items[1].quality);
        assertEquals(-1, items[1].sellIn);
    }

    @Test
    void testSulfurasDoesNotChange() {
        // Test Sulfuras - quality and sellIn should never change
        int initialQuality = items[2].quality;
        int initialSellIn = items[2].sellIn;

        gildedRose.updateQuality();
        assertEquals(initialQuality, items[2].quality);
        assertEquals(initialSellIn, items[2].sellIn);
    }

    @Test
    void testConjuredItemsDegradeFaster() {
        // Test Conjured items - degrade by 2 normally, but 4 after sell-by date
        gildedRose.updateQuality();
        assertEquals(18, items[3].quality);
        assertEquals(4, items[3].sellIn);

        // Simulate sellIn <= 0
        items[3].sellIn = 0;
        gildedRose.updateQuality();
        assertEquals(14, items[3].quality);
        assertEquals(-1, items[3].sellIn);
    }

    @Test
    void testNormalItemsDegrade() {
        // Test normal items - degrade by 1 normally, by 2 after sell-by date
        gildedRose.updateQuality();
        assertEquals(9, items[4].quality);
        assertEquals(4, items[3].sellIn);

        // Simulate sellIn <= 0
        items[4].sellIn = 0;
        gildedRose.updateQuality();
        assertEquals(7, items[4].quality);
        assertEquals(-1, items[4].sellIn);
    }

    @Test
    void testQualityNeverExceeds50() {
        // Test that quality never exceeds 50
        items[0].quality = 50;
        gildedRose.updateQuality();
        assertEquals(50, items[0].quality);

        items[1].quality = 50;
        gildedRose.updateQuality();
        assertEquals(50, items[1].quality);

        items[3].quality = 55;
        gildedRose.updateQuality();
        assertEquals(50, items[3].quality);

        items[4].quality = 55;
        gildedRose.updateQuality();
        assertEquals(50, items[4].quality);
    }

    @Test
    void testQualityNeverNegative() {
        // Test that quality never becomes negative
        items[4].quality = 0;
        gildedRose.updateQuality();
        assertEquals(0, items[4].quality);
    }
}
