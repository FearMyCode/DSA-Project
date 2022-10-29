import java.util.Stack;
//Kang Chau Yip 23268041,Haoxuan Long 23144143
//Passes all small test cases

/**
 * An implementation of the Subsidiaries problem from the 2022 CITS2200 Project
 */
public class SubsidiariesImpl implements Subsidiaries {
	int[] owners;
	Query[] queries;

	/**
	 * Implement a function that finds for each transaction query the smallest
	 * company for which the payment could be considered internal, if there is one.
	 * 
	 * @param an array such that owners[id] is the id of the company that owns id
	 * @param an array of Subsidiaires.Query corresponding to each transaction
	 * @return result an array of id of the smallest company that owns both payer
	 *         and payee.
	 */
	public int[] sharedOwners(int[] owners, Query[] queries) {
		this.owners = owners;
		this.queries = queries;
		int[] result = new int[queries.length];

		for (int i = 0; i < queries.length; i++) {
			int payee = queries[i].payee;
			int payer = queries[i].payer;
			Stack<Integer> payerStack = parentsOf(payer);
			Stack<Integer> payeeStack = parentsOf(payee);
			// reverse the stack so we can start examine from the bottom(LCA)
			payeeStack = reverse(payeeStack);
			payerStack = reverse(payerStack);
			int LCA = lca(payerStack, payeeStack);
			result[i] = LCA;
		}
		return result;
	}

	/**
	 * Construct a Stack to store the id of the parent of the target
	 * 
	 * @param target either the payer or payee
	 * @return ancestor the parent stack of the target
	 */
	private Stack<Integer> parentsOf(int target) {
		// a stack to keep the list of ancestor
		Stack<Integer> ancestor = new Stack<>();
		ancestor.push(target);
		int index = target; // update the index
		while (owners[index] != -1) {
			ancestor.push(owners[index]);
			index = owners[index];
		}
		ancestor.push(-1);
		return ancestor;
	}

	/**
	 * Reverse the stack so it can search from the bottom
	 * 
	 * @param stack the parent stack of the payer or payee
	 * @return reversedStack the reversed Stack
	 */
	private Stack<Integer> reverse(Stack<Integer> stack) {
		Stack<Integer> reversedStack = new Stack<Integer>();
		while (!stack.empty()) {
			reversedStack.push(stack.pop());
		}
		return reversedStack;
	}

	/**
	 * Compare two reversed stacks and find first common integer (lowest common
	 * ancestor)
	 * 
	 * @param payerStack the parent stack of payer
	 * @param payeeStack the parent stack of payee
	 * @return result the smallest company that owns both payer and payee.
	 */
	private int lca(Stack<Integer> payerStack, Stack<Integer> payeeStack) {
		int result = -1;// unassigned value
		if (payerStack.size() >= payeeStack.size())
			result = payerStack.pop();
		else
			result = payeeStack.pop();
		// as long as one stack is not empty then it can be loop
		while (!payeeStack.empty()) {
			if (result == payeeStack.peek() || result == payerStack.peek())
				return result;
			else if (payerStack.size() >= payeeStack.size()) {
				result = payerStack.pop();
				if (payerStack.empty() && result != payeeStack.peek())
					return -1;
			} else
				result = payeeStack.pop();
		}
		return result;
	}
}
