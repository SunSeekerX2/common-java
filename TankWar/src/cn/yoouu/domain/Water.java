package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.busness.Blockable;
import cn.yoouu.game.utils.DrawUtils;

/**
 * 水墙
 * 
 * @author YOU
 *
 */
public class Water extends Element implements Blockable {

	public Water(int x, int y) {
		super(x, y);

		try {
			int[] size = DrawUtils.getSize("res/img/steel.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		try {
			DrawUtils.draw("res/img/water.gif", x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
