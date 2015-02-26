# android-lotterywheel
这是一个抽奖转盘


在布局中对应用：
		  <com.ylc.lotterywheel.MyLotteryWheel
		        android:id="@+id/lotterywheel"
		        android:layout_width="300dp"
		        android:layout_height="300dp"
		        android:layout_centerInParent="true"
		        />
		        
        

在代码中对使用：
		//控件的使用
		lotterywheel = (MyLotteryWheel) this.findViewById(R.id.lotterywheel);
		lotterywheel.setOnSelectListener(new SelectListener() {
	            @Override
	            public void onSelect(String str) {
	                Toast.makeText(getApplicationContext(), str, 0).show();
	            }



显示对效果如下：
       

 ![image](https://github.com/yanglichuan/android-lotterywheel/edit/master/device-2015-02-26-160629.png)





