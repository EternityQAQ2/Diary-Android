package com.example.diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteDiaryActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextDiary;
    private Button buttonInsertImage;
    private Button buttonSave;
    private ImageView imageView;  // 在XML中定义的 ImageView
    private Uri imageUri; // 存储图片的 URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);

        editTextDiary = findViewById(R.id.editTextDiary);
        buttonInsertImage = findViewById(R.id.buttonInsertImage);
        buttonSave = findViewById(R.id.buttonSave);
        imageView = findViewById(R.id.imageView); // 获取XML中的ImageView

        // 插入图片
        buttonInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 选择图片
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "选择图片"), PICK_IMAGE_REQUEST);
            }
        });

        // 保存按钮
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(); // 保存日记内容和图片
                Intent intent = new Intent(WriteDiaryActivity.this, home.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData(); // 获取选择的图片 URI
            imageView.setImageURI(imageUri); // 显示选择的图片
        }
    }

    private void saveDiary() {
        String diaryContent = editTextDiary.getText().toString();
        File folder = new File(getExternalFilesDir(null), "Diaries");
        if (!folder.exists()) {
            folder.mkdirs(); // 创建文件夹
        }

        File mdFile = new File(folder, "diary.md");
        try (FileOutputStream fos = new FileOutputStream(mdFile)) {
            fos.write(diaryContent.getBytes()); // 保存文本内容为 .md 文件
            if (imageUri != null) {
                // 保存图片逻辑
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                saveImage(bitmap, folder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImage(Bitmap bitmap, File folder) {
        File imageFile = new File(folder, "image.png");
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // 保存图片
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
