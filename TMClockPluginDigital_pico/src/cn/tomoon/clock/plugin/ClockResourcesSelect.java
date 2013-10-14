package cn.tomoon.clock.plugin;

public class ClockResourcesSelect {
	
	/**
	 * @param i 第几个数字
	 * @param color 颜色
	 * @return  大数字的资源id
	 */
	public static int getBigNumResId(int i,int color) {
	
		int resId=0;
		switch(color){
		case 3:
			switch (i) {
			case 0:
				resId = R.drawable.green0;
				break;
			case 1:
				resId = R.drawable.green1;
				break;
			case 2:
				resId = R.drawable.green2;
				break;
			case 3:
				resId = R.drawable.green3;
				break;
			case 4:
				resId = R.drawable.green4;
				break;
			case 5:
				resId = R.drawable.green5;
				break;
			case 6:
				resId = R.drawable.green6;
				break;
			case 7:
				resId = R.drawable.green7;
				break;
			case 8:
				resId = R.drawable.green8;
				break;
			case 9:
				resId = R.drawable.green9;
				break;
				
			default:
				resId = R.drawable.green_dark_8;
				break;
			}
			break;
		default:
			break;
		}
		
		return resId;
	}
	
	/**
	 * @param i 指定数字
	 * @param color 指定颜色
	 * @return 小数字资源对应的id
	 */
	public static int getSmallNumResId(int i,int color) {
		int resId=0;
		switch(color){
		case 3:
			switch (i) {
			case 0:
				resId = R.drawable.small_green0;
				break;
			case 1:
				resId = R.drawable.small_green1;
				break;
			case 2:
				resId = R.drawable.small_green2;
				break;
			case 3:
				resId = R.drawable.small_green3;
				break;
			case 4:
				resId = R.drawable.small_green4;
				break;
			case 5:
				resId = R.drawable.small_green5;
				break;
			case 6:
				resId = R.drawable.small_green6;
				break;
			case 7:
				resId = R.drawable.small_green7;
				break;
			case 8:
				resId = R.drawable.small_green8;
				break;
			case 9:
				resId = R.drawable.small_green9;
				break;
			default:
				resId = R.drawable.small_green_dark_8;
				break;
			}
			break;
		default:
			break;
			
		}
		return resId;
	}
	

}
