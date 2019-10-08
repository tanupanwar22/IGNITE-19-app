package com.NITK.ignite;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class MyAnimations {


    public static Animation in(long duration){
        Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(duration);
        return in;
    }

    public static  Animation out(long duration){
        Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(duration);
        return out;
    }

}
