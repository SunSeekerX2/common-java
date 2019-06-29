package cn.yoouu.domain;

import cn.yoouu.busness.Blockable;
import cn.yoouu.busness.Destoryable;
import cn.yoouu.busness.Hitable;
import cn.yoouu.busness.Moveable;
import cn.yoouu.game.utils.CollisionUtils;

/**
 * 所有坦克的父类
 * 
 * @author YOU
 *
 */
public abstract class Tank extends Element implements Moveable, Blockable, Hitable, Destoryable {
	/**
	 * 
	 */
	// 属性
	// 1：血量
	protected int blood = 1;
	// 2：攻击力
	protected int power = 1;
	// 3：移动方向
	protected Direction direction = Direction.UP; // 要有初始化值，小细节
	// 4：移动速度
	protected int speed = 2; // 要有初始化的值
	// 5:用来纪录最后一颗子弹的发射时间
	protected long lastShotTime;
	// 6:纪录坦克不能移动的方向
	protected Direction badDirection;
	// 7:纪录坦克碰撞前的最小移动间隙
	protected int badSpeed;
	// 8:纪录坦克的类型
	protected int type;
	// 构造方法
	public Tank() {
		super();
	}

	public Tank(int x, int y) {
		super(x, y);
	}

	/**
	 * 绘制坦克
	 */
	public abstract void draw();

	/**
	 * 坦克发射子弹
	 * 
	 * @return 子弹对象
	 */
	public Bullet shot() {
		// 谁调用，this就代表谁
		// 如果最后一颗子弹的发射时间和现在要发射的子弹间隔小于400ms，就不发射
		// 获取当前时间
		long nowTime = System.currentTimeMillis();
		// 如果当前时间和最后一颗子弹的发射时间小于400ms，就不发射,增加子弹发射数量，
		if (nowTime - lastShotTime < 400) {

			return null;
		} else {
			// lastShowTime变量值要重置
			lastShotTime = nowTime;

			return new Bullet(this);
		}

	}

	/**
	 * 获取坦克的移动方向
	 * 
	 * @return
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * 检测坦克和铁墙是否撞上
	 * 
	 * @param steel
	 * @return
	 */
	public boolean checkHit(Blockable block) {

		Element e = (Element) block;
		// 获取铁墙的坐标和宽高
		int x1 = e.x;
		int y1 = e.y;
		int w1 = e.width;
		int h1 = e.height;

		// 预判坦克的坐标
		int x2 = x;
		int y2 = y;
		switch (direction) {
		case UP:
			y2 -= speed;
			break;
		case DOWN:
			y2 += speed;
			break;
		case LEFT:
			x2 -= speed;
			break;
		case RIGHT:
			x2 += speed;
			break;

		default:
			break;
		}

		boolean flag = CollisionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, width, height);
		if (flag) {
			// 撞上了，不仅要记录不能移动的方向，还要计算最小间隙。
			badDirection = direction;
			switch (direction) {
			case UP:
				badSpeed = y - y1 - h1;
				break;
			case DOWN:
				badSpeed = y1 - y - height;
				break;
			case LEFT:
				badSpeed = x - x1 - w1;
				break;
			case RIGHT:
				badSpeed = x1 - w1 - width;
				break;

			default:
				break;
			}
		} else {
			// 没有撞上
			badDirection = null;
		}
		return flag;
	}

	/**
	 * 销毁的方法
	 */
	public boolean isDestory() {
		return blood <= 0;
	}

	public Blast showDestory() {
		return new Blast(this);
	}
}
