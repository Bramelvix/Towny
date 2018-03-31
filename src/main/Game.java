package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

import com.sun.xml.internal.ws.util.StringUtils;
import entity.Entity;
import entity.dynamic.mob.work.*;
import entity.nonDynamic.Ore;
import entity.nonDynamic.Tree;
import entity.nonDynamic.building.container.Chest;
import entity.dynamic.item.Clothing;
import entity.dynamic.item.ClothingType;
import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import entity.dynamic.mob.Zombie;
import entity.nonDynamic.building.workstations.Anvil;
import entity.nonDynamic.building.workstations.Furnace;
import entity.pathfinding.PathFinder;
import graphics.Sprite;
import graphics.SpriteHashtable;
import graphics.SpritesheetHashtable;
import graphics.ui.Ui;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.MenuItem;
import input.Keyboard;
import input.Mouse;
import map.Level;
import map.Tile;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import sound.Sound;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class Game {

    public static final int width = 500;
    public static final int height = width / 16 * 9;
    private Thread thread;
    public static final int SCALE = 3;
    public Level[] map;
    private boolean running = false;
    private Mouse mouse;
    private ArrayList<Villager> vills;
    private ArrayList<Villager> sols;
    private ArrayList<Mob> mobs;
    private Ui ui;
    private boolean paused = false;
    private byte speed = 60;
    private double ns = 1000000000.0 / speed;
    private Villager selectedvill;
    public int xScroll = 0;
    public int yScroll = 0;
    public int currentLayerNumber = 0;
    private long window;

    public static void main(String[] args) {
        try {
            new Game();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Game() throws Exception {
        init();
        loop();
    }
    private void init() throws Exception{
        if (glfwInit() != true) {
            System.err.println("GLFW failed to initialize");
        }
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        window = glfwCreateWindow(width*SCALE, height*SCALE, "Towny", 0, 0);
        if (window == 0) {
            System.err.println("Window failed to be created");
        }
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, 100, 100);
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //set up projection matrix; allows us to draw.
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width*SCALE, height*SCALE, 0.0f, 0.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
        glEnable(GL_TEXTURE_2D);
        SpritesheetHashtable.registerSpritesheets();
        SpriteHashtable.registerSprites();
        ItemHashtable.registerItems();
        Sound.initSound();
        mouse = new Mouse(this);
        generateLevel();
        mobs = new ArrayList<>();
        vills = new ArrayList<>();
        sols = new ArrayList<>();
        ui = new Ui(map);
        PathFinder.init(100, 100);
        spawnvills();
        spawnZombies();
    }

    private void generateLevel() {
        map = new Level[20];
        for (int i = 0; i < map.length; i++) {
            map[i] = new Level(100, 100, i);
        }
    }

    private void spawnvills() {
        Villager vil1 = new Villager(144, 144, 0, map);
        vil1.addClothing(new Clothing("Brown Shirt", vil1, SpriteHashtable.get(70), "A brown tshirt", ClothingType.SHIRT));
        addVillager(vil1);
        Villager vil2 = new Villager(144, 160, 0, map);
        vil2.addClothing(new Clothing("Green Shirt", vil2, SpriteHashtable.get(74), "A green tshirt", ClothingType.SHIRT));
        addVillager(vil2);
        Villager vil3 = new Villager(160, 160, 0, map);
        vil3.addClothing(new Clothing("Green Shirt", vil3, SpriteHashtable.get(75), "A green tshirt", ClothingType.SHIRT));
        addVillager(vil3);

    }

    private void spawnZombies() {
        int teller = Entity.RANDOM.nextInt(5) + 1;
        for (int i = 0; i < teller; i++) {
            Zombie zomb = new Zombie(map, Entity.RANDOM.nextInt(256) + 16, Entity.RANDOM.nextInt(256) + 16, 0);
            mobs.add(zomb);
        }
    }

    private void addVillager(Villager vil) {
        if (!vills.contains(vil)) {
            sols.remove(vil);
            vills.add(vil);
            ui.updateCounts(sols.size(), vills.size());
        }
    }

    private void addSoldier(Villager vil) {
        if (!sols.contains(vil)) {
            vills.remove(vil);
            sols.add(vil);
            ui.updateCounts(sols.size(), vills.size());
        }
    }



    private void loop() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;
        long now;
        GL.createCapabilities();
        while (!glfwWindowShouldClose(window)) {
            now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                updateUI();
                if (!paused) {
                    update();
                }
                delta--;
                mouse.reset();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
            }

            draw();

            glfwPollEvents();
        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();

    }
    private void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        map[currentLayerNumber].render(xScroll,yScroll);
        renderMobs();
        map[currentLayerNumber].renderHardEntities(xScroll,yScroll);
        glfwSwapBuffers(window);
    }



    private void update() {
        updateMobs();
        updateMouse();

    }

    private void updateUI() {
        ui.update(mouse, xScroll, yScroll, currentLayerNumber);
        getKeyPositions();
        if (mouse.getMouseWheelMoved() != 0) {
            currentLayerNumber += mouse.getMouseWheelMoved();
            if (currentLayerNumber < 0) {
                currentLayerNumber = 0;
            } else if (currentLayerNumber > map.length - 1) {
                currentLayerNumber = map.length - 1;
            }
            ui.updateMinimap(map, currentLayerNumber);
        }
        if (paused != ui.isPaused()) {
            paused = ui.isPaused();
        }
        if (speed != ui.getSpeed()) {
            speed = ui.getSpeed();
            ns = 100000000.0 / speed;
        }

    }

    private Villager getIdlestVil() {
        Villager lowest = vills.get(0);
        for (Villager i : vills) {
            if (i.getJobSize() < lowest.getJobSize())
                return i;
        }
        return lowest;
    }

    private Villager anyVillHoverOn(int x, int y) {
        for (Villager i : vills) {
            if (i.hoverOn(x, y, currentLayerNumber))
                return i;
        }
        return null;
    }

    private Villager anyVillHoverOn(Mouse mouse) {
        return anyVillHoverOn(mouse.getX(), mouse.getY());
    }

    private Mob anyMobHoverOn(Mouse mouse) {
        return anyMobHoverOn(mouse.getX(), mouse.getY());
    }

    private Mob anyMobHoverOn(int x, int y) {
        for (Mob i : mobs) {
            if (i.hoverOn(x, y, currentLayerNumber))
                return i;
        }
        return null;
    }

    private void deselectAllVills() {
        vills.forEach((Villager i) -> i.setSelected(false));
        selectedvill = null;
    }

    private void deselect(Villager vill) {
        vill.setSelected(false);
        selectedvill = null;
    }

    private void updateMouse() {
        if ((UiIcons.isWoodSelected()) && UiIcons.hoverOnNoIcons()) {
            if (mouse.getMouseB() == 1) {
                ui.showSelectionSquare(mouse);
                int x = ui.getSelectionX();
                int y = ui.getSelectionY();
                int width = ui.getSelectionWidth();
                int height = ui.getSelectionHeight();
                for (int xs = x; xs < (x + width); xs += 16) {
                    for (int ys = y; ys < (y + height); ys += 16) {
                        Tree tree = map[currentLayerNumber].selectTree(xs, ys);
                        if (tree != null) {
                            tree.setSelected(true);
                        }
                    }
                }
                return;
            }
            if (mouse.getReleasedLeft()) {
                int x = ui.getSelectionX();
                int y = ui.getSelectionY();
                int width = ui.getSelectionWidth();
                int height = ui.getSelectionHeight();
                for (int xs = x; xs < (x + width); xs += 16) {
                    for (int ys = y; ys < (y + height); ys += 16) {
                        Tree tree = map[currentLayerNumber].selectTree(xs, ys, false);
                        if (tree != null) {
                            Villager idle = getIdlestVil();
                            idle.addJob(tree);
                        }
                    }
                }
                ui.resetSelection();
                ui.deSelectIcons();
                return;

            }
            return;
        } else if (((UiIcons.isMiningSelected()) && UiIcons.hoverOnNoIcons() && mouse.getClickedLeft())
                && (map[currentLayerNumber].selectOre(mouse.getX(), mouse.getY()) != null)) {
            Villager idle = getIdlestVil();
            idle.setPath(null);
            deselectAllVills();
            idle.addJob(map[currentLayerNumber].selectOre(mouse.getX(), mouse.getY()));
            ui.deSelectIcons();
            return;

        } else if (UiIcons.isShovelHover() && !ui.menuVisible() && mouse.getClickedLeft()) {
            deselectAllVills();
            ui.showBuildSquare(mouse, xScroll, yScroll, false, BuildingRecipe.STAIRSDOWN, currentLayerNumber);
            ui.deSelectIcons();
            return;

        } else if (((UiIcons.isSwordsSelected()) && UiIcons.hoverOnNoIcons() && mouse.getClickedLeft())
                && (anyMobHoverOn(mouse) != null)) {
            Villager idle = getIdlestVil();
            idle.setPath(null);
            deselectAllVills();
            idle.addJob(new FightJob(idle, anyMobHoverOn(mouse)));
            ui.deSelectIcons();
            return;

        } else if (mouse.getClickedLeft() && anyVillHoverOn(mouse) != null && !ui.outlineIsVisible()) {
            deselectAllVills();
            selectedvill = anyVillHoverOn(mouse);
            selectedvill.setSelected(true);
            ui.deSelectIcons();
            return;
        } else if (UiIcons.isSawHover() && !ui.menuVisible() && mouse.getClickedLeft()) {
            deselectAllVills();
            MenuItem[] items = new MenuItem[BuildingRecipe.RECIPES.length + 1];
            for (int i = 0; i < BuildingRecipe.RECIPES.length; i++) {
                items[i] = new MenuItem(BuildingRecipe.RECIPES[i]);
            }
            items[items.length - 1] = new MenuItem(MenuItem.CANCEL);
            ui.showMenuOn(mouse, items);
            return;
        } else if (mouse.getClickedRight()) {
            if (selectedvill != null) {
                List<MenuItem> options = new ArrayList<>();
                if (selectedvill.getHolding() != null)
                    options.add(new MenuItem((MenuItem.DROP + " " + selectedvill.getHolding().getName())));
                Tree boom = map[currentLayerNumber].selectTree(mouse.getX(), mouse.getY());
                if (boom != null) {
                    options.add(new MenuItem((MenuItem.CHOP), boom));
                }
                Ore ore = map[currentLayerNumber].selectOre(mouse.getX(), mouse.getY());
                if (ore != null) {
                    options.add(new MenuItem((MenuItem.MINE), ore));
                }
                Mob mob = anyMobHoverOn(mouse);
                if (mob != null) {
                    options.add(new MenuItem((MenuItem.FIGHT), mob));
                }
                Item item = map[currentLayerNumber].getItemOn(mouse.getX(), mouse.getY());
                if (item != null) {
                    if (item instanceof Weapon) {
                        options.add(new MenuItem((MenuItem.EQUIP), item));
                    } else if (item instanceof Clothing) {
                        options.add(new MenuItem((MenuItem.WEAR), item));
                    } else {
                        options.add(new MenuItem((MenuItem.PICKUP), item));
                    }

                }
                if (map[currentLayerNumber].getEntityOn(mouse.getX(), mouse.getY()) instanceof Chest) {
                    Chest chest = (Chest) map[currentLayerNumber].getEntityOn(mouse.getX(), mouse.getY());
                    for (Item i : chest.getItems()) {
                        options.add(new MenuItem((MenuItem.PICKUP), i));
                    }
                } else {
                    options.add(new MenuItem(MenuItem.MOVE));
                }
                options.add(new MenuItem(MenuItem.CANCEL));
                ui.showMenuOn(mouse, options.toArray(new MenuItem[0]));
            } else {
                if (map[currentLayerNumber].getEntityOn(mouse.getX(), mouse.getY()) instanceof Furnace) {
                    ui.showMenuOn(mouse, new MenuItem(MenuItem.SMELT), new MenuItem(MenuItem.CANCEL));
                } else if (map[currentLayerNumber].getEntityOn(mouse.getX(), mouse.getY()) instanceof Anvil) {
                    ui.showMenuOn(mouse, new MenuItem(MenuItem.SMITH), new MenuItem(MenuItem.CANCEL));
                } else {
                    ui.showMenuOn(mouse, new MenuItem(MenuItem.CANCEL));
                }

            }
        }
        if (ui.outlineIsVisible() && !ui.menuVisible() && mouse.getReleasedLeft() && UiIcons.hoverOnNoIcons()) {
            int[][] coords = ui.getOutlineCoords();
            for (int[] blok : coords) {
                if (map[currentLayerNumber].tileIsEmpty(blok[0] / 16, blok[1] / 16)) {
                    Villager idle = getIdlestVil();
                    idle.setPath(null);
                    idle.addBuildJob(blok[0], blok[1], currentLayerNumber, ui.getBuildRecipeOutline().getProduct(),
                            ui.getBuildRecipeOutline().getResources()[0]);
                }
            }
            ui.removeBuildSquare();
            deselectAllVills();
            ui.deSelectIcons();
            return;
        }
        if (ui.menuVisible()) {
            MenuItem item = ui.getMenu().clickedItem(mouse);
            if (item != null) {
                if (item.getText().contains(MenuItem.CANCEL)) {
                    ui.getMenu().hide();
                    ui.deSelectIcons();
                    if (selectedvill != null) {
                        deselect(selectedvill);
                    }
                } else if (item.getText().contains(MenuItem.MOVE)) {
                    selectedvill.resetAll();
                    selectedvill.addJob(new MoveJob(mouse.getX(), mouse.getY(), currentLayerNumber, selectedvill));
                    //selectedvill.setPath(selectedvill.getPath(mouse.getTileX(), mouse.getTileY()));
                    deselect(selectedvill);
                    ui.deSelectIcons();
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.CHOP)) {
                    selectedvill.setPath(null);
                    selectedvill.addJob((Tree) item.getEntity());
                    deselect(selectedvill);
                    ui.deSelectIcons();
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.FIGHT)) {
                    selectedvill.setPath(null);
                    selectedvill.addJob(new FightJob(selectedvill, (Mob) item.getEntity()));
                    deselect(selectedvill);
                    ui.deSelectIcons();
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.MINE)) {
                    selectedvill.setPath(null);
                    selectedvill.addJob((Ore) item.getEntity());
                    deselect(selectedvill);
                    ui.deSelectIcons();
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.BUILD) && !ui.outlineIsVisible()) {
                    ui.showBuildSquare(mouse, xScroll, yScroll, false, item.getRecipe(), currentLayerNumber);
                    ui.deSelectIcons();
                    ui.getMenu().hide();
                } else if ((item.getText().contains(MenuItem.PICKUP) || item.getText().contains(MenuItem.EQUIP)
                        || item.getText().contains(MenuItem.WEAR)) && !ui.outlineIsVisible()) {
                    selectedvill.setPath(null);
                    Item e = (Item) item.getEntity();
                    selectedvill.addJob(new MoveItemJob(e, selectedvill));
                    ui.deSelectIcons();
                    deselect(selectedvill);
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.DROP) && !ui.outlineIsVisible()) {
                    selectedvill.setPath(null);
                    selectedvill.addJob(new MoveItemJob(ui.getMenuIngameX(), ui.getMenuIngameY(), selectedvill));
                    ui.deSelectIcons();
                    deselect(selectedvill);
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.SMELT)) {
                    MenuItem[] craftingOptions = new MenuItem[ItemRecipe.FURNACE_RECIPES.length + 1];
                    for (int i = 0; i < ItemRecipe.FURNACE_RECIPES.length; i++) {
                        craftingOptions[i] = new MenuItem(ItemRecipe.FURNACE_RECIPES[i]);
                    }
                    craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
                    ui.showMenuOn(mouse, craftingOptions);
                } else if (item.getText().contains(MenuItem.CRAFT)) {
                    Villager idle = getIdlestVil();
                    ItemRecipe recipe = ui.getMenu().recipeFromMenuOption(mouse, MenuItem.CRAFT);
                    if (recipe != null) {
                        idle.setPath(null);
                        Item[] res = new Item[recipe.getResources().length];
                        for (int i = 0; i < res.length; i++) {
                            res[i] = idle.getNearestItemOfType(recipe.getResources()[i]);
                        }
                        idle.addJob(new CraftJob(idle, res, recipe.getProduct(),
                                map[currentLayerNumber].getNearestWorkstation(recipe.getWorkstationClass(), idle.getX(), idle.getY())));
                        ui.deSelectIcons();
                        ui.getMenu().hide();
                    }
                } else if (item.getText().contains(MenuItem.SMITH)) {
                    MenuItem[] craftingOptions = new MenuItem[WeaponMaterial.values().length + 1];
                    for (int i = 0; i < WeaponMaterial.values().length; i++) {
                        craftingOptions[i] = new MenuItem(
                                StringUtils.capitalize(WeaponMaterial.values()[i].toString().toLowerCase()));
                    }
                    craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
                    ui.showMenuOn(mouse, craftingOptions);
                } else if (MenuItem.isEqualToAnyMaterial(item.getText())) {
                    ItemRecipe[] recipes = ItemRecipe
                            .smithingRecipesFromWeaponMaterial(WeaponMaterial.valueOf(item.getText().toUpperCase()));
                    MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
                    for (int i = 0; i < WeaponMaterial.values().length; i++) {
                        craftingOptions[i] = new MenuItem(recipes[i]);
                    }
                    craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
                    ui.showMenuOn(mouse, craftingOptions);
                }
            }
        }
    }

    private void moveCamera(int xScroll, int yScroll) {
        this.xScroll += xScroll;
        this.yScroll += yScroll;
        glTranslatef(-this.xScroll, -this.yScroll, 0);
    }

    private void getKeyPositions() {
        int _yScroll = 0;
        int _xScroll = 0;
        if (glfwGetKey(window,GLFW_KEY_UP)==1 && yScroll > 1) {
            _yScroll -= 2;
        }
        if (glfwGetKey(window,GLFW_KEY_DOWN)==1 && yScroll < (map[currentLayerNumber].height * Tile.SIZE) - 1 - height) {
            _yScroll += 2;
        }
        if (glfwGetKey(window,GLFW_KEY_LEFT)==1 && xScroll > 1) {
            _xScroll -= 2;
        }
        if (glfwGetKey(window,GLFW_KEY_RIGHT)==1 && xScroll < (map[currentLayerNumber].width * Tile.SIZE) - width - 1) {
            _xScroll += 2;
        }
        moveCamera(_xScroll, _yScroll);
    }

    private <T extends Mob> void update(T mob, Iterator<T> iterator) {
        mob.update();
        if (mob.getHealth() == 0) {
            mob.die();
            iterator.remove();
        }
    }

    private void updateMobs() {
        Iterator<Mob> iMob = mobs.iterator();
        while (iMob.hasNext()) {
            Mob i = iMob.next();
            update(i, iMob);
        }
        Iterator<Villager> iVill = vills.iterator();
        while (iVill.hasNext()) {
            Villager i = iVill.next();
            update(i, iVill);
        }
        Iterator<Villager> iSoll = sols.iterator();
        while (iSoll.hasNext()) {
            Villager i = iSoll.next();
            update(i, iSoll);
        }
    }

    private void renderMobs() {
        int x1 = (xScroll + width*SCALE + Sprite.SIZE);
        int y1 = (yScroll + height*SCALE + Sprite.SIZE);
        for (Mob i : mobs) {
            if (i.getZ() == currentLayerNumber && i.getX() + 16 >= xScroll && i.getX() - 16 <= x1 && i.getY() + 16 >= yScroll && i.getY() - 16 <= y1) {
                i.render();
            }
        }
        for (Villager i : vills) {
            if (i.getZ() == currentLayerNumber && i.getX() + 16 >= xScroll && i.getX() - 16 <= x1 && i.getY() + 16 >= yScroll && i.getY() - 16 <= y1) {
                i.render();
            }
        }
        for (Villager i : sols) {
            if (i.getZ() == currentLayerNumber && i.getX() + 16 >= xScroll && i.getX() - 16 <= x1 && i.getY() + 16 >= yScroll && i.getY() - 16 <= y1) {
                i.render();
            }
        }
    }


}
