module Pages.TimeRecord.Types.EndMinute exposing (EndMinute(..), decoder, encode, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Json.Encode
import Types.Message exposing (Message(..))


type EndMinute
    = EmptyEndMinute
    | EndMinute Int
    | DirtyEndMinute String
    | InvalidEndMinute Message String


parse : String -> EndMinute
parse string =
    String.toInt string
        |> Maybe.map EndMinute
        |> Maybe.withDefault EmptyEndMinute


decoder : Decoder EndMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


encode : EndMinute -> Json.Encode.Value
encode endMinute =
    Json.Encode.string (toString endMinute)


toString : EndMinute -> String
toString endMinute =
    case endMinute of
        EndMinute value ->
            String.fromInt value

        DirtyEndMinute value ->
            value

        InvalidEndMinute _ value ->
            value

        EmptyEndMinute ->
            ""


toInt : EndMinute -> Int
toInt endMinute =
    case endMinute of
        EndMinute value ->
            value

        DirtyEndMinute value ->
            parse value |> toInt

        InvalidEndMinute _ value ->
            parse value |> toInt

        EmptyEndMinute ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "終了時刻（分）は数値で入力してください"


mandatory : Message
mandatory =
    ErrorMessage "終了時刻（分）を入力してください"


rangMismatch : Message
rangMismatch =
    ErrorMessage "終了時刻（分）は 0〜60 の範囲で入力してください"


validate : EndMinute -> EndMinute
validate endMinute =
    case endMinute of
        DirtyEndMinute value ->
            case String.toInt value of
                Just intVal ->
                    if intVal < 0 || intVal > 60 then
                        InvalidEndMinute rangMismatch value

                    else
                        EndMinute intVal

                Nothing ->
                    if value == "" then
                        InvalidEndMinute mandatory value

                    else
                        InvalidEndMinute typeMismatch value

        EmptyEndMinute ->
            InvalidEndMinute mandatory ""

        _ ->
            endMinute


isValid : EndMinute -> Bool
isValid endMinute =
    case endMinute of
        EndMinute _ ->
            True

        _ ->
            False
