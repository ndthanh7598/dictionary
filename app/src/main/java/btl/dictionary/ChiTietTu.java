package btl.dictionary;

import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import btl.dictionary.Model.MainModel;

public class ChiTietTu extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {

    MainModel mainModel;
    TextToSpeech textToSpeech;
    TextView tvdTienganh, tvdTiengviet, tvPhatam, tvLoaitu, tvExam;
    MenuItem like;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiettu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // library speech text
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH); //set language
                }
            }
        });

        String intent = getIntent().getExtras().getString("id_tu"); //get id_tu put từ layout trước sang
        String bang = getIntent().getExtras().getString("bang"); //get tên bảng put từ layout trước sang
        int id_tu = Integer.parseInt(intent);

        getControl();

        mainModel = new MainModel();

        mainModel.updateTime(this, id_tu); //update thời gian tra trong db

        Cursor obj = mainModel.getTubyId(this, id_tu, bang); //Lấy ra thông tin từ theo id

        tvdTienganh.setText(obj.getString(1));
        tvdTiengviet.setText(obj.getString(2));
        tvPhatam.setText("/"+ obj.getString(5) +"/");
        tvLoaitu.setText("("+ obj.getString(6) +")");
        tvExam.setText(obj.getString(7));

        ImageView imgSpeak = (ImageView) findViewById(R.id.imgLoa);

        // Sự kiện click vào loa
        imgSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = tvdTienganh.getText().toString();
                textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }

    private void getControl(){
        tvdTienganh = (TextView) findViewById(R.id.tvdTienganh);
        tvdTiengviet = (TextView) findViewById(R.id.tvdTiengviet);
        tvPhatam = (TextView) findViewById(R.id.tvPhatam);
        tvLoaitu = (TextView) findViewById(R.id.tvLoaitu);
        tvExam = (TextView) findViewById(R.id.tvExample);
    }

    //chuyển văn bản thành giọng nói
    public void onDestroy(){ //khi double click
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_like,menu);
        String intent = getIntent().getExtras().getString("id_tu");
        String bang = getIntent().getExtras().getString("bang");
        int id_tu = Integer.parseInt(intent);

        if (bang.equals("tbl_tucuatoi")){
            menu.getItem(0).setVisible(false);
        }
        else{
            mainModel = new MainModel();
            Cursor obj = mainModel.getTubyId(this, id_tu, bang);

            String trangthai = obj.getString(3); //lấy trạng thái từ trong db

            if (trangthai.equals("yes")){
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_yellow));
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String intent = getIntent().getExtras().getString("id_tu");
        String bang = getIntent().getExtras().getString("bang");
        int id_tu = Integer.parseInt(intent);
        mainModel = new MainModel();

        int id = item.getItemId();
        switch (id){
            case R.id.like:
                Cursor obj = mainModel.getTubyId(this, id_tu, bang);
                String trangthai = obj.getString(3);
                if (trangthai.equals("yes")){
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star));
                    mainModel.updateTrangthai(this, id_tu, "no");
                }
                if (trangthai.equals("no")){
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star_yellow));
                    mainModel.updateTrangthai(this, id_tu, "yes");
                }
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
        return false;
    }
}
