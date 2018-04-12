package entity.dynamic.item.weapon;

import entity.dynamic.item.Item;
import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import graphics.Sprite;
import graphics.SpriteHashtable;

public class Weapon extends Item {
    private float crush;
    private float pierce;
    private float cut;
    private float def;
    private float mat_strong;
    private float speed;
    private int range;
    private WeaponType type;
    private WeaponMaterial mat;

    public Weapon(String name, Sprite sprite, String tooltip, WeaponType type, WeaponMaterial mat, int id) {
        super(name, sprite, tooltip, id);
        this.mat = mat;
        this.type = type;
        mat_strong = calcMatStrong(mat);
        calcDam(type);
    }

    public float getDamage(Villager user) {
        return mat_strong * cut * 10;
    }

    public float getSpeed() {
        return speed;
    }

    private void calcDam(WeaponType type) {
        switch (type) {
            case AXE:
                setStats(0.25f, 0f, 3f, 0f, 3f, 3);
                break;
            case BOW:
                setStats(0f, 3f, 0f, 0f, 2f, 10);
                break;
            case BUCKLER:
                setStats(0.75f, 0f, 0f, 4f, -0.75f, 1);
                break;
            case DAGGER:
                setStats(0f, 2f, 1f, 0f, 5f, 1);
                break;
            case HALBERT:
                setStats(0.5f, 1f, 5f, 0.5f, 1f, 5);
                break;
            case HEATER:
                setStats(1f, 0f, 0f, 5f, -1.5f, 1);
                break;
            case SCIMITAR:
                setStats(0f, 1f, 5f, 0.5f, 4f, 3);
                break;
            case SPEAR:
                setStats(0.25f, 5f, 0.25f, 2f, 2.5f, 5);
                break;
            case SWORD:
                setStats(0.5f, 4f, 4f, 2.5f, 3f, 4);
                break;
        }
    }

    private void setStats(float crush, float pierce, float cut, float def, float speed, int range) {
        this.crush = crush;
        this.pierce = pierce;
        this.cut = cut;
        this.def = def;
        this.speed = speed;
        this.range = range;
    }


    private float calcMatStrong(WeaponMaterial mat) {
        float strong = 0;
        switch (mat) {
            case WOOD:
                strong = 0.5f;
                break;
            case GOLD:
                strong = 1.25f;
                break;
            case IRON:
                strong = 1f;
                break;
            case COPPER:
                strong = 0.75f;
                break;
            case CRYSTAL:
                strong = 1.5f;
                break;
        }
        return strong;
    }

    public boolean isShield() {
        return type == WeaponType.BUCKLER || type == WeaponType.HEATER;
    }

    public Weapon copy() {
        return new Weapon(this.getName(), this.sprite, this.tooltip, this.type, this.mat, this.getId());
    }

    public Weapon copy(int x, int y, int z) {
        Weapon copy = this.copy();
        copy.setLocation(x, y, z);
        return copy;
    }

}
