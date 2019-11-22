module Types.Time.YearMonth exposing (YearMonth(..), from, next, previous, toString)

import Time exposing (Posix, Zone, utc)
import Time.Extra exposing (Interval(..), Parts, add, partsToPosix)
import Types.Time.ClientTime as ClientTime exposing (ClientTime)



-- public


type YearMonth
    = EmptyYearMonth
    | PosixYearMonth Zone Posix
    | FormattedYearMonth String


from : ClientTime -> YearMonth
from clientTime =
    ClientTime.toString clientTime
        |> String.slice 0 7
        |> FormattedYearMonth


next : YearMonth -> YearMonth
next original =
    let
        ( zone, posix ) =
            toPosix original
    in
    add Month 1 zone posix
        |> PosixYearMonth zone


previous : YearMonth -> YearMonth
previous original =
    let
        ( zone, posix ) =
            toPosix original
    in
    add Month -1 zone posix
        |> PosixYearMonth zone


toString : YearMonth -> String
toString yearMonth =
    case yearMonth of
        FormattedYearMonth value ->
            value

        PosixYearMonth zone posix ->
            let
                yyyy =
                    Time.toYear zone posix |> String.fromInt

                mm =
                    case Time.toMonth zone posix of
                        Time.Jan ->
                            "01"

                        Time.Feb ->
                            "02"

                        Time.Mar ->
                            "03"

                        Time.Apr ->
                            "04"

                        Time.May ->
                            "05"

                        Time.Jun ->
                            "06"

                        Time.Jul ->
                            "07"

                        Time.Aug ->
                            "08"

                        Time.Sep ->
                            "09"

                        Time.Oct ->
                            "10"

                        Time.Nov ->
                            "11"

                        Time.Dec ->
                            "12"
            in
            yyyy ++ "-" ++ mm

        EmptyYearMonth ->
            ""



-- private


toPosix : YearMonth -> ( Zone, Posix )
toPosix yearMonth =
    case yearMonth of
        FormattedYearMonth value ->
            let
                year =
                    String.slice 0 4 value
                        |> String.toInt
                        |> Maybe.withDefault 1970

                month =
                    case String.slice 5 7 value of
                        "01" ->
                            Time.Jan

                        "02" ->
                            Time.Feb

                        "03" ->
                            Time.Mar

                        "04" ->
                            Time.Apr

                        "05" ->
                            Time.May

                        "06" ->
                            Time.Jun

                        "07" ->
                            Time.Jul

                        "08" ->
                            Time.Aug

                        "09" ->
                            Time.Sep

                        "10" ->
                            Time.Oct

                        "11" ->
                            Time.Nov

                        "12" ->
                            Time.Dec

                        _ ->
                            Time.Jan
            in
            ( utc, partsToPosix utc (Parts year month 1 0 0 0 0) )

        PosixYearMonth zone posix ->
            ( zone, posix )

        EmptyYearMonth ->
            ( utc, partsToPosix utc (Parts 1970 Time.Jan 1 0 0 0 0) )
