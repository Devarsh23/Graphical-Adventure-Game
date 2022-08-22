package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to implement the kruskal algorithm to generate the dungeon with the help of
 * union find concept. It specifies on how to unify the nodes and find the root of the node
 * and check if the nodes belong to same component or not. Here I have make this class package
 * private because It is not required by the user to run the program. Hence, I have made this
 * method the package private as it would be used by the internal functioning.
 */
class UnionFind {
  private List<List<Integer>> leftOver;
  private List<List<Integer>> mstLocation;
  private int size;
  private int[] sz;
  private int[] id;
  private int numComponents;

  /**
   * This helps to construct the object of the union find class. It initializes all the private
   * field of the union find class. The constructor of the package private method is package
   * private as well as it would be not used outside by the outside user. It would be only called
   * by the internal classes of the same package.
   * @param size the size of the dungeon.
   * @throws IllegalArgumentException if the size is less than or equal to zero.
   */
  UnionFind(int size) throws  IllegalArgumentException {
    if (size <= 0) {
      throw new IllegalArgumentException("Size less than is not allowed");
    }

    this.size = numComponents = size;
    this.leftOver = new ArrayList<>();
    this.mstLocation = new ArrayList<>();
    sz = new int[size];
    id = new int[size];

    for (int i = 0; i < size; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }

  private int find(int p) {
    int root = p;
    while (root != id[root]) {
      root = id[root];
    }
    while (p != root) {
      int next = id[p];
      id[p] = root;
      p = next;
    }

    return root;
  }

  private boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  /**
   * This method unifies the two nodes in to the one component. It also handles the case where the
   * both the nodes belong to the same components. Also, it keeps the track of the size of the
   * components.This method is package private because it would be not used outside by the outside
   * user. It would be only called by the internal classes of the same package.
   * @param p the integer vale of the first node
   * @param q the integer value of the second node.
   */
  void unify(int p, int q) {
    if (connected(p, q)) {
      List<Integer> tempList = new ArrayList<>();
      tempList.add(p);
      tempList.add(q);
      this.leftOver.add(tempList);
    }
    else {
      size = this.size;
      List<Integer> h = new ArrayList<>();
      h.add(p);
      h.add(q);
      this.mstLocation.add(h);
      int root1 = find(p);
      int root2 = find(q);
      if (sz[root1] < sz[root2]) {
        sz[root2] += sz[root1];
        id[root1] = root2;
        sz[root1] = 0;
      } else {
        sz[root1] += sz[root2];
        id[root2] = root1;
        sz[root2] = 0;
      }
      numComponents--;
    }
  }

  /**
   * This method specifies the list of the leftover edges which are generated while running the
   * Kruskals algorithm. This method is package private because it would be not used outside by the
   * outside user. It would be only called by the internal classes of the same package.
   * @return the deep copy of the list of the leftover edges.
   */
  List<List<Integer>> getLeftOver() {
    //Now we will return the deep copy.
    List<List<Integer>> deepLeftOverCopyList = new ArrayList<>();
    for (int index = 0; index < this.leftOver.size(); index++) {
      deepLeftOverCopyList.add(new ArrayList<>(this.leftOver.get(index)));
    }

    return deepLeftOverCopyList;
  }

  /**
   * It specifies the list of the minimum spanning tree which is generated after running the
   * kruskals algorithm. This method is package private because it would be not used outside by the
   * outside user. It would be only called by the internal classes of the same package.
   * @return the 2D list which contains the edges in the minimum spanning tree.
   */
  List<List<Integer>> getMst() {
    //Now we will return the deep copy.
    List<List<Integer>> deepMstCopyList = new ArrayList<>();
    for (int index = 0; index < this.mstLocation.size(); index++) {
      deepMstCopyList.add(new ArrayList<>(this.mstLocation.get(index)));
    }

    return deepMstCopyList;
  }
}