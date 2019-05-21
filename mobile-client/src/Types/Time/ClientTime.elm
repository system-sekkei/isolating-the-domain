module Types.Time.ClientTime exposing (ClientTime(..), toString)

import Time exposing (Posix, Zone, toDay, toHour, toMinute, toMonth, toSecond, toYear)


type ClientTime
    = ClientTime Zone Posix


toString : ClientTime -> String
toString clientDate =
    case clientDate of
        ClientTime zone posix ->
            iso8601Format zone posix


iso8601Format : Zone -> Posix -> String
iso8601Format zone posix =
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
