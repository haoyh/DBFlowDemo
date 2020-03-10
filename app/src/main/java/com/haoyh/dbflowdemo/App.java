package com.haoyh.dbflowdemo;

import android.app.Application;

import com.haoyh.dbflowdemo.database.AppDatabase;
import com.haoyh.dbflowdemo.database.CustomOpenHelper;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseHelperListener;
import com.raizlabs.android.dbflow.structure.database.OpenHelper;

/**
 * @FileName: App
 * @Description: 作用描述
 * @Author: haoyanhui
 * @Date: 2020/3/9 16:22
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 模拟已经存在未加密数据的情况操作：
         * 首先只调用 initDBFlow() 运行安装并插入几条数据；
         * 改为只调用 initEncryptDBFlow() 再次安装，查看之前插入的数据是否仍然存在。
         */
//        initDBFlow();
        // 默认执行加密数据库的初始化方法
        initEncryptDBFlow();
    }

    /**
     * 初始化 FlowManager
     */
    private void initDBFlow() {
        FlowManager.init(FlowConfig.builder(this).addDatabaseConfig(
                DatabaseConfig.builder(AppDatabase.class)
                        .databaseName(CustomOpenHelper.DB_NAME_OLD)
                        .build()
        ).build());
    }

    /**
     * 初始化 FlowManager，配置加密 OpenHelper
     */
    private void initEncryptDBFlow() {
        FlowManager.init(FlowConfig.builder(this).addDatabaseConfig(
                DatabaseConfig.builder(AppDatabase.class)
                        .databaseName(CustomOpenHelper.DB_NAME_NOW)
                        .openHelper(new DatabaseConfig.OpenHelperCreator() {
                            @Override
                            public OpenHelper createHelper(DatabaseDefinition databaseDefinition, DatabaseHelperListener helperListener) {
                                return new CustomOpenHelper(App.this, databaseDefinition, helperListener);
                            }
                        })
                        .build()
        ).build());
    }

}
