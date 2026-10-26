public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     // This method checks if the 3 strings can come from the same BST.
     public static String verify(String traversal1, String traversal2, String traversal3){
          // WRONG IDEA: Guess the traversal type by looking at the numbers.
          // This does not really check if all 3 strings come from one tree.
          int[] first = toArray(traversal1);
          int[] second = toArray(traversal2);
          int[] third = toArray(traversal3);

          if (first == null || second == null || third == null) {
               return "Invalid traversals";
          }

          // This check is also incomplete because it only checks the length.
          if (first.length != second.length || first.length != third.length ||
                    !hasUniqueValues(first) || !hasSameValues(first, second) ||
                    !hasSameValues(first, third)) {
               return "Invalid traversals";
          }

          int[] inorder = first.clone();
          java.util.Arrays.sort(inorder);
          java.util.ArrayList<Node> trees = makeTrees(inorder, 0, inorder.length - 1);
          String[] input = {toText(first), toText(second), toText(third)};
          String[] names = {"Inorder", "Preorder", "Postorder"};

          for (Node tree : trees) {
               String[] actual = {
                    getTraversal(tree, 0),
                    getTraversal(tree, 1),
                    getTraversal(tree, 2)
               };

               for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                         if (i == j) continue;
                         int k = 3 - i - j;
                         if (input[0].equals(actual[i]) && input[1].equals(actual[j]) &&
                                   input[2].equals(actual[k])) {
                              return "Traversal 1 - " + names[i] + ", Traversal 2 - " +
                                        names[j] + ", Traversal 3 - " + names[k];
                         }
                    }
               }
          }

          return "Invalid traversals";
     }

     // A small class for one BST node.
     static class Node {
          int value;
          Node left, right;

          Node(int value) {
               this.value = value;
          }
     }

     // Convert text like "1-2-3" into numbers.
     private static int[] toArray(String text) {
          try {
               if (text == null || text.length() == 0) return null;
               String[] parts = text.split("-");
               int[] numbers = new int[parts.length];
               for (int i = 0; i < parts.length; i++) {
                    numbers[i] = Integer.parseInt(parts[i]);
               }
               return numbers;
          } catch (Exception e) {
               return null;
          }
     }

     // Check that one traversal does not contain duplicate numbers.
     private static boolean hasUniqueValues(int[] values) {
          for (int i = 0; i < values.length; i++) {
               for (int j = i + 1; j < values.length; j++) {
                    if (values[i] == values[j]) return false;
               }
          }
          return true;
     }

     // Check that two traversals contain exactly the same values.
     private static boolean hasSameValues(int[] a, int[] b) {
          for (int value : a) {
               boolean found = false;
               for (int other : b) {
                    if (value == other) found = true;
               }
               if (!found) return false;
          }
          return true;
     }

     // Create all BSTs by trying every possible root value.
     private static java.util.ArrayList<Node> makeTrees(int[] values, int start, int end) {
          java.util.ArrayList<Node> result = new java.util.ArrayList<Node>();

          if (start > end) {
               result.add(null);
               return result;
          }

          for (int rootIndex = start; rootIndex <= end; rootIndex++) {
               java.util.ArrayList<Node> leftTrees = makeTrees(values, start, rootIndex - 1);
               java.util.ArrayList<Node> rightTrees = makeTrees(values, rootIndex + 1, end);

               for (Node left : leftTrees) {
                    for (Node right : rightTrees) {
                         Node root = new Node(values[rootIndex]);
                         root.left = left;
                         root.right = right;
                         result.add(root);
                    }
               }
          }
          return result;
     }

     // order 0 = inorder, 1 = preorder, 2 = postorder.
     private static String getTraversal(Node root, int order) {
          java.util.ArrayList<Integer> values = new java.util.ArrayList<Integer>();
          visit(root, order, values);
          String answer = "";
          for (int i = 0; i < values.size(); i++) {
               if (i > 0) answer += "-";
               answer += values.get(i);
          }
          return answer;
     }

     private static void visit(Node root, int order, java.util.ArrayList<Integer> values) {
          if (root == null) return;
          if (order == 1) values.add(root.value);
          visit(root.left, order, values);
          if (order == 0) values.add(root.value);
          visit(root.right, order, values);
          if (order == 2) values.add(root.value);
     }

     // Try all 6 ways to assign the 3 input strings to traversal orders.
     private static String matchTraversals(int[] a, int[] b, int[] c,
               String in, String pre, String post, String[] names, int[] used) {
          int[][] inputs = {a, b, c};
          String[] actual = {in, pre, post};
          String[] inputText = {toText(a), toText(b), toText(c)};

          for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {
                    if (j == i) continue;
                    int k = 3 - i - j;
                    if (inputText[0].equals(actual[i]) && inputText[1].equals(actual[j]) &&
                              inputText[2].equals(actual[k])) {
                         return "Traversal 1 - " + names[i] + ", Traversal 2 - " +
                                   names[j] + ", Traversal 3 - " + names[k];
                    }
               }
          }
          return null;
     }

     private static String toText(int[] values) {
          String answer = "";
          for (int i = 0; i < values.length; i++) {
               if (i > 0) answer += "-";
               answer += values[i];
          }
          return answer;
     }
}
