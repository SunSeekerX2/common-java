package cn.yoouu.domain;

import java.io.IOException;
import java.util.Random;

import cn.yoouu.game.Config;
import cn.yoouu.game.utils.DrawUtils;

/**
 * 敌方坦克类
 * 
 * @author YOU
 *
 */
public class EnemyTank extends Tank {

	public EnemyTank() {
		super();
	}

	public EnemyTank(int x, int y,int type) {
		super(x, y);
		this.type = type;
		if (type == 2) {
			//如果是二类坦克，速度加快
			speed = 6;
		}else if (type == 3) {
			//如果是3类坦克，血量加强
			blood = 6;
		}else if (type == 4) {
			speed = 8;
			blood = 6;
		}
		
		// 设置坦克的宽和高
		try {
			int[] size = DrawUtils.getSize("res\\img\\enemy_u.gif");
			width = size[0];
			height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绘制坦克
	 */
	public void draw() {
		// 加入坦克炮口方向朝移动方向
		String res = "";
		if (type == 1) {
			switch (direction) {
			case UP:
				res = "res\\img\\enemy_u.gif";
				break;
			case DOWN:
				res = "res\\img\\enemy_d.gif";
				break;
			case LEFT:
				res = "res\\img\\enemy_l.gif";
				break;
			case RIGHT:
				res = "res\\img\\enemy_r.gif";
				break;

			default:
				break;
			}
		}else if (type == 2) {
			switch (direction) {
			case UP:
				res = "res\\img\\enemy2U.gif";
				break;
			case DOWN:
				res = "res\\img\\enemy2D.gif";
				break;
			case LEFT:
				res = "res\\img\\enemy2L.gif";
				break;
			case RIGHT:
				res = "res\\img\\enemy2R.gif";
				break;

			default:
				break;
			}
		}else if (type == 3) {
			switch (direction) {
			case UP:
				res = "res\\img\\enemy3U.gif";
				break;
			case DOWN:
				res = "res\\img\\enemy3D.gif";
				break;
			case LEFT:
				res = "res\\img\\enemy3L.gif";
				break;
			case RIGHT:
				res = "res\\img\\enemy3R.gif";
				break;

			default:
				break;
			}
		}else if (type == 4) {
			switch (direction) {
			case UP:
				res = "res\\img\\enemy4u.png";
				break;
			case DOWN:
				res = "res\\img\\enemy4d.png";
				break;
			case LEFT:
				res = "res\\img\\enemy4l.png";
				break;
			case RIGHT:
				res = "res\\img\\enemy4r.png";
				break;

			default:
				break;
			}
		}{
			
		}

		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取敌方坦克的随机移动的方向
	 * 
	 * @return
	 */
	public Direction getRandomDirection() {
		// 获取随机数，java中涉及范围，包左不包右。
		int num = new Random().nextInt(4);
		switch (num) {
		case 0:
			return Direction.UP;
		case 1:
			return Direction.DOWN;
		case 2:
			return Direction.LEFT;
		case 3:
			return Direction.RIGHT;

		default:
			break;
		}
		return Direction.UP;
	}

	/**
	 * 坦克移动
	 * 
	 * @param direction
	 *            坦克的移动方向(用户录入的键)
	 */
	public void move() {
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
			// 不能移动，获取随机的一次方向
			direction = getRandomDirection();

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
			// 不能移动，获取随机的一次方向
			direction = getRandomDirection();

		}
		if (y < 0) {// 往上不能出界
			y = 0;
			// 不能移动，获取随机的一次方向
			direction = getRandomDirection();

		}
		if (x > Config.WIDTH - 64) {// 往右不能出界
			x = Config.WIDTH - 64;
			// 不能移动，获取随机的一次方向
			direction = getRandomDirection();

		}
		if (y > Config.HEIGHT - 64) {// 往下不能出界
			y = Config.HEIGHT - 64;
			// 不能移动，获取随机的一次方向
			direction = getRandomDirection();

		}
	}

	/**
	 * 挨打的方法
	 */
	public Blast showAttack() {
		// 扣血,每次扣己方坦克的攻击力
		blood -= new MyTank().power;
		// 挨打后的效果（爆炸物）
		return new Blast(this, true);
	}

}
