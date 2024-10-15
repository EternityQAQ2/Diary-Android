package com.example.diary;

import android.os.Bundle;  // 导入 Bundle 类
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManager;
import android.view.Window;
import androidx.fragment.app.FragmentTransaction;

public class home extends AppCompatActivity implements View.OnClickListener{

    //UI Object
    private TextView txt_topbar;
    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private TextView txt_setting;
    private FrameLayout ly_content;

    //Fragment Object
    private MyFragment fg1,fg2,fg3,fg4;
    private FileListFragment fg5;
    private FragmentManager fManager;
    private TextView txt_view_files; // 定义一个新的 TextView 对象

// 在 bindViews() 方法中初始化它


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home);//确保是home.xml
        fManager = getSupportFragmentManager();;
        bindViews();
        txt_channel.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_channel = (TextView) findViewById(R.id.txt_channel);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_better = (TextView) findViewById(R.id.txt_better);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);
        txt_view_files = (TextView) findViewById(R.id.txt_view_files);
        txt_view_files.setOnClickListener(this);
        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_better.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
        txt_setting.setSelected(false);
        txt_view_files.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
        if(fg5 != null) fragmentTransaction.hide(fg5);  // 添加文件列表 Fragment 的隐藏逻辑
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        if (v.getId() == R.id.txt_channel) {
            setSelected();
            txt_channel.setSelected(true);
            if(fg1 == null){
                fg1 = MyFragment.newInstance("这里是写日记的地方喵");
                fTransaction.add(R.id.ly_content,fg1);
            }else{
                fTransaction.show(fg1);
            }
        }
        else if(v.getId() == R.id.txt_message){
            setSelected();
            txt_message.setSelected(true);
            if(fg2 == null){
                fg2 =  MyFragment.newInstance("这里是社区喵");
                fTransaction.add(R.id.ly_content,fg2);
            }else{
                fTransaction.show(fg2);
            }
        }
        else if(v.getId() == R.id.txt_better){
            setSelected();
            txt_better.setSelected(true);
            if(fg3 == null){
                fg3 =  MyFragment.newInstance("这里是日历喵");
                fTransaction.add(R.id.ly_content,fg3);
            }else{
                fTransaction.show(fg3);
            }
        }
        else if(v.getId() == R.id.txt_setting){
            setSelected();
            txt_setting.setSelected(true);
            if(fg4 == null){
                fg4 =  MyFragment.newInstance("这里是设置喵");
                fTransaction.add(R.id.ly_content,fg4);
            }else{
                fTransaction.show(fg4);
            }
        }
        else if (v.getId() == R.id.txt_view_files) {
            setSelected();
            txt_view_files.setSelected(true);
            if (fg5 == null) {  // 假设 fg5 是 ViewDiaryFragment 的实例
                fg5 = new FileListFragment();  // 切换到文件列表 Fragment
                fTransaction.add(R.id.ly_content, fg5);
            } else {
                fTransaction.show(fg5);
            }
        }

        fTransaction.commit();
    }
}
