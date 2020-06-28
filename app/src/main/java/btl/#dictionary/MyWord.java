package btl.dictionary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import btl.dictionary.Adapter.TuDienAnhVietAdap;
import btl.dictionary.Model.MainModel;

public class MyWord extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {
    ListView lvDS;
    TuDienAnhVietAdap tuDienAdap;
    MainModel mainModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_word);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWord.this,ThemTuActivity.class);
                startActivity(intent);
            }
        });

        lvDS = (ListView)findViewById(R.id.lvDS);

        mainModel = new MainModel();

        display();

        lvDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor get_id = (Cursor)parent.getItemAtPosition(position); //lấy vị trí mà con trỏ đã được chọn từ lớp cha
                int id_tu = get_id.getInt(0);//id đó chính là cột nào trong db - ở đây là cột 0 mã
                String idtu = String.valueOf(id_tu); // trả về biểu diễn chuỗi của id_tu
                Intent intent = new Intent(MyWord.this, ChiTietTu.class);
                intent.putExtra("id_tu", idtu);
                intent.putExtra("bang", "tbl_tucuatoi");
                startActivity(intent);
            }
        });

        lvDS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                PopupMenu popup = new PopupMenu(MyWord.this, view);
                popup.inflate(R.menu.task_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        Cursor get_id = (Cursor)parent.getItemAtPosition(position);
                        final int id_tu = get_id.getInt(0);
                        Intent intent;
                        switch (id){
                            case R.id.delete:
                                mainModel.delWord(MyWord.this, id_tu);
                                Toast.makeText(MyWord.this,"Xoá từ thành công",Toast.LENGTH_LONG).show();
                                intent = new Intent(MyWord.this,MyWord.class);
                                startActivity(intent);

                                return true;
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
            }
        });
    }

    private void display() {
        tuDienAdap = new TuDienAnhVietAdap(this, mainModel.getMyWord(this),true);
        lvDS.setAdapter(tuDienAdap);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        display();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            case R.id.search:
                Intent info = new Intent(MyWord.this,MyWord.class);
                startActivity(info);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        tuDienAdap = new TuDienAnhVietAdap(this, mainModel.searchMyWord(this, newText),true);
        lvDS.setAdapter(tuDienAdap);
        return true;
    }
}
