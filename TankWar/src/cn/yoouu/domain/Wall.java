package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.busness.Blockable;
import cn.yoouu.busness.Destoryable;
import cn.yoouu.busness.Hitable;
import cn.yoouu.game.utils.DrawUtils;

/**
 * 砖墙
 * 
 * @author YOU
 *
 */
public class Wall extends Element implements Blockable, Hitable, Destoryable {
	// 定义血量
	private int blood = 1;

	// 构造方法
	public Wall(int x, int y) {
		super(x, y);

		try {
			int[] size = DrawUtils.getSize("res/img/water.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 行为：成员方法
	public void draw() {
		// 创建砖墙
		try {
			DrawUtils.draw("res/img/wall.gif", x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 挨打
	 */
	public Blast showAttack() {
		blood--;
		return new Blast(this, true);
	}

	/**
	 * 销毁z砖墙
	 * 
	 * @return
	 */
	public boolean isDestory() {
		return blood <= 0;
	}

	/**
	 * 销毁时响应的动作
	 */
	public Blast showDestory() {
		return new Blast(this);
	}
}
