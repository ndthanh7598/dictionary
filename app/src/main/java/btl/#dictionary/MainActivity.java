package btl.dictionary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import btl.dictionary.Model.MainModel;

public class MainActivity extends AppCompatActivity{

    LinearLayout dichvanban,info,tudienvietanh,tuquantrong,tucuatoi;
    AutoCompleteTextView search;
    MainModel mainModel;
    ArrayList<String> obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(my_toolbar);

        getcontrol();

        activecontrol();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        obj = mainModel.getListHistory(MainActivity.this);

        if (!obj.isEmpty()){
            display(obj);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                search.showDropDown();
            }
        }, 100);
    }

    private void activecontrol(){
        mainModel = new MainModel();

        tudienvietanh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TuDienVietAnhActivity.class);
                startActivity(intent);
            }
        });
        dichvanban.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DichVanBanActivity.class);
                startActivity(intent);
            }
        });
        tucuatoi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyWord.class);
                startActivity(intent);
            }
        });
        tuquantrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TuVungQuanTrongActivity.class);
                startActivity(intent);
            }
        });
        info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj = mainModel.getListHistory(MainActivity.this);

                if (!obj.isEmpty()){
                    display(obj);
                }
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        search.showDropDown();
                    }
                }, 100);
            }
        });

        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search.setText("");
                String get_tu = (String) parent.getItemAtPosition(position);
                Log.e("pr abc1",get_tu);
                Cursor obj = mainModel.searchWord(MainActivity.this, get_tu);
                String idtu = obj.getString(0);
                Log.e("pr abc",idtu);
                Intent intent = new Intent(MainActivity.this, ChiTietTu.class);
                intent.putExtra("id_tu", idtu);
                intent.putExtra("bang", "tbl_tu");
                startActivity(intent);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String word = search.getText().toString();
                if (word.equals("")){
                    obj = mainModel.getListHistory(MainActivity.this);
                }
                else{
                    obj = mainModel.searchInMain(MainActivity.this, word);
                }
                if (!obj.isEmpty()){
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            display(obj);
                            search.showDropDown();
                        }
                    }, 100);
                }
            }
        });
    }

    private void display(ArrayList<String> arrayList){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayList);
        search.setAdapter(adapter);
    }

    private void getcontrol(){
        tudienvietanh = (LinearLayout) findViewById(R.id.tudienvietanh);
        dichvanban = (LinearLayout) findViewById(R.id.dichvanban);
        tuquantrong = (LinearLayout) findViewById(R.id.tuvungquantrong);
        info = (LinearLayout) findViewById(R.id.info);
        tucuatoi = (LinearLayout) findViewById(R.id.tucuatoi);
        search = findViewById(R.id.search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.info:
                Intent info = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(info);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}