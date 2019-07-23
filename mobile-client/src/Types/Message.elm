module Types.Message exposing (Message(..), isError, toString)


type Message
    = EmptyMessage
    | InfoMessage String
    | WarningMessage String
    | ErrorMessage String


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
