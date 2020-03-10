package com.haoyh.dbflowdemo.database.tables;

import androidx.annotation.NonNull;

import com.haoyh.dbflowdemo.database.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @FileName: User
 * @Description: 作用描述
 * @Author: haoyanhui
 * @Date: 2020/3/9Mon11 16:28
 */
@Table(database = AppDatabase.class)
public class User extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    private long _id;

    @Column
    private int id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String address;

    public User() {

    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    @Override
    public String toString() {
        return "_id = " + _id + " id = " + id + " name = " + name + " age = " + age + " address = " + address;
    }
}
