module Pages.TimeRecord.Types.PostedStatus exposing (PostedStatus(..), decoder)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type PostedStatus
    = OK
    | NG


decoder : Decoder PostedStatus
decoder =
    string
        |> andThen
            (\str ->
                if str == "OK" then
                    succeed OK

                else
                    succeed NG
            )


isOK : PostedStatus -> Bool
isOK postedStatus =
    case postedStatus of
        OK ->
            True

        _ ->
            False
