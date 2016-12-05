package com.stupid_game.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by aoprisor on 13.11.2016.
 */

public class Tube {
    public static final int TUBE_WIDHT = 150;

    private static final int FLUCTUATION = 200;
    private static final int TUBE_GAP = 150;
    private static final int LOWEST_OPENING = 300;

    private Vector2 posTopTube, posBotTube;
    private Texture topTube, bottomTube;

    private Rectangle boundsTop, boundsBot;

    private Random rand;

    public Tube(float x){
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");

        rand = new Random();

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public void reposition(float x){

        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsBot.setPosition(posBotTube.x, posBotTube.y);
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBot) || player.overlaps(boundsTop);
    }

    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
