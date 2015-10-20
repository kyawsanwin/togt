package jp.co.hivelocity.togt;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by kyawsanwin on 7/3/15.
 */
public class MyApplication extends Application {
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public Typeface getMMTypeface() {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/mymm.ttf");
        return typeface;
    }
}
