package btl.dictionary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import btl.dictionary.Model.MainModel;

public class ThemTuActivity extends AppCompatActivity {

    Button btnAdd, btnBack;
    EditText etTu, etNghia, etPhatam, etLoaitu, etExam;
    MainModel mainModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themtu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        // Hiện nút back ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getControl();

        mainModel = new MainModel();

        etTu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tu = etTu.getText().toString();
                Cursor check = mainModel.getTucuatoiByTu(ThemTuActivity.this, tu);
                if (check.getCount()>0){
                    Toast.makeText(ThemTuActivity.this,"Từ đã tồn tại. Vui lòng thử lại!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tu = etTu.getText().toString();
                String nghia = etNghia.getText().toString();
                String phatam = etPhatam.getText().toString();
                String loaitu = etLoaitu.getText().toString();
                String vidu = etExam.getText().toString();

                ContentValues values = new ContentValues();
                values.put("tu", tu);
                values.put("nghia", nghia);
                values.put("trangthai", "no");
                values.put("phatam", phatam);
                values.put("loaitu", loaitu);
                values.put("example", vidu);

                if(tu.isEmpty() || nghia.isEmpty()){
                    Toast.makeText(ThemTuActivity.this,"Hãy điền từ và nghĩa của từ",Toast.LENGTH_LONG).show();
                }
                else{
                    Cursor check = mainModel.getTucuatoiByTu(ThemTuActivity.this, tu);
                    if (check.getCount()>0){
                        Toast.makeText(ThemTuActivity.this,"Từ đã tồn tại. Vui lòng thử lại!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        mainModel.insertWord(ThemTuActivity.this, values);
                        Toast.makeText(ThemTuActivity.this, "Thêm từ thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ThemTuActivity.this, MyWord.class);
                        startActivity(intent);
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemTuActivity.this,MyWord.class);
                startActivity(intent);
            }
        });
    }

    public void getControl(){
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);
        etTu = (EditText) findViewById(R.id.etTu);
        etNghia = (EditText) findViewById(R.id.etNghia);
        etPhatam = (EditText) findViewById(R.id.etPhatam);
        etLoaitu = (EditText) findViewById(R.id.etLoaitu);
        etExam = (EditText) findViewById(R.id.etExam);
    }

}
