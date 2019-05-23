module Pages.TimeRecord.Types.StartHour exposing (StartHour(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type StartHour
    = StartHour Int
    | DirtyStartHour String
    | EmptyStartHour


parse : String -> StartHour
parse string =
    String.toInt string
        |> Maybe.map StartHour
        |> Maybe.withDefault EmptyStartHour


decoder : Decoder StartHour
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : StartHour -> String
toString hour =
    case hour of
        StartHour value ->
            String.fromInt value

        DirtyStartHour value ->
            value

        EmptyStartHour ->
            ""
