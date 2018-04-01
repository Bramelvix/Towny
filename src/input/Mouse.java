package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import main.Game;
import map.Tile;

public class Mouse extends MouseAdapter implements MouseInputListener, MouseWheelListener {
    // used to register the mouse on the screen
    private Game game;
    private int mouseX = -1; // x and y coord on the screen, in ingame pixels
    // (/3), WITH OFFSET
    private int mouseY = -1;
    private int mouseB = -1; // the mouse button pressed
    public int mouseTileX = -1; // the x and y of the tiles in the game that
    // the mouse is on
    public int mouseTileY = -1;
    private int trueX = -1; // x and y coord on the screen, in ingame pixels
    // (/3), WITHOUT OFFSET
    private int trueY = -1;
    private int trueXpixels = -1; // x and y coord on the screen, in acutal
    // pixels, WITHOUT OFFSET
    private int trueYpixels = -1;
    private boolean clickedLinks; // has the left mousebutton been clicked
    private boolean clickedRechts; // has the right mousebutton been clicked
    private boolean drag; // has the mouse been dragged
    private int mouseWheelDirection; // has the mousewheel moved
    private boolean releasedLeft; // has the left mouse been released
    private boolean releasedRight; // has the left mouse been released

    private boolean isMiddlePressed = false;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    // constructor
    public Mouse(Game game) {
        this.game = game;
    }

    // getters
    public int getX() {
        return mouseX;
    }

    public int getMouseB() {
        return mouseB;
    }

    public int getY() {
        return mouseY;
    }

    public int getTileY() {
        return mouseTileY;
    }

    public int getTileX() {
        return mouseTileX;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (arg0.getButton() == 1) {
            clickedLinks = true;
        }
        if (arg0.getButton() == 3) {
            clickedRechts = true;
        }

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        mouseB = arg0.getButton();
        if (arg0.getButton() == 2) {
            isMiddlePressed = true;
            trueXpixels = arg0.getX();
            trueYpixels = arg0.getY();
            trueX = trueXpixels / Game.SCALE;
            trueY = trueYpixels / Game.SCALE;
            mouseX = trueX + game.xScroll;
            mouseY = trueY + game.yScroll;
            dragOffsetX = mouseX;
            dragOffsetY = mouseY;
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        mouseB = -1;
        if (arg0.getButton() == 1) {
            releasedLeft = true;
        }
        if (arg0.getButton() == 2) {
            isMiddlePressed = false;
        }
        if (arg0.getButton() == 3) {
            releasedRight = true;
        }

    }

    public boolean getClickedLeft() {
        return clickedLinks;
    }

    public boolean getClickedRight() {
        return clickedRechts;
    }

    public boolean getReleasedLeft() {
        return releasedLeft;
    }

    public boolean getReleasedRight() {
        return releasedRight;
    }

    public void reset() {
        clickedLinks = false;
        clickedRechts = false;
        releasedLeft = false;
        releasedRight = false;
        if (mouseB != 1) {
            drag = false;
        }
        mouseWheelDirection = 0;

    }

    public boolean getDrag() {
        return drag;
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        if (clickedLinks) {
            drag = true;
        }
        trueXpixels = arg0.getX();
        trueYpixels = arg0.getY();
        trueX = trueXpixels / Game.SCALE;
        trueY = trueYpixels / Game.SCALE;
        mouseX = trueX + game.xScroll;
        mouseY = trueY + game.yScroll;
        mouseTileX = mouseX / 16;
        mouseTileY = mouseY / 16;

        if (isMiddlePressed) {
            int deltaX = (game.xScroll)-mouseX;
            int deltaY = (game.yScroll)-mouseY;
            if (deltaX + dragOffsetX >= 0 && deltaX + dragOffsetX <= (game.map[game.currentLayerNumber].width * Tile.SIZE) - game.width) {
                game.xScroll = deltaX + dragOffsetX;
            }
            if (deltaY + dragOffsetY >= 0 && deltaY + dragOffsetY <= (game.map[game.currentLayerNumber].height * Tile.SIZE) - game.height)
            game.yScroll = deltaY+dragOffsetY;
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        trueXpixels = arg0.getX();
        trueYpixels = arg0.getY();
        trueX = trueXpixels / Game.SCALE;
        trueY = trueYpixels / Game.SCALE;
        mouseX = trueX + game.xScroll;
        mouseY = trueY + game.yScroll;
        mouseTileX = mouseX / 16;
        mouseTileY = mouseY / 16;

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelDirection = e.getWheelRotation();
    }

    public int getTrueX() {
        return trueX;
    }

    public int getTrueY() {
        return trueY;
    }

    public int getTrueXPixels() {
        return trueXpixels;
    }

    public int getTrueYPixels() {
        return trueYpixels;
    }

    public int getMouseWheelMoved() {
        return mouseWheelDirection;
    }

}
