package com.haoyh.dbflowdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haoyh.dbflowdemo.database.DatabaseUtil;
import com.haoyh.dbflowdemo.database.tables.User;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView mTvShowInfo;

    private int mId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        showInfo();
    }

    private void showInfo() {
        List<User> list = DatabaseUtil.allUsers();
        String info = "now count = " + list.size() + "\n";
        for (User user : list) {
            info += user.toString() + "\n";
        }
        mTvShowInfo.setText(info);
    }

    private void initView() {
        mTvShowInfo = findViewById(R.id.tv_show);
        findViewById(R.id.tv_add).setOnClickListener(this);
        findViewById(R.id.tv_del).setOnClickListener(this);
        findViewById(R.id.tv_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                new User(mId, "name_" + new Random().nextInt()).save();
                mId += 1;
                break;
            case R.id.tv_del:
                DatabaseUtil.firstUser().delete();
                break;
            case R.id.tv_update:
                User user = DatabaseUtil.firstUser();
                if (null != user) {
                    user.setAge(new Random().nextInt());
                    user.update();
                }
                break;
            default:
                break;
        }
        showInfo();
    }


}
