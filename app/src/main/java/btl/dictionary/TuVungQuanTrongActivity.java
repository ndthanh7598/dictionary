package btl.dictionary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import btl.dictionary.Adapter.TuQuanTrongAdap;
import btl.dictionary.Model.MainModel;
import btl.dictionary.Object.tudien;

public class TuVungQuanTrongActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    ListView lvTuQuanTrong;
    TuQuanTrongAdap tuQuanTrongAdap;
    MainModel mainModel;
    ArrayList<tudien> listTudien = new ArrayList<tudien>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuquantrong);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        // Hiện nút back ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvTuQuanTrong = (ListView) findViewById(R.id.lvTuQuanTrong);

        mainModel = new MainModel();
        display(); //hiển thị danh sách từ
    }

    private void display() {
        listTudien.clear();
        try{
            String intent = getIntent().getExtras().getString("orderby");
            String order = intent;
            if (order.equals("za")){
                Cursor obj = mainModel.getTuQuanTrong(this,"DESC");
                for (obj.moveToFirst(); !obj.isAfterLast(); obj.moveToNext()){
                    listTudien.add(new tudien(Integer.parseInt(obj.getString(0)),obj.getString(1), obj.getString(2),obj.getString(3)));
                }
                tuQuanTrongAdap = new TuQuanTrongAdap(TuVungQuanTrongActivity.this, R.layout.tuquantrong_custome,listTudien);
            }
            else {
                Cursor obj = mainModel.getTuQuanTrong(this,"ASC");
                for (obj.moveToFirst(); !obj.isAfterLast(); obj.moveToNext()){
                    listTudien.add(new tudien(Integer.parseInt(obj.getString(0)),obj.getString(1), obj.getString(2),obj.getString(3)));
                }
                tuQuanTrongAdap = new TuQuanTrongAdap(TuVungQuanTrongActivity.this, R.layout.tuquantrong_custome,listTudien);
            }
        }catch (Exception e){
            Cursor obj = mainModel.getTuQuanTrong(this,"ASC");
            for (obj.moveToFirst(); !obj.isAfterLast(); obj.moveToNext()){
                listTudien.add(new tudien(Integer.parseInt(obj.getString(0)),obj.getString(1), obj.getString(2),obj.getString(3)));
            }
            tuQuanTrongAdap = new TuQuanTrongAdap(TuVungQuanTrongActivity.this, R.layout.tuquantrong_custome,listTudien);
        }
        lvTuQuanTrong.setAdapter(tuQuanTrongAdap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.context_menu,menu);

        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.itTimkiem);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTimkiem:
                return true;
            case R.id.za:
                Intent za = new Intent(TuVungQuanTrongActivity.this,TuVungQuanTrongActivity.class);
                za.putExtra("orderby", "za");
                startActivity(za);
                return true;
            case R.id.az:
                Intent az = new Intent(TuVungQuanTrongActivity.this,TuVungQuanTrongActivity.class);
                az.putExtra("orderby", "az");
                startActivity(az);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // click back and reload activity android
    @Override
    protected void onRestart() {
        super.onRestart();
        display();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listTudien.clear();
        Cursor obj = mainModel.searchWordLike(this, newText);
        for (obj.moveToFirst(); !obj.isAfterLast(); obj.moveToNext()){
            listTudien.add(new tudien(Integer.parseInt(obj.getString(0)),obj.getString(1), obj.getString(2),obj.getString(3)));
        }
        tuQuanTrongAdap = new TuQuanTrongAdap(this, R.layout.tuquantrong_custome,listTudien);
        lvTuQuanTrong.setAdapter(tuQuanTrongAdap);
        return true;
    }
}
