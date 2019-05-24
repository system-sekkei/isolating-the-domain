module Pages.TimeRecord.Types.StartMinute exposing (StartMinute(..), decoder, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, int, string, succeed)
import Types.Message exposing (Message(..))


type StartMinute
    = EmptyStartMinute
    | StartMinute Int
    | DirtyStartMinute String
    | InvalidStartMinute Message String


parse : String -> StartMinute
parse string =
    String.toInt string
        |> Maybe.map StartMinute
        |> Maybe.withDefault EmptyStartMinute


decoder : Decoder StartMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : StartMinute -> String
toString breakTime =
    case breakTime of
        StartMinute value ->
            String.fromInt value

        DirtyStartMinute value ->
            value

        InvalidStartMinute _ value ->
            value

        EmptyStartMinute ->
            ""


toInt : StartMinute -> Int
toInt startMinute =
    case startMinute of
        StartMinute value ->
            value

        DirtyStartMinute value ->
            parse value |> toInt

        InvalidStartMinute _ value ->
            parse value |> toInt

        EmptyStartMinute ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "開始時刻（分）は数値で入力してください"


mandatory : Message
mandatory =
    ErrorMessage "開始時刻（分）を入力してください"


rangMismatch : Message
rangMismatch =
    ErrorMessage "開始時刻（分）は 0〜60 の範囲で入力してください"


validate : StartMinute -> StartMinute
validate startMinute =
    case startMinute of
        DirtyStartMinute value ->
            case String.toInt value of
                Just intVal ->
                    if intVal < 0 || intVal > 60 then
                        InvalidStartMinute rangMismatch value

                    else
                        StartMinute intVal

                Nothing ->
                    if value == "" then
                        InvalidStartMinute mandatory value

                    else
                        InvalidStartMinute typeMismatch value

        EmptyStartMinute ->
            InvalidStartMinute mandatory ""

        _ ->
            startMinute


isValid : StartMinute -> Bool
isValid startMinute =
    case startMinute of
        StartMinute _ ->
            True

        _ ->
            False
