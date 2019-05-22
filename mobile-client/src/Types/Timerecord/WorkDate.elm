module Types.Timerecord.WorkDate exposing (WorkDate(..), decoder, from, toDayOfMonth, toDayOfWeek, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Time exposing (Posix, Zone, utc)
import Time.Extra exposing (Parts, partsToPosix)
import Types.Time.ClientTime as ClientTime exposing (ClientTime)



-- public


type WorkDate
    = EmptyWordDate
    | PosixWorkDate Zone Posix
    | FormattedWorkDate String


from : ClientTime -> WorkDate
from clientTime =
    ClientTime.toString clientTime
        |> String.slice 0 10
        |> FormattedWorkDate


toDayOfMonth : WorkDate -> String
toDayOfMonth workDate =
    case workDate of
        FormattedWorkDate _ ->
            let
                ( zone, posix ) =
                    toPosix workDate
            in
            PosixWorkDate zone posix
                |> toDayOfMonth

        PosixWorkDate zone posix ->
            Time.toDay zone posix
                |> String.fromInt

        EmptyWordDate ->
            ""


toDayOfWeek : WorkDate -> String
toDayOfWeek workDate =
    case workDate of
        FormattedWorkDate _ ->
            let
                ( zone, posix ) =
                    toPosix workDate
            in
            PosixWorkDate zone posix
                |> toDayOfWeek

        PosixWorkDate zone posix ->
            case Time.toWeekday zone posix of
                Time.Mon ->
                    "月"

                Time.Tue ->
                    "火"

                Time.Wed ->
                    "水"

                Time.Thu ->
                    "木"

                Time.Fri ->
                    "金"

                Time.Sat ->
                    "土"

                Time.Sun ->
                    "日"

        EmptyWordDate ->
            ""


decoder : Decoder WorkDate
decoder =
    string |> andThen (\str -> succeed (FormattedWorkDate str))


toString : WorkDate -> String
toString workDate =
    case workDate of
        FormattedWorkDate value ->
            value

        PosixWorkDate zone posix ->
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

                dd =
                    Time.toDay zone posix |> String.fromInt
            in
            yyyy ++ "-" ++ mm ++ "-" ++ dd

        EmptyWordDate ->
            ""



-- private


toPosix : WorkDate -> ( Zone, Posix )
toPosix workDate =
    case workDate of
        FormattedWorkDate value ->
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

                date =
                    String.slice 8 10 value
                        |> String.toInt
                        |> Maybe.withDefault 1
            in
            ( utc, partsToPosix utc (Parts year month date 0 0 0 0) )

        PosixWorkDate zone posix ->
            ( zone, posix )

        EmptyWordDate ->
            ( utc, partsToPosix utc (Parts 1970 Time.Jan 1 0 0 0 0) )
