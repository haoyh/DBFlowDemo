package com.haoyh.dbflowdemo.database;

import com.haoyh.dbflowdemo.database.tables.User;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * @FileName: DatabaseUtil
 * @Description: 作用描述
 * @Author: haoyanhui
 * @Date: 2020/3/9Mon11 16:35
 */
public class DatabaseUtil {

    public static List<User> allUsers() {
        return SQLite.select().from(User.class).queryList();
    }

    public static User firstUser() {
        return SQLite.select().from(User.class).querySingle();
    }

}
