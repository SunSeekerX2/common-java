package cn.yoouu.domain;

/**
 * 父类：表示所有元素
 * 
 * @author YOU
 *
 */
public abstract class Element {
	// 属性
	// 坐标
	protected int x;
	protected int y;

	// 宽高
	protected int width;
	protected int height;

	// 空参构造
	public Element() {

	}

	public Element(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 绘制元素
	public abstract void draw();

	/**
	 * 返回元素的渲染级别，默认都是0，渲染级别越大，元素越靠后渲染
	 * 
	 * @return
	 */
	public int getOrder() {
		return 0;
	}
}
