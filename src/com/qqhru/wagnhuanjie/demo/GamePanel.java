package com.qqhru.wagnhuanjie.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

	ImageIcon title = new ImageIcon("imgs/title.jpg");
	ImageIcon body = new ImageIcon("imgs/snake_body.png");
	ImageIcon up = new ImageIcon("imgs/head_up.png");
	ImageIcon down = new ImageIcon("imgs/head_down.png");
	ImageIcon left = new ImageIcon("imgs/head_left.png");
	ImageIcon right = new ImageIcon("imgs/head_right.png");
	ImageIcon food = new ImageIcon("imgs/food.png");

	// 定义蛇
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	int len;
	int score;

	// 定义食物
	Random rand = new Random();
	int foodx = rand.nextInt(34) * 25 + 25;
	int foody = rand.nextInt(24) * 25 + 75;

	// 定义方向
	String direction;

	boolean isStarted = false;
	boolean isFailed = false;

	// 定义蛇的速度(100ms)
	Timer timer = new Timer(100, this);

	public GamePanel() {
		this.setFocusable(true);
		init();
		this.addKeyListener(this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 25, 11);

		// 界面长850,宽600(为了是蛇身不贴着界面,故多取5)
		g.fillRect(25, 72, 855, 605);

		// 绘制蛇头(U->上,U->下,L->左,R->右)
		if (direction.equals("U")) {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (direction.equals("D")) {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (direction.equals("L")) {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (direction.equals("R")) {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}

		// 绘制身体
		for (int i = 1; i < len; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}

		// 开始或暂停游戏
		if (!isStarted) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("Press Space to Start / Pause", 325, 300);
		}

		// 结束游戏
		if (isFailed) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("Game Over , Press Space to Restart", 325, 300);
		}

		// 绘制食物
		if (isStarted && !isFailed) {
			food.paintIcon(this, g, foodx, foody);
		}

		// 绘制计分板
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 15));
		g.drawString("Len : " + len, 750, 35);
		g.drawString("Score : " + score, 750, 55);

	}

	// 初始化
	public void init() {
		isFailed = false;
		isStarted = false;
		len = 3;
		score = 0;
		direction = "R";
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			if (isFailed) {
				init();
			} else {
				isStarted = !isStarted;
			}
			repaint();
		} else if (keyCode == KeyEvent.VK_UP && direction != "D") {
			direction = "U";
		} else if (keyCode == KeyEvent.VK_DOWN && direction != "U") {
			direction = "D";
		} else if (keyCode == KeyEvent.VK_LEFT && direction != "R") {
			direction = "L";
		} else if (keyCode == KeyEvent.VK_RIGHT && direction != "L") {
			direction = "R";
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 再定一个闹钟
		timer.start();

		// 游戏开始 - 数据移动
		if (isStarted && !isFailed) {
			// 蛇身的移动
			for (int i = len; i > 0; i--) {
				snakex[i] = snakex[i - 1];
				snakey[i] = snakey[i - 1];
			}
			// 蛇头的移动
			if (direction.equals("U")) {
				snakey[0] = snakey[0] - 25;
				if (snakey[0] < 75)
					snakey[0] = 650;
			} else if (direction.equals("D")) {
				snakey[0] = snakey[0] + 25;
				if (snakey[0] > 650)
					snakey[0] = 75;
			} else if (direction.equals("L")) {
				snakex[0] = snakex[0] - 25;
				if (snakex[0] < 25)
					snakex[0] = 850;
			} else if (direction.equals("R")) {
				snakex[0] = snakex[0] + 25;
				if (snakex[0] > 850)
					snakex[0] = 25;
			}
			if (snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score++;
				foodx = rand.nextInt(34) * 25 + 25;
				foody = rand.nextInt(24) * 25 + 75;
			}

			// 判断游戏结束 : 头撞身
			for (int i = 1; i < len; i++) {
				if (snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
					isFailed = true;
				}
			}

			// 重新绘制
			repaint();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
