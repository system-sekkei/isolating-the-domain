module URLs exposing
    ( attendanceGetEndpoint
    , attendancePageURL
    , dashboardPageURL
    , payrollGetEndpoint
    , payrollPageURL
    , timeRecordPageURL
    , timeRecordPostEndpoint
    , timeRecordPreparedFormGetEndpoint
    )

import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.Time.WorkDate as WorkDate exposing (WorkDate)
import Types.Time.YearMonth as YearMonth exposing (YearMonth)
import Url.Builder exposing (absolute)



-- Page URLs


dashboardPageURL : String
dashboardPageURL =
    absolute [ "dashboard" ] []


payrollPageURL : YearMonth -> String
payrollPageURL yearMonth =
    absolute [ "payroll", yearMonth |> YearMonth.toString ] []


attendancePageURL : EmployeeNumber -> YearMonth -> String
attendancePageURL employeeNumber yearMonth =
    absolute [ "attendance", employeeNumber |> EmployeeNumber.toString, yearMonth |> YearMonth.toString ] []


timeRecordPageURL : EmployeeNumber -> WorkDate -> String
timeRecordPageURL employeeNumber workDate =
    absolute [ "timerecord", employeeNumber |> EmployeeNumber.toString, workDate |> WorkDate.toString ] []



-- API endpoint URLs


apiEndpointPrefix : String
apiEndpointPrefix =
    "api"


payrollGetEndpoint : YearMonth -> String
payrollGetEndpoint yearMonth =
    absolute [ apiEndpointPrefix, "payroll", YearMonth.toString yearMonth ] []


attendanceGetEndpoint : EmployeeNumber -> YearMonth -> String
attendanceGetEndpoint employeeNumber yearMonth =
    absolute [ apiEndpointPrefix, "attendances", EmployeeNumber.toString employeeNumber, YearMonth.toString yearMonth ] []


timeRecordPreparedFormGetEndpoint : EmployeeNumber -> WorkDate -> String
timeRecordPreparedFormGetEndpoint employeeNumber workDate =
    absolute [ apiEndpointPrefix, "timerecord", "prepare", EmployeeNumber.toString employeeNumber, WorkDate.toString workDate ] []


timeRecordPostEndpoint : String
timeRecordPostEndpoint =
    absolute [ apiEndpointPrefix, "timerecord" ] []
