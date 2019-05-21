module Types.Timerecord.BreakTime exposing (BreakTime(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type BreakTime
    = EmptyBreakTime
    | FormattedBreakTime String


decoder : Decoder BreakTime
decoder =
    string |> andThen (\str -> succeed (FormattedBreakTime str))


toString : BreakTime -> String
toString breakTime =
    case breakTime of
        FormattedBreakTime value ->
            value

        EmptyBreakTime ->
            ""
