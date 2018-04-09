package com.ty.maptest.beautychina.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ty.maptest.beautychina.R;
import com.ty.maptest.beautychina.db.DBHelper;
import com.ty.maptest.beautychina.db.PlaceInfoBean;

import java.util.List;

public class PlaceListActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private PlaceListAdapter adapter;
    private List<PlaceInfoBean> data;
    private DBHelper dbHelper;
    private EditText etKeyword;
    private Button btSearch;
    private TextView tvTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        listView = findViewById(R.id.list_view);
        etKeyword = findViewById(R.id.et_keyword);
        btSearch = findViewById(R.id.bt_search);
        tvTips = findViewById(R.id.tv_tips);
        btSearch.setOnClickListener(this);

        adapter = new PlaceListAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        dbHelper = new DBHelper(getApplicationContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                  PlaceInfoBean bean = data.get(i);
                  Intent intent = new Intent(PlaceListActivity.this,PlaceDetailActivity.class);
                  intent.putExtra("name",bean.getName());
                  intent.putExtra("id",bean.getId());
                  intent.putExtra("description",bean.getDescription());
                  intent.putExtra("detail",bean.getDetail());
                  startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etKeyword.setText("");
        data = dbHelper.queryAll();
        Log.d("PlaceListActivity:","data.size()-->"+data.size());
        adapter.setData(data);
        adapter.notifyDataSetChanged();
        if(data.size() == 0)
        {
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText("没有记录信息");
        }
        else
        {
            tvTips.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_search:
                search();
                break;
        }
    }

    private void search()
    {
        String keyword = etKeyword.getText().toString();
        if(keyword.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"请输入关键词",Toast.LENGTH_SHORT).show();
        }
        else
        {
            data = dbHelper.rawQueryKeyWork(DBHelper.FIELD_NAME,keyword);
            adapter.setData(data);
            adapter.notifyDataSetChanged();
            if(data.size() == 0)
            {
                tvTips.setVisibility(View.VISIBLE);
                tvTips.setText("没有记录信息");
            }
            else
            {
                tvTips.setText("搜索结果");
            }
        }

    }
}
