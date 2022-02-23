package topinterviewquestions;
/*
SQL Schema
Table: SurveyLog

+-------------+------+
| Column Name | Type |
+-------------+------+
| id          | int  |
| action      | ENUM |
| question_id | int  |
| answer_id   | int  |
| q_num       | int  |
| timestamp   | int  |
+-------------+------+
There is no primary key for this table. It may contain duplicates.
action is an ENUM of the type: "show", "answer", or "skip".
Each row of this table indicates the user with ID = id has taken an action with the question question_id at time timestamp.
If the action taken by the user is "answer", answer_id will contain the id of that answer, otherwise, it will be null.
q_num is the numeral order of the question in the current session.


The answer rate for a question is the number of times a user answered the question by the number of times a user showed the question.

Write an SQL query to report the question that has the highest answer rate. If multiple questions have the same maximum answer rate, report the question with the smallest question_id.

The query result format is in the following example.



Example 1:

Input:
SurveyLog table:
+----+--------+-------------+-----------+-------+-----------+
| id | action | question_id | answer_id | q_num | timestamp |
+----+--------+-------------+-----------+-------+-----------+
| 5  | show   | 285         | null      | 1     | 123       |
| 5  | answer | 285         | 124124    | 1     | 124       |
| 5  | show   | 369         | null      | 2     | 125       |
| 5  | skip   | 369         | null      | 2     | 126       |
+----+--------+-------------+-----------+-------+-----------+
Output:
+------------+
| survey_log |
+------------+
| 285        |
+------------+
Explanation:
Question 285 was showed 1 time and answered 1 time. The answer rate of question 285 is 1.0
Question 369 was showed 1 time and was not answered. The answer rate of question 369 is 0.0
Question 285 has the highest answer rate.
 */
public class Problem_0578_GetHighestAnswerRateQuestion {
    //diss
    /*
    SELECT question_id as survey_log
FROM
(
	SELECT question_id, SUM(case when action="show" THEN 1 ELSE 0 END) as num_show,    SUM(case when action="answer" THEN 1 ELSE 0 END) as num_answer
	FROM survey_log
	GROUP BY question_id
) as tbl
ORDER BY (num_answer / num_show) DESC LIMIT 1
     */

    /*
    My idea (intuitively but not efficiently):

SELECT question_id AS survey_log
FROM survey_log
GROUP BY question_id
HAVING SUM(IF(action='answer',1,0))/SUM(IF(action='show',1,0)) = (SELECT SUM(IF(action='answer',1,0))/SUM(IF(action='show',1,0)) AS answer_rate
                                                                     FROM survey_log
                                                                     GROUP BY question_id
                                                                     ORDER BY answer_rate DESC
                                                                     LIMIT 1);
     */
}
