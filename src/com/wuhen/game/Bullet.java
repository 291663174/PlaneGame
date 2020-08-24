package com.wuhen.game;

/**
 * Description:子弹属性类
 * @author WuHen
 * @date 2020年8月20日
 */
public class Bullet extends FlyObject{

	// 每次移动的步数
	private int speed = 3;
	
	public Bullet(int x,int y) {
		image = PlayGame.img_bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}
	
	public void step() {
		y -= speed;
	}

}
