import model.AVLTree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (int i = 0; i < n; i++) {
            String[] line = sc.nextLine().split(" ");
            int type = Integer.parseInt(line[0]);
            int value = Integer.parseInt(line[1]);
            if (type == 1) {
                avl.insert(value, value);
            } else {
                avl.delete(value);
            }
            avl.levelOrder();
        }
    }
}
