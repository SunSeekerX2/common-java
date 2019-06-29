package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.busness.Attackable;
import cn.yoouu.busness.Destoryable;
import cn.yoouu.busness.Hitable;
import cn.yoouu.game.Config;
import cn.yoouu.game.utils.CollisionUtils;
import cn.yoouu.game.utils.DrawUtils;
import cn.yoouu.game.utils.SoundUtils;

/*
 * 子弹类
 */
public class Bullet extends Element implements Attackable, Destoryable {
	private Direction direction;// 子弹的移动方向，应该和坦克的移动方向一致
	private int speed = 10;// 记录子弹的移动速度
	private Tank tank;// 定义变量，纪录子弹所属的坦克

	public Bullet(Tank tank) {
		super();

		// 用Tank纪录，子弹所属的坦克
		this.tank = tank;

		// 子弹的坐标是根据坦克的坐标来定的
		// 获取坦克的坐标和宽高
		int tankX = tank.x;
		int tanky = tank.y;
		int tankWidth = tank.width;
		int tankHeight = tank.height;
		this.direction = tank.getDirection();

		// 获取子弹的宽和高
		try {
			int[] size = DrawUtils.getSize("res\\img\\bullet_u.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}

		method1(tankX, tanky, tankWidth, tankHeight);
	}

	/**
	 * 普通的坐标计算方法
	 * 
	 * @param tankX
	 * @param tanky
	 * @param tankWidth
	 * @param tankHeight
	 */

	private void method1(int tankX, int tanky, int tankWidth, int tankHeight) {
		// 计算子弹的坐标（加入四舍五入的元素）
		switch (direction) {
		case UP:
			x = Math.round(tankX + (tankWidth - width) / 2.0f);
			y = Math.round(tanky - height / 2.0f);

			break;
		case DOWN:
			x = Math.round(tankX + (tankWidth - width) / 2f);
			y = Math.round(tanky + tankHeight - height / 2f);

			break;
		case LEFT:
			// x = Math.round(tankX - width / 2; //图片问题，修改算法
			x = tankX - width;
			y = Math.round(tanky + (tankHeight - height) / 2f);

			break;
		case RIGHT:
			x = Math.round(tankX + tankWidth - width / 2f);
			y = Math.round(tanky + (tankHeight - height) / 2f);

			break;

		default:
			break;
		}
		// 播放子弹发射的声音
		/*
		try {
			SoundUtils.play("res\\snd\\fire.wav");
		} catch (Exception e) {
		}
		*/
	}

	/**
	 * 绘制子弹
	 */
	public void draw() {
		// 子弹的方向和坦克的方向一致
		String res = "";
		switch (direction) {
		case UP:
			res = "res\\img\\bullet_u.gif";
			y -= speed;
			break;
		case DOWN:
			res = "res\\img\\bullet_d.gif";
			y += speed;
			break;
		case LEFT:
			res = "res\\img\\bullet_l.gif";
			x -= speed;
			break;
		case RIGHT:
			res = "res\\img\\bullet_r.gif";
			x += speed;
			break;

		default:
			break;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isDestory() {
		// 判断子弹是否出界，出界就返回true
		// 子弹完全出界才销毁
		if (x < 0 - width || x > Config.WIDTH || y < 0 - height || y > Config.HEIGHT) {
			return true;
		}
		return false;
	}

	public boolean checkAttack(Hitable hit) {
		Element e = ((Element) hit);
		// 获取铁墙的宽和高
		int x1 = e.x;
		int y1 = e.y;
		int w1 = e.width;
		int h1 = e.height;
		// 第一个矩形：铁墙的坐标和宽高，第二个矩形：子弹的坐标和宽高
		return CollisionUtils.isCollsionWithRect(x1, y1, w1, h1, x, y, width, height);
	}

	/**
	 * 子弹销毁时的反应
	 */
	public Blast showDestory() {
		return null;
	}

	/**
	 * 获取子弹所属的坦克
	 * 
	 * @return
	 */
	public Tank getTank() {
		return tank;
	}

}
