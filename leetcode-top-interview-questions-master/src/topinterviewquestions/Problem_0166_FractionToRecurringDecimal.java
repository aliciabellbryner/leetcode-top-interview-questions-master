package topinterviewquestions;

import java.util.HashMap;

public class Problem_0166_FractionToRecurringDecimal {

	public String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) {
			return "0";
		}
		StringBuilder res = new StringBuilder();
		// "+" or "-"
		res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");//先判断结果是正还是负
		long num = Math.abs((long) numerator);
		long den = Math.abs((long) denominator);
		// integral part
		res.append(num / den);
		num %= den;//剩下的余数部分赋值给num
		if (num == 0) {
			return res.toString();
		}
		// fractional part，小数部分
		res.append(".");
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		map.put(num, res.length());//key是每次剩下来的余数，value是此时res的长度，
		// 这样如果是循环，就可以找到开始循环时res的长度从而知道（）应该插在什么位置
		while (num != 0) {
			num *= 10;
			res.append(num / den);
			num %= den;
			if (map.containsKey(num)) {
				int index = map.get(num);
				res.insert(index, "(");
				res.append(")");
				break;
			} else {
				map.put(num, res.length());
			}
		}
		return res.toString();
	}

}
