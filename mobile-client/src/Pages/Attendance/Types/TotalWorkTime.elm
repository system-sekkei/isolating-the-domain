module Pages.Attendance.Types.TotalWorkTime exposing (TotalWorkTime(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type TotalWorkTime
    = EmptyTotalWorkTime
    | FormattedTotalWorkTime String


decoder : Decoder TotalWorkTime
decoder =
    string |> andThen (\str -> succeed (FormattedTotalWorkTime str))


toString : TotalWorkTime -> String
toString totalWorkTime =
    case totalWorkTime of
        FormattedTotalWorkTime value ->
            value

        EmptyTotalWorkTime ->
            ""
