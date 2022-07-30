package com.vendoau.core.listener;

import com.vendoau.core.CoreExtension;
import com.vendoau.core.event.EntityPreDeathEvent;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.event.entity.EntityDamageEvent;

public final class EntityPreDeathListener {

    public EntityPreDeathListener(CoreExtension core) {
        core.eventNode().addListener(EntityDamageEvent.class, event -> {
            final LivingEntity entity = event.getEntity();
            final float damage = event.getDamage();

            if (entity.getHealth() >= damage) {
                EntityPreDeathEvent entityPreDeathEvent = new EntityPreDeathEvent(entity, event.getDamageType(), damage);
                EventDispatcher.callCancellable(entityPreDeathEvent, () -> event.setCancelled(true));
            }
        });
    }
}
