package com.qqhru.wagnhuanjie.demo;

import javax.swing.JFrame;

public class GameStart {

	public static void main(String[] args) {

		// 创建游戏界面
		JFrame frame = new JFrame();
		frame.setBounds(10, 10, 900, 720);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GamePanel panel = new GamePanel();
		frame.add(panel);

		frame.setVisible(true);
	}

}
