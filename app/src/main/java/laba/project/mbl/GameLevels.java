package laba.project.mbl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class GameLevels extends Activity {

    AnimationDrawable background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        //анимация фона
        LinearLayout layout = findViewById(R.id.levels_container1);
        layout.setBackgroundResource(R.drawable.background_animation);
        background = (AnimationDrawable) layout.getBackground();
        background.start();
        //убираем строку состояния и панель навигации
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        // Кнопка назад
        Button buttonBack = (Button) findViewById(R.id.back);
        buttonBack.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(GameLevels.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {
            }
        });

        //Кнопка легко
        Button easy = findViewById(R.id.easy);
        easy.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(GameLevels.this, Sudoku.class);
                intent.putExtra("difficulty", 0);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {

            }
        });

        //кнопка средняя сложность
        Button medium = findViewById(R.id.medium);
        medium.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(GameLevels.this, Sudoku.class);
                intent.putExtra("difficulty", 1);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {

            }
        });

        //кнопка сложно
        Button hard = findViewById(R.id.hard);
        hard.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(GameLevels.this, Sudoku.class);
                intent.putExtra(Sudoku.DIFFICULTY, 2);
                startActivity(intent);
                finish();
            } catch (Exception ignored) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception ignored) {
        }
    }
}