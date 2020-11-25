package graphics.ui.menu;

import entity.Entity;
import entity.dynamic.mob.work.recipe.BuildingRecipe;
import entity.dynamic.mob.work.recipe.ItemRecipe;
import entity.dynamic.mob.work.recipe.Recipe;
import events.Subscription;
import graphics.opengl.OpenGLUtils;
import input.PointerInput;
import input.PointerMoveEvent;
import util.vectors.Vec2f;

import java.util.Optional;
import java.util.function.Consumer;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static events.EventListener.onlyWhen;

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
	public MenuItem(String text, Consumer<MenuItem> onClick, PointerInput pointer) {
		this.text = text;
		position = new Vec2f(0);
		subscriptionClick = pointer.on(PointerInput.EType.PRESSED, onlyWhen(
			event -> event.button == GLFW_MOUSE_BUTTON_LEFT && hover,
			event -> onClick.accept(this)
		));
		subscriptionMove = pointer.on(PointerInput.EType.MOVE, this::update);
	}

	public MenuItem(String text, Entity e, Consumer<MenuItem> onClick, PointerInput pointer) {
		this(text, onClick, pointer);
		this.entity = e;
	}


	public MenuItem(ItemRecipe recipe, Consumer<MenuItem> onClick, PointerInput pointer) {
		this(CRAFT + " " + recipe.getRecipeName(), onClick, pointer);
		this.recipe = recipe;
	}

	public MenuItem(BuildingRecipe recipe, Consumer<MenuItem> onClick, PointerInput pointer) {
		this(BUILD + " " + recipe.getRecipeName(), onClick, pointer);
		this.recipe = recipe;
	}

	public void init(Menu menu) {
		position.x = menu.getX();
		position.y = menu.getYLocForMenuItem();
		width = menu.getWidth();
	}

	public void destroy() {
		if (subscriptionClick != null) { subscriptionClick.unsubscribe(); }
		if (subscriptionMove != null) { subscriptionMove.unsubscribe(); }
	}

	public Entity getEntity() {
		return entity;
	}

	// rendering the menuitem's text
	public void render() {
		OpenGLUtils.menuItemDraw(position, text, hover);
	}

	// updating the mouse hover
	public void update(PointerMoveEvent event) {
		hover = (event.x >= position.x && event.x <= position.x + width && event.y >= position.y && event.y <= position.y + 10);
	}

	// getter
	public boolean clicked(PointerInput pointer) {
		return hover && pointer.wasPressed(GLFW_MOUSE_BUTTON_LEFT);
	}

	public String getText() {
		return text;
	}

	public static String defaultText(String text, Entity e) {
		return text + " " + e.getName();
	}

	@SuppressWarnings("unchecked") // again, shouldnt ever be a problem
	public <T extends Recipe> T getRecipe() {
		return (T) recipe;
	}

	public <T extends Recipe> Optional<T> getRecipe(Class<T> clazz) {
		return clazz.isInstance(recipe) ? Optional.of(clazz.cast(recipe)) : Optional.empty();
	}

}
