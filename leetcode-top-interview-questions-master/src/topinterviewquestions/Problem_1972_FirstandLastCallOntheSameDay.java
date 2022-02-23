package topinterviewquestions;
/*
SQL Schema
Table: Calls

+--------------+----------+
| Column Name  | Type     |
+--------------+----------+
| caller_id    | int      |
| recipient_id | int      |
| call_time    | datetime |
+--------------+----------+
(caller_id, recipient_id, call_time) is the primary key for this table.
Each row contains information about the time of a phone call between caller_id and recipient_id.


Write an SQL query to report the IDs of the users whose first and last calls on any day were with the same person. Calls are counted regardless of being the caller or the recipient.

Return the result table in any order.

The query result format is in the following example.



Example 1:

Input:
Calls table:
+-----------+--------------+---------------------+
| caller_id | recipient_id | call_time           |
+-----------+--------------+---------------------+
| 8         | 4            | 2021-08-24 17:46:07 |
| 4         | 8            | 2021-08-24 19:57:13 |
| 5         | 1            | 2021-08-11 05:28:44 |
| 8         | 3            | 2021-08-17 04:04:15 |
| 11        | 3            | 2021-08-17 13:07:00 |
| 8         | 11           | 2021-08-17 22:22:22 |
+-----------+--------------+---------------------+
Output:
+---------+
| user_id |
+---------+
| 1       |
| 4       |
| 5       |
| 8       |
+---------+
Explanation:
On 2021-08-24, the first and last call of this day for user 8 was with user 4. User 8 should be included in the answer.
Similarly, user 4 on 2021-08-24 had their first and last call with user 8. User 4 should be included in the answer.
On 2021-08-11, user 1 and 5 had a call. This call was the only call for both of them on this day. Since this call is the first and last call of the day for both of them, they should both be included in the answer.
 */
public class Problem_1972_FirstandLastCallOntheSameDay {
    //UNION + RANK() + HAVING()
    /*
    WITH CTE AS (
                SELECT caller_id AS user_id, call_time, recipient_id FROM Calls
                UNION
                SELECT recipient_id AS user_id, call_time, caller_id AS recipient_id FROM Calls
            ),

CTE1 AS (
        SELECT
        user_id,
        recipient_id,
        DATE(call_time) AS DAY,
        DENSE_RANK() OVER(PARTITION BY user_id, DATE(call_time) ORDER BY call_time ASC) AS RN,
        DENSE_RANK() OVER(PARTITION BY user_id, DATE(call_time) ORDER BY call_time DESC) AS RK
        FROM CTE
        )

SELECT DISTINCT user_id
FROM CTE1
WHERE RN = 1 OR RK = 1
GROUP BY user_id, DAY
     */


    //Note: FIRST_VALUE(DESC) & Why LAST_VALUE() might not work for you...
    /*
    I see many solutions using FIRST_VALUE() in combination with DESC for accessing the last call. Probably because LAST_VALUE() did not work as expected.

The reason why LAST_VALUE() might not have worked, is because the last value in the current window is the current value (and not the last value in the partition). This window is implictly set by the ORDER BY clause - it sets the window to ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW).
To use LAST_VALUE() you need to extent the window to the full partition by using ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING.

with calls_augmented as (
    select caller_id as user_id_1, recipient_id as user_id_2, call_time from Calls
    union
    select recipient_id, caller_id, call_time from Calls
),
first_last_call as (
    select
        user_id_1 as user_id,
        first_value(user_id_2) over (partition by user_id_1, date(call_time) order by call_time) as first_call_id,
        last_value(user_id_2)  over (partition by user_id_1, date(call_time) order by call_time rows between unbounded preceding and unbounded following) as last_call_id
    from calls_augmented
)
select distinct user_id
from first_last_call
where first_call_id = last_call_id
order by user_id
     */




    /*
    Question
1972. First and Last Call On the Same Day
Hard

SQL Schema
Table: Calls

+--------------+----------+
| Column Name  | Type     |
+--------------+----------+
| caller_id    | int      |
| recipient_id | int      |
| call_time    | datetime |
+--------------+----------+
(caller_id, recipient_id, call_time) is the primary key for this table.
Each row contains information about the time of a phone call between caller_id and recipient_id.


Write an SQL query to report the IDs of the users whose first and last calls on any day were with the same person. Calls are counted regardless of being the caller or the recipient.

Return the result table in any order.

The query result format is in the following example.



Example 1:

Input:
Calls table:
+-----------+--------------+---------------------+
| caller_id | recipient_id | call_time           |
+-----------+--------------+---------------------+
| 8         | 4            | 2021-08-24 17:46:07 |
| 4         | 8            | 2021-08-24 19:57:13 |
| 5         | 1            | 2021-08-11 05:28:44 |
| 8         | 3            | 2021-08-17 04:04:15 |
| 11        | 3            | 2021-08-17 13:07:00 |
| 8         | 11           | 2021-08-17 22:22:22 |
+-----------+--------------+---------------------+
Output:
+---------+
| user_id |
+---------+
| 1       |
| 4       |
| 5       |
| 8       |
+---------+
Explanation:
On 2021-08-24, the first and last call of this day for user 8 was with user 4. User 8 should be included in the answer.
Similarly, user 4 on 2021-08-24 had their first and last call with user 8. User 4 should be included in the answer.
On 2021-08-11, user 1 and 5 had a call. This call was the only call for both of them on this day. Since this call is the first and last call of the day for both of them, they should both be included in the answer.
Answer
Approach 1


    with a as (SELECT caller_id id1, recipient_id id2, call_time FROM Calls
            UNION ALL
            SELECT recipient_id id1, caller_id id2, call_time FROM Calls
    )
,first_last_call as (select id1, max(call_time) max_call, min(call_time) min_call from a
    GROUP BY id1, format(call_time,'yyyy-MM-dd')
                    )

    select distinct a.id1 as user_id
    from first_last_call c
    join a on a.id1 = c.id1 and a.call_time in (max_call,min_call)
    group by a.id1, format(call_time,'yyyy-MM-dd')
    having max(id2) = min(id2)
    Approach 2
    with a as (SELECT caller_id id1, recipient_id id2, call_time FROM Calls
            UNION ALL
            SELECT recipient_id id1, caller_id id2, call_time FROM Calls
    )
,first_last_call as (select id1,
                     format(call_time,'yyyy-MM-dd') day,
    id2,
    rank() over (partition by id1,format(call_time,'yyyy-MM-dd')  order by call_time) max_call,
    rank() over (partition by id1,format(call_time,'yyyy-MM-dd') order by call_time desc) min_call
    from a
                    )

    select distinct id1 as user_id
    from first_last_call c
    where max_call = 1 or min_call = 1
    group by id1, day
    having count(distinct id2) = 1
     */

}
