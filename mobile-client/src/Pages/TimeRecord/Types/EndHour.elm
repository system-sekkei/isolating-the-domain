module Pages.TimeRecord.Types.EndHour exposing (EndHour(..), decoder, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Types.Message exposing (Message(..))


type EndHour
    = EmptyEndHour
    | EndHour Int
    | DirtyEndHour String
    | InvalidEndHour Message String


parse : String -> EndHour
parse string =
    String.toInt string
        |> Maybe.map EndHour
        |> Maybe.withDefault EmptyEndHour


decoder : Decoder EndHour
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : EndHour -> String
toString hour =
    case hour of
        EndHour value ->
            String.fromInt value

        DirtyEndHour value ->
            value

        InvalidEndHour _ value ->
            value

        EmptyEndHour ->
            ""


toInt : EndHour -> Int
toInt endHour =
    case endHour of
        EndHour value ->
            value

        DirtyEndHour value ->
            parse value |> toInt

        InvalidEndHour _ value ->
            parse value |> toInt

        EmptyEndHour ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "終了時刻（時）は数値で入力してください"


mandatory : Message
mandatory =
    ErrorMessage "終了時刻（時）を入力してください"


rangMismatch : Message
rangMismatch =
    ErrorMessage "終了時刻（時）は 0〜30 の範囲で入力してください"


validate : EndHour -> EndHour
validate endHour =
    case endHour of
        DirtyEndHour value ->
            case String.toInt value of
                Just intVal ->
                    if intVal < 0 || intVal > 30 then
                        InvalidEndHour rangMismatch value

                    else
                        EndHour intVal

                Nothing ->
                    if value == "" then
                        InvalidEndHour mandatory value

                    else
                        InvalidEndHour typeMismatch value

        EmptyEndHour ->
            InvalidEndHour mandatory ""

        _ ->
            endHour


isValid : EndHour -> Bool
isValid endHour =
    case endHour of
        EndHour _ ->
            True

        _ ->
            False
