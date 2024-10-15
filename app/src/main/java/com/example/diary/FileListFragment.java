package com.example.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListFragment extends Fragment {

    private ListView listView;
    private List<String> fileNames; // 用于保存文件名

    public FileListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file_list, container, false);

        listView = view.findViewById(R.id.listViewFiles);

        // 获取保存的文件列表
        fileNames = getFileNames();

        // 创建适配器，将文件名显示在 ListView 中
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, fileNames);
        listView.setAdapter(adapter);

        // 点击某个文件时，打开该文件内容
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = fileNames.get(position);
                openFile(fileName);
            }
        });

        return view;
    }

    // 获取文件列表
    private List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(getActivity().getExternalFilesDir(null), "Diaries");
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".md")) { // 只显示 .md 文件
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    // 打开并显示文件内容
    private void openFile(String fileName) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ViewFileFragment viewFileFragment = ViewFileFragment.newInstance(fileName); // 传递文件名
        transaction.replace(R.id.ly_content, viewFileFragment);
        transaction.addToBackStack(null); // 添加到返回栈，允许用户按返回键返回文件列表
        transaction.commit();
    }
}
