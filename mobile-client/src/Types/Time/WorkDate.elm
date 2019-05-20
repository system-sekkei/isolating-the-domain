module Types.Time.WorkDate exposing (WorkDate(..), toString)

import Types.Time.ClientTime as ClientTime exposing (ClientTime)


type WorkDate
    = EmptyWordDate
    | WorkDate String


from : ClientTime -> WorkDate
from clientTime =
    ClientTime.toString clientTime
        |> String.slice 0 9
        |> WorkDate


toString : WorkDate -> String
toString workDate =
    case workDate of
        WorkDate value ->
            value

        EmptyWordDate ->
            ""
