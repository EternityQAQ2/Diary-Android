package com.example.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ViewFileFragment extends Fragment {

    private static final String ARG_FILE_NAME = "fileName";
    private String fileName;
    private TextView textViewFileContent;

    public ViewFileFragment() {
        // Required empty public constructor
    }

    public static ViewFileFragment newInstance(String fileName) {
        ViewFileFragment fragment = new ViewFileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_NAME, fileName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileName = getArguments().getString(ARG_FILE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_file, container, false);
        textViewFileContent = view.findViewById(R.id.textViewFileContent);
        displayFileContent();
        return view;
    }

    // 显示文件内容
    private void displayFileContent() {
        File folder = new File(getActivity().getExternalFilesDir(null), "Diaries");
        File file = new File(folder, fileName);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                String fileContent = new String(data);
                textViewFileContent.setText(fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
