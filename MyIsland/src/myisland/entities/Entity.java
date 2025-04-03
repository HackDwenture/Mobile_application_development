package myisland.entities;

import myisland.core.Location;

public abstract class Entity {
    protected Location location;

    public abstract void act();

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
