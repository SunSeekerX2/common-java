package cn.yoouu.game;

public class App {
	public static void main(String[] args) {
		/**
		 * 游戏的主入口。所有代码从这里执行
		 * 后期还未添加的功能
		 * 1：加入BOOS坦克
		 * 2：选择关卡
		 */
		// 创建游戏窗体
		GameWindow gw = new GameWindow(Config.TITLE, Config.WIDTH, Config.HEIGHT, Config.FPS);
		// 开始游戏
		gw.start();
	}
}
