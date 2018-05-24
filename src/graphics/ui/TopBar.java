package graphics.ui;

import graphics.OpenglUtils;
import graphics.ui.icon.Icon;
import input.MouseButton;
import main.Game;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class TopBar {
    private int x, y; // x and y of the top left corner
    private int width, height; // width and height
    private int vilcount, solcount; // amount of villagers and soldiers
    private Icon pause, play;
    private Icon fast,slow;
    private Icon sol,vil;
    private static final float r = 0.3568f,g =0.3686f,b =0.3882f,a = 0.43137f; //colour for background
    private byte speed = 6;

    // constructor
    TopBar() {
        width = 270;
        height = 85;
        this.x = (Game.width * Game.SCALE - width) / 2;
        this.y = 5;
        init();
    }

    // intialise
    private void init() {
            pause = new Icon(x + 120, y + 25, "/res/icons/pause-button.png", 0.060f);
            play = new Icon(x + 120, y + 25, "/res/icons/play-button.png", 0.060f);
            fast = new Icon(x+160,y+30,"/res/icons/fast.png",1.0f);
            slow = new Icon(x+75,y+30,"/res/icons/slow.png",1.0f);
            sol = new Icon(x+210,y+17,"/res/icons/soldier.png",1.0f);
            vil = new Icon(x+10,y+17,"/res/icons/villager.png",1.0f);
    }

    // update the villager and soldier counts
    public void updateCounts(int solcount, int vilcount) {
        this.solcount = solcount;
        this.vilcount = vilcount;
    }

    public void update() {
        play.update();
        pause.update();
        slow.update();
        fast.update();
        sol.update();
        vil.update();
        if (clickedOnPause()) {
            togglePause();
        } else if (clickedOnFast()) {
            upSpeed();
        } else if (clickedOnSlow()) {
            downSpeed();
        }
    }

    public byte getSpeed() {
        if (speed == 0) {
            return 6;
        }
        return speed;
    }

    private void upSpeed() {
        if (speed < 9) {
            speed++;
        }
    }

    private void downSpeed() {
        if (speed > 3) {
            speed--;
        }
    }

    // render the topbar on the screen
    public void render() {
        OpenglUtils.drawFilledSquare(x, y, width, height, r, g, b, a);
        vil.render();
        sol.render();
        slow.render();
        fast.render();
        if (!Game.paused) {
            pause.render();
        } else {
            play.render();
        }
        OpenglUtils.drawText("Villagers", x + 10, y + 5);
        OpenglUtils.drawText(vilcount + "", x + 30, y + 70);
        OpenglUtils.drawText("Speed: " + (speed - 2), x + 95, y + 65);
        OpenglUtils.drawText("Soldiers", x + 200, y + 5);
        OpenglUtils.drawText(solcount + "", x + 230, y + 70);
    }

    // has the user clicked on the pause button
    private boolean clickedOnPause() {
        if (MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT)) {
            if (Game.paused) {
                return play.hoverOn();
            } else {
                return pause.hoverOn();
            }
        }
        return false;
    }

    private boolean clickedOnFast() {
        return fast.hoverOn() && MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT);
    }

    private boolean clickedOnSlow() {
        return slow.hoverOn() && MouseButton.wasPressed(GLFW_MOUSE_BUTTON_LEFT);
    }

    // toggle pausing
    private void togglePause() {
        if (Game.paused) {
            Game.paused = false;
            speed = 6;
        } else {
            Game.paused = true;
            speed = 2;
        }
    }

}
