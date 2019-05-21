module Types.Time.DayOfWeek exposing (DayOfWeek(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type DayOfWeek
    = EmptyDayOfWeek
    | FormattedDayOfWeek String


decoder : Decoder DayOfWeek
decoder =
    string |> andThen (\str -> succeed (FormattedDayOfWeek str))


toString : DayOfWeek -> String
toString totalWorkTime =
    case totalWorkTime of
        FormattedDayOfWeek value ->
            value

        EmptyDayOfWeek ->
            ""
