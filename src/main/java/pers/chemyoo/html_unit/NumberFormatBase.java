package pers.chemyoo.html_unit;

/**
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年3月14日 上午10:10:38
 * @since 2018年3月14日 上午10:10:38
 * @description 类说明
 */
public enum NumberFormatBase {
	/** 2 进制 */
	Binary(2),
	/** 4 进制 */
	Quanternary(4),
	/** 8 进制 */
	Octal(8),
	/** 10 进制 */
	Decimal(10);

	private int number;

	NumberFormatBase(int number) {
		this.number = number;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

}
