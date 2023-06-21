package chapter2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericSearch {

    public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
        for (T item : list) {
            if (item.compareTo(key) == 0) {
                return true; // found a match
            }
        }

        return false;
    }

    public static <T extends Comparable<T>> boolean binaryContains(List<T> list, T key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = list.get(middle).compareTo(key);
            if (comparison < 0) {
                low = middle + 1;
            } else if (comparison > 0) {
                high = middle - 1;
            } else {
                return true;
            }
        }

        return false;
    }

    public static class Node<T> implements Comparable<Node<T>> {
        final T state;
        Node<T> parent;
        double cost;
        double heuristic;

        // dfs, bfs
        Node(T state, Node<T> parent) {
            this.state = state;
            this.parent = parent;
        }

        // a*
        Node(T state, Node<T> parent, double cost, double heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node<T> other) {
            Double mine = cost + heuristic;
            Double theirs = other.cost + other.heuristic;

            return mine.compareTo(theirs);
        }
    }

    public static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        // frontier is where we've yet to go
        Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial, null));

        // explored is where we've been
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        // keep going while there is more to explore
        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.state;
            if (goalTest.test(currentState)) {
                return currentNode;
            }

            // check where we can go next and haven't explored
            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }

                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }

        return null;
    }

    public static <T> List<T> nodeToPath(Node<T> node) {
        List<T> path = new ArrayList<>();
        path.add(node.state);

        while (node.parent != null) {
            node = node.parent;
            path.add(0, node.state);
        }

        return path;
    }

    public static void main(String[] args) {
        System.out.println(linearContains(List.of(1, 5, 15, 15, 15, 15, 20),5)); // true
        System.out.println(binaryContains(List.of("a", "d", "e", "f", "z"),"f")); // true
        System.out.println(binaryContains(List.of("john", "mark", "ronald", "sarah"), "sheila")); // false
    }
}
