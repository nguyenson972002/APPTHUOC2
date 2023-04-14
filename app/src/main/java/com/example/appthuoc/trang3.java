package com.example.appthuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class trang3 extends AppCompatActivity {

    Button xoa,hienthi,themthuoc;
    SQLiteDatabase mydatabase;
    ListView lv2;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    EditText edt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang3);

        xoa = findViewById(R.id.xoa);
        hienthi = findViewById(R.id.hienthi);
        themthuoc = findViewById(R.id.themthuoc);
        lv2 = findViewById(R.id.lv2);
        edt1 = findViewById(R.id.edt1);

        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mylist);
        lv2.setAdapter(myadapter);

        mydatabase = openOrCreateDatabase("appthuoc.sql",MODE_PRIVATE,null);

        try {
            String sql = "CREATE TABLE tbtime(tenthuoc TEXT primary key ,NGAY TEXT,gio TEXT )";
            mydatabase.execSQL(sql);
        }
        catch (Exception e){
            Log.e("Error","Table đã tồn tại");
        }

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = edt1.getText().toString();
                int n = mydatabase.delete("tbtime","time = ?",new String[]{time});
                String msg = "";
                if(n==0)
                {
                    msg = " No record to delete";
                }
                else{
                    msg = n + "record is delete";
                }
                Toast.makeText(trang3.this,msg,  Toast.LENGTH_SHORT).show();
            }
        });

        hienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor c = mydatabase.query("tbtime",null,null,null,null,null,null);
                c.moveToNext();
                String data = "";
                while (c.isAfterLast() == false)
                {
                    data = c.getString(0);
                    c.moveToNext();
                    mylist.add(data);
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });


        themthuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


    }
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}