module Pages.TimeRecord.Types.StartMinute exposing (StartMinute(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, int, string, succeed)


type StartMinute
    = StartMinute Int
    | DirtyStartMinute String
    | EmptyStartMinute


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

        EmptyStartMinute ->
            ""
