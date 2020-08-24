package com.wuhen.game;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Description: 游戏操作主界面
 *
 * @author WuHen
 * @date 2020年8月19日
 */
public class PlayGame extends JPanel {

    // 窗体宽度<500 图片宽度不足500
    public static final int WIDTH = 400;
    // 窗体高度 <852左右
    public static final int HEIGHT = 650;
    // 背景
    public static BufferedImage background;
    // 英雄01
    public static BufferedImage img_hero0;
    // 英雄02
    public static BufferedImage img_hero1;
    // 子弹图片
    public static BufferedImage img_bullet;
    //敌机图片
    public static BufferedImage img_enemy;
    //游戏失败图片
    public static BufferedImage img_gameover;
    // 实例化英雄对象
    private Hero hero = new Hero();

    // 敌机数组
    private Enemy[] enemies = {};

    // 子弹数组
    private Bullet[] bullets = {};

    // 当前位置的坐标
    int x, y;

    public int state = 1;//1运行，0结束

    public int display = 1;//1显示，0消失

    private int score = 0;

    boolean begin =false;//进入游戏开关-进入提示页

    // 静态初始化
    static {
        try {
            // 加载背景图片
            background = ImageIO.read(PlayGame.class.getResource("images/xingji.jpeg"));
            img_hero0 = ImageIO.read(PlayGame.class.getResource("images/hero0.png"));
            img_hero1 = ImageIO.read(PlayGame.class.getResource("images/hero1.png"));
            img_bullet = ImageIO.read(PlayGame.class.getResource("images/bullet.png"));
            img_enemy = ImageIO.read(PlayGame.class.getResource("images/enemy.png"));
            img_gameover = ImageIO.read(PlayGame.class.getResource("images/gameover.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 操作动作
    public void operAction() {
        System.out.println("call operAction..");

        // 监听鼠标事件
        MouseAdapter mouseAdapter = new MouseAdapter() { // 鼠标移动事件
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // 获取鼠标移动的X/Y位置
                x = e.getX();
                y = e.getY();
                System.out.println("鼠标移动X=" + x + ",Y=" + y); // 英雄移动
                hero.move(x - hero.width / 2, y - hero.height / 2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("鼠标移出了游戏界面！");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("鼠标进入了游戏界面！");
            }

        };

        // 键盘事件
        KeyAdapter keyAdapter = new KeyAdapter() {

            // 按下WASD,将当前的坐标x,y值传入，即可移动
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    UDLR.up(hero.x, hero.y);
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    UDLR.down(hero.x, hero.y);
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    UDLR.left(hero.x, hero.y);
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    UDLR.right(hero.x, hero.y);
                    repaint();
                }
            }

        };

        // 注册监听事件----this表示窗体
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);

        this.addKeyListener(keyAdapter);

        // 开启一个定时器线程（每隔多久，可以调用）
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isGameOver()) {
                    //移动的坐标修改
                    stepAction();
                    //调用发射子弹的动作
                    shootAction();

                    //敌机生成
                    makeEnemy();
                    //敌机移动
                    enemyStepAction();

                    isShootedAction();
                }
                //重绘
                repaint();
            }
        }, 10, 10);
    }

    // 定义绘制敌机方法
    public void paintEnemy(Graphics g) {
        for (int i = 0; i < enemies.length; i++) {
            Enemy enemy = enemies[i];
            g.drawImage(enemy.image, enemy.x, enemy.y, null);
        }
    }

    int enemyIndex = 0;

    // 定义生成敌机方法
    public void makeEnemy() {
        enemyIndex++;
        if (enemyIndex % 100 == 0) {
            // 数组es
            Enemy[] es = Enemy.shoots();
            // 数组es要拷贝给属性enemies
            enemies = Arrays.copyOf(enemies, enemies.length + es.length);
            System.arraycopy(es, 0, enemies, enemies.length - es.length, es.length);
        }
    }

    //定义敌机移动方法
    public void enemyStepAction() {
        for (int i = 0; i < enemies.length; i++) {
            Enemy enemy = enemies[i];
            enemy.step();
        }
    }

    //子弹和敌机碰撞的检测,目标是把被击中的敌机从数组中删除
    public void isShooted(Bullet bullet) {
        //被击中敌机的索引
        int idx = -1;
        //循环判断一个子弹是否集中
        for (int i = 0; i < enemies.length; i++) {
            Enemy enemy = enemies[i];
            if (enemy.shootedBy(bullet)) {
                //记录被击中的敌机的索引
                idx = i;
                score++;
                System.out.println(score + "");
                break;
            }
        }
        //目标是把被击中的敌机从数组中删除
        if (idx != -1) {//有击中
            Enemy enemy = enemies[idx];
            Enemy temp = enemies[idx];
            enemies[idx] = enemies[enemies.length - 1];
            enemies[enemies.length - 1] = temp;
            enemies = Arrays.copyOf(enemies, enemies.length - 1);
        }
    }

    //多颗子弹与敌机的碰撞检验
    public void isShootedAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet bullet = bullets[i];
            isShooted(bullet);
        }
    }

    // 重写绘图方法
    @Override
    public void paint(Graphics g) {
        // 画背景
        g.drawImage(background, 0, 0, null);
        // 画英雄
        hero.paintHero(g);
        //画子弹
        paintBullets(g);
        //画敌机
        paintEnemy(g);
        //画状态
        if (isGameOver()) {
            paintGameOver(g);
        }
    }

    //定义绘制State
    public void paintGameOver(Graphics g) {
        if (state == 1) {
            //不画
        } else {
            //画gameover
            g.drawImage(img_gameover, 0, 0, null);
        }
    }

    // 定义绘制子弹方法
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.image, b.x, b.y, null);
        }
    }

    //子弹移动
    public void stepAction() {
        for (int i = 0; i < bullets.length; i++) {
            Bullet bullet = bullets[i];
            bullet.step();
        }
    }

    int shootIndex = 0;

    // 射击动作
    public void shootAction() {
        shootIndex++;
        if (shootIndex % 50 == 0) {
            // 数组bs
            Bullet[] bs = hero.shoot();
            // 数组bs要拷贝给属性bullets
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < enemies.length; i++) {
            if (hero.hit(enemies[i])) {
                this.state = 0;
                //调用记录写入
                PropertiesUtils.writeRecord(score + "");
                return true;
            }
        }
        return false;
    }

    // 入口主方法
    public static void main(String[] args) {

        JFrame frame = new JFrame("雷霆战机V1.0");
        PlayGame playGame = new PlayGame();

        frame.add(playGame);
        // 设置窗体的尺寸
        frame.setSize(WIDTH, HEIGHT);
        // 最上面一层
        frame.setAlwaysOnTop(true);
        // 点击关闭按钮，程序结束
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // JPanel夺取swing的焦点
        playGame.requestFocus();

        // 调用operAction
        playGame.operAction();

        //调用背景音乐
        File file = new File("src/com/wuhen/game/testmusic.mp3");
        Player player = null;
        try {
            player = new Player(new FileInputStream(file));
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
