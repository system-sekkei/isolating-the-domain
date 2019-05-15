module Route exposing (Route(..), parse)

import Url exposing (Url)
import Url.Parser exposing ((</>), Parser, int, map, oneOf, s, string, top)



-- Route


type Route
    = Top
    | DashboardRoute
    | PayrollRoute YearMonth
    | AttendanceRoute EmployeeNumber YearMonth
    | TimeRecordRoute EmployeeNumber WorkDate



-- Path Variables


type alias EmployeeNumber =
    Int


type alias YearMonth =
    String


type alias WorkDate =
    String



-- URL Parser


parse : Url -> Maybe Route
parse url =
    Url.Parser.parse parser url


parser : Parser (Route -> a) a
parser =
    oneOf
        [ map Top top
        , map DashboardRoute (s "dashboard")
        , map PayrollRoute (s "payroll" </> string)
        , map AttendanceRoute (s "attendance" </> int </> string)
        , map TimeRecordRoute (s "timerecord" </> int </> string)
        ]
