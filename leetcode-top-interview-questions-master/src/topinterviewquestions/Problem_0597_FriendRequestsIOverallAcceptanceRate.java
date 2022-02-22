package topinterviewquestions;
/*
SQL Schema
Table: FriendRequest

+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| sender_id      | int     |
| send_to_id     | int     |
| request_date   | date    |
+----------------+---------+
There is no primary key for this table, it may contain duplicates.
This table contains the ID of the user who sent the request, the ID of the user who received the request, and the date of the request.


Table: RequestAccepted

+----------------+---------+
| Column Name    | Type    |
+----------------+---------+
| requester_id   | int     |
| accepter_id    | int     |
| accept_date    | date    |
+----------------+---------+
There is no primary key for this table, it may contain duplicates.
This table contains the ID of the user who sent the request, the ID of the user who received the request, and the date when the request was accepted.


Write an SQL query to find the overall acceptance rate of requests, which is the number of acceptance divided by the number of requests. Return the answer rounded to 2 decimals places.

Note that:

The accepted requests are not necessarily from the table friend_request. In this case, Count the total accepted requests (no matter whether they are in the original requests), and divide it by the number of requests to get the acceptance rate.
It is possible that a sender sends multiple requests to the same receiver, and a request could be accepted more than once. In this case, the ‘duplicated’ requests or acceptances are only counted once.
If there are no requests at all, you should return 0.00 as the accept_rate.
The query result format is in the following example.



Example 1:

Input:
FriendRequest table:
+-----------+------------+--------------+
| sender_id | send_to_id | request_date |
+-----------+------------+--------------+
| 1         | 2          | 2016/06/01   |
| 1         | 3          | 2016/06/01   |
| 1         | 4          | 2016/06/01   |
| 2         | 3          | 2016/06/02   |
| 3         | 4          | 2016/06/09   |
+-----------+------------+--------------+
RequestAccepted table:
+--------------+-------------+-------------+
| requester_id | accepter_id | accept_date |
+--------------+-------------+-------------+
| 1            | 2           | 2016/06/03  |
| 1            | 3           | 2016/06/08  |
| 2            | 3           | 2016/06/08  |
| 3            | 4           | 2016/06/09  |
| 3            | 4           | 2016/06/10  |
+--------------+-------------+-------------+
Output:
+-------------+
| accept_rate |
+-------------+
| 0.8         |
+-------------+
Explanation:
There are 4 unique accepted requests, and there are 5 requests in total. So the rate is 0.80.


Follow up:

Could you write a query to return the acceptance rate for every month?
Could you write a query to return the cumulative acceptance rate for every day?
 */
public class Problem_0597_FriendRequestsIOverallAcceptanceRate {

    /*
    Solution
Approach: Using ROUND and IFNULL [Accepted]
Intuition

Count the accepted requests and then divides it by the number of all requests.

Algorithm

To get the distinct number of accepted requests, we can query from the RequestAccepted table.

SELECT COUNT(*) FROM (SELECT DISTINCT requester_id, accepter_id FROM RequestAccepted) AS A;
With the same technique, we can have the total number of requests from the FriendRequest table:

SELECT COUNT(*) FROM (SELECT DISTINCT sender_id, send_to_id FROM FriendRequest) AS B;
At last, divide these two numbers and ROUND it to a scale of 2 decimal places to get the required acceptance rate.

Wait! The divisor (total number of requests) could be '0' if the table friend_request is empty. So, we have to utilize IFNULL to deal with this special case.

MySQL

SELECT
ROUND(
    IFNULL(
    (SELECT COUNT(*) FROM (SELECT DISTINCT requester_id, accepter_id FROM RequestAccepted) AS A)
    /
    (SELECT COUNT(*) FROM (SELECT DISTINCT sender_id, send_to_id FROM FriendRequest) AS B),
    0)
, 2) AS accept_rate;
     */

    //discussion
    /*
    This post is a summary of the solutions to all these 3 questions.

First, the original question:
This is simple since we just need to count unique request number and divide with each other.

select ifnull(round((count(distinct requester_id,accepter_id)/count(distinct sender_id,send_to_id)),2),0.00) as accept_rate
from friend_request, request_accepted
Second, follow-up1:Can you write a query to return the accept rate but for every month?
This solution comes from this post:
https://leetcode.com/problems/friend-requests-i-overall-acceptance-rate/discuss/103579/Following-up-questions.-Solved-Q1-how-to-solve-Q2

##  create a subquery which contains count, month, join each other with month and group by month so we can get the finally result.
select if(d.req =0, 0.00, round(c.acp/d.req,2)) as accept_rate, c.month from
(select count(distinct requester_id, accepter_id) as acp, Month(accept_date) as month from request_accepted) c,
(select count(distinct sender_id, send_to_id) as req, Month(request_date) as month from friend_request) d
where c.month = d.month
group by c.month
Third, follow-up2:How about the cumulative accept rate for every day?
This solution comes from this post:
https://leetcode.com/problems/friend-requests-i-overall-acceptance-rate/discuss/103583/one-possible-answer-for-question-2-cumulative-sums-by-day.

## sum up the case when ind is 'a', which means it belongs to accept table, divided by sum of ind is 'r', which means it belong to request table
select s.date1, ifnull(round(sum(case when t.ind = 'a' then t.cnt else 0 end)/sum(case when t.ind = 'r' then t.cnt else 0 end),2),0)
from
## get a table of all unique dates
(select distinct x.request_date as date1 from friend_request x
## The reason here use union sicne we don't want duplicate date
union
 select distinct y.accept_date as date1 from request_accepted y
) s
## left join to make sure all dates are in the final output
left join
## get a table of all dates, count of each days, ind to indicate which table it comes from
(select v.request_date as date1, count(*) as cnt,'r' as ind from friend_request v group by v.request_date
## The reason here use union all sicne union all will be faster
union all
select w.accept_date as date1, count(*) as cnt,'a' as ind from request_accepted w group by w.accept_date) t
## s.date1 >= t.date1, which for each reacord in s, it will join with all records earlier than it in t
on s.date1 >= t.date1
# group by s.date1 then we can get a cumulative result to that day
group by s.date1
order by s.date1
;
     */

}
