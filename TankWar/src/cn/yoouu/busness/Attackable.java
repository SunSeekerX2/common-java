package cn.yoouu.busness;

/**
 * 具有挨打能力的事物
 * 
 * @author YOU
 *
 */
public interface Attackable {

	/**
	 * 具有攻击能力的事物，和具有挨打能力的事物是否碰撞到一起。
	 */
	boolean checkAttack(Hitable hit);
}
