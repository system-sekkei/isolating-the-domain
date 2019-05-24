module Types.Message exposing (Message(..), isNotEmpty, toString)


type Message
    = EmptyMessage
    | InfoMessage String
    | WarningMessage String
    | ErrorMessage String


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
