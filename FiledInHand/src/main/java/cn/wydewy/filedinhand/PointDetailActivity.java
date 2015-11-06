package cn.wydewy.filedinhand;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import cn.wydewy.filedinhand.entity.Point;

public class PointDetailActivity extends Activity {

    private TextView detailText;
    private Point point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_detail);
        initDate();
        initView();
    }

    private void initDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id",0);
        point = DataSupport.find(Point.class,id);
    }

    private void initView() {
        detailText = (TextView)findViewById(R.id.detailText);
        detailText.setText(point.getDetail());
    }
}
