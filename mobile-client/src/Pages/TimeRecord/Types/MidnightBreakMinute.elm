module Pages.TimeRecord.Types.NightBreakMinute exposing (NightBreakMinute(..), decoder, encode, errorMessage, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Json.Encode
import Types.Message as Message exposing (Message(..))


type NightBreakMinute
    = EmptyNightBreakMinute
    | NightBreakMinute Int
    | DirtyNightBreakMinute String
    | InvalidNightBreakMinute Message String


parse : String -> NightBreakMinute
parse string =
    String.toInt string
        |> Maybe.map NightBreakMinute
        |> Maybe.withDefault EmptyNightBreakMinute


decoder : Decoder NightBreakMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


encode : NightBreakMinute -> Json.Encode.Value
encode nightBreakMinute =
    Json.Encode.string (toString nightBreakMinute)


toString : NightBreakMinute -> String
toString breakTime =
    case breakTime of
        NightBreakMinute value ->
            String.fromInt value

        DirtyNightBreakMinute value ->
            value

        InvalidNightBreakMinute _ value ->
            value

        EmptyNightBreakMinute ->
            ""


toInt : NightBreakMinute -> Int
toInt breakTime =
    case breakTime of
        NightBreakMinute value ->
            value

        DirtyNightBreakMinute value ->
            parse value |> toInt

        InvalidNightBreakMinute _ value ->
            parse value |> toInt

        EmptyNightBreakMinute ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "休憩時間（深夜）は数値（分単位）で入力してください"


validate : NightBreakMinute -> NightBreakMinute
validate breakTime =
    case breakTime of
        DirtyNightBreakMinute value ->
            case String.toInt value of
                Just intVal ->
                    NightBreakMinute intVal

                Nothing ->
                    if value == "" then
                        NightBreakMinute 0

                    else
                        InvalidNightBreakMinute typeMismatch value

        EmptyNightBreakMinute ->
            NightBreakMinute 0

        _ ->
            breakTime


isValid : NightBreakMinute -> Bool
isValid breakMinute =
    case breakMinute of
        NightBreakMinute _ ->
            True

        _ ->
            False


errorMessage : NightBreakMinute -> Message
errorMessage nightBreakMinute =
    case nightBreakMinute of
        InvalidNightBreakMinute message _ ->
            message

        _ ->
            EmptyMessage
