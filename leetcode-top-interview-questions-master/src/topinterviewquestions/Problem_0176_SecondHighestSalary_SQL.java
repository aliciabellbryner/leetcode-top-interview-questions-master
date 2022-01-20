package topinterviewquestions;

public class Problem_0176_SecondHighestSalary_SQL {
	//https://leetcode.com/problems/second-highest-salary/solution/

	/**
	 * SELECT
	 *     IFNULL(
	 *       (SELECT DISTINCT Salary
	 *        FROM Employee
	 *        ORDER BY Salary DESC
	 *         LIMIT 1 OFFSET 1),
	 *     NULL) AS SecondHighestSalary
	 */

}
