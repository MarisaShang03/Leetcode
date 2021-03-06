package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
Time complexity is O(E + V)
Because when we build the HashMap, it is O(E). We need to go through each edge to compute the hashmap
The backtracking process takes O(V+E), since we implement DFS travasal in the graph
Thus we visit each node and each edge only once in worst case.

Space complexity is O(E+V)
When we build the graph, it uses E+V space. During the backtracking process, the visited and checked array takes O(V) space
Thus overall it takes O(E+V) space
 */
public class LC207 {
        public boolean canFinish(int numCourses, int[][] prerequisites) {

            // We construct the hashmap, key is the preprerequisite course -> next courses
            HashMap<Integer, List<Integer>> courseMap = new HashMap<>();
            // Loop through the prerequisites, build up the hashmap
            for (int[] course : prerequisites) {
                // course[0] is the next course, course[1] is the prereq course
                //if course[1] is a key, then add course[0] to the arrayList;
                if (courseMap.containsKey(course[1])) {
                    courseMap.get(course[1]).add(course[0]);
                } else {
                    //if not a key, then create a new pair of course[1]->course[0]
                    List<Integer> next = new ArrayList<>();
                    next.add(course[0]);
                    courseMap.put(course[1], next);
                }
            }

            //two boolean array to keep track of which node is visited and which is checked
            boolean[] checked = new boolean[numCourses];
            boolean[] visited = new boolean[numCourses];


            for(int current: courseMap.keySet()){
                if (this.cycle(current, courseMap, checked, visited))
                    return false;
            }
            return true;
        }

        /*
         * use DFS to check that there is no cycle from current
         */
        protected boolean cycle(Integer current,HashMap<Integer, List<Integer>> courseMap,
                boolean[] checked, boolean[] visited) {

            // base cases
            // if node was checked, then no cycle from current node.
            if (checked[current])
                return false;
            if (visited[current])
                // if current node is visited, then there must a cycle
                return true;

            // no next course, then must no cycle
            if (!courseMap.containsKey(current))
                return false;

            // before backtracking, mark the node in the visited
            visited[current] = true;

            boolean result = false;
            // postorder DFS, to visit all its children first.
            for (Integer next : courseMap.get(current)) {
                result = this.cycle(next, courseMap, checked, visited);
                if (result)
                    break;
            }
            //mark check to true, because we checked all the next courses
            checked[current] = true;
            return result;
        }
}
