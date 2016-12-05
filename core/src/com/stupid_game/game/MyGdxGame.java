package com.stupid_game.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stupid_game.game.States.GameStateManager;
import com.stupid_game.game.States.MenuStates;

public class MyGdxGame extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Stupid Game";

    private SpriteBatch batch;
    private GameStateManager gsm;

    private Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("halfbrick-studios-fruit-ninja-halloween-01-menu.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

        gsm.push(new MenuStates(gsm));
        Gdx.gl.glClearColor(1, 0, 0, 1);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }

}
