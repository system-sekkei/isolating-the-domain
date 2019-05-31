module Types.Message exposing (Message(..), errorMessageDecoder, isError, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type Message
    = EmptyMessage
    | InfoMessage String
    | WarningMessage String
    | ErrorMessage String


errorMessageDecoder : Decoder Message
errorMessageDecoder =
    string |> andThen (\str -> succeed (ErrorMessage str))


isError : Message -> Bool
isError message =
    case message of
        ErrorMessage _ ->
            True

        _ ->
            False


toString : Message -> String
toString message =
    case message of
        InfoMessage value ->
            value

        WarningMessage value ->
            value

        ErrorMessage value ->
            value

        EmptyMessage ->
            ""
