module URLs exposing
    ( attendanceGetEndpoint
    , attendancePageURL
    , dashboardPageURL
    , payrollGetEndpoint
    , payrollPageURL
    , timerecordPageURL
    )

import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.Time.YearMonth as YearMonth exposing (YearMonth)
import Types.Timerecord.WorkDate as WorkDate exposing (WorkDate)
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


timerecordPageURL : EmployeeNumber -> WorkDate -> String
timerecordPageURL employeeNumber workDate =
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
