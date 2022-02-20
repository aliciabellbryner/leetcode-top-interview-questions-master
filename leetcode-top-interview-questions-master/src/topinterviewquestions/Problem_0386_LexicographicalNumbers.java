package topinterviewquestions;

import java.util.LinkedList;
import java.util.List;

public class Problem_0386_LexicographicalNumbers {

	//The idea is pretty simple. If we look at the order we can find out
	// we just keep adding digit from 0 to 9 to every digit and make it a tree.
	//Then we visit every node in pre-order.
	//Overflow checking is also really important.
	public List<Integer> lexicalOrder(int n) {
		List<Integer> res = new LinkedList<>();
		for(int i = 1;i<=9;i++){
			backtrack(res,i,n);
		}
		return res;
	}
	private void backtrack(List<Integer> res, int root, int n){
		if(root > n) {
			return;
		}
		res.add(root);
		if(root > Integer.MAX_VALUE/10) return; //check Overflow before  multiply
		int nextRoot = root*10;
		for(int i = 0;i<= 9;i++){
			if(nextRoot > Integer.MAX_VALUE - i) {
				break; // check Overflow before addition
			}
			backtrack(res,nextRoot+i,n);
		}
	}

}
