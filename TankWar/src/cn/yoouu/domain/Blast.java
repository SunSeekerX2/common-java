package cn.yoouu.domain;

import java.io.IOException;

import cn.yoouu.busness.Destoryable;
import cn.yoouu.busness.Hitable;
import cn.yoouu.game.utils.DrawUtils;
import cn.yoouu.game.utils.SoundUtils;

/**
 * 爆炸物类
 * 
 * @author YOU
 *
 */
public class Blast extends Element implements Destoryable {

	// 1：属性
	private String[] arr = { "res/img/blast_1.gif", "res/img/blast_2.gif", "res/img/blast_3.gif", "res/img/blast_4.gif",
			"res/img/blast_5.gif", "res/img/blast_6.gif", "res/img/blast_7.gif", "res/img/blast_8.gif" };

	// 定义变量，记住要绘制图片的索引
	private int index;

	// 定义变量，是否需要销毁该爆炸物
	// true表示销毁，false不销毁
	private boolean isDestory;

	// 2：构造方法
	// 爆炸物的坐标依赖铁墙的坐标

	public Blast(Hitable hit) {
		this(hit, false);
		// 播放爆炸死亡的声音
		try { SoundUtils.play("res/snd/blast.wav"); } catch (IOException e) {
		e.printStackTrace(); }
		 
	}

	// flag:true说明是挨打，绘制1-4个图片，falg：false，说明是销毁，绘制所有图片
	public Blast(Hitable hit, boolean flag) {
		Element element = (Element) hit;
		// 绘制爆炸物
		// 计算爆炸物的坐标
		// 2.1获取铁墙的坐标和宽高
		int x1 = element.x;
		int y1 = element.y;
		int w1 = element.width;
		int h1 = element.height;
		// 2.2爆炸物的宽和高
		try {
			int[] size = DrawUtils.getSize("res/img/blast_1.gif");
			width = size[0];
			height = size[1];
			// 计算爆炸物的坐标
			x = x1 + (w1 - width) / 2;
			y = y1 + (h1 - height) / 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (flag) {
			arr = new String[] { "res/img/blast_1.gif", "res/img/blast_2.gif", "res/img/blast_3.gif",
					"res/img/blast_4.gif" };
			
			  try { SoundUtils.play("res/snd/hit.wav"); } catch (IOException e) {
			  e.printStackTrace(); }
			
		}
	}

	// 3:成员方法
	public void draw() {
		// 定义变量，纪录要绘制图片的路径
		String res = arr[index++];
		if (index >= arr.length) {
			index = 0;
			// 说明爆炸物绘制了一次，销毁即可
			isDestory = true;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取爆炸物的状态,true：销毁，false：不销毁
	 * 
	 * @return
	 */
	public boolean isDestory() {
		return isDestory;
	}

	/**
	 * 销毁时，绘制爆炸物
	 */
	public Blast showDestory() {
		return null;
	}

}
