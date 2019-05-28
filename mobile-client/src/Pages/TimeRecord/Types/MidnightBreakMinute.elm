module Pages.TimeRecord.Types.MidnightBreakMinute exposing (MidnightBreakMinute(..), decoder, encode, isValid, toInt, toString, validate)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Json.Encode
import Types.Message exposing (Message(..))


type MidnightBreakMinute
    = EmptyMidnightBreakMinute
    | MidnightBreakMinute Int
    | DirtyMidnightBreakMinute String
    | InvalidMidnightBreakMinute Message String


parse : String -> MidnightBreakMinute
parse string =
    String.toInt string
        |> Maybe.map MidnightBreakMinute
        |> Maybe.withDefault EmptyMidnightBreakMinute


decoder : Decoder MidnightBreakMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


encode : MidnightBreakMinute -> Json.Encode.Value
encode midnightBreakMinute =
    Json.Encode.string (toString midnightBreakMinute)


toString : MidnightBreakMinute -> String
toString breakTime =
    case breakTime of
        MidnightBreakMinute value ->
            String.fromInt value

        DirtyMidnightBreakMinute value ->
            value

        InvalidMidnightBreakMinute _ value ->
            value

        EmptyMidnightBreakMinute ->
            ""


toInt : MidnightBreakMinute -> Int
toInt breakTime =
    case breakTime of
        MidnightBreakMinute value ->
            value

        DirtyMidnightBreakMinute value ->
            parse value |> toInt

        InvalidMidnightBreakMinute _ value ->
            parse value |> toInt

        EmptyMidnightBreakMinute ->
            0


typeMismatch : Message
typeMismatch =
    ErrorMessage "休憩時間（深夜）は数値（分単位）で入力してください"


validate : MidnightBreakMinute -> MidnightBreakMinute
validate breakTime =
    case breakTime of
        DirtyMidnightBreakMinute value ->
            case String.toInt value of
                Just intVal ->
                    MidnightBreakMinute intVal

                Nothing ->
                    if value == "" then
                        MidnightBreakMinute 0

                    else
                        InvalidMidnightBreakMinute typeMismatch value

        EmptyMidnightBreakMinute ->
            MidnightBreakMinute 0

        _ ->
            breakTime


isValid : MidnightBreakMinute -> Bool
isValid breakMinute =
    case breakMinute of
        MidnightBreakMinute _ ->
            True

        _ ->
            False
