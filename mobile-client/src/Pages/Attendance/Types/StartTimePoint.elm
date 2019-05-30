module Pages.Attendance.Types.StartTimePoint exposing (StartTimePoint(..), decoder, isEmpty, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type StartTimePoint
    = EmptyStartTimePoint
    | FormattedStartTimePoint String


decoder : Decoder StartTimePoint
decoder =
    string |> andThen (\str -> succeed (FormattedStartTimePoint str))


toString : StartTimePoint -> String
toString startTimePoint =
    case startTimePoint of
        FormattedStartTimePoint value ->
            value

        EmptyStartTimePoint ->
            ""


isEmpty : StartTimePoint -> Bool
isEmpty startTimePoint =
    case startTimePoint of
        EmptyStartTimePoint ->
            True

        _ ->
            False
