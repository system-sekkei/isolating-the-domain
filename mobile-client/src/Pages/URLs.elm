module Pages.URLs exposing (attendancePageURL, dashboardPageURL, payrollGetEndpoint)

import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.TIme.YearMonth as YearMonth exposing (YearMonth)
import Url.Builder exposing (absolute)



-- Page URLs


dashboardPageURL : String
dashboardPageURL =
    absolute [ "dashboard" ] []


attendancePageURL : EmployeeNumber -> YearMonth -> String
attendancePageURL employeeNumber yearMonth =
    absolute [ "attendance", employeeNumber |> EmployeeNumber.toString, yearMonth |> YearMonth.toString ] []



-- API endpoint URLs


apiEndpointPrefix : String
apiEndpointPrefix =
    "api"


payrollGetEndpoint : YearMonth -> String
payrollGetEndpoint yearMonth =
    absolute [ apiEndpointPrefix, "payroll", YearMonth.toString yearMonth ] []
