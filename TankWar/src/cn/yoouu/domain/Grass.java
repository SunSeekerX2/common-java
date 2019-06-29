package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.game.utils.DrawUtils;

/**
 * 草坪
 * 
 * @author YOU
 *
 */
public class Grass extends Element {

	public Grass(int x, int y) {
		super(x, y);

		try {
			int[] size = DrawUtils.getSize("res/img/grass.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		try {
			DrawUtils.draw("res/img/grass.gif", x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置草坪的渲染级别
	 */
	public int getOrder() {
		return 1;
	}
}
