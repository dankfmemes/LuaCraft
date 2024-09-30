package com.dankfmemes.luacraft.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.json.simple.JSONObject;
import org.luaj.vm2.LuaValue;

import net.kyori.adventure.text.Component;

import java.util.UUID;

/**
 * Represents a LuaCraft entity in the Minecraft world, providing various
 * methods to interact with and manipulate the entity's attributes.
 */
public class LuaCraftEntity {
    private Entity entity;

    /**
     * Constructs a LuaCraftEntity with a given Entity instance.
     * 
     * @param entity The Bukkit Entity to wrap.
     */
    public LuaCraftEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Constructs a LuaCraftEntity using an entity's UUID as a string.
     * 
     * @param entityUUID The UUID of the entity as a String.
     */
    public LuaCraftEntity(String entityUUID) {
        this.entity = getEntityByUUID(entityUUID);
    }

    /**
     * Constructs a LuaCraftEntity using an entity's UUID as a UUID object.
     * 
     * @param entityUUID The UUID of the entity as a UUID object.
     */
    public LuaCraftEntity(UUID entityUUID) {
        this.entity = getEntityByUUID(entityUUID.toString());
    }

    /**
     * Returns the entity's data as a JSON object, including its UUID, type,
     * and position.
     * 
     * @return A JSONObject containing the entity's information.
     */
    public JSONObject getEntityDataAsJson() {
        JSONObject entityInfo = new JSONObject();
        entityInfo.put("id", entity.getUniqueId().toString());
        entityInfo.put("type", entity.getType().toString());

        Location location = entity.getLocation();
        JSONObject position = new JSONObject();
        position.put("x", location.getX());
        position.put("y", location.getY());
        position.put("z", location.getZ());
        entityInfo.put("position", position);

        return entityInfo;
    }

    /**
     * Retrieves an Entity by its UUID string.
     * 
     * @param uuid The UUID of the entity as a String.
     * @return The Entity with the specified UUID, or null if not found.
     */
    public Entity getEntityByUUID(String uuid) {
        try {
            UUID entityUUID = UUID.fromString(uuid);
            for (org.bukkit.World world : Bukkit.getWorlds()) {
                for (Entity e : world.getEntities()) {
                    if (e.getUniqueId().equals(entityUUID)) {
                        return e;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("Invalid UUID format: " + uuid);
        }
        return null;
    }

    /**
     * Checks if the entity is valid (i.e., not null).
     * 
     * @return True if the entity is valid, otherwise false.
     */
    public boolean isValid() {
        return entity != null;
    }

    /**
     * Sets a custom name for the entity.
     * 
     * @param name The custom name to set.
     */
    public void setCustomName(String name) {
        if (entity != null) {
            entity.customName(Component.text(name));
        }
    }

    /**
     * Sets the health of the entity if it is a LivingEntity.
     * 
     * @param health The health value to set.
     */
    public void setHealth(double health) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setHealth(health);
        }
    }

    /**
     * Teleports the entity to a specified position.
     * 
     * @param x The x-coordinate to teleport to.
     * @param y The y-coordinate to teleport to.
     * @param z The z-coordinate to teleport to.
     */
    public void teleport(double x, double y, double z) {
        if (entity != null) {
            Location newLocation = new Location(entity.getWorld(), x, y, z);
            entity.teleport(newLocation);
        }
    }

    /**
     * Sets whether the entity is a baby (if it is an Ageable entity).
     * 
     * @param isBaby True to set the entity as a baby, false to set it as an adult.
     */
    public void setBaby(boolean isBaby) {
        if (entity instanceof Ageable) {
            Ageable ageable = (Ageable) entity;
            if (isBaby) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }
    }

    /**
     * Sets whether a Creeper is charged.
     * 
     * @param charged True to charge the Creeper, false to discharge it.
     */
    public void setCharged(boolean charged) {
        if (entity instanceof Creeper) {
            ((Creeper) entity).setPowered(charged);
        }
    }

    /**
     * Returns the entity's UUID as a String.
     * 
     * @return The UUID of the entity, or null if the entity is not valid.
     */
    public String getUUID() {
        if (entity != null) {
            return entity.getUniqueId().toString();
        }
        return null;
    }

    /**
     * Retrieves the custom name of the entity as a LuaValue.
     * 
     * @return The custom name as a LuaValue, or LuaValue.NIL if not set.
     */
    public LuaValue getCustomName() {
        if (entity != null && entity.customName() != null) {
            return LuaValue.valueOf(entity.customName().toString());
        }
        return LuaValue.NIL;
    }

    /**
     * Enables or disables the AI for the entity if it is a LivingEntity.
     * 
     * @param enabled True to enable AI, false to disable it.
     */
    public void setAI(boolean enabled) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).setAI(enabled);
        }
    }
}
