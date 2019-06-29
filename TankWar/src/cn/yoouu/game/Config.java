package cn.yoouu.game;

public interface Config {
	/*
	 * 四大权限修饰符 private: 强调的是给自己使用。 默认: 强调的是同包下使用。 protected: 强调的时给子类使用。 public:
	 * 强调给大家(项目下的所有类)使用。
	 */
	String TITLE = "坦克大战";
	int WIDTH = 64 * 15;
	int HEIGHT = 64 * 10;
	int FPS = 41;

}
