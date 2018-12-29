package entity.nonDynamic.building;

import entity.nonDynamic.StaticEntity;
import map.Level;
import sound.Sound;

public abstract class BuildAbleObject extends StaticEntity {
    private boolean open; // can a villager walk over the object
    public boolean initialised = false; // has the building been initialised
    private byte condition = 0; // condition of the building (0=not built ,100= done)

    public BuildAbleObject() {
        setVisible(false);
    }

    protected void setOpened(boolean open) { //the open value has to be changeable for certain objects like doors
        this.open = open;
        if(initialised) {
            level.getTile(x * 16, y * 16).setSolid(!this.open);
        }
    }

    public void initialise(int x, int y, Level[] levels, int depth) {
        this.level = levels[depth];
        setLocation(x * 16, y * 16, depth);
        if (this.open) {
            level.addEntity(this, false);
        } else {
            level.addEntity(this, true);
        }

        initialised = true;

    }

    // build method called by villagers when building
    public boolean build() {
        if (initialised) {
            if (condition < 100) {
                if (condition == 1) {
                    Sound.speelGeluid(Sound.drill);
                }
                condition++;
                return false;
            } else {
                this.setVisible(true);
                if (!this.open) {
                    level.getTile(x * 16, y * 16).setSolid(true);
                }
                return true;
            }

        }
        return false;
    }


    public abstract BuildAbleObject clone();

}
