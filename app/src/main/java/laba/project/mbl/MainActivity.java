package laba.project.mbl;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    AnimationDrawable kittyAnimation;
    AnimationDrawable backgroundAnimation;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //убираем строку состояния и панель навигации
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //устанавливаем анимацию фона и картинки
        imageView = findViewById(R.id.kitty);
        ConstraintLayout constraintLayout = findViewById(R.id.main_container);
        imageView.setBackgroundResource(R.drawable.kitty_animation);
        constraintLayout.setBackgroundResource(R.drawable.background_animation);
        kittyAnimation = (AnimationDrawable) imageView.getBackground();
        backgroundAnimation = (AnimationDrawable) constraintLayout.getBackground();
        kittyAnimation.start();
        backgroundAnimation.start();
        Button buttonStart = findViewById(R.id.sayMeow);
        buttonStart.setOnClickListener(view -> {
            try {
                 Intent intent = new Intent(MainActivity.this, GameLevels.class);
                 startActivity(intent);
                 finish();
            }catch (Exception ignored){
            }
        });
    }
    //выход на даблклик

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "tap again to exit...(￣︿￣))", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}