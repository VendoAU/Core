package com.vendoau.core.trigger;

import net.minestom.server.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TriggerManager {

    public List<Trigger> triggers = new ArrayList<>();

    public void debugTriggers(Player player, boolean show) {
        if (show) {
            for (Trigger trigger : triggers) {
                trigger.showDebug(player);
            }
        } else {
            for (Trigger trigger : triggers) {
                trigger.hideDebug(player);
            }
        }
    }

    public void add(Trigger trigger) {
        triggers.add(trigger);
    }

    public void remove(Trigger trigger) {
        triggers.remove(trigger);
    }
}
