package com.vendoau.core.selection;

import com.mattworzala.debug.DebugMessage;
import com.mattworzala.debug.Layer;
import com.mattworzala.debug.shape.OutlineBox;
import com.vendoau.core.CoreExtension;
import com.vendoau.core.trigger.BetterBox;
import com.vendoau.core.util.DebugUtil;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerStartDiggingEvent;
import net.minestom.server.item.Material;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class SelectionTool {

    public final Map<Player, Point[]> selectionMap = new HashMap<>();

    public SelectionTool() {
        CoreExtension.get().eventNode().addListener(PlayerStartDiggingEvent.class, event -> {
            if (getPositions(event.getPlayer(), event.getBlockPosition(), 0)) {
                event.setCancelled(true);
            }
        });
        CoreExtension.get().eventNode().addListener(PlayerBlockBreakEvent.class, event -> {
            if (getPositions(event.getPlayer(), event.getBlockPosition(), 0)) {
                event.setCancelled(true);
            }
        });

        CoreExtension.get().eventNode().addListener(PlayerBlockInteractEvent.class, event -> {
            if (getPositions(event.getPlayer(), event.getBlockPosition(), 1)) {
                event.setCancelled(true);
            }
        });
    }

    private boolean getPositions(Player player, Point blockPos, int posNumber) {
        if (player.getItemInMainHand().material() != Material.BLAZE_ROD) return false;

        Point pos1;
        Point pos2;
        if (posNumber == 0) {
            pos1 = blockPos;
            pos2 = selectionMap.get(player) == null ? blockPos : selectionMap.get(player)[1];
        } else {
            pos1 = selectionMap.get(player) == null ? blockPos : selectionMap.get(player)[0];
            pos2 = blockPos;
        }
        selectionMap.put(player, new Point[]{pos1, pos2});
        updateSelection(player, pos1.add(1), pos2.add(1));
        return true;
    }

    private void updateSelection(Player player, Point pos1, Point pos2) {
        final BetterBox betterBox = new BetterBox(pos1, pos2);
        final OutlineBox selection = DebugUtil.createOutlineBox(betterBox)
                .color(new Color(0, 255, 0, 64).getRGB())
                .layer(Layer.TOP)
                .build();

        final DebugMessage debugMessage = DebugMessage.builder()
                .set("Selection_" + player.getUuid(), selection)
                .set("Pos1_" + player.getUuid(), createPosBox(pos1))
                .set("Pos2_" + player.getUuid(), createPosBox(pos2))
                .build();
        player.sendPacket(debugMessage.getPacket());
    }

    private OutlineBox createPosBox(Point pos) {
        return new OutlineBox.Builder()
                .start(Vec.fromPoint(pos))
                .end(Vec.fromPoint(pos).sub(1))
                .color(new Color(255, 0, 0, 192).getRGB())
                .layer(Layer.TOP)
                .build();
    }

    public void clear(Player player) {
        selectionMap.remove(player);

        final DebugMessage debugMessage = DebugMessage.builder()
                .remove("Selection_" + player.getUuid())
                .remove("Pos1_" + player.getUuid())
                .remove("Pos2_" + player.getUuid())
                .build();
        player.sendPacket(debugMessage.getPacket());
    }

    public Point[] get(Player player) {
        return selectionMap.get(player);
    }
}
