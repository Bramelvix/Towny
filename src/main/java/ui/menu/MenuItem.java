package ui.menu;

import entity.Entity;
import entity.dynamic.mob.work.recipe.BuildingRecipe;
import entity.dynamic.mob.work.recipe.ItemRecipe;
import entity.dynamic.mob.work.recipe.Recipe;
import events.PointerMoveEvent;
import events.Subscription;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import util.vectors.Vec2f;
import util.vectors.Vec4f;

import java.util.function.Consumer;

import static events.EventListener.onlyWhen;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MenuItem {

	private final String text; // text on the menuitem
	private final Vec2f position; // x and y of top left corner
	private float width; // width of the menuitem
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
	public static final String SOW = "Sow seeds";
	public static final String HARVEST = "Harvest";
	private Recipe recipe;
	private Entity entity;
	private final Subscription subscriptionMove;
	private final Subscription subscriptionClick;

	// constructor
	public MenuItem(String text, Consumer<MenuItem> onClick) {
		this.text = text;
		position = new Vec2f(0);
		subscriptionClick = PointerInput.getInstance().on(PointerInput.EType.PRESSED, onlyWhen(
				event -> event.button() == GLFW_MOUSE_BUTTON_LEFT && hover,
				event -> onClick.accept(this)
		));
		subscriptionMove = PointerInput.getInstance().on(PointerInput.EType.MOVE, this::update);
	}

	public MenuItem(String text, Runnable onClick) {
		this(text, item -> onClick.run());
	}

	public MenuItem(String text, Entity e, Consumer<MenuItem> onClick) {
		this(text, onClick);
		this.entity = e;
	}


	public MenuItem(ItemRecipe recipe, Consumer<MenuItem> onClick) {
		this(CRAFT + " " + recipe.getRecipeName(), onClick);
		this.recipe = recipe;
	}

	public MenuItem(BuildingRecipe recipe, Consumer<MenuItem> onClick) {
		this(BUILD + " " + recipe.getRecipeName(), onClick);
		this.recipe = recipe;
	}

	public void init(Menu menu) {
		position.x = menu.getX();
		position.y = menu.getYLocForMenuItem();
		width = menu.getWidth();
	}

	public void destroy() {
		if (subscriptionClick != null) {
			subscriptionClick.unsubscribe();
		}
		if (subscriptionMove != null) {
			subscriptionMove.unsubscribe();
		}
	}

	public <T extends Entity> T getEntity(Class<T> clazz) {
		return clazz.cast(entity);
	}

	// rendering the menuitem's text
	public void render() {
		OpenGLUtils.menuItemDraw(position, text, hover);
	}

	// updating the mouse hover
	public void update(PointerMoveEvent event) {
		hover = (event.x >= position.x && event.x <= position.x + width && event.y >= position.y && event.y <= position.y + 20);
	}

	public String getText() {
		return text;
	}

	public static String defaultText(String text, Entity e) {
		return text + " " + e.getName();
	}

	public <T extends Recipe> T getRecipe(Class<T> clazz) {
		return clazz.cast(recipe);
	}

}
