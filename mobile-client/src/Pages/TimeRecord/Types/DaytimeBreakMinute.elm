module Pages.TimeRecord.Types.DaytimeBreakMinute exposing (DaytimeBreakMinute(..), decoder, encode, errorMessage, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Json.Encode
import Types.Message as Message exposing (Message(..))


type DaytimeBreakMinute
    = EmptyDaytimeBreakMinute
    | DaytimeBreakMinute Int
    | DirtyDaytimeBreakMinute String
    | InvalidDaytimeBreakMinute Message String


parse : String -> DaytimeBreakMinute
parse string =
    String.toInt string
        |> Maybe.map DaytimeBreakMinute
        |> Maybe.withDefault EmptyDaytimeBreakMinute


decoder : Decoder DaytimeBreakMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


encode : DaytimeBreakMinute -> Json.Encode.Value
encode daytimeBreakMinute =
    Json.Encode.string (toString daytimeBreakMinute)


toString : DaytimeBreakMinute -> String
toString breakTime =
    case breakTime of
        DaytimeBreakMinute value ->
            String.fromInt value

        DirtyDaytimeBreakMinute value ->
            value

        InvalidDaytimeBreakMinute _ value ->
            value

        EmptyDaytimeBreakMinute ->
            ""


toInt : DaytimeBreakMinute -> Int
toInt breakTime =
    case breakTime of
        DaytimeBreakMinute value ->
            value

        DirtyDaytimeBreakMinute value ->
            parse value |> toInt

        InvalidDaytimeBreakMinute _ value ->
            parse value |> toInt

        EmptyDaytimeBreakMinute ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "休憩時間は数値（分単位）で入力してください"


validate : DaytimeBreakMinute -> DaytimeBreakMinute
validate breakTime =
    case breakTime of
        DirtyDaytimeBreakMinute value ->
            case String.toInt value of
                Just intVal ->
                    DaytimeBreakMinute intVal

                Nothing ->
                    if value == "" then
                        DaytimeBreakMinute 0

                    else
                        InvalidDaytimeBreakMinute typeMismatch value

        EmptyDaytimeBreakMinute ->
            DaytimeBreakMinute 0

        _ ->
            breakTime


isValid : DaytimeBreakMinute -> Bool
isValid breakMinute =
    case breakMinute of
        DaytimeBreakMinute _ ->
            True

        _ ->
            False


errorMessage : DaytimeBreakMinute -> Message
errorMessage daytimeBreakMinute =
    case daytimeBreakMinute of
        InvalidDaytimeBreakMinute message _ ->
            message

        _ ->
            EmptyMessage
