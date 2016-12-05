package com.stupid_game.game.sprites;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

/**
 * Created by aoprisor on 21.11.2016.
 */

public class Points {

    private Vector2 position;
    private Array<Texture> number;
    private Array<Texture> digits;
    private Texture coin = new Texture("coin.png");

    public Points(){
        position = new Vector2(25, 750);
        number = new Array<Texture>();
        digits = new Array<Texture>();

        for(int i = 0; i<10; i++){
            digits.add(new Texture(Integer.toString(i) + ".png"));
        }
    }

    public void update(int points){
        number.clear();
        number.add(coin);

        String strNumber = Integer.toString(points);

        for(int i=0; i< strNumber.length(); i++){
            number.add(
                    digits.get(
                            Character.getNumericValue(strNumber.charAt(i))
                    )
            );
        }


    }

    public Array<Texture> getNumber(){
        return number;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void dispose(){

        coin.dispose();

        for (Texture tx: number){
            tx.dispose();
        }

        for (Texture tx: number){
            tx.dispose();
        }
    }
}
