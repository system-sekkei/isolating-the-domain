module Types.Time.WorkDate exposing (WorkDate(..), decoder, encode, errorMessage, from, isSaturday, isSunday, isValid, toDayOfMonth, toDayOfWeek, toDayText, toString, toYearMonth, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Json.Encode
import Time exposing (Posix, Zone, utc)
import Time.Extra exposing (Parts, partsToPosix)
import Types.Message as Message exposing (Message(..))
import Types.Time.ClientTime as ClientTime exposing (ClientTime)
import Types.Time.YearMonth exposing (YearMonth(..))



-- public


type WorkDate
    = EmptyWordDate
    | PosixWorkDate Zone Posix
    | FormattedWorkDate String
    | DirtyWorkDate String
    | InvalidWorkDate Message String


from : ClientTime -> WorkDate
from clientTime =
    ClientTime.toString clientTime
        |> String.slice 0 10
        |> FormattedWorkDate


toYearMonth : WorkDate -> YearMonth
toYearMonth workDate =
    let
        ( zone, posix ) =
            toPosix workDate
    in
    PosixYearMonth zone posix


toDayText : WorkDate -> String
toDayText workDate =
    let
        dayOfMonth =
            toDayOfMonth workDate

        dayOfWeek =
            toDayOfWeek workDate
    in
    dayOfMonth ++ "(" ++ dayOfWeek ++ ")"


toDayOfMonth : WorkDate -> String
toDayOfMonth workDate =
    case workDate of
        FormattedWorkDate _ ->
            let
                ( zone, posix ) =
                    toPosix workDate
            in
            PosixWorkDate zone posix |> toDayOfMonth

        PosixWorkDate zone posix ->
            Time.toDay zone posix |> String.fromInt

        _ ->
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

        _ ->
            ""


decoder : Decoder WorkDate
decoder =
    string |> andThen (\str -> succeed (FormattedWorkDate str))


encode : WorkDate -> Json.Encode.Value
encode workDate =
    Json.Encode.string (toString workDate)


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

        DirtyWorkDate value ->
            value

        _ ->
            ""


mandatory : Message
mandatory =
    ErrorMessage "勤務日を入力してください"


validate : WorkDate -> WorkDate
validate workDate =
    case workDate of
        DirtyWorkDate value ->
            if value == "" then
                InvalidWorkDate mandatory value

            else
                FormattedWorkDate value

        EmptyWordDate ->
            InvalidWorkDate mandatory ""

        _ ->
            workDate


isValid : WorkDate -> Bool
isValid workDate =
    case workDate of
        InvalidWorkDate _ _ ->
            False

        _ ->
            True


errorMessage : WorkDate -> String
errorMessage workDate =
    case workDate of
        InvalidWorkDate message _ ->
            Message.toString message

        _ ->
            ""


isSaturday : WorkDate -> Bool
isSaturday workDate =
    toDayOfWeek workDate == "土"


isSunday : WorkDate -> Bool
isSunday workDate =
    toDayOfWeek workDate == "日"



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

        DirtyWorkDate value ->
            FormattedWorkDate value |> toPosix

        _ ->
            ( utc, partsToPosix utc (Parts 1970 Time.Jan 1 0 0 0 0) )
