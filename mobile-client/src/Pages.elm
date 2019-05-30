module Pages exposing (Page(..), Route(..), urlParse)

import Pages.Attendance
import Pages.Dashboard
import Pages.Payroll
import Pages.TimeRecord
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.Time.WorkDate as WorkDate exposing (WorkDate(..))
import Types.Time.YearMonth exposing (YearMonth(..))
import Url exposing (Url)
import Url.Parser exposing ((</>), Parser, custom, map, oneOf, s, top)



-- Page


type Page
    = NotFoundPage
    | InitializingPage
    | DashboardPage Pages.Dashboard.Model
    | PayrollPage Pages.Payroll.Model
    | AttendancePage Pages.Attendance.Model
    | TimeRecordPage Pages.TimeRecord.Model



-- Route


type Route
    = Top
    | DashboardRoute
    | PayrollRoute YearMonth
    | AttendanceRoute EmployeeNumber YearMonth
    | TimeRecordRoute EmployeeNumber WorkDate



-- URL Parser


urlParse : Url -> Maybe Route
urlParse url =
    Url.Parser.parse urlParser url


urlParser : Parser (Route -> a) a
urlParser =
    oneOf
        [ map Top top
        , map DashboardRoute (s "dashboard")
        , map PayrollRoute (s "payroll" </> yearMonthParser)
        , map AttendanceRoute (s "attendance" </> employeeNumberParser </> yearMonthParser)
        , map TimeRecordRoute (s "timerecord" </> employeeNumberParser </> workDateParser)
        ]


yearMonthParser : Parser (YearMonth -> a) a
yearMonthParser =
    custom "YEAR_MONTH" <| \segment -> Just (FormattedYearMonth segment)


workDateParser : Parser (WorkDate -> a) a
workDateParser =
    custom "WORK_DATE" <| \segment -> Just (FormattedWorkDate segment)


employeeNumberParser : Parser (EmployeeNumber -> a) a
employeeNumberParser =
    custom "EMPLOYEE_NUMBER" <| \segment -> Just (EmployeeNumber.parse segment)
