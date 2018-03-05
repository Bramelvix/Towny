package entity.item;

import entity.Entity;
import entity.mob.Villager;
import graphics.Sprite;
import graphics.SpriteHashtable;
import org.json.*;

import java.io.*;

public class Item extends Entity{
	public static final Item logs = new Item("logs", SpriteHashtable.get(58), "Wooden logs");
	public static final Item iron_bar = new Item("iron bar", SpriteHashtable.get(59), "a bar of solid iron");
	public static final Item gold_bar = new Item("gold bar", SpriteHashtable.get(60), "a bar of solid gold");
	public static final Item copper_bar = new Item("copper bar", SpriteHashtable.get(61), "a bar of solid copper");
	public static final Item iron_ore = new Item("iron ore", SpriteHashtable.get(62), "iron ore");
	public static final Item copper_ore = new Item("copper ore", SpriteHashtable.get(66), "copper ore");
	public static final Item gold_ore = new Item("gold ore", SpriteHashtable.get(63), "gold ore");
	public static final Item coal_ore = new Item("coal ore", SpriteHashtable.get(65), "coal ore");
	public static final Item crystal_ore = new Item("uncut crystal", SpriteHashtable.get(67), "an uncut crystal");
	public static final Item stone = new Item("stone", SpriteHashtable.get(64), "some stones");
	public static final Item cut_crystal = new Item("cut crystal", SpriteHashtable.get(68), "a cut crystal");

	private final int id;
	protected String tooltip; // the item's tooltip
	private Villager reservedVil; // the villager that plans to pick the item
									// up, or is already holding it

	// basic constructors
	protected Item(String name, int x, int y, Sprite sprite, String tooltip, boolean visible) {
		super(x, y);
		this.sprite = sprite;
		setVisible(visible);
		super.setName(name);
		this.tooltip = tooltip;
		id = 0;
	}

	public Item(int id, int x, int y, boolean visible){
		super(x, y);
		this.id = id;
		setVisible(visible);
		JSONArray itemList =  ItemList.jsonObj.getJSONArray("itemList");
		for (int i = 0; i < itemList.length(); i++) {
			JSONObject element = itemList.getJSONObject(i);
			if (element.getInt("id") == id){
				super.setName(element.getString("name"));
				this.sprite = SpriteHashtable.get(element.getInt("sprite"));
				this.tooltip = element.getString("tooltip");
			}
		}
	}

	public boolean isSameType(Item item) {
		return item.getName().equals(getName());
	}

	protected Item(String name, int x, int y, Sprite sprite, boolean visible) {
		this(name, x, y, sprite, "", visible);
	}

	protected Item(String name, Sprite sprite, String tooltip) {
		super.setName(name);
		this.sprite = sprite;
		this.tooltip = tooltip;
		setVisible(true);
		id = 0;
	}

	public Item copy() {
		return new Item(this.getName(), this.x, this.y, this.sprite, this.getToolTip(), this.isVisible());
	}

	public Item copy(int x, int y) {
		Item copy = this.copy();
		copy.setLocation(x, y);
		return copy;
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public String getToolTip() {
		return tooltip;
	}

	public boolean isReserved(Villager vil) {
		return reservedVil == null || reservedVil.equals(vil);
	}

	public void setReserved(Villager vil) {
		reservedVil = vil;
	}

	public void removeReserved() {
		reservedVil = null;
	}

	private static class ItemList {
		private static JSONTokener file;
		protected static JSONObject jsonObj;

		static{
			try {
				file = new JSONTokener(new FileInputStream("src/entity/item/items.json"));
				jsonObj = new JSONObject(file);
			} catch (FileNotFoundException e) {
				System.out.println("File not found: items.json");
			}
		}
	}

}