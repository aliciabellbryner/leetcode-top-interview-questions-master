package topinterviewquestions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem_0690_EmployeeImportance_G {

    class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    //Approach #1: Depth-First Search： https://leetcode.com/problems/employee-importance/solution/
    //Time Complexity: O(N), where N is the number of employees. We might query each employee in dfs.
    //Space Complexity: O(N), the size of the implicit call stack when evaluating dfs.
    class Solution {
        Map<Integer, Employee> map;//key id of employee, value, Employee
        public int getImportance(List<Employee> employees, int queryid) {
            map = new HashMap();
            for (Employee i : employees) {
                map.put(i.id, i);
            }
            return dfs(queryid);
        }
        public int dfs(int id) {
            Employee employee = map.get(id);
            for (Integer subid : employee.subordinates)
                employee.importance += dfs(subid);
            return employee.importance;
        }
    }

}
