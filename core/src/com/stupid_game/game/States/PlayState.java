package com.stupid_game.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.stupid_game.game.MyGdxGame;
import com.stupid_game.game.sprites.Bird;
import com.stupid_game.game.sprites.Points;
import com.stupid_game.game.sprites.Tube;

/**
 * Created by aoprisor on 13.11.2016.
 */


public class PlayState extends State {

    private static final int TUBE_SPACING = 300;
    private static final int TUBE_COUNT = 5;
    private static final int GROUND_Y_OFFSET = 0;
    private int points = 0;

    private Texture ground;
    private Bird bird;
    private Texture bg;
    private Points pts;

    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    private Sound coinSound;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(100, 400);
        bg = new Texture("bg.jpg");
        ground = new Texture("ground.png");
        pts = new Points();

        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.mp3"));

        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.position.x - cam.viewportWidth/2 + ground.getWidth(), GROUND_Y_OFFSET);

        cam.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        tubes = new Array<Tube>();

        for(int i=1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i*TUBE_SPACING + Tube.TUBE_WIDHT));
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;

        for(int i = 0; i< tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDHT + TUBE_SPACING)*TUBE_COUNT) - 750);
            }

            if(tube.collides(bird.getBounds())) {
                gsm.set(new MenuStates(gsm));
            }

            if((bird.getBounds().x >= tube.getPosBotTube().x + tube.TUBE_WIDHT / 2) && (bird.getBounds().x <= tube.getPosBotTube().x + tube.TUBE_WIDHT / 2 + 5)){
                coinSound.play(0.25f);
                points++;
            }

            pts.update(points);
        }

        if(bird.getBounds().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new MenuStates(gsm));

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for(Tube tube : tubes) {
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        int i = 0;
        int offset;
        int additOffset = 0;

        if (pts.getNumber().size > 2) {
             additOffset = (pts.getNumber().size - 2) * 25;
        }

        System.out.print("Aditional offset:"+additOffset);

        while (pts.getNumber().size > 0){
            offset = (190 +25*i) - additOffset;
            sb.draw(pts.getNumber().pop(), cam.position.x  - offset, cam.position.y + 350);
            i++;
        }

        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();

        for(Tube tube : tubes){
            tube.dispose();
        }

        ground.dispose();

        pts.dispose();

        coinSound.dispose();

        System.out.print("Dispose");
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth/2) > groundPos1.x+ground.getWidth())
            groundPos1.add(ground.getWidth()*2, 0);
        if(cam.position.x - (cam.viewportWidth/2) > groundPos2.x+ground.getWidth())
            groundPos2.add(ground.getWidth()*2, 0);
    }
}
