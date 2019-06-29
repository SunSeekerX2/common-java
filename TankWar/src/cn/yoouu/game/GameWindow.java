package cn.yoouu.game;

import java.io.IOException;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import cn.yoouu.busness.Attackable;
import cn.yoouu.busness.Blockable;
import cn.yoouu.busness.Destoryable;
import cn.yoouu.busness.Hitable;
import cn.yoouu.busness.Moveable;
import cn.yoouu.domain.BackgroundImg;
import cn.yoouu.domain.Blast;
import cn.yoouu.domain.Bullet;
import cn.yoouu.domain.Direction;
import cn.yoouu.domain.Element;
import cn.yoouu.domain.EnemyTank;
import cn.yoouu.domain.Grass;
import cn.yoouu.domain.MyTank;
import cn.yoouu.domain.Steel;
import cn.yoouu.domain.Wall;
import cn.yoouu.domain.Water;
import cn.yoouu.game.utils.DrawUtils;
import cn.yoouu.game.utils.SoundUtils;

public class GameWindow extends Window {
	// 定义集合。存储元素（砖）
	CopyOnWriteArrayList<Element> list = new CopyOnWriteArrayList<>();// 第一个地图元素类集合
	
	// 创建己方坦克
	MyTank mt;
	// 创建敌方坦克
	EnemyTank et1;
	EnemyTank et2;
	EnemyTank et3;
	EnemyTank et4;
	EnemyTank et5;
	EnemyTank et6;
	EnemyTank et7;
	// 创建背景
	BackgroundImg bg;

	public GameWindow(String title, int width, int height, int fps) {
		super(title, width, height, fps);
	}

	// 窗体加载时执行
	protected void onCreate() {

		// 创建背景
		bg = new BackgroundImg(0, 0);
		// 把背景加入集合中
		addElement(bg);
		
		// 创建砖墙
		for (int i = 1; i < Config.WIDTH / 64 - 1; i++) {
			Wall wall = new Wall(i * 64, 64);
			// 把墙加到集合中
			addElement(wall);
		}

		// 创建水墙
		for (int i = 1; i < Config.WIDTH / 64; i++) {
			Water water = new Water(i * 64, 64 * 3);
			// 把墙加到集合中
			// list.add(water);
			addElement(water);
		}
		// 创建铁墙
		for (int i = 0; i < Config.WIDTH / 64 - 1; i++) {
			Steel steel = new Steel(i * 64, 64 * 5);
			// 把墙加到集合中
			// list.add(steel);
			addElement(steel);
		}
		// 创建草坪
		for (int i = 1; i < Config.WIDTH / 64; i++) {
			Grass grass = new Grass(i * 64, 64 * 7);
			// 把墙加到集合中
			// list.add(grass);
			addElement(grass);
		}
		/*
		 * 隐藏坦克到草坪中
		 * 
		 * 简单做法，先绘制坦克，在绘制草坪，扩展性差，不推荐
		 * 
		 * 推荐做法：Comparator比较器接口 每往集合中添加一个元素，按照渲染级别渲染，渲染级别越高，元素就越靠后 渲染级别高的，最后渲染
		 * 
		 */

		// 创建坦克类对象
		mt = new MyTank(Config.WIDTH / 2 - 32, Config.HEIGHT - 64);
		// 把坦克对象添加到集合中
		// list.add(mt);
		addElement(mt);

		// 创建敌方坦克
		et1 = new EnemyTank(0, 0, 1);
		et2 = new EnemyTank(Config.WIDTH - 64, 0, 2);
		et3 = new EnemyTank(0, 64 * 2, 3);
		et4 = new EnemyTank(Config.WIDTH - 64, 64 * 2, 3);
		et5 = new EnemyTank(Config.WIDTH - 64, 64 * 4, 4);
		et6 = new EnemyTank(0, 64 * 4, 4);
		et7 = new EnemyTank(Config.WIDTH /2, 64 * 4, 4);
		// 将敌方坦克放进集合
		addElement(et1);
		addElement(et2);
		addElement(et3);
		addElement(et4);
		addElement(et5);
		addElement(et6);
		addElement(et7);

	}

	protected void onMouseEvent(int key, int x, int y) {

	}

	protected void onKeyEvent(int key) {
		// 如果背景图存在，或者集合元素为空就return，如果按下F1移除背景图，并播放声音开始游戏
		for (Element element : list) {
			if (element instanceof BackgroundImg || list.size() <= 0) {
				if (key == Keyboard.KEY_F1) {
					list.remove(bg);
					// 播放背景音乐
					try {
						SoundUtils.play("res\\snd\\start.wav");
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				return;
			}
		}
		//
		switch (key) {
		case Keyboard.KEY_UP:// 如果按下的上键
		case Keyboard.KEY_W:
			mt.move(Direction.UP);
			break;
		case Keyboard.KEY_DOWN:// 如果按下的下键
		case Keyboard.KEY_S:
			mt.move(Direction.DOWN);
			break;
		case Keyboard.KEY_LEFT:// 如果按左的上键
		case Keyboard.KEY_A:
			mt.move(Direction.LEFT);
			break;
		case Keyboard.KEY_RIGHT:// 如果按右的上键
		case Keyboard.KEY_D:
			mt.move(Direction.RIGHT);
			break;
		case Keyboard.KEY_RETURN:// 按下的是回车键
		case Keyboard.KEY_SPACE:
			Bullet shot = mt.shot();
			if (shot != null) {
				addElement(shot);
			} else {

			}
		default:

			break;
		}
	}

	// 实时刷新执行的方法
	protected void onDisplayUpdate() {

		// 如果己方坦克销毁或者敌方坦克销毁，游戏结束

		if (mt.isDestory() || ((et1).isDestory() && et2.isDestory())) {
			list.clear();

			try {
				DrawUtils.draw("res/img/gameover.gif", (Config.WIDTH - 96) / 2, (Config.HEIGHT - 96) / 2);
			} catch (Exception e) {
			} // 停止所有音乐
			SoundUtils.stop("res/snd/fire.wav");
			return;
		}

		// 如果集合中没有元素就不绘制，如果有就绘制元素,如果背景图没有移除就不继续
		if (list.size() <= 0) {
			return;
		} else {
			for (Element e : list) {
				e.draw();
				if (e instanceof BackgroundImg) {
					return;
				}
			}
		}

		// 调用敌方坦克的随机移动的功能
		for (Element e : list) {
			if (e instanceof EnemyTank) {
				((EnemyTank) e).move();

				// 如果是敌方坦克, 就调用其发射子弹的能力.
				Bullet shot = ((EnemyTank) e).shot();
				if (shot != null) {
					addElement(shot);
				}
			}
		}

		// 销毁出界的子弹
		for (Element e : list) {
			// 如果遍历的元素是子弹，判断是否需要销毁
			if (e instanceof Bullet) {
				// 判断子弹是否需要销毁
				boolean flag = ((Bullet) e).isDestory();
				if (flag) {// 如果需要，就从集合中移除子弹
					list.remove(e);
				}
			} else {

			}
		}
		// 销毁需要销毁的事物
		for (Element e : list) {
			// 判断事物是否具有销毁能力的事物
			if (e instanceof Destoryable) {
				// 判断是否需要销毁的事物，true：表示是
				boolean blast = ((Destoryable) e).isDestory();
				if (blast) {
					// 能走到这里，说明事物（对象）要销毁
					// 1:绘制销毁时的爆炸物
					Blast blast2 = ((Destoryable) e).showDestory();
					if (blast2 != null) {
						list.add(blast2);
					}
					// 2:从集合中移除该元素
					list.remove(e);
				}
			}
		}

		/*
		 * 1:校验坦克和铁墙是否撞上 2:校验可以移动的坦克和具有阻挡的功能的铁墙是否碰撞上 3:校验可以移动功能的事物和具有阻挡功能的事物是否碰撞上
		 */
		for (Element e1 : list) {
			for (Element e2 : list) {
				// 如果两个对象（e1,e2）不是同一个，在校验
				if (e1 != e2 && e1 instanceof Moveable && e2 instanceof Blockable) {
					// 走到这里说明e1是坦克，e2是铁墙
					boolean flag = ((Moveable) e1).checkHit((Blockable) e2);
					if (flag) {// flag = true ，说明撞上了
						break;
					}
				}
			}
		}

		/*
		 * 1:校验子弹和铁墙是否撞上 2:校验具有攻击能力的子弹和具有挨打能力的铁墙是否撞上 3:校验具有攻击能力的事物和具有挨打能力的事物是否撞上
		 */
		for (Element e1 : list) {
			for (Element e2 : list) {
				if (e1 instanceof Attackable && e2 instanceof Hitable) {
					// 走到这里说明e1是子弹，e2是铁墙
					boolean flag = ((Attackable) e1).checkAttack((Hitable) e2);
					if (flag) {
						// 如果是自己发射的子弹，不能攻击自己
						if (((Bullet) e1).getTank() == e2) {
							continue;
						}
						// 校验子弹不能攻击友军
						// 一个.java文件对应一个.class文件，一个java类对应一个字节码文件
						if (((Bullet) e1).getTank().getClass() == e2.getClass()) {
							continue;
						}
						// 说明子弹和铁墙碰上了
						// 关于子弹：销毁
						list.remove(e1);
						// 关于铁墙：给出响应（挨打后的反映，就是绘制爆炸物）
						Blast blast = ((Hitable) e2).showAttack();
						addElement(blast);
					}
				}
			}
		}

	}

	/**
	 * 往集合中添加元素，每添加一个元素，都按照渲染级别进行升序排列
	 * 
	 * @param e
	 *            要往集合中添加的元素
	 */
	public void addElement(Element e) {
		// 往集合中添加元素
		list.add(e);

		// 每添加一个元素，都按照渲染级别进行升序排列
		list.sort(new Comparator<Element>() {

			public int compare(Element e1, Element e2) {
				// 前减后：升序 后减前：降序
				return e1.getOrder() - e2.getOrder();
			}

		});
	}
}
