package com.example.a2048.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048.R;
import com.example.a2048.listView.ListMenu;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        TextView title = (TextView) findViewById(R.id.appTitle);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        Animation fade1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in_img);
        title.startAnimation(fade1);
        logo.startAnimation(fade2);

        fade2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,
                        ListMenu.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation spinin = AnimationUtils.loadAnimation(this, R.anim.custom_anim);
        LayoutAnimationController controller = new LayoutAnimationController(spinin);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            row.setLayoutAnimation(controller);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        TableLayout table = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            row.clearAnimation();
        }
    }
}