package com.vendoau.core.util;

import net.minestom.server.inventory.InventoryType;

public class InventoryUtil {

    public static InventoryType getInventoryTypeFromSize(int size) {
        if (size < 10) return InventoryType.CHEST_1_ROW;
        if (size < 19) return InventoryType.CHEST_2_ROW;
        if (size < 28) return InventoryType.CHEST_3_ROW;
        if (size < 37) return InventoryType.CHEST_4_ROW;
        if (size < 46) return InventoryType.CHEST_5_ROW;
        return InventoryType.CHEST_6_ROW;
    }
}
