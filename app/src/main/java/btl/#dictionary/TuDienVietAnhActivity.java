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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import btl.dictionary.Adapter.TuDienAnhVietAdap;
import btl.dictionary.Model.MainModel;

public class TuDienVietAnhActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    ListView lvDS;
    TuDienAnhVietAdap tuDienAdap;
    MainModel mainModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tudienvietanh);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        // Hiện nút back ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lvDS = (ListView)findViewById(R.id.lvDS);

        mainModel = new MainModel();

        display(); //hiển thị danh sách từ

        lvDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor get_id = (Cursor)parent.getItemAtPosition(position); //lấy vị trí mà con trỏ đã được chọn từ lớp cha
                int id_tu = get_id.getInt(0);//id đó chính là cột nào trong db - ở đây là cột 0 mã
                String idtu = String.valueOf(id_tu); // trả về biểu diễn chuỗi của id_tu
                Intent intent = new Intent(TuDienVietAnhActivity.this, ChiTietTu.class);
                intent.putExtra("id_tu", idtu);
                intent.putExtra("bang", "tbl_tu");
                startActivity(intent);
            }
        });
    }

    private void display() {
        try{
            String order = getIntent().getExtras().getString("orderby"); //get extra "orderby" put sang từ menu
            if (order.equals("za")){
                tuDienAdap = new TuDienAnhVietAdap(this, mainModel.getAllDesc(this),true);
            }
            else {
                tuDienAdap = new TuDienAnhVietAdap(this, mainModel.getAll(this),true);
            }
        }catch (Exception e){
            tuDienAdap = new TuDienAnhVietAdap(this, mainModel.getAll(this),true);
        }

        lvDS.setAdapter(tuDienAdap);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        tuDienAdap = new TuDienAnhVietAdap(this, mainModel.searchWord(this, newText),true);
        lvDS.setAdapter(tuDienAdap);
        return true;
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
                Intent za = new Intent(TuDienVietAnhActivity.this,TuDienVietAnhActivity.class);
                za.putExtra("orderby", "za");
                startActivity(za);
                return true;
            case R.id.az:
                Intent az = new Intent(TuDienVietAnhActivity.this,TuDienVietAnhActivity.class);
                az.putExtra("orderby", "az");
                startActivity(az);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
