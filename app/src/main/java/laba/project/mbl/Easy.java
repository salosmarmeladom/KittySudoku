package laba.project.mbl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.appcompat.app.AppCompatActivity;

public class Easy extends AppCompatActivity {

    //создание класса клетка

    private class Cell{
        int value;
        boolean fixed;
        Button bt;

        public Cell(int initvalue, Context THIS)
        {
            value=initvalue;
            fixed= value != 0;
            bt=new Button(THIS);
            bt.setBackgroundResource(R.drawable.cells);
            bt.setTextColor(getColor(R.color.dark_peach));
            if(fixed) {
                bt.setText(String.valueOf(value));
                bt.setBackgroundResource(R.drawable.fixed_cell);
            }
        }
    }

    //создаем таблицу значений
    Cell[][] table;
    String input;
    TableLayout tableLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);


        //Создание TableLayout
        //код из видео инициализация таблицы

        input= "2 9 ? 7 4 3 8 6 1 "+
               "4 ? 1 8 6 5 9 ? 7 "+
               "8 7 6 1 9 2 5 4 3 "+
               "3 8 7 4 5 9 2 1 6 "+
               "6 1 2 3 ? 7 4 ? 5 "+
                "? 4 9 2 ? 6 7 3 8 "+
                "? ? 3 5 2 4 1 8 9 "+
                "9 2 8 6 7 1 ? 5 4 "+
                "1 5 4 9 3 ? 6 7 2 ";

        String[] split = input.split(" ");

        table = new Cell[9][9];
        tableLayout = findViewById(R.id.tableSudoku);

        tableLayout.setGravity(Gravity.CENTER_VERTICAL);




        for (int x = 0; x < 9; x++){
            TableRow tableRow = new TableRow(this);
            for (int y = 0; y < 9; y++){
                String s = split[x*9+y];
                Character c = s.charAt(0);
                table[x][y]= new Cell(c=='?'?0:c-'0',this);
                tableRow.addView(table[x][y].bt);
            }
            tableLayout.addView(tableRow);
        }
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);




        //развернуть игру на весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
    //Системная кнопка назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(Easy.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch(Exception ignored){
        }
    }
}