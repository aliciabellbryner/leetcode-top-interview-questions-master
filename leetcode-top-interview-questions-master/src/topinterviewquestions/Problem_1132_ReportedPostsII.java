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


Table: Removals

+---------------+---------+
| Column Name   | Type    |
+---------------+---------+
| post_id       | int     |
| remove_date   | date    |
+---------------+---------+
post_id is the primary key of this table.
Each row in this table indicates that some post was removed due to being reported or as a result of an admin review.


Write an SQL query to find the average daily percentage of posts that got removed after being reported as spam, rounded to 2 decimal places.

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
| 2       | 2       | 2019-07-04  | view   | null   |
| 2       | 2       | 2019-07-04  | report | spam   |
| 3       | 4       | 2019-07-04  | view   | null   |
| 3       | 4       | 2019-07-04  | report | spam   |
| 4       | 3       | 2019-07-02  | view   | null   |
| 4       | 3       | 2019-07-02  | report | spam   |
| 5       | 2       | 2019-07-03  | view   | null   |
| 5       | 2       | 2019-07-03  | report | racism |
| 5       | 5       | 2019-07-03  | view   | null   |
| 5       | 5       | 2019-07-03  | report | racism |
+---------+---------+-------------+--------+--------+
Removals table:
+---------+-------------+
| post_id | remove_date |
+---------+-------------+
| 2       | 2019-07-20  |
| 3       | 2019-07-18  |
+---------+-------------+
Output:
+-----------------------+
| average_daily_percent |
+-----------------------+
| 75.00                 |
+-----------------------+
Explanation:
The percentage for 2019-07-04 is 50% because only one post of two spam reported posts were removed.
The percentage for 2019-07-02 is 100% because one post was reported as spam and it was removed.
The other days had no spam reports so the average is (50 + 100) / 2 = 75%
Note that the output is only one number and that we do not care about the remove dates.
 */
public class Problem_1132_ReportedPostsII {
    /*
    SOLUTION 1

Step 1: Left join the Actions table with the Removals table to filter out the rows that have been removed after being marked as spam.

SELECT a.post_id, a.action_date, r.remove_date
from Actions a left join Removals r
on a.post_id = r.post_id
WHERE a.extra='spam'
The result will be like this:
+-------------------------------------+
| post_id | action_date | remove_date |
| 2 | 2019-07-04 | 2019-07-20 |
| 3 | 2019-07-02 | 2019-07-18 |
| 4 | 2019-07-04 | null |
+-------------------------------------+
On 2019-07-04, there were two posts and one of them was removed, so the removal rate was 50%. For 2019-07-02, the only post on that date was removed so the removal rate on that date was 100%.

Step 2:
Use CASE WHEN function to calculate the daily average of the removal rate.

The result will be like:
+--------------------+
| date | daily_average |
| 2019-07-02 | 1.0 |
| 2019-07-04 | 0.5 |
+--------------------+

Step 3: Calculate the general average number based on the result calculated before.

Final query:

select ROUND(sum(daily_avg)/count(date)*100,2) as average_daily_percent FROM
(select
    t.action_date as date,
    (count(distinct case when remove_date is not null then post_id else null end)/count(distinct post_id)) as daily_avg
FROM
(SELECT a.post_id, a.action_date, r.remove_date
from Actions a left join Removals r
on a.post_id = r.post_id
WHERE a.extra='spam') t
GROUP BY t.action_date) t2;
SOLUTION 2
The second solution is pretty straightforward.
First calculate the daily percentage by joining the Actions table and the Removels table after we filtered out the posts that have been reported as 'spam'.
And then we can calculate the general average based on the daily average we calculated from the subquery.

select round(sum(percent)/count(distinct action_date),2) as average_daily_percent
from
    (select a.action_date,
    count(distinct r.post_id)/count(distinct a.post_id)*100 as percent
    from actions a left join removals r
    on a.post_id = r.post_id
    where a.extra='spam'
    group by 1) temp;
mysql
self-join
subquery
     */

    /*
    SELECT ROUND(AVG(cnt), 2) AS average_daily_percent FROM
(
    SELECT (COUNT(DISTINCT r.post_id)/ COUNT(DISTINCT a.post_id))*100  AS cnt
FROM Actions a
LEFT JOIN Removals r
ON a.post_id = r.post_id
WHERE extra='spam' and action = 'report'
GROUP BY action_date)tmp
     */
}
