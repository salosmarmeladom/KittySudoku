package laba.project.mbl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class Medium extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);

        //развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

           }


    //системная кнопка назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(Medium.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch(Exception e){
        }
    }
}