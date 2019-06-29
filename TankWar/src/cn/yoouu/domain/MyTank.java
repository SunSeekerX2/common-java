package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.busness.Destoryable;
import cn.yoouu.game.Config;
import cn.yoouu.game.utils.DrawUtils;

/**
 * 己方坦克
 * 
 * @author YOU
 *
 */
public class MyTank extends Tank{

	// 构造方法
	public MyTank() {
		super();
		// 初始化攻击力
		power = 2;
	}

	public MyTank(int x, int y) {
		super(x, y);
		// 初始化移动速度
		speed = 32;
		power = 2;
		blood = 6;

		// 设置坦克的宽和高
		try {
			int[] size = DrawUtils.getSize("res\\img\\tank_u.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绘制乙方坦克
	 */
	public void draw() {
		// 加入坦克炮口方向朝移动方向
		String res = "";
		switch (direction) {
		case UP:
			res = "res\\img\\tank_u.gif";
			break;
		case DOWN:
			res = "res\\img\\tank_d.gif";
			break;
		case LEFT:
			res = "res\\img\\tank_l.gif";
			break;
		case RIGHT:
			res = "res\\img\\tank_r.gif";
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

	/**
	 * 坦克移动
	 * 
	 * @param direction
	 *            坦克的移动方向(用户录入的键)
	 */
	public void move(Direction direction) {
		// 如果传过来的方向和坦克不能移动的方向一致，就return
		if (direction == badDirection) {
			//System.out.println("不能移动");
			// 移动最小间隙
			switch (direction) {
			case UP:
				y -= badSpeed;
				break;
			case DOWN:
				y += badSpeed;
				break;
			case LEFT:
				x -= badSpeed;
				break;
			case RIGHT:
				x += badSpeed;
				break;

			default:
				break;
			}
			return;
		}
		// 如果传过来的方向和坦克的方向不一致，就把传过来的方向赋值给坦克的方向。然后return
		if (this.direction != direction) {
			this.direction = direction;
			return;
		}

		switch (direction) {
		case UP:
			y -= speed;
			break;
		case DOWN:
			y += speed;
			break;
		case LEFT:
			x -= speed;
			break;
		case RIGHT:
			x += speed;
			break;

		default:
			break;
		}

		// 越界处理
		if (x < 0) {// 往走不能出界
			x = 0;
		}
		if (y < 0) {// 往上不能出界
			y = 0;
		}
		if (x > Config.WIDTH - 64) {// 往右不能出界
			x = Config.WIDTH - 64;
		}
		if (y > Config.HEIGHT - 64) {// 往下不能出界
			y = Config.HEIGHT - 64;
		}
	}

	/**
	 * 挨打的方法
	 */
	public Blast showAttack() {
		// 扣血,每次扣敌方坦克的攻击力
		blood -= new EnemyTank().power;
		// 挨打后的效果（爆炸物）
		return new Blast(this, true);
	}

}
