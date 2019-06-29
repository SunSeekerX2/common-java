package cn.yoouu.busness;

import cn.yoouu.domain.Blast;

/**
 * 具有销毁能力的事物
 * 
 * @author YOU
 *
 */
public interface Destoryable {

	/**
	 * 判断是否需要销毁
	 * 
	 * @return
	 */
	boolean isDestory();

	/**
	 * 销毁的时候绘制爆炸物
	 */
	Blast showDestory();
}
