module Pages.TimeRecord.Types.DaytimeBreakMinute exposing (DaytimeBreakMinute(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type DaytimeBreakMinute
    = DaytimeBreakMinute Int
    | DirtyDaytimeBreakMinute String
    | EmptyDaytimeBreakMinute


parse : String -> DaytimeBreakMinute
parse string =
    String.toInt string
        |> Maybe.map DaytimeBreakMinute
        |> Maybe.withDefault EmptyDaytimeBreakMinute


decoder : Decoder DaytimeBreakMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : DaytimeBreakMinute -> String
toString breakTime =
    case breakTime of
        DaytimeBreakMinute value ->
            String.fromInt value

        DirtyDaytimeBreakMinute value ->
            value

        EmptyDaytimeBreakMinute ->
            ""
