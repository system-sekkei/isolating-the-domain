module Route exposing (Route(..), parse)

import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.Time.YearMonth exposing (YearMonth(..))
import Types.Timerecord.WorkDate exposing (WorkDate(..))
import Url exposing (Url)
import Url.Parser exposing ((</>), Parser, custom, map, oneOf, s, top)



-- Route


type Route
    = Top
    | DashboardRoute
    | PayrollRoute YearMonth
    | AttendanceRoute EmployeeNumber YearMonth
    | TimeRecordRoute EmployeeNumber WorkDate



-- URL Parser


parse : Url -> Maybe Route
parse url =
    Url.Parser.parse parser url


parser : Parser (Route -> a) a
parser =
    oneOf
        [ map Top top
        , map DashboardRoute (s "dashboard")
        , map PayrollRoute (s "payroll" </> yearMonth)
        , map AttendanceRoute (s "attendance" </> employeeNumber </> yearMonth)
        , map TimeRecordRoute (s "timerecord" </> employeeNumber </> workDate)
        ]


yearMonth : Parser (YearMonth -> a) a
yearMonth =
    custom "YEAR_MONTH" <| \segment -> Just (FormattedYearMonth segment)


workDate : Parser (WorkDate -> a) a
workDate =
    custom "WORK_DATE" <| \segment -> Just (FormattedWorkDate segment)


employeeNumber : Parser (EmployeeNumber -> a) a
employeeNumber =
    custom "EMPLOYEE_NUMBER" <| \segment -> Just (EmployeeNumber.parse segment)
