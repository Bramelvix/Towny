package entity.dynamic.item;

import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.mob.Villager;

public class VillagerInventory {
    private Clothing[] clothes;
    private Weapon[] weapons;
    private Villager wearer;

    public VillagerInventory(Villager wearer) {
        this.wearer = wearer;
        clothes = new Clothing[4]; // 0 = hat, 1 = shirt, 2 = trousers, 3 = shoes
        weapons = new Weapon[2]; // 0 = weaponhand, 1 = shieldhand
    }

    public void render() {
        for (Clothing i : clothes) {
            if (i != null) {
                i.sprite.draw(i.getX(),i.getY());
            }
        }
        for (Weapon i : weapons) {
            if (i != null) {
                i.sprite.draw(i.getX(),i.getY());
            }
        }
    }

    public float getWeaponDamage() {
        return (weapons[0] != null ? weapons[0].getDamage(wearer) : wearer.getMeleeDamage());
    }

    public void addClothing(Clothing item) {
        if (alreadyWearing(item)) {
            removeClothing(item.getType());
        }
            clothes[item.getType().getNumVal()] = item;
    }

    public void addWeapon(Weapon item) {
        if (alreadyHolding(item)) {
            removeWeapon(item);
        }
        weapons[item.isShield() ? 1 : 0] = item;
    }

    public void removeWeapon(Weapon item) {
        int num = item.isShield() ? 1 : 0;
        wearer.levels[wearer.getZ()].addItem(weapons[num]);
        weapons[num].removeReserved();
        weapons[num] = null;
    }

    private boolean alreadyHolding(Weapon item) {
        return ((item.isShield() && weapons[1] != null) || (!(item.isShield()) && weapons[0] != null));
    }

    private boolean alreadyWearing(Clothing item) {
        return clothes[item.getType().getNumVal()] != null;
    }

    public void removeClothing(ClothingType type) {
        wearer.levels[wearer.getZ()].addItem(clothes[type.getNumVal()]);
        clothes[type.getNumVal()].removeReserved();
        clothes[type.getNumVal()] = null;

    }

    public boolean isHoldingShield() {
        return weapons[1] != null;
    }

    public boolean isHoldingWeapon() {
        return weapons[0] != null;
    }

    public void dropAll() {
        for (int i = 0; i < clothes.length; i++) {
            wearer.levels[wearer.getZ()].addItem(clothes[i]);
            clothes[i].removeReserved();
            clothes[i] = null;
        }
        for (int i = 0; i < weapons.length; i++) {
            wearer.levels[wearer.getZ()].addItem(weapons[i]);
            weapons[i].removeReserved();
            weapons[i] = null;
        }

    }

    public void update(int x, int y, int z) {
        for (Clothing i : clothes) {
            if (i != null) {
                i.setLocation(x, y, z);
            }
        }
        for (Weapon i : weapons) {
            if (i != null) {
                i.setLocation(x, y, z);
            }
        }
    }

}
