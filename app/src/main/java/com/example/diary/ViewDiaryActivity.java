package com.example.diary;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ViewDiaryActivity extends AppCompatActivity {
    private TextView textViewDiaryContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary); // 新布局文件

        textViewDiaryContent = findViewById(R.id.textViewDiaryContent);

        // 读取保存的 md 文件
        File folder = new File(getExternalFilesDir(null), "Diaries");
        File mdFile = new File(folder, "diary.md");
        if (mdFile.exists()) {
            try (FileInputStream fis = new FileInputStream(mdFile)) {
                byte[] data = new byte[(int) mdFile.length()];
                fis.read(data);
                String diaryContent = new String(data);
                textViewDiaryContent.setText(diaryContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            textViewDiaryContent.setText("没有保存的日记");
        }
    }
}
