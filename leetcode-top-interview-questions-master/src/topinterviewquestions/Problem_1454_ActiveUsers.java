package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//https://leetcode.com/problems/active-users/discuss/644808/(_)-Mysql-Solutions%3A-3-easy-ways-%2B-1-RECURSIVE-CTE-way-to-solve-N
public class Problem_1454_ActiveUsers {
 /*
 Related Questions:
1321. Restaurant Growth
601. Human Traffic of Stadium

Choice 1(I like this one):

SELECT DISTINCT l1.id,
(SELECT name FROM Accounts WHERE id = l1.id) AS name
FROM Logins l1
JOIN Logins l2 ON l1.id = l2.id AND DATEDIFF(l2.login_date, l1.login_date) BETWEEN 1 AND 4
GROUP BY l1.id, l1.login_date
HAVING COUNT(DISTINCT l2.login_date) = 4

Choice 2:

SELECT *
FROM Accounts
WHERE id IN
    (SELECT DISTINCT t1.id
     FROM Logins t1 INNER JOIN Logins t2 on t1.id = t2.id AND DATEDIFF(t1.login_date, t2.login_date) BETWEEN 1 AND 4
     GROUP BY t1.id, t1.login_date
     HAVING COUNT(DISTINCT(t2.login_date)) = 4)
ORDER BY id
Choice 3:

WITH temp AS (SELECT l1.id, l1.login_date, COUNT(DISTINCT l2.login_date) AS cnt
FROM Logins l1
LEFT JOIN Logins l2 ON l1.id = l2.id AND DATEDIFF(l2.login_date, l1.login_date) BETWEEN 1 AND 4
GROUP BY 1,2
HAVING cnt>=4)

SELECT DISTINCT temp.id, name
FROM temp
JOIN Accounts ON temp.id = Accounts.id
ORDER BY 1;
Recursive CTE solution to solve the follow up question(just for test):

WITH RECURSIVE
rec_t AS
(SELECT id, login_date, 1 AS days FROM Logins
 UNION ALL
 SELECT l.id, l.login_date, rec_t.days+1 FROM rec_t
 INNER JOIN Logins l
 ON rec_t.id = l.id AND DATE_ADD(rec_t.login_date, INTERVAL 1 DAY) = l.login_date
)

SELECT * FROM Accounts
WHERE id IN
(SELECT DISTINCT id FROM rec_t WHERE days = 5)
ORDER BY id
Explanation for RECURSIVE CTE solution:

First, you need a recursive cte query to get the data as the following:

-TEST CODE:

WITH RECURSIVE
rec_t AS
(SELECT id, login_date, 1 AS days FROM Logins
 UNION ALL
 SELECT l.id, l.login_date, rec_t.days+1 FROM rec_t
 INNER JOIN Logins l
 ON rec_t.id = l.id AND DATE_ADD(rec_t.login_date, INTERVAL 1 DAY) = l.login_date
)

SELECT * FROM rec_t
  */
}
