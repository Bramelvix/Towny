package graphics.ui.menu;

import entity.Entity;
import entity.dynamic.mob.work.BuildingRecipe;
import entity.dynamic.mob.work.ItemRecipe;
import entity.dynamic.mob.work.Recipe;
import graphics.OpenglUtils;
import input.MouseButton;
import input.MousePosition;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MenuItem {
    private String text; // text on the menuitem
    private int x, y; // x and y of top left corner
    private int width; // width of the menuitem
    private boolean hover; // is the mouse hovering over the item
    // some static strings to use as menuitem texts
    // TODO theres probably a better way to do this
    public static final String CANCEL = "Cancel";
    public static final String MOVE = "Move";
    public static final String CHOP = "Chop";
    public static final String MINE = "Mine";
    public static final String BUILD = "Build";
    public static final String PICKUP = "Pick up";
    public static final String DROP = "Drop";
    public static final String FIGHT = "Fight";
    public static final String EQUIP = "Equip";
    public static final String WEAR = "Wear";
    public static final String CRAFT = "Craft";
    public static final String SMELT = "Smelt";
    public static final String SMITH = "Smith";
    public static final String IRON = "Iron";
    public static final String COPPER = "Copper";
    public static final String GOLD = "Gold";
    public static final String CRYSTAL = "Crystal";
    public static final String WOOD = "Wood";
    private Recipe recipe;
    private Entity entity;

    // constructor
    public MenuItem(String text) {
        this.text = text;

    }

    public MenuItem(String text, Entity e) {
        this(text + " " + e.getName());
        this.entity = e;

    }

    public MenuItem(ItemRecipe recipe) {
        this.text = CRAFT + " " + recipe.getRecipeName();
        this.recipe = recipe;
    }

    public MenuItem(BuildingRecipe recipe) {
        this.text = BUILD + " " + recipe.getRecipeName();
        this.recipe = recipe;
    }

    public void init(Menu menu) {
        x = menu.getX();
        width = menu.getWidth();
        y = menu.getYLocForMenuItem();
    }

    public Entity getEntity() {
        return entity;
    }

    // rendering the menuitem's text
    public void render() {
        OpenglUtils.menuItemDraw(x, y, text, hover);
    }

    // updating the mouse hover
    public void update() {
        hover = ((((MousePosition.getTrueXPixels()) >= x) && ((MousePosition.getTrueXPixels()) <= x + width)
                && ((MousePosition.getTrueYPixels()) >= y) && ((MousePosition.getTrueYPixels()) <= y + 10)));
    }

    // getter
    public boolean clicked() {
        return hover && MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT);
    }

    public String getText() {
        return text;
    }

    @SuppressWarnings("unchecked") // again, shouldnt ever be a problem
    public <T extends Recipe> T getRecipe() {
        return (T) recipe;
    }

    public static boolean isEqualToAnyMaterial(String input) {
        return input.equals(MenuItem.WOOD) || input.equals(MenuItem.IRON) || input.equals(MenuItem.COPPER)
                || input.equals(MenuItem.GOLD) || input.equals(MenuItem.CRYSTAL);
    }

}
