package entity.nonDynamic;

import entity.dynamic.item.ItemHashtable;
import entity.dynamic.mob.Villager;
import entity.nonDynamic.Resource;
import graphics.Screen;
import graphics.SpriteHashtable;
import map.Level;
import sound.Sound;

public class Tree extends Resource {
    private byte chopped = 100;

    // basic constructor
    public Tree(int x, int y, int z, Level level) {
        super(x, y, z, level);
        sprites.add(SpriteHashtable.get(12));
        sprites.add(SpriteHashtable.get(13));
        setVisible(true);
        setName("tree");
    }

    // render method to render onto the screen
    public void render(Screen screen) {
        screen.renderTree(x, y, this);
    }

    // work method (same as in the Ore class)
    public boolean work(Villager worker) {
        if (chopped > 0) {
            if (chopped % 20 == 0) {
                Sound.speelGeluid(Sound.woodChopping);
            }
            chopped--;
            return false;
        } else {
            level.removeEntity(this);
            level.addItem(ItemHashtable.get(1, this.x, this.y, this.z));
            return true;
        }

    }

}
