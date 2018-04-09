package com.ty.maptest.beautychina.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ty.maptest.beautychina.R;
import com.ty.maptest.beautychina.db.DBHelper;
import com.ty.maptest.beautychina.db.PlaceInfoBean;

public class PlaceDetailActivity extends AppCompatActivity {

    private TextView tvName;
    private EditText etDescription;
    private Button btEdit;
    private Button btDelete;
    private DBHelper dbHelper;

    private PlaceInfoBean bean = new PlaceInfoBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        //获取数据
        Intent intent = getIntent();
        bean.setName(intent.getStringExtra("name"));
        bean.setId(intent.getIntExtra("id",0));
        bean.setDescription(intent.getStringExtra("description"));
        bean.setDetail(intent.getStringExtra("detail"));

        //获取控件
        tvName = findViewById(R.id.tv_name);
        etDescription = findViewById(R.id.tv_description);
        btEdit = findViewById(R.id.bt_edit);
        btDelete = findViewById(R.id.bt_delete);

        tvName.setText(bean.getName());
        etDescription.setText(bean.getDescription());

        //实例化数据库对象
        dbHelper = new DBHelper(getApplicationContext());

        //绑定点击事件监听器
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bean.setDescription(etDescription.getText().toString());
               dbHelper.update(bean);
                Toast.makeText(getApplicationContext(),"更新成功",Toast.LENGTH_SHORT).show();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.delete(bean.getId());
                Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
