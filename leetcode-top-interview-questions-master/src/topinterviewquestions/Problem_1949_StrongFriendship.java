package topinterviewquestions;
/*
SQL Schema
Table: Friendship

+-------------+------+
| Column Name | Type |
+-------------+------+
| user1_id    | int  |
| user2_id    | int  |
+-------------+------+
(user1_id, user2_id) is the primary key for this table.
Each row of this table indicates that the users user1_id and user2_id are friends.
Note that user1_id < user2_id.


A friendship between a pair of friends x and y is strong if x and y have at least three common friends.

Write an SQL query to find all the strong friendships.

Note that the result table should not contain duplicates with user1_id < user2_id.

Return the result table in any order.

The query result format is in the following example.



Example 1:

Input:
Friendship table:
+----------+----------+
| user1_id | user2_id |
+----------+----------+
| 1        | 2        |
| 1        | 3        |
| 2        | 3        |
| 1        | 4        |
| 2        | 4        |
| 1        | 5        |
| 2        | 5        |
| 1        | 7        |
| 3        | 7        |
| 1        | 6        |
| 3        | 6        |
| 2        | 6        |
+----------+----------+
Output:
+----------+----------+---------------+
| user1_id | user2_id | common_friend |
+----------+----------+---------------+
| 1        | 2        | 4             |
| 1        | 3        | 3             |
+----------+----------+---------------+
Explanation:
Users 1 and 2 have 4 common friends (3, 4, 5, and 6).
Users 1 and 3 have 3 common friends (2, 6, and 7).
We did not include the friendship of users 2 and 3 because they only have two common friends (1 and 6).
 */
public class Problem_1949_StrongFriendship {
    /*
    with f as (
select user1_id, user2_id
from Friendship
union
select user2_id user1_id, user1_id user2_id
from Friendship
)

select a.user1_id, a.user2_id, count(c.user2_id) common_friend
from Friendship a
join f b
on a.user1_id = b.user1_id # u1 friends
join f c
on a.user2_id = c.user1_id # u2 friends
and b.user2_id = c.user2_id # u1 u2 comman friends
group by a.user1_id, a.user2_id
having count(c.user2_id) >= 3
     */

    /*
    with cte as(select user1_id,user2_id from friendship
            union
            select user2_id,user1_id from friendship)

select c1.user1_id
      ,c2.user1_id as user2_id
	  ,count(*) as common_friend
from cte as c1
     join
	 cte as c2
on c1.user1_id<c2.user1_id
   and
   c1.user2_id=c2.user2_id
where (c1.user1_id,c2.user1_id) in (select * from friendship)
group by 1,2
having count(*)>=3
     */



}
