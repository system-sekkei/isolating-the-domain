module Types.Message exposing (Message(..), errorMessageDecoder, isNotEmpty, toString)

import Json.Decode exposing (Decoder, andThen, string, succeed)


type Message
    = EmptyMessage
    | InfoMessage String
    | WarningMessage String
    | ErrorMessage String


errorMessageDecoder : Decoder Message
errorMessageDecoder =
    string |> andThen (\str -> succeed (ErrorMessage str))


isNotEmpty : Message -> Bool
isNotEmpty message =
    case message of
        EmptyMessage ->
            False

        _ ->
            True


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
