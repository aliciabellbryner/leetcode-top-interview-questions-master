package topinterviewquestions;
import java.util.NoSuchElementException;
import java.util.*;

//左没讲过，他跳过了
public class Problem_0341_FlattenNestedListIterator {

	//https://leetcode.com/problems/flatten-nested-list-iterator/solution/
	//Approach 3: Two Stacks

	//工具类不用提交
	public interface NestedInteger {

		// @return true if this NestedInteger holds a single integer, rather than a
		// nested list.
		public boolean isInteger();

		// @return the single integer that this NestedInteger holds, if it holds a
		// single integer
		// Return null if this NestedInteger holds a nested list
		public Integer getInteger();

		// @return the nested list that this NestedInteger holds, if it holds a nested
		// list
		// Return null if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}


	public class NestedIterator implements Iterator<Integer> {

		private Deque<List<NestedInteger>> listStack = new ArrayDeque<>();
		private Deque<Integer> indexStack = new ArrayDeque<>();

		public NestedIterator(List<NestedInteger> nestedList) {
			listStack.addFirst(nestedList);
			indexStack.addFirst(0);
		}

		@Override
		public Integer next() {
			if (!hasNext()) throw new NoSuchElementException();
			int currentPosition = indexStack.removeFirst();
			indexStack.addFirst(currentPosition + 1);
			return listStack.peekFirst().get(currentPosition).getInteger();
		}


		@Override
		public boolean hasNext() {
			makeStackTopAnInteger();
			return !indexStack.isEmpty();
		}


		private void makeStackTopAnInteger() {//就是把listStack中的第一个元素变成是integer

			while (!indexStack.isEmpty()) {

				// If the top list is used up, pop it and its index.
				if (indexStack.peekFirst() >= listStack.peekFirst().size()) {
					indexStack.removeFirst();
					listStack.removeFirst();
					continue;
				}

				// Otherwise, if it's already an integer, we don't need to do anything.
				if (listStack.peekFirst().get(indexStack.peekFirst()).isInteger()) {
					break;
				}

				// Otherwise, it must be a list. We need to update the previous index
				// and then add the new list with an index of 0.
				listStack.addFirst(listStack.peekFirst().get(indexStack.peekFirst()).getList());
				indexStack.addFirst(indexStack.removeFirst() + 1);
				indexStack.addFirst(0);
			}
		}

	}


}
	/*

	// 不要提交这个接头类
	public interface NestedInteger {

		// @return true if this NestedInteger holds a single integer, rather than a
		// nested list.
		public boolean isInteger();

		// @return the single integer that this NestedInteger holds, if it holds a
		// single integer
		// Return null if this NestedInteger holds a nested list
		public Integer getInteger();

		// @return the nested list that this NestedInteger holds, if it holds a nested
		// list
		// Return null if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}

	public class NestedIterator implements Iterator<Integer> {

		private List<NestedInteger> list;
		private Stack<Integer> stack;
		private boolean used;

		public NestedIterator(List<NestedInteger> nestedList) {
			list = nestedList;
			stack = new Stack<>();
			stack.push(-1);
			used = true;
			hasNext();
		}

		@Override
		public Integer next() {
			Integer ans = null;
			if (!used) {
				ans = get(list, stack);
				used = true;
				hasNext();
			}
			return ans;
		}

		@Override
		public boolean hasNext() {
			if (stack.isEmpty()) {
				return false;
			}
			if (!used) {
				return true;
			}
			if (findNext(list, stack)) {
				used = false;
			}
			return !used;
		}

		private Integer get(List<NestedInteger> nestedList, Stack<Integer> stack) {
			int index = stack.pop();
			Integer ans = null;
			if (!stack.isEmpty()) {
				ans = get(nestedList.get(index).getList(), stack);
			} else {
				ans = nestedList.get(index).getInteger();
			}
			stack.push(index);
			return ans;
		}

		private boolean findNext(List<NestedInteger> nestedList, Stack<Integer> stack) {
			int index = stack.pop();
			if (!stack.isEmpty() && findNext(nestedList.get(index).getList(), stack)) {
				stack.push(index);
				return true;
			}
			for (int i = index + 1; i < nestedList.size(); i++) {
				if (pickFirst(nestedList.get(i), i, stack)) {
					return true;
				}
			}
			return false;
		}

		private boolean pickFirst(NestedInteger nested, int position, Stack<Integer> stack) {
			if (nested.isInteger()) {
				stack.add(position);
				return true;
			} else {
				List<NestedInteger> actualList = nested.getList();
				for (int i = 0; i < actualList.size(); i++) {
					if (pickFirst(actualList.get(i), i, stack)) {
						stack.add(position);
						return true;
					}
				}
			}
			return false;
		}

	}


	 */
