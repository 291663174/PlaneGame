package com.wuhen.game;

/**
 * Description:敌方战机属性类
 * @author WuHen
 * @date 2020年8月20日
 */
public class Enemy extends FlyObject{

    //构造方法
    public Enemy(){
        image = PlayGame.img_enemy;
        width = image.getWidth();
        height = image.getHeight();
        x = (int)(Math.random()*(PlayGame.WIDTH-width));
        y = -height;
    }

    //无子弹
    public static Enemy[] shoots(){
        Enemy[] enemies = new Enemy[1];
        enemies[0] = new Enemy();
        return enemies;
    }

    public void step() {
        // 每次移动的步数
        int SPEED = 2;
        y += SPEED;
    }

    /**
     * 检测敌机是否被子弹击中
     * true=击中
     */
    public boolean shootedBy(Bullet bullet){
        int bullet_x = bullet.x;//子弹x坐标
        int bullet_y = bullet.y;//子弹y坐标

        int enemy_x = this.x;
        int enemy_y = this.y;

        return enemy_x<bullet_x && bullet_x<enemy_x+width && enemy_y<bullet_y && bullet_y<enemy_y+height;
    }

}
