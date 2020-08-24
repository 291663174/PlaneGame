package com.wuhen.game;

import java.awt.*;

import static com.wuhen.game.PlayGame.img_hero0;
import static com.wuhen.game.PlayGame.img_hero1;

/**
* Description:  英雄：我方战机属性类 
* @author WuHen
* @date 2020年8月19日  
*/

public class Hero extends FlyObject{

	// 坐标X
	public static int x;
	// 坐标Y
	public static int y;

	//构造方法
	public Hero(){
		image = img_hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 300;
	}

	public void move(int x, int y) {
		this.x = x ;
		this.y = y ;
	}

	//英雄发射子弹
	public Bullet[] shoot() {
		int xStep = width / 2 - 3;
		int yStep = 40;
		Bullet[] bs = new Bullet[1];
		bs[0] = new Bullet(x + xStep, y - yStep);
		return bs;
	}

	int heroIndex = 0;
	// 定义绘制英雄方法
	public void paintHero(Graphics g) {
		heroIndex++;
		if (heroIndex % 5 == 0) {
			g.drawImage(img_hero0, Hero.x, Hero.y, null);
		} else {
			g.drawImage(img_hero1, Hero.x, Hero.y, null);
		}
	}

	//英雄和敌机碰撞
	public boolean hit(Enemy enemy){
		//敌机的坐标, x1，y1 左上角顶点
		int x1 = enemy.x-this.width/2;
		int y1 = enemy.y-this.height/2;

		int x2 = enemy.x + enemy.width + this.width/2;
		int y2 = enemy.y + enemy.height + this.height/2;

		//英雄的坐标
		int x3 = Hero.x + this.width/2;
		int y3 = Hero.y + this.height/2;

		//判断碰撞
		return x3>=x1 && x3<=x2 && y3>=y1 && y3<=y2;
	}
}
