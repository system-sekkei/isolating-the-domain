module Types.Time.YearMonth exposing (YearMonth(..), from, toString)

import Types.Time.ClientTime as ClientTime exposing (ClientTime)


type YearMonth
    = EmptyYearMonth
    | YearMonth String


from : ClientTime -> YearMonth
from clientTime =
    ClientTime.toString clientTime
        |> String.slice 0 7
        |> YearMonth


toString : YearMonth -> String
toString yearMonth =
    case yearMonth of
        YearMonth value ->
            value

        EmptyYearMonth ->
            ""
