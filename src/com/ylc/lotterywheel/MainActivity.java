package com.ylc.lotterywheel;

import com.ylc.lotterywheel.MyLotteryWheel.SelectListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
public class MainActivity extends Activity
{

    MyLotteryWheel lotterywheel;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//控件的使用
		lotterywheel = (MyLotteryWheel) this.findViewById(R.id.lotterywheel);
		lotterywheel.setOnSelectListener(new SelectListener() {
            @Override
            public void onSelect(String str) {
                Toast.makeText(getApplicationContext(), str, 0).show();
            }
        });
		//控件的使用
		
		
		
	}
}
