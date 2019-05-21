module Types.Timerecord.WorkTime exposing (WorkTime(..), decoder, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type WorkTime
    = EmptyWorkTime
    | FormattedWorkTime String


decoder : Decoder WorkTime
decoder =
    string |> andThen (\str -> succeed (FormattedWorkTime str))


toString : WorkTime -> String
toString workTime =
    case workTime of
        FormattedWorkTime value ->
            value

        EmptyWorkTime ->
            ""
