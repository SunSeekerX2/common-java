package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.game.utils.DrawUtils;

public class BackgroundImg extends Element {

	public BackgroundImg(int x, int y) {
		super(x, y);
		try {
			int[] size = DrawUtils.getSize("res/img/grass.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//绘制背景图
	public void draw() {
		try {
			DrawUtils.draw("res/img/background.jpg", 0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置背景图的渲染级别
	 */
	public int getOrder() {
		return 2;
	}
}
