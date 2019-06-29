package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.busness.Blockable;
import cn.yoouu.busness.Destoryable;
import cn.yoouu.busness.Hitable;
import cn.yoouu.game.utils.DrawUtils;

/**
 * 铁墙
 * 
 * @author YOU
 *
 */
public class Steel extends Element implements Blockable, Hitable, Destoryable {

	// 血量
	private int blood = 4;

	public Steel(int x, int y) {
		super(x, y);
		try {
			int[] size = DrawUtils.getSize("res/img/steel.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 行为：成员方法
	public void draw() {
		try {
			DrawUtils.draw("res/img/steel.gif", x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 挨打后的反映
	 * 
	 * @return
	 */
	public Blast showAttack() {
		// 血量-1
		//blood--;
		return new Blast(this, true);// 传true,挨打
	}

	/**
	 * 销毁铁墙
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
