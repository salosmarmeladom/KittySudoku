package laba.project.mbl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;


public class Sudoku extends Activity implements View.OnClickListener {

    AnimationDrawable background;
    public static final String DIFFICULTY = "difficulty";
    Cell[][] sudokuTable;
    TableLayout mainTable;
    int[][] table = new int[9][9];
    int osX;
    int osY;
    int difficulty;
    Cell focusedCell;
    int square = 0;
    Dialog congratulations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
        //убираем строку состояния и панель навигации
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //получение сложности из интента
        difficulty = (Integer) getIntent().getExtras().get(DIFFICULTY);
        //создание диалогового окна при выигрыше
        congratulations = new Dialog(this);
        congratulations.requestWindowFeature(Window.FEATURE_NO_TITLE);
        congratulations.setContentView(R.layout.congratulations);
        //congratulations.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        congratulations.setCancelable(true);
        //установление фоновой анимации
        LinearLayout layout = findViewById(R.id.sudoku_container1);
        layout.setBackgroundResource(R.drawable.background_animation);
        background = (AnimationDrawable) layout.getBackground();
        background.start();
        //создание диалогового окна поздравления

        //получиение ссылок на кнопки выбора значений
        final Button selector1 = findViewById(R.id.one);
        final Button selector2 = findViewById(R.id.two);
        final Button selector3 = findViewById(R.id.three);
        final Button selector4 = findViewById(R.id.four);
        final Button selector5 = findViewById(R.id.five);
        final Button selector6 = findViewById(R.id.six);
        final Button selector7 = findViewById(R.id.seven);
        final Button selector8 = findViewById(R.id.eight);
        final Button selector9 = findViewById(R.id.nine);
        final Button clear = findViewById(R.id.clear);
        //привязываем слушателя к кнопкам
        selector1.setOnClickListener(this);
        selector2.setOnClickListener(this);
        selector3.setOnClickListener(this);
        selector4.setOnClickListener(this);
        selector5.setOnClickListener(this);
        selector6.setOnClickListener(this);
        selector7.setOnClickListener(this);
        selector8.setOnClickListener(this);
        selector9.setOnClickListener(this);
        clear.setOnClickListener(this);
        //Создание TableLayout + генерация таблицы значений
        sudokuTable = new Cell[9][9];
        mainTable = findViewById(R.id.tableSudoku);
        mainTable.setPadding(1,1,1,1);
        table = generateNewTable();
        osX = 0;
        osY = 0;

        //создение таблицы значений объектов класса Cell
        for (int x = 0; x < 9; x++){
            for (int y = 0; y < 9; y++){
                //с помощью Math.random заполняем клетку значением или оставляем пустой в зависимости от выбранной сложности
                if (difficulty == 0){
                    sudokuTable[x][y] = new Cell(((int) (Math.random()*2)==0?0:table[x][y]), table[x][y], x, y, 0,this);
                } else if (difficulty == 1){
                    sudokuTable[x][y] = new Cell(((int) (Math.random()*100)>=40?0:table[x][y]), table[x][y], x, y, 0,this);
                } else {
                    sudokuTable[x][y] = new Cell(((int) (Math.random()*100)>=30?0:table[x][y]), table[x][y], x, y, 0,this);
                }
            }
        }

        //Разметка TableLayout на дочерние квадраты
        for (int i = 0; i < 3; i++){
            TableRow tableRow = new TableRow(this);
            for(int j = 0; j < 3; j++) {
                TableLayout tableLayout = new TableLayout(this);
                //инициализация дочернего квадрата
                squareInitialization(tableLayout, sudokuTable, osX, osY, square);
                tableLayout.setBackgroundResource(R.drawable.inside_table_borders);
                //выравнивание дочернего квадрата
                tableLayout.setShrinkAllColumns(true);
                tableLayout.setStretchAllColumns(true);
                //добавление дочернего квадрата в строку
                tableRow.addView(tableLayout);
                osY+=3;
                square++;
            }
            mainTable.addView(tableRow);
            osY = 0;
            osX+=3;
        }

        //выравнивание таблицы
        mainTable.setShrinkAllColumns(true);
        mainTable.setStretchAllColumns(true);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if(focusedCell == null || focusedCell.fixed){
            return;
        }
        switch (view.getId()) {
            case R.id.one:
                focusedCell.value = 1;
                focusedCell.bt.setText("1");
                break;
            case R.id.two:
                focusedCell.value = 2;
                focusedCell.bt.setText("2");
                break;
            case R.id.three:
                focusedCell.value = 3;
                focusedCell.bt.setText("3");
                break;
            case R.id.four:
                focusedCell.value = 4;
                focusedCell.bt.setText("4");
                break;
            case R.id.five:
                focusedCell.value = 5;
                focusedCell.bt.setText("5");
                break;
            case R.id.six:
                focusedCell.value = 6;
                focusedCell.bt.setText("6");
                break;
            case R.id.seven:
                focusedCell.value = 7;
                focusedCell.bt.setText("7");
                break;
            case R.id.eight:
                focusedCell.value = 8;
                focusedCell.bt.setText("8");
                break;
            case R.id.nine:
                focusedCell.value = 9;
                focusedCell.bt.setText("9");
                break;
            case R.id.clear:
                sudokuTable[focusedCell.coordinateX][focusedCell.coordinateY].value = 0;
                sudokuTable[focusedCell.coordinateX][focusedCell.coordinateY].bt.setText("");
                focusedCell.value = 0;
                focusedCell.bt.setText("");
                break;
        }
       valueChecking(focusedCell);
        if(isFull()){
            congratulations.show();
        }
    }

    public void backButton(View view) {
        try {
            Intent intent = new Intent(Sudoku.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch(Exception ignored){
        }
    }

    //класс Клетка, обработка событий ввода
    private class Cell {

        int trueValue;
        int value;
        boolean fixed;
        Button bt;
        int coordinateX;
        int coordinateY;
        int square;

        //конструктор класса Клетка
        public Cell(int value, int trueValue, int coordinateX, int coordinateY, int square, Context THIS) {
            this.value = value;
            this.trueValue = trueValue;
            fixed = value != 0;
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            this.square = square;
            bt = new Button(THIS);
            bt.setBackgroundResource(R.drawable.cells);
            bt.setTextColor(getResources().getColor(R.color.dark_violet));
            bt.setTextSize(20);
            bt.setFocusableInTouchMode(true);
            if(fixed) {
                bt.setText(String.valueOf(value));
                bt.setBackgroundResource(R.drawable.fixed_cell);
            }
            bt.setOnFocusChangeListener((view, hasFocus) -> {
                if(hasFocus) {
                    if(this.value!=0){
                        highlightSameValues(this);
                        if(!this.fixed){
                            focusedCell = this;
                        }
                    } else{
                        highlightRowsColumns(this);
                        focusedCell = this;
                    }
                } else {
                    removeHighlightRowsColumns();
                }
            });
        }
    }

    //генерация таблицы
    public int[][] generateNewTable(){
        int[] line1 = new int[]{3, 2, 5, 7, 4, 6, 8, 9, 1};
        int[] line2 = new int[]{6, 7, 8, 1, 3, 9, 4, 5, 2};
        int[] line3 = new int[]{1, 9, 4, 8, 5, 2, 3, 6, 7};
        int[] line4 = new int[]{5, 1, 2, 9, 7, 4, 6, 8, 3};
        int[] line5 = new int[]{9, 4, 7, 6, 8, 3, 1, 2, 5};
        int[] line6 = new int[]{8, 3, 6, 5, 2, 1, 9, 7, 4};
        int[] line7 = new int[]{2, 6, 3, 4, 9, 5, 7, 1, 8};
        int[] line8 = new int[]{4, 8, 1, 2, 6, 7, 5, 3, 9};
        int[] line9 = new int[]{7, 5, 9, 3, 1, 8, 2, 4, 6};

        int[][] table = new int[][]{line1, line2, line3,
                                         line4, line5, line6,
                                         line7, line8, line9};
        int times = (int) (Math.random()*10+10);
        for (int x = 0; x <= times; x++){
            swapRows(table);
            swapColumns(table);
        }
        return table;
    }

    //перемешивание рядов
    public void swapRows(int[][] table){
        int currentRow;
        int secondRow;
        int thirdRow;
        int row = (int) (Math.random()*3);
        switch (row) {
            case 0:
                currentRow = row;
                secondRow = 1;
                thirdRow = 2;
                shiftRows(currentRow, secondRow, thirdRow, table);
            case 1:
                currentRow = row;
                secondRow = 0;
                thirdRow = 2;
                shiftRows(currentRow, secondRow, thirdRow, table);
            case 2:
                currentRow = row;
                secondRow = 0;
                thirdRow = 1;
                shiftRows(currentRow, secondRow, thirdRow, table);
        }
        row = (int) (Math.random()*3+3);
        switch (row) {
            case 3:
                currentRow = row;
                secondRow = 4;
                thirdRow = 5;
                shiftRows(currentRow, secondRow, thirdRow, table);
            case 4:
                currentRow = row;
                secondRow = 3;
                thirdRow = 5;
                shiftRows(currentRow, secondRow, thirdRow, table);
            case 5:
                currentRow = row;
                secondRow = 3;
                thirdRow = 4;
                shiftRows(currentRow, secondRow, thirdRow, table);
        }
        row = (int) (Math.random()*3+6);
        switch (row) {
            case 6:
                currentRow = row;
                secondRow = 7;
                thirdRow = 8;
                shiftRows(currentRow, secondRow, thirdRow, table);
            case 7:
                currentRow = row;
                secondRow = 6;
                thirdRow = 8;
                shiftRows(currentRow, secondRow, thirdRow, table);
            case 8:
                currentRow = row;
                secondRow = 6;
                thirdRow = 7;
                shiftRows(currentRow, secondRow, thirdRow, table);
        }
    }

    //перемешивание колонок
    public void swapColumns(int[][] table){
        int currentColumn;
        int secondColumn;
        int thirdColumn;
        int column = (int) (Math.random()*3);
        switch (column) {
            case 0:
                currentColumn = column;
                secondColumn = 1;
                thirdColumn = 2;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
            case 1:
                currentColumn = column;
                secondColumn = 0;
                thirdColumn = 2;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
            case 2:
                currentColumn = column;
                secondColumn = 0;
                thirdColumn = 1;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
        }
        column = (int) (Math.random()*3+3);
        switch (column) {
            case 3:
                currentColumn = column;
                secondColumn = 4;
                thirdColumn = 5;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
            case 4:
                currentColumn = column;
                secondColumn = 3;
                thirdColumn = 5;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
            case 5:
                currentColumn = column;
                secondColumn = 3;
                thirdColumn = 4;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
        }
        column = (int) (Math.random()*3+6);
        switch (column) {
            case 6:
                currentColumn = column;
                secondColumn = 7;
                thirdColumn = 8;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
            case 7:
                currentColumn = column;
                secondColumn = 6;
                thirdColumn = 8;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
            case 8:
                currentColumn = column;
                secondColumn = 6;
                thirdColumn = 7;
                shiftColumns(currentColumn, secondColumn, thirdColumn, table);
        }
    }

    //меняет местами два ряда в триплете
    public void shiftRows(int currentRow, int secondRow, int thirdRow, int[][] table) {
        int[] reserve;
        int slide = (int) (Math.random()*2);
        reserve = table[currentRow];
        if(slide == 0){
            table[currentRow] = table[secondRow];
            table[secondRow] = reserve;
        } else {
            table[currentRow] = table[thirdRow];
            table[thirdRow] = reserve;
        }
    }

    //меняет местами две колонки в триплете
    public void shiftColumns(int currentColumn, int secondColumn, int thirdColumn, int[][]table){
        int reserve;
        int slide = (int) (Math.random()*2);
        if(slide==0){
            for(int x = 0; x < 9; x++){
                reserve = table[x][currentColumn];
                table[x][currentColumn] = table[x][secondColumn];
                table[x][secondColumn] = reserve;
            }
        } else {
            for(int x = 0; x < 9; x++){
                reserve = table[x][currentColumn];
                table[x][currentColumn] = table[x][thirdColumn];
                table[x][thirdColumn] = reserve;
            }
        }
    }

    //инициализирует таблицу по квадратам
    public void squareInitialization(TableLayout square, Cell[][] sudokuTable, int osX, int osY, int squareNumber){
        for (int i = osX; i < osX+3; i++){
            TableRow tableRow = new TableRow(this);
            for (int j = osY; j < osY+3; j++){
                sudokuTable[i][j].square = squareNumber;
                tableRow.addView(sudokuTable[i][j].bt);
            }
            square.addView(tableRow);
        }
    }

    //подсветка колонок и рядов
    public void highlightRowsColumns(Cell cell){
          for(int x = 0; x < 9; x++){
              for(int y = 0; y < 9; y++){
                  if (sudokuTable[x][y].coordinateX == cell.coordinateX && sudokuTable[x][y].coordinateY == cell.coordinateY){
                      sudokuTable[x][y].bt.setBackgroundResource(R.drawable.cells_focused);
                  } else if (sudokuTable[x][y].coordinateX == cell.coordinateX || sudokuTable[x][y].coordinateY == cell.coordinateY){
                      sudokuTable[x][y].bt.setBackgroundResource(R.drawable.cells_highlight);
                  } else if(sudokuTable[x][y].square == cell.square){
                      sudokuTable[x][y].bt.setBackgroundResource(R.drawable.cells_highlight);
                  }
              }
          }
    }

    //убирается подсветка колонок и рядов при расфокусировке
    public void removeHighlightRowsColumns(){
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                if (sudokuTable[x][y].fixed){
                    sudokuTable[x][y].bt.setBackgroundResource(R.drawable.fixed_cell);
                } else {
                    sudokuTable[x][y].bt.setBackgroundResource(R.drawable.cells_unpressed);
                }
            }
        }
    }

    //подсветка клеток с тем же значением
    public void highlightSameValues(Cell cell){
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++) {
                if (sudokuTable[x][y].coordinateX == cell.coordinateX && sudokuTable[x][y].coordinateY == cell.coordinateY) {
                    sudokuTable[x][y].bt.setBackgroundResource(R.drawable.cells_highlight);
                } else if(sudokuTable[x][y].value == cell.value) {
                    sudokuTable[x][y].bt.setBackgroundResource(R.drawable.cells_highlight);
                }
            }
        }
    }

    //проверка введенного значение
    public void valueChecking(Cell cell){
        if(cell.value != cell.trueValue){
            sudokuTable[cell.coordinateX][cell.coordinateY].bt.setTextColor(getResources().getColor(R.color.dark_red));
        } else {
            sudokuTable[cell.coordinateX][cell.coordinateY].bt.setTextColor(getResources().getColor(R.color.dark_violet));
            sudokuTable[cell.coordinateX][cell.coordinateY].fixed = true;
        }
    }

    //проверка заполненности таблицы
    public boolean isFull(){
        boolean fullness = true;
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                if (!sudokuTable[x][y].fixed) {
                    fullness = false;
                    break;
                }
            }
        }
        return fullness;
    }

    //Системная кнопка назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(Sudoku.this, GameLevels.class);
            startActivity(intent);
            finish();
        }catch(Exception ignored){
        }
    }


}