package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.myapplication.R;

public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }

}