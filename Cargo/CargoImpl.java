//Kang Chau Yip 23268041
//Passes all large tests

/**
 * An implementation of the Cargo problem from the 2022 CITS2200 Project
 */
public class CargoImpl implements Cargo {
	/**
	 * Apply Binary Index Tree(Fenwick Tree) data structure to predict what total
	 * mass of cargo they will have on board at points throughout their voyage.
	 * 
	 * @param stops   the number of stops on the route
	 * @param quesirs a sequence of queries
	 * @return result an array of the result of each query in the same order.
	 */
	public int[] departureMasses(int stops, Query[] queries) {
		// create a Binary Indexed Tree that store the cargoMass value
		// to add the value of the specific index to the result list at the end
		int[] BITree = new int[stops + 1];
		int[] cargoMassList = new int[stops];
		for (int i = 0; i < cargoMassList.length; i++)
			cargoMassList[i] = 0;

		// construct a Binary Index Tree
		for (int i = 1; i <= stops; i++)
			BITree[i] = 0;
		// Copy the cargoMassList value into BITree
		for (int i = 0; i < stops; i++)
			updateBIT(i, cargoMassList[i], stops, BITree);

		// Traverse through the queries
		int result[] = new int[queries.length];
		for (int i = 0; i < queries.length; i++) {
			Query query = queries[i];
			int cargoMass = query.cargoMass;
			int start = query.collect;
			int end = query.deliver;
			update(start, end - 1, cargoMass, stops, BITree);
			result[i] = getValue(start, BITree);
		}
		return result;
	}

	/**
	 * Updating the Binary Indexed Tree's cargoMass value
	 * 
	 * @param index     the index in the BIT
	 * @param cargoMass the mass of cargo being transported
	 * @param stops     the number of stops on the route
	 * @param BITree    the Binary Indexed Tree that store the cargoMass value
	 */
	private void updateBIT(int index, int cargoMass, int stops, int[] BITree) {
		// index in BITree[] is 1 more than the index in cargoMassList[]
		index = index + 1;

		// Looping the ancestors of BITree by flipping the last set bit
		for (int i = index; i <= stops; i += i & (-i)) {
			BITree[i] += cargoMass;
		}
	}

	/**
	 * Set the range of the update in the Binary Indexed Tree
	 * 
	 * @param start     the index of the port at which we will collect the cargo
	 * @param end       the index of the port to which the cargo must be delivered
	 * @param cargoMass the mass of cargo being transported
	 * @param stops     the number of stops on the route
	 * @param BITree    the Binary Indexed Tree that store the cargoMass value
	 */
	private void update(int start, int end, int cargoMass, int stops, int[] BITree) {
		// Update the values from start to end
		updateBIT(start, cargoMass, stops, BITree);

		// cancel the effect of the first one
		updateBIT(end + 1, -cargoMass, stops, BITree);
	}

	/**
	 * calculate all the cargo masses that have been added in that stop by simply
	 * adding (Flipping the last seen bit) the sum of elements value that is stored
	 * in that stop.
	 * 
	 * @param index  the index in the BIT
	 * @param BITree the Binary Indexed Tree that store the cargoMass value
	 * @return value all the cargo masses that have been added in that stop
	 */
	private int getValue(int index, int[] BITree) {
		// index in BITree[] is 1 more than the index in cargoMassList[]
		index = index + 1;

		int value = 0;
		// Looping the ancestors of BITree by flipping the last set bit
		for (int i = index; i > 0; i -= i & (-i)) {
			value += BITree[i];
		}

		// Return the value
		return value;
	}

}