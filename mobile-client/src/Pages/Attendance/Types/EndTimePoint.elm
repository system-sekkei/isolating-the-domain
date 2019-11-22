module Pages.Attendance.Types.EndTimePoint exposing (EndTimePoint(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type EndTimePoint
    = EmptyEndTimePoint
    | FormattedEndTimePoint String


decoder : Decoder EndTimePoint
decoder =
    string |> andThen (\str -> succeed (FormattedEndTimePoint str))


toString : EndTimePoint -> String
toString endTimePoint =
    case endTimePoint of
        FormattedEndTimePoint value ->
            value

        EmptyEndTimePoint ->
            ""
