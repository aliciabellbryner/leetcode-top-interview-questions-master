package topinterviewquestions;
/*
SQL Schema
Table: Cinema

+-------------+------+
| Column Name | Type |
+-------------+------+
| seat_id     | int  |
| free        | bool |
+-------------+------+
seat_id is an auto-increment primary key column for this table.
Each row of this table indicates whether the ith seat is free or not. 1 means free while 0 means occupied.


Write an SQL query to report all the consecutive available seats in the cinema.

Return the result table ordered by seat_id in ascending order.

The test cases are generated so that more than two seats are consecutively available.

The query result format is in the following example.



Example 1:

Input:
Cinema table:
+---------+------+
| seat_id | free |
+---------+------+
| 1       | 1    |
| 2       | 0    |
| 3       | 1    |
| 4       | 1    |
| 5       | 1    |
+---------+------+
Output:
+---------+
| seat_id |
+---------+
| 3       |
| 4       |
| 5       |
+---------+
 */
public class Problem_0603_ConsecutiveAvailableSeats {
    /*
    Approach: Using self join and abs()[Accepted]
Intuition

There is only one table in this problem, so we probably need to use self join for this relative complex problem.

Algorithm

First, let's see what we have after joining this table with itself.

Note: The result of join two tables is the Cartesian product of these two tables.

select a.seat_id, a.free, b.seat_id, b.free
from cinema a join cinema b;
seat_id	free	seat_id	free
1	1	1	1
2	0	1	1
3	1	1	1
4	1	1	1
5	1	1	1
1	1	2	0
2	0	2	0
3	1	2	0
4	1	2	0
5	1	2	0
1	1	3	1
2	0	3	1
3	1	3	1
4	1	3	1
5	1	3	1
1	1	4	1
2	0	4	1
3	1	4	1
4	1	4	1
5	1	4	1
1	1	5	1
2	0	5	1
3	1	5	1
4	1	5	1
5	1	5	1
To find the consecutive available seats, the value in the a.seat_id should be more(or less) than the value b.seat_id, and both of them should be free.

select a.seat_id, a.free, b.seat_id, b.free
from cinema a join cinema b
  on abs(a.seat_id - b.seat_id) = 1
  and a.free = true and b.free = true;
seat_id	free	seat_id	free
4	1	3	1
3	1	4	1
5	1	4	1
4	1	5	1
At last, choose the concerned column seat_id, and display the result ordered by seat_id.

Note: You may notice that the seat with seat_id '4' appears twice in this table. This is because seat '4' next to '3' and also next to '5'. So we need to use distinct to filter the duplicated records.

MySQL

select distinct a.seat_id
from cinema a join cinema b
  on abs(a.seat_id - b.seat_id) = 1
  and a.free = true and b.free = true
order by a.seat_id
;

     */


    //diss
    /*
     select distinct a.seat_id
 from cinema a, cinema b
 where a.free = 1 and b.free = 1 and
 (a.seat_id+1 = b.seat_id or a.seat_id = b.seat_id+1)
 order by a.seat_id asc
     */


}

