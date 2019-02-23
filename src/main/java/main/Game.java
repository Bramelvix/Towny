package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import entity.Entity;
import entity.dynamic.mob.work.*;
import entity.nonDynamic.building.container.Chest;
import entity.nonDynamic.building.container.Container;
import entity.nonDynamic.resources.Ore;
import entity.nonDynamic.resources.Tree;
import entity.dynamic.item.Clothing;
import entity.dynamic.item.Item;
import entity.dynamic.item.ItemHashtable;
import entity.dynamic.item.weapon.Weapon;
import entity.dynamic.item.weapon.WeaponMaterial;
import entity.dynamic.mob.Mob;
import entity.dynamic.mob.Villager;
import entity.dynamic.mob.Zombie;
import entity.nonDynamic.building.container.workstations.Anvil;
import entity.nonDynamic.building.container.workstations.Furnace;
import entity.pathfinding.PathFinder;
import graphics.*;
import graphics.ui.Ui;
import graphics.ui.icon.Icon;
import graphics.ui.icon.UiIcons;
import graphics.ui.menu.MenuItem;
import input.Keyboard;
import input.MouseButton;
import input.MousePosition;
import map.Level;
import map.Tile;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import sound.Sound;

import javax.imageio.ImageIO;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class Game {

    public static final int width = 1500;
    public static final int height = width / 16 * 9;
    public Level[] map;
    private ArrayList<Villager> vills;
    private ArrayList<Villager> sols;
    private ArrayList<Mob> mobs;
    private Ui ui;
    public static boolean paused = false;
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

    private void init() throws Exception {
        if (!glfwInit()) {
            System.err.println("GLFW failed to initialize");
            return;
        }
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        window = glfwCreateWindow(width, height, "Towny by Bramelvix", 0, 0);
        if (window == 0) {
            System.err.println("Window failed to be created");
            return;
        }
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidmode == null) {
            System.err.println("Vidmode is null");
            return;
        }
        glfwSetWindowPos(window, (vidmode.width() - (width)) / 2, (vidmode.height() - (height )) / 2);
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        GL.createCapabilities();
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glMatrixMode(GL_PROJECTION);
        glfwSwapInterval(0); //0 = VSYNC OFF, 1= VSYNC ON
        setIcon();
        glLoadIdentity();
        glOrtho(0, width , height, 0.0f, 0.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glfwSetKeyCallback(window, new Keyboard());
        glfwSetMouseButtonCallback(window, new MouseButton());
        glfwSetCursorPosCallback(window, new MousePosition(this));
        glfwSetScrollCallback(window, this::scroll);
        SpritesheetHashtable.registerSpritesheets();
        SpriteHashtable.registerSprites();
        ItemHashtable.registerItems();
        Sound.initSound();
        TrueTypeFont.init();
        generateLevel();
        mobs = new ArrayList<>();
        vills = new ArrayList<>();
        sols = new ArrayList<>();
        ui = new Ui(map);
        PathFinder.init(100, 100);
        spawnvills();
        spawnZombies();

    }

    private void setIcon() {
        try {
            BufferedImage img = ImageIO.read(Icon.class.getResource("/res/icons/soldier.png"));
            int width = img.getWidth();
            int height = img.getHeight();
            int[] pixels = new int[width * height];
            img.getRGB(0, 0, width, height, pixels, 0, width);
            ByteBuffer buffer = OpenglUtils.getByteBuffer(pixels, width, height);
            GLFWImage image = GLFWImage.malloc();
            image.set(width, height, buffer);
            GLFWImage.Buffer images = GLFWImage.malloc(1);
            images.put(0, image);
            glfwSetWindowIcon(window, images);
            images.free();
            image.free();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateLevel() {
        map = new Level[20];
        for (int i = 0; i < map.length; i++) {
            map[i] = new Level(100, 100, i);
        }
    }

    private void spawnvills() {
        Villager vill = new Villager(432, 432, 0, map);
        vill.addClothing(ItemHashtable.get(61));
        vill.addClothing(ItemHashtable.get(75));
        addVillager(vill);
        Villager vil2 = new Villager(432, 480, 0, map);
        vil2.addClothing(ItemHashtable.get(65));
        vil2.addClothing(ItemHashtable.get(74));
        addVillager(vil2);
        Villager vil3 = new Villager(480, 480, 0, map);
        vil3.addClothing(ItemHashtable.get(70));
        vil3.addClothing(ItemHashtable.get(73));
        addVillager(vil3);

    }

    private void spawnZombies() {
        int teller = Entity.RANDOM.nextInt(5) + 1;
        for (int i = 0; i < teller; i++) {
            mobs.add(new Zombie(map, Entity.RANDOM.nextInt(768/48)*48 + Tile.SIZE, Entity.RANDOM.nextInt(768/48)*48 + Tile.SIZE, 0));
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
                glfwPollEvents();
                updateUI();
                if (!paused) {
                    updateMobs();
                }
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
            }

            draw();


        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();

    }

    private void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        map[currentLayerNumber].render(xScroll, yScroll);
        renderMobs();
        map[currentLayerNumber].renderHardEntities(xScroll, yScroll);
        ui.render();
        glfwSwapBuffers(window);
    }


    private void updateUI() {
        ui.update(xScroll, yScroll, currentLayerNumber);
        updateMouse();
        moveCamera();
        if (speed != ui.getSpeed()) {
            speed = ui.getSpeed();
            ns = 100000000.0 / speed;
        }
        if (currentLayerNumber != ui.getZFromLevelChanger()) {
            currentLayerNumber = ui.getZFromLevelChanger();
            ui.updateMinimap(map, currentLayerNumber);
        }
        MouseButton.resetLeftAndRight();

    }

    private void scroll(long window, double v, double v1) {
        if ((currentLayerNumber <= map.length - 1 && v1 > 0) || (currentLayerNumber >= 0 && v1 < 0)) {
            currentLayerNumber -= v1;
            if (currentLayerNumber < 0) {
                currentLayerNumber = 0;
            } else if (currentLayerNumber > map.length - 1) {
                currentLayerNumber = map.length - 1;
            }
            ui.updateMinimap(map, currentLayerNumber);
        }

    }

    private Villager getIdlestVil() {
        Villager lowest = vills.get(0);
        for (Villager i : vills) {
            if (i.getJobSize() < lowest.getJobSize()) {
                lowest = i;
            }
        }
        return lowest;
    }

    private Villager anyVillHoverOn() {
        for (Villager i : vills) {
            if (i.hoverOn(currentLayerNumber)) {
                return i;
            }
        }
        return null;
    }


    private Mob anyMobHoverOn() {
        for (Mob i : mobs) {
            if (i.hoverOn(currentLayerNumber)) {
                return i;
            }
        }
        return null;
    }

    private void deselectAllVills() {
        vills.forEach(this::deselect);
    }

    private void deselect(Villager vill) {
        vill.setSelected(false);
        selectedvill = null;
    }

    private void updateMouse() {
        if ((UiIcons.isWoodSelected()) && UiIcons.hoverOnNoIcons()) {
            if (MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
                ui.showSelectionSquare();
                int x = ui.getSelectionX();
                int y = ui.getSelectionY();
                int width = ui.getSelectionWidth();
                int height = ui.getSelectionHeight();
                for (int xs = x; xs < (x + width); xs += Tile.SIZE) {
                    for (int ys = y; ys < (y + height); ys += Tile.SIZE) {
                        Tree tree = map[currentLayerNumber].selectTree(xs, ys);
                        if (tree != null) {
                            tree.setSelected(true);
                        }
                    }
                }
                return;
            }
            if (MouseButton.wasReleased(GLFW_MOUSE_BUTTON_LEFT)) {
                int x = ui.getSelectionX();
                int y = ui.getSelectionY();
                int width = ui.getSelectionWidth();
                int height = ui.getSelectionHeight();
                for (int xs = x; xs < (x + width); xs += Tile.SIZE) {
                    for (int ys = y; ys < (y + height); ys += Tile.SIZE) {
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
        }
        if (MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
            if (((UiIcons.isMiningSelected()) && UiIcons.hoverOnNoIcons())
                    && (map[currentLayerNumber].selectOre(MousePosition.getX(), MousePosition.getY()) != null)) {
                Villager idle = getIdlestVil();
                deselectAllVills();
                idle.addJob(map[currentLayerNumber].selectOre(MousePosition.getX(), MousePosition.getY()));
                ui.deSelectIcons();
                return;

            } else if (UiIcons.isShovelHover() && !ui.menuVisible()) {
                deselectAllVills();
                ui.showBuildSquare(xScroll, yScroll, true, BuildingRecipe.STAIRSDOWN, currentLayerNumber);
                ui.deSelectIcons();
                return;
            }else if (UiIcons.isPlowHover() && !ui.menuVisible()){
                ui.showBuildSquare(xScroll, yScroll, false, BuildingRecipe.TILLED_SOIL, currentLayerNumber);
                ui.deSelectIcons();
                return;
            } else if (((UiIcons.isSwordsSelected()) && UiIcons.hoverOnNoIcons())
                    && (anyMobHoverOn() != null)) {
                Villager idle = getIdlestVil();
                deselectAllVills();
                idle.addJob(new FightJob(idle, anyMobHoverOn()));
                ui.deSelectIcons();
                return;

            } else if (anyVillHoverOn() != null && !ui.outlineIsVisible()) {
                deselectAllVills();
                selectedvill = anyVillHoverOn();
                selectedvill.setSelected(true);
                ui.deSelectIcons();
                return;
            } else if (UiIcons.isSawHover() && !ui.menuVisible()) {
                deselectAllVills();
                MenuItem[] items = new MenuItem[BuildingRecipe.RECIPES.length + 1];
                for (int i = 0; i < BuildingRecipe.RECIPES.length; i++) {
                    items[i] = new MenuItem(BuildingRecipe.RECIPES[i]);
                }
                items[items.length - 1] = new MenuItem(MenuItem.CANCEL);
                ui.showMenu(items);
                return;
            }
            if (ui.outlineIsVisible() && !ui.menuVisible() && UiIcons.hoverOnNoIcons()) {
                int[][] coords = ui.getOutlineCoords();
                for (int[] blok : coords) {
                    if (map[currentLayerNumber].tileIsEmpty(blok[0] / Tile.SIZE, blok[1] / Tile.SIZE)) {
                        Villager idle = getIdlestVil();
                        idle.addBuildJob(blok[0], blok[1], currentLayerNumber, ui.getBuildRecipeOutline().getProduct(),
                                ui.getBuildRecipeOutline().getResources()[0]);
                    }
                }
                ui.removeBuildSquare();
                deselectAllVills();
                ui.deSelectIcons();
                return;
            }
        } else if (MouseButton.wasPressed(GLFW_MOUSE_BUTTON_RIGHT)) {
            if (selectedvill != null) {
                List<MenuItem> options = new ArrayList<>();
                if (selectedvill.getHolding() != null&&(map[currentLayerNumber].tileIsEmpty(MousePosition.getTileX(),MousePosition.getTileY()) || map[currentLayerNumber].getEntityOn(MousePosition.getTileX(),MousePosition.getTileY()) instanceof Chest)) {
                    options.add(new MenuItem((MenuItem.DROP + " " + selectedvill.getHolding().getName())));
                }
                Tree boom = map[currentLayerNumber].selectTree(MousePosition.getX(), MousePosition.getY());
                if (boom != null) {
                    options.add(new MenuItem((MenuItem.CHOP), boom));
                }
                Ore ore = map[currentLayerNumber].selectOre(MousePosition.getX(), MousePosition.getY());
                if (ore != null) {
                    options.add(new MenuItem((MenuItem.MINE), ore));
                }
                Mob mob = anyMobHoverOn();
                if (mob != null) {
                    options.add(new MenuItem((MenuItem.FIGHT), mob));
                }
                Item item = map[currentLayerNumber].getItemOn(MousePosition.getX(), MousePosition.getY());
                if (item != null) {
                    if (item instanceof Weapon) {
                        options.add(new MenuItem((MenuItem.EQUIP), item));
                    } else if (item instanceof Clothing) {
                        options.add(new MenuItem((MenuItem.WEAR), item));
                    } else {
                        options.add(new MenuItem((MenuItem.PICKUP), item));
                    }

                }
                if (map[currentLayerNumber].getEntityOn(MousePosition.getTileX(), MousePosition.getTileY()) instanceof Container) {
                    Container container = map[currentLayerNumber].getEntityOn(MousePosition.getTileX(), MousePosition.getTileY());
                    for (Item i : container.getItems()) {
                        options.add(new MenuItem((MenuItem.PICKUP), i));
                    }
                } else {
                    options.add(new MenuItem(MenuItem.MOVE));
                }
                options.add(new MenuItem(MenuItem.CANCEL));
                ui.showMenu(options.toArray(new MenuItem[0]));
            } else {
                if (map[currentLayerNumber].getEntityOn(MousePosition.getTileX(), MousePosition.getTileY()) instanceof Furnace) {
                    ui.showMenu(new MenuItem(MenuItem.SMELT), new MenuItem(MenuItem.CANCEL));
                } else if (map[currentLayerNumber].getEntityOn(MousePosition.getTileX(), MousePosition.getTileY()) instanceof Anvil) {
                    ui.showMenu(new MenuItem(MenuItem.SMITH), new MenuItem(MenuItem.CANCEL));
                } else {
                    ui.showMenu(new MenuItem(MenuItem.CANCEL));
                }

            }
        }
        if (ui.menuVisible()) {
            MenuItem item = ui.getMenu().clickedItem();
            if (item != null) {
                if (item.getText().contains(MenuItem.CANCEL)) {
                    ui.getMenu().hide();
                    ui.deSelectIcons();
                    if (selectedvill != null) {
                        deselect(selectedvill);
                    }
                } else if (item.getText().contains(MenuItem.MOVE)) {
                    selectedvill.resetAll();
                    selectedvill.addJob(new MoveJob(MousePosition.getX(), MousePosition.getY(), currentLayerNumber, selectedvill));
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
                    ui.showBuildSquare(xScroll, yScroll, false, item.getRecipe(), currentLayerNumber);
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
                    selectedvill.addJob(new MoveItemJob(ui.getMenuIngameX(), ui.getMenuIngameY(), currentLayerNumber, selectedvill));
                    ui.deSelectIcons();
                    deselect(selectedvill);
                    ui.getMenu().hide();
                } else if (item.getText().contains(MenuItem.SMELT)) {
                    MenuItem[] craftingOptions = new MenuItem[ItemRecipe.FURNACE_RECIPES.length + 1];
                    for (int i = 0; i < ItemRecipe.FURNACE_RECIPES.length; i++) {
                        craftingOptions[i] = new MenuItem(ItemRecipe.FURNACE_RECIPES[i]);
                    }
                    craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
                    ui.showMenu(craftingOptions);
                } else if (item.getText().contains(MenuItem.CRAFT)) {
                    Villager idle = getIdlestVil();
                    ItemRecipe recipe = ui.getMenu().recipeFromMenuOption(MenuItem.CRAFT);
                    if (recipe != null) {
                        idle.setPath(null);
                        Item[] res = new Item[recipe.getResources().length];
                        for (int i = 0; i < res.length; i++) {
                            res[i] = idle.getNearestItemOfType(recipe.getResources()[i]);
                        }
                        idle.addJob(new CraftJob(idle, res, recipe.getProduct(),
                                map[currentLayerNumber].getNearestWorkstation(recipe.getWorkstationClass(), idle.getX()/Tile.SIZE, idle.getY()/Tile.SIZE)));
                        ui.deSelectIcons();
                        ui.getMenu().hide();
                    }
                } else if (item.getText().contains(MenuItem.SMITH)) {
                    MenuItem[] craftingOptions = new MenuItem[WeaponMaterial.values().length + 1];
                    for (int i = 0; i < WeaponMaterial.values().length; i++) {
                        craftingOptions[i] = new MenuItem(capitalize(WeaponMaterial.values()[i].toString().toLowerCase()));
                    }
                    craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
                    ui.showMenu(craftingOptions);
                } else if (MenuItem.isEqualToAnyMaterial(item.getText())) {
                    ItemRecipe[] recipes = ItemRecipe
                            .smithingRecipesFromWeaponMaterial(WeaponMaterial.valueOf(item.getText().toUpperCase()));
                    MenuItem[] craftingOptions = new MenuItem[recipes.length + 1];
                    for (int i = 0; i < WeaponMaterial.values().length; i++) {
                        craftingOptions[i] = new MenuItem(recipes[i]);
                    }
                    craftingOptions[craftingOptions.length - 1] = new MenuItem(MenuItem.CANCEL);
                    ui.showMenu(craftingOptions);
                }
            }

        }
    }

    private void moveCamera(int xScroll, int yScroll) {
        this.xScroll += xScroll;
        this.yScroll += yScroll;
        ui.setOffset(this.xScroll, this.yScroll);
    }

    private void moveCamera() {
        int _yScroll = 0;
        int _xScroll = 0;
        if (Keyboard.isKeyDown(GLFW_KEY_UP) && yScroll > 1) {
            _yScroll -= 6;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_DOWN) && yScroll < ((map[currentLayerNumber].height * Tile.SIZE) - 1 - height)) {
            _yScroll += 6;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_LEFT) && xScroll > 1) {
            _xScroll -= 6;
        }
        if (Keyboard.isKeyDown(GLFW_KEY_RIGHT) && xScroll < ((map[currentLayerNumber].width * Tile.SIZE) - width - 1)) {
            _xScroll += 6;
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
        int x1 = (xScroll + width + Sprite.SIZE);
        int y1 = (yScroll + height + Sprite.SIZE);
        glTranslatef(-xScroll, -yScroll, 0);
        for (Mob i : mobs) {
            if (i.getZ() == currentLayerNumber && i.getX() + 48 >= xScroll && i.getX() - 48 <= x1 && i.getY() + 48 >= yScroll && i.getY() - 48 <= y1) {
                i.render();
            }
        }
        for (Villager i : vills) {
            if (i.getZ() == currentLayerNumber && i.getX() + 48 >= xScroll && i.getX() - 48 <= x1 && i.getY() + 48 >= yScroll && i.getY() - 48 <= y1) {
                i.render();
            }
        }
        for (Villager i : sols) {
            if (i.getZ() == currentLayerNumber && i.getX() + 48 >= xScroll && i.getX() - 48 <= x1 && i.getY() + 48 >= yScroll && i.getY() - 48 <= y1) {
                i.render();
            }
        }
        glTranslatef(xScroll, yScroll, 0);
    }

    public static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }


}
