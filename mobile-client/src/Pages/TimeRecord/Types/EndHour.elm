module Pages.TimeRecord.Types.EndHour exposing (EndHour(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type EndHour
    = EndHour Int
    | DirtyEndHour String
    | EmptyEndHour


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

        EmptyEndHour ->
            ""
