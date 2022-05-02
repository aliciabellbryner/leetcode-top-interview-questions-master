package topinterviewquestions;

public class Problem_1270_AllPeopleReporttotheGivenManager_G {
    //https://leetcode.ca/2019-05-23-1270-All-People-Report-to-the-Given-Manager/
    /*
    -- solution-1
select a.employee_id as EMPLOYEE_ID
from
    Employees as a
    left join
    Employees as b on a.manager_id = b.employee_id
    left join
    Employees as c on b.manager_id = c.employee_id
    left join
    Employees as d on c.manager_id = d.employee_id
where
    a.employee_id != 1
    and
    d.employee_id = 1;

-- solution-2
select employee_id as EMPLOYEE_ID from Employees where manager_id in
(select employee_id from Employees WHERE manager_id in
(select employee_id from Employees where manager_id =1))
and employee_id !=1
     */
}
