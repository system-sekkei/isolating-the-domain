module Types.Timerecord.WorkDate exposing (WorkDate(..), decoder, from, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)
import Types.Time.ClientTime as ClientTime exposing (ClientTime)


type WorkDate
    = EmptyWordDate
    | FormattedWorkDate String


from : ClientTime -> WorkDate
from clientTime =
    ClientTime.toString clientTime
        |> String.slice 0 9
        |> FormattedWorkDate


decoder : Decoder WorkDate
decoder =
    string |> andThen (\str -> succeed (FormattedWorkDate str))


toString : WorkDate -> String
toString workDate =
    case workDate of
        FormattedWorkDate value ->
            value

        EmptyWordDate ->
            ""
