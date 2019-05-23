module Pages.TimeRecord.Types.EndMinute exposing (EndMinute(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type EndMinute
    = EndMinute Int
    | DirtyEndMinute String
    | EmptyEndMinute


parse : String -> EndMinute
parse string =
    String.toInt string
        |> Maybe.map EndMinute
        |> Maybe.withDefault EmptyEndMinute


decoder : Decoder EndMinute
decoder =
    string |> andThen (\str -> succeed (parse str))


toString : EndMinute -> String
toString breakTime =
    case breakTime of
        EndMinute value ->
            String.fromInt value

        DirtyEndMinute value ->
            value

        EmptyEndMinute ->
            ""
