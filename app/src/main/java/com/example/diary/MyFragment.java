package com.example.diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    private static final String ARG_CONTENT = "content"; // 参数的键
    private String content;
    private Button btnWriteDiary; // 声明按钮变量

    // 使用 newInstance 方法来创建 Fragment 实例
    public static MyFragment newInstance(String content) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, content); // 将参数放入 Bundle 中
        fragment.setArguments(args); // 设置参数
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取传递的参数
        if (getArguments() != null) {
            content = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content, container, false);
        TextView txt_content = view.findViewById(R.id.txt_content);
        txt_content.setText(content);
        // 获取按钮
        btnWriteDiary = view.findViewById(R.id.btn_write_diary); // 确保这个 ID 是正确的

        // 检查 Fragment 的初始化参数
        if (content != null && content.contains("写日记的地方")) {
            btnWriteDiary.setVisibility(View.VISIBLE); // 显示按钮
        } else {
            btnWriteDiary.setVisibility(View.GONE); // 隐藏按钮
        }

        // 按钮的点击逻辑
        btnWriteDiary.setOnClickListener(v -> {
            // 这里添加打开写日记的活动或逻辑
            Intent intent = new Intent(getActivity(), WriteDiaryActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
