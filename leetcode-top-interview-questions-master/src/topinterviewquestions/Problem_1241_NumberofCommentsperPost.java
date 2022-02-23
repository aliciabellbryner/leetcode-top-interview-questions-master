package topinterviewquestions;
/*
SQL Schema
Table: Submissions

+---------------+----------+
| Column Name   | Type     |
+---------------+----------+
| sub_id        | int      |
| parent_id     | int      |
+---------------+----------+
There is no primary key for this table, it may have duplicate rows.
Each row can be a post or comment on the post.
parent_id is null for posts.
parent_id for comments is sub_id for another post in the table.


Write an SQL query to find the number of comments per post. The result table should contain post_id and its corresponding number_of_comments.

The Submissions table may contain duplicate comments. You should count the number of unique comments per post.

The Submissions table may contain duplicate posts. You should treat them as one post.

The result table should be ordered by post_id in ascending order.

The query result format is in the following example.



Example 1:

Input:
Submissions table:
+---------+------------+
| sub_id  | parent_id  |
+---------+------------+
| 1       | Null       |
| 2       | Null       |
| 1       | Null       |
| 12      | Null       |
| 3       | 1          |
| 5       | 2          |
| 3       | 1          |
| 4       | 1          |
| 9       | 1          |
| 10      | 2          |
| 6       | 7          |
+---------+------------+
Output:
+---------+--------------------+
| post_id | number_of_comments |
+---------+--------------------+
| 1       | 3                  |
| 2       | 2                  |
| 12      | 0                  |
+---------+--------------------+
Explanation:
The post with id 1 has three comments in the table with id 3, 4, and 9. The comment with id 3 is repeated in the table, we counted it only once.
The post with id 2 has two comments in the table with id 5 and 10.
The post with id 12 has no comments in the table.
The comment with id 6 is a comment on a deleted post with id 7 so we ignored it.
 */
public class Problem_1241_NumberofCommentsperPost {
    /* MySQL two solutions: (subquery v.s. self join)
    //Subquery
SELECT
    DISTINCT sub_id AS post_id,
    (SELECT COUNT(DISTINCT sub_id) FROM Submissions S2 WHERE S1.sub_id = S2.parent_id) AS number_of_comments
FROM
    Submissions AS S1
WHERE parent_id IS NULL
ORDER BY sub_id


//Self-Join
SELECT
    S1.sub_id AS post_id,
    COUNT(DISTINCT S2.sub_id) AS number_of_comments
FROM
    Submissions S1
LEFT JOIN
    Submissions S2
ON
    S1.sub_id = S2.parent_id
WHERE S1.parent_id IS NULL
GROUP BY S1.sub_id
     */



    //MYSQL subquery count solution
    /*
    SELECT s.sub_id AS post_id,
(SELECT COUNT(DISTINCT(s1.sub_id)) FROM Submissions s1 WHERE s1.parent_id = s.sub_id)
AS number_of_comments
FROM Submissions s
WHERE s.parent_id IS null
GROUP BY s.sub_id
;
     */
}
