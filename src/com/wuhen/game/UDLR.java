package com.wuhen.game;

/**  
* Description: 传入当前的坐标x,y值
* @author WuHen
* @date 2020年8月19日  
*/
public class UDLR {

	// 实例化英雄对象
	private static Hero hero = new Hero();

	/*
	 * 按'W'上移,边缘是y==0
	 */
	public static void up(int x,int y) {
		System.out.println("您按到了W，将向上移动");
		System.out.println("上移前坐标（x,y）为" + x + "," + y);
		if (y > 0) {
			y -= 10;
		}else {
			System.out.println("您已经上移到顶啦!!!");
		}
		hero.move(x, y);
		System.out.println("上移后坐标（x,y）为" + x + "," + y);
	}

	/*
	 * 按'S'下移,边缘是y==(PlayGame.HEIGHT-Hero.height)*0.909
	 */
	public static void down(int x,int y) {
		System.out.println("您按到了S，将向下移动");
		System.out.println("下移前坐标（x,y）为" + hero.x + "," + hero.y);
		int height = PlayGame.HEIGHT-hero.height;
		//System.out.println(height * 0.909);
		if (y < height*0.909) {
			y += 10;
		}else {
			System.out.println("您已经下移到底啦!!!");
		}
		hero.move(x, y);
		System.out.println("下移后坐标（x,y）为" + x + "," + y);
	}
	
	/*
	 * 按'A'左移,边缘是x==0
	 */
	public static void left(int x,int y) {
		System.out.println("您按到了A，将向左移动");
		System.out.println("左移前坐标（x,y）为" + x + "," + y);
		if (x > 0) {
			x -= 10;
		}else {
			System.out.println("您已经左移到顶啦!!!");
		}
		hero.move(x, y);
		System.out.println("左移后坐标（x,y）为" + x + "," +  y);
	}

	/*
	 * 按'D'右移,边缘是x==(PlayGame.WIDTH-Hero.width)*0.95
	 */
	public static void right(int x,int y) {
		System.out.println("您按到了D，将向右移动");
		System.out.println("右移前坐标（x,y）为" + x + "," + y);
		int width = PlayGame.WIDTH-hero.width;
		//System.out.println(width * 0.95);
		if (x < width * 0.95) {
			x += 10;
		}else {
			System.out.println("您已经右移到顶啦!!!");
		}
		hero.move(x, y);
		System.out.println("右移后坐标（x,y）为" + x + "," + y);
	}

}
