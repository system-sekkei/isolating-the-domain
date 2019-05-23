module Pages.TimeRecord.Types.MidnightBreakMinute exposing (MidnightBreakMinute(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type MidnightBreakMinute
    = MidnightBreakMinute Int
    | DirtyMidnightBreakMinute String
    | EmptyMidnightBreakMinute


parse : String -> MidnightBreakMinute
parse string =
    String.toInt string
        |> Maybe.map MidnightBreakMinute
        |> Maybe.withDefault EmptyMidnightBreakMinute


decoder : Decoder MidnightBreakMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : MidnightBreakMinute -> String
toString breakTime =
    case breakTime of
        MidnightBreakMinute value ->
            String.fromInt value

        DirtyMidnightBreakMinute value ->
            value

        EmptyMidnightBreakMinute ->
            ""
