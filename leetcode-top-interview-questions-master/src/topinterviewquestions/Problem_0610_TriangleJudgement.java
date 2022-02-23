package topinterviewquestions;
/*
SQL Schema
Table: Triangle

+-------------+------+
| Column Name | Type |
+-------------+------+
| x           | int  |
| y           | int  |
| z           | int  |
+-------------+------+
(x, y, z) is the primary key column for this table.
Each row of this table contains the lengths of three line segments.


Write an SQL query to report for every three line segments whether they can form a triangle.

Return the result table in any order.

The query result format is in the following example.



Example 1:

Input:
Triangle table:
+----+----+----+
| x  | y  | z  |
+----+----+----+
| 13 | 15 | 30 |
| 10 | 20 | 15 |
+----+----+----+
Output:
+----+----+----+----------+
| x  | y  | z  | triangle |
+----+----+----+----------+
| 13 | 15 | 30 | No       |
| 10 | 20 | 15 | Yes      |
 */
public class Problem_0610_TriangleJudgement {

    /*
    Solution
Approach: Using case...when... [Accepted]
Algorithm

In Math, three segments can form a triangle only if the sum of any of the two segments is larger than the third one. (In other words, the subtraction of any of the two segments are smaller than the third one.)

So, we can use this knowledge to judge with the help of the MySQL control statements case...when....

MySQL

SELECT
    x,
    y,
    z,
    CASE
        WHEN x + y > z AND x + z > y AND y + z > x THEN 'Yes'
        ELSE 'No'
    END AS 'triangle'
FROM
    triangle
;
     */


    //diss
    //Simple Answer Using IF() Function
    //SELECT *, IF(x+y>z and x+z>y and y+z>x, 'Yes', 'No') as triangle FROM triangle



    /*
    SELECT x, y, z,
CASE WHEN  x + y > z AND
                    x + z > y AND
                    y + z > x AND
                    x - y < z AND
                    x - z < y AND
                    y - z < x
          THEN 'Yes'
          ELSE 'No'
END AS triangle
FROM triangle;
     */
}
