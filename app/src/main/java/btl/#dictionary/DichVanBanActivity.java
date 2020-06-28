package btl.dictionary;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import btl.dictionary.Model.MainModel;

public class DichVanBanActivity extends AppCompatActivity {
    Button btnAV, btnVA;
    EditText tvInput;
    TextView tvResult;
    MainModel mainModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dichvanban);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        // Hiện nút back ở toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getControl();
        
        mainModel = new MainModel();

        btnAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput = tvInput.getText().toString().trim();
                if (textInput.equals("")){
                    Toast.makeText(DichVanBanActivity.this,"Vui lòng nhập câu muốn dịch",Toast.LENGTH_LONG).show();
                }
                else {
                    Cursor obj = mainModel.transParagraph(DichVanBanActivity.this, "cauta", textInput);
                    if (obj != null) {
                        tvResult.setText(obj.getString(2));
                        tvResult.setBackgroundColor(Color.parseColor("#ffffff"));
                    } else {
                        String[] arrIn = textInput.split("\\s");
                        String result = "";

                        for(String i:arrIn){
                            Cursor obj1 = mainModel.getTuByTu(DichVanBanActivity.this,i);
                            if (obj1.getCount()>0) {
                                result = result + " " + obj1.getString(2).toLowerCase();
                            }
                            else{
                                result = "";
                                break;
                            }
                        }
                        if (result.equals("")) {
                            Toast.makeText(DichVanBanActivity.this, "Không có dữ liệu về câu đã nhập", Toast.LENGTH_LONG).show();
                        }
                        else{
                            tvResult.setText(result.trim());
                            tvResult.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }
                }
            }
        });

        btnVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textInput = tvInput.getText().toString().trim();
                if (textInput.equals("")){
                    Toast.makeText(DichVanBanActivity.this,"Vui lòng nhập câu muốn dịch",Toast.LENGTH_LONG).show();
                }
                else {
                    Cursor obj = mainModel.transParagraph(DichVanBanActivity.this, "cautv", textInput);
                    if (obj != null) {
                        tvResult.setText(obj.getString(1));
                    } else {
                        String[] arrIn = textInput.split("\\s");
                        String result = "";
                        for(String i:arrIn){
                            Cursor obj1 = mainModel.getTuTVByTu(DichVanBanActivity.this,i);
                            if (obj1.getCount()>0) {
                                String text;
                                if (obj1.getString(1).equals("I")) {
                                    text = obj1.getString(1);
                                } else {
                                    text = obj1.getString(1).toLowerCase();
                                }
                                result = result + " " + text;
                            }
                            else{
                                result = "";
                                break;
                            }
                        }
                        if (result.equals("")) {
                            Toast.makeText(DichVanBanActivity.this, "Không có dữ liệu về câu đã nhập", Toast.LENGTH_LONG).show();
                        }
                        else{
                            tvResult.setText(result.trim());
                            tvResult.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }
                }
            }
        });
    }

    private void getControl(){
        btnAV = (Button) findViewById(R.id.btnAV);
        btnVA = (Button) findViewById(R.id.btnVA);
        tvInput = (EditText) findViewById(R.id.tvInput);
        tvResult = (TextView) findViewById(R.id.tvResult);
    }

}
