module Pages.TimeRecord.Types.StartHour exposing (StartHour(..), decoder, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Types.Message exposing (Message(..))


type StartHour
    = EmptyStartHour
    | StartHour Int
    | DirtyStartHour String
    | InvalidStartHour Message String


parse : String -> StartHour
parse string =
    String.toInt string
        |> Maybe.map StartHour
        |> Maybe.withDefault EmptyStartHour


decoder : Decoder StartHour
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : StartHour -> String
toString startHour =
    case startHour of
        StartHour value ->
            String.fromInt value

        DirtyStartHour value ->
            value

        InvalidStartHour _ value ->
            value

        EmptyStartHour ->
            ""


toInt : StartHour -> Int
toInt startHour =
    case startHour of
        StartHour value ->
            value

        DirtyStartHour value ->
            parse value |> toInt

        InvalidStartHour _ value ->
            parse value |> toInt

        EmptyStartHour ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "開始時刻（時）は数値で入力してください"


mandatory : Message
mandatory =
    ErrorMessage "開始時刻（時）を入力してください"


rangMismatch : Message
rangMismatch =
    ErrorMessage "開始時刻（分）は 0〜30 の範囲で入力してください"


validate : StartHour -> StartHour
validate startHour =
    case startHour of
        DirtyStartHour value ->
            case String.toInt value of
                Just intVal ->
                    if intVal < 0 || intVal > 30 then
                        InvalidStartHour rangMismatch value

                    else
                        StartHour intVal

                Nothing ->
                    if value == "" then
                        InvalidStartHour mandatory value

                    else
                        InvalidStartHour typeMismatch value

        EmptyStartHour ->
            InvalidStartHour mandatory ""

        _ ->
            startHour


isValid : StartHour -> Bool
isValid startHour =
    case startHour of
        StartHour _ ->
            True

        _ ->
            False
