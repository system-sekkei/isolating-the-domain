module Types.Time.ClientTime exposing (ClientTime(..), nextDate, nextMonth, previousDate, previousMonth, toString, updateTime)

import Time exposing (Posix, Zone, toDay, toHour, toMinute, toMonth, toSecond, toYear)
import Time.Extra exposing (Interval(..))


type ClientTime
    = ClientTime Zone Posix


toString : ClientTime -> String
toString clientDate =
    case clientDate of
        ClientTime zone posix ->
            iso8601 zone posix


iso8601 : Zone -> Posix -> String
iso8601 zone posix =
    let
        year =
            toYear zone posix |> String.fromInt

        month =
            case toMonth zone posix of
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

        day =
            toDay zone posix |> String.fromInt |> String.padLeft 2 '0'

        hour =
            toHour zone posix |> String.fromInt |> String.padLeft 2 '0'

        minutes =
            toMinute zone posix |> String.fromInt |> String.padLeft 2 '0'

        seconds =
            toSecond zone posix |> String.fromInt |> String.padLeft 2 '0'
    in
    year ++ "-" ++ month ++ "-" ++ day ++ "T" ++ hour ++ ":" ++ minutes ++ ":" ++ seconds


updateTime : ClientTime -> Posix -> ClientTime
updateTime original posix =
    case original of
        ClientTime zone _ ->
            ClientTime zone posix


nextDate : ClientTime -> ClientTime
nextDate original =
    case original of
        ClientTime zone posix ->
            ClientTime zone
                (Time.Extra.add Day 1 zone posix)


previousDate : ClientTime -> ClientTime
previousDate original =
    case original of
        ClientTime zone posix ->
            ClientTime zone
                (Time.Extra.add Day -1 zone posix)


nextMonth : ClientTime -> ClientTime
nextMonth original =
    case original of
        ClientTime zone posix ->
            ClientTime zone
                (Time.Extra.add Month 1 zone posix)


previousMonth : ClientTime -> ClientTime
previousMonth original =
    case original of
        ClientTime zone posix ->
            ClientTime zone
                (Time.Extra.add Month -1 zone posix)
