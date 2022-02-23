package topinterviewquestions;
/*
SQL Schema
Table: Actions

+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| user_id       | int     |
| post_id       | int     |
| action_date   | date    |
| action        | enum    |
| extra         | varchar |
+---------------+---------+
There is no primary key for this table, it may have duplicate rows.
The action column is an ENUM type of ('view', 'like', 'reaction', 'comment', 'report', 'share').
The extra column has optional information about the action, such as a reason for the report or a type of reaction.


Write an SQL query that reports the number of posts reported yesterday for each report reason. Assume today is 2019-07-05.

Return the result table in any order.

The query result format is in the following example.



Example 1:

Input:
Actions table:
+---------+---------+-------------+--------+--------+
| user_id | post_id | action_date | action | extra  |
+---------+---------+-------------+--------+--------+
| 1       | 1       | 2019-07-01  | view   | null   |
| 1       | 1       | 2019-07-01  | like   | null   |
| 1       | 1       | 2019-07-01  | share  | null   |
| 2       | 4       | 2019-07-04  | view   | null   |
| 2       | 4       | 2019-07-04  | report | spam   |
| 3       | 4       | 2019-07-04  | view   | null   |
| 3       | 4       | 2019-07-04  | report | spam   |
| 4       | 3       | 2019-07-02  | view   | null   |
| 4       | 3       | 2019-07-02  | report | spam   |
| 5       | 2       | 2019-07-04  | view   | null   |
| 5       | 2       | 2019-07-04  | report | racism |
| 5       | 5       | 2019-07-04  | view   | null   |
| 5       | 5       | 2019-07-04  | report | racism |
+---------+---------+-------------+--------+--------+
Output:
+---------------+--------------+
| report_reason | report_count |
+---------------+--------------+
| spam          | 1            |
| racism        | 2            |
+---------------+--------------+
Explanation: Note that we only care about report reasons with non-zero number of reports.
 */
public class Problem_1113_ReportedPosts {
    //Do not 画蛇添足:
    //
    //No need to filter extra is not null. When action = 'report', extra is guaranteed not null.
    //No need to filter count > 0. If a group exists, it must have at least one row.
    //I'm maintaining a GitHub repository that documents useful SQL techniques and common pitfalls in interview. Please star/fork if you find it helpful: https://github.com/shawlu95/Beyond-LeetCode-SQL
    /*
    select
  extra as report_reason
  ,count(distinct post_id) as report_count
from Actions
where action_date = '2019-07-04'
  and action = 'report'
group by extra
     */
}
